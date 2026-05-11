package com.his.dc.dto;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Data;

import java.util.List;

@JsonPropertyOrder({
        "planId",
        "caseNum",
        "planName",
        "income",
        "Education",
         "children"
})
@Data
public class DcPlanDto {
    private Long planId;
    private Long caseNum;
    private String planName;
    private DcIncomeDto income;
    private DcEducationDto Education;
    private List<DcChildDto> children;
}
