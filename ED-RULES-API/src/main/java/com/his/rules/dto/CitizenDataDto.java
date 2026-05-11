package com.his.rules.dto;

import lombok.Data;

@Data
public class CitizenDataDto {

    private Long caseNum;
    private String planName;
    private Double annualIncome;
    private Double otherIncome;
    private String employmentStatus;
    private Integer kidsCount;
    private String qualification;
    private Integer age;
    private String gender;
}
