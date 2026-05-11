package com.his.ssa.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.time.LocalDate;

@Data
public class SsaResponse {
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private LocalDate dob;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String firstName;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String gender;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String lastName;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String ssnNo;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String status;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String stateName;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String phNo;
    private Boolean valid;

    public SsaResponse() {}

    public SsaResponse(String status, String stateName) {
        this.status = status;
        this.stateName = stateName;
    }
}