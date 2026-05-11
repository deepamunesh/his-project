package com.his.ed.service;

import com.his.ed.dto.CitizenDataDto;
import com.his.ed.dto.EdEligDto;
import com.his.ed.entity.*;
import com.his.ed.reposirory.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class EdService {

    @Autowired
    private EdEligRepository eligRepo;

    @Autowired
    private DcCaseRepository caseRepo;
    @Autowired
    private DcIncomeRepository incomeRepo;
    @Autowired
    private DcChildRepository childRepo;
    @Autowired
    private DcEducationRepository educationRepo;
    @Autowired
    private RestTemplate restTemplate;

    public EdEligDto determineEligibility(Long caseNum) {
        // 1. Collect citizen data from DC tables
        DcCaseEntity caseEntity = caseRepo.findById(caseNum)
                .orElseThrow(() -> new RuntimeException("Case not found"));

        DcIncomeEntity income = incomeRepo.findByCaseNum(caseNum);
        List<DcChildEntity> children = childRepo.findByCaseNum(caseNum);
        DcEducationEntity education = educationRepo.findByCaseNum(caseNum);

        CitizenDataDto citizenData = new CitizenDataDto();
        citizenData.setCaseNum(caseNum);
        citizenData.setPlanName(caseEntity.getPlanName());
        citizenData.setAnnualIncome(income != null ? income.getAnnualIncome() : 0.0);
        citizenData.setOtherIncome(income != null ? income.getOtherIncome() : 0.0);
        citizenData.setEmploymentStatus(income != null ? income.getEmploymentStatus() : "Unknown");
        citizenData.setKidsCount(children != null ? children.size() : 0);
        citizenData.setQualification(education != null ? education.getQualification() : "NotAvailable");


        System.out.println("CitizenDataDto  ::::: "+citizenData);
        //citizenData.setAge(caseEntity.getAge());
        //citizenData.setGender(caseEntity.getGender());

        // 2. Call ED-RULES-API
        String rulesApiUrl = "http://localhost:8085/ed-rules/check";
        EdEligDto response = restTemplate.postForObject(rulesApiUrl, citizenData, EdEligDto.class);
        response.setCaseNum(caseNum);


        // 3. Save result in ed_elig_dtls
        EdEligEntity entry = new EdEligEntity();
        entry.setCaseNum(caseNum);
        entry.setPlanStatus(response.getPlanStatus());
        entry.setPlanStartDate(response.getPlanStartDate());
        entry.setPlanEndDate(response.getPlanEndDate());
        entry.setBenefitAmt(response.getBenefitAmt());
        entry.setDenialReason(response.getDenialReason());
        entry.setPlanName(response.getPlanName());
        eligRepo.save(entry);

        return response;
    }

    public EdEligDto getEligibility(Long caseNum) {
        EdEligEntity entry = eligRepo.findByCaseNum(caseNum);
        if (entry == null) {
            throw new RuntimeException("Eligibility not found for case: " + caseNum);
        }
        EdEligDto dto = new EdEligDto();
        dto.setCaseNum(entry.getCaseNum());
        dto.setPlanStatus(entry.getPlanStatus());
        dto.setPlanStartDate(entry.getPlanStartDate());
        dto.setPlanEndDate(entry.getPlanEndDate());
        dto.setBenefitAmt(entry.getBenefitAmt());
        dto.setDenialReason(entry.getDenialReason());
        dto.setPlanName(entry.getPlanName());
        return dto;
    }
}







