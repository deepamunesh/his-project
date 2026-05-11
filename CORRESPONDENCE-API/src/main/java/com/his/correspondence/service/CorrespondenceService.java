package com.his.correspondence.service;

import com.his.correspondence.entity.CoTriggerEntity;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;
import com.his.correspondence.repository.CoTriggerRepository;


import java.time.LocalDateTime;
import java.util.List;

@Service
public class CorrespondenceService {

    @Autowired
    private CoTriggerRepository repo;

    @Autowired
    private EmailService emailService;

    public int processTriggers() {
        List<CoTriggerEntity> pendingTriggers = repo.findByStatus("PENDING");
        int count = 0;

        for (CoTriggerEntity trigger : pendingTriggers) {
            try {
                String subject = "HIS Notice - " + trigger.getTriggerType();
                String body = buildNoticeBody(trigger);

                // Fetch citizen email dynamically via join query
                String citizenEmail = repo.findCitizenEmailByCaseNum(trigger.getCaseNum());

                boolean sent = emailService.sendMail(citizenEmail, subject, body);

                if (sent) {
                    trigger.setStatus("SENT");
                    trigger.setNoticeText(body);
                    trigger.setProcessedDate(LocalDateTime.now());
                } else {
                    trigger.setStatus("FAILED");
                }
                repo.save(trigger);
                count++;

            } catch (Exception e) {
                trigger.setStatus("FAILED");
                repo.save(trigger);
            }
        }
        return count;
    }

    private String buildNoticeBody(CoTriggerEntity trigger) {
        switch (trigger.getTriggerType()) {
            case "MISSING_DOC":
                return "Dear Citizen, please submit: " + trigger.getDocType();
            case "ELIG_RESULT":
                return "Your plan " + trigger.getPlanName() +
                        " eligibility result: " + trigger.getStatus();
            case "RENEWAL":
                return "Your plan " + trigger.getPlanName() + " is due for renewal.";
            default:
                return "Notice regarding your case.";
        }
    }



    //insert trigger(called by ed/dc modules)
    public void insertTrigger(CoTriggerEntity trigger) {
        trigger.setStatus("PENDING");
        repo.save(trigger);
    }
}
