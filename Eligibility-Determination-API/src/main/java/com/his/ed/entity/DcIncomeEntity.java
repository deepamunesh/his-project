package com.his.ed.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

//public class DcIncomeEntity {
     @Data
    @Entity
    @Table(name = "DC_INCOME_DTLS")
    public class DcIncomeEntity {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        @Column(name = "INCOME_ID")
        private Long incomeId;

        @Column(name = "CASE_NUM")
        private Long caseNum;   // FK to DC_CASES

        @Column(name = "ANNUAL_INCOME")
        private Double annualIncome;

        @Column(name = "OTHER_INCOME")
        private Double otherIncome;

        @Column(name = "EMPLOYMENT_STATUS")
        private String employmentStatus; // e.g. Employed, Self-Employed, Unemployed

        @Column(name = "CREATED_DT")
        private LocalDateTime createdDt;

        @Column(name = "CREATED_BY")
        private String createdBy;

        @Column(name = "UPDATED_DT")
        private LocalDateTime updatedDt;

        @Column(name = "UPDATED_BY")
        private String updatedBy;
    }

//}
