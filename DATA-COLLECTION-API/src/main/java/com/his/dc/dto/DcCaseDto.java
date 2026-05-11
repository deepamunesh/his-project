package com.his.dc.dto;

import lombok.Data;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonPropertyOrder({
        "appId",
        "fName",
        "lName",
        "planName",
        "income",
        "education",
        "children"
})
    @Data
    public class DcCaseDto {
        private Long appId;          // FK to APP_REG_DTLS
        private String fName;
        private String lName;
        private String planName;     // selected plan
        private DcIncomeDto income;
        private DcEducationDto education;
        private List<DcChildDto> children;
    }


