package com.his.reports.entity;

//public class EdEligEntity {

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "ed_elig_dtls")
public class EdEligEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ELIG_ID")
    private Integer eligId;

    @Column(name = "CASE_NUM")
    private Long caseNum;

    @Column(name = "PLAN_STATUS")
    private String planStatus;

    @Column(name = "PLAN_START_DATE")
    private LocalDateTime planStartDate;

    @Column(name = "PLAN_END_DATE")
    private LocalDateTime planEndDate;

    @Column(name = "BENEFIT_AMT")
    private Integer benefitAmt;

    @Column(name = "DENIAL_REASON")
    private String denialReason;

    @Column(name = "PLAN_NAME")
    private String planName;

    // getters & setters
}

//}
