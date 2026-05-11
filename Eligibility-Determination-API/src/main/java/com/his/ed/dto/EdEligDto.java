package com.his.ed.dto;

//public class EdEligDto {

import lombok.Data;

import java.time.LocalDateTime;
 @Data
public class EdEligDto {
        private Long caseNum;
        private String planStatus;
        private LocalDateTime planStartDate;
        private LocalDateTime planEndDate;
        private Integer benefitAmt;
        private String denialReason;
        private String planName;
        // getters & setters
    }

//}
