package com.his.correspondence.controller;

import com.his.correspondence.entity.CoTriggerEntity;
import com.his.correspondence.service.CorrespondenceService;
import com.his.correspondence.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/co")
public class CorrespondenceController {

    @Autowired
    private CorrespondenceService service;

    // endpoint to insert trigger
    @PostMapping("/trigger")
    public ResponseEntity<String> createTrigger(@RequestBody CoTriggerEntity trigger) {
        service.insertTrigger(trigger);
        return ResponseEntity.ok("Trigger created successfully!");
    }

    // endpoint to process triggers (could be scheduled/async in real-time)
    @PostMapping("/process")
    public ResponseEntity<String> processTriggers() {
        int processedCount = service.processTriggers();
        return ResponseEntity.ok(processedCount + " triggers processed!");
    }
}
