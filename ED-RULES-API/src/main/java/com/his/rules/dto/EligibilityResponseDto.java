package com.his.rules.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class EligibilityResponseDto {

    private String planStatus;        // Approved / Denied
    private LocalDateTime planStartDate;
    private LocalDateTime planEndDate;
    private Integer benefitAmt;
    private String denialReason;
    private String planName;


}
