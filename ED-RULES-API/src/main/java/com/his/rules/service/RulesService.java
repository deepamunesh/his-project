package com.his.rules.service;

import com.his.rules.dto.CitizenDataDto;
import com.his.rules.dto.EligibilityResponseDto;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class RulesService {

    public EligibilityResponseDto evaluate(CitizenDataDto data) {
        EligibilityResponseDto response = new EligibilityResponseDto();
        response.setPlanName(data.getPlanName());

        switch (data.getPlanName().toUpperCase()) {
            case "SNAP":
                if (data.getAnnualIncome() < 30000) {
                    response.setPlanStatus("Approved");
                    response.setPlanStartDate(LocalDateTime.now());
                    response.setPlanEndDate(LocalDateTime.now().plusMonths(6));
                    response.setBenefitAmt(5000);
                    response.setDenialReason("Citizen eligible for SNAP"); // ✅ default message
                } else {
                    response.setPlanStatus("Denied");
                    response.setDenialReason("Income too high for SNAP");
                    response.setBenefitAmt(0); // ✅ default
                    response.setPlanStartDate(LocalDateTime.now()); // ✅ default
                    response.setPlanEndDate(LocalDateTime.now());   // ✅ default
                }
                break;

            case "CCAP":
                if (data.getKidsCount() != null && data.getKidsCount() > 0) {
                    response.setPlanStatus("Approved");
                    response.setPlanStartDate(LocalDateTime.now());
                    response.setPlanEndDate(LocalDateTime.now().plusMonths(12));
                    response.setBenefitAmt(8000);
                } else {
                    response.setPlanStatus("Denied");
                    response.setDenialReason("no childern for ccap plan");
                }
                break;
            case "MEDICAID":
                if (data.getAge() > 60 || data.getAnnualIncome() < 20000) {
                    response.setPlanStatus("Approved");
                    response.setPlanStartDate(LocalDateTime.now());
                    response.setPlanEndDate(LocalDateTime.now().plusMonths(12));
                    response.setBenefitAmt(10000);
                    response.setDenialReason("Citizen eligible for MEDICAID");
                } else {
                    response.setPlanStatus("Denied");
                    response.setDenialReason("Not meeting Medicaid criteria");
                }
                break;
            case "MEDICARE":
                if (data.getAge() >= 65) {
                    response.setPlanStatus("Approved");
                    response.setPlanStartDate(LocalDateTime.now());
                    response.setPlanEndDate(LocalDateTime.now().plusMonths(6));
                    response.setBenefitAmt(7000);
                    response.setDenialReason("Citizen eligible for MEDICARE");
                } else {
                    response.setPlanStatus("Denied");
                    response.setDenialReason("Citizen is employ");
                    response.setBenefitAmt(0);
                    response.setPlanStartDate(LocalDateTime.now());
                    response.setPlanEndDate(LocalDateTime.now());
                }
                break;
            case "QHP":
                if ("Graduate".equalsIgnoreCase(data.getQualification()) ||
                        "postGraduate".equalsIgnoreCase(data.getQualification())) {
                    response.setPlanStatus("Approved");
                    response.setPlanStartDate(LocalDateTime.now());
                    response.setPlanEndDate(LocalDateTime.now());
                    response.setBenefitAmt(15000);
                    response.setDenialReason("Citizen eligible for QHP");

                } else {
                    response.setPlanStatus("Denied");
                    response.setDenialReason("Education criteria not met for Qhp");
                    response.setBenefitAmt(0);
                    response.setPlanStartDate(LocalDateTime.now());
                    response.setPlanEndDate(LocalDateTime.now());
                }
                break;
            default:
                response.setPlanStatus("Denied");
                response.setDenialReason("Unknow plan type");

        }
        return response;
    }

    }
