package com.his.dc.entity;

import jakarta.persistence.*;
import lombok.Data;


    @Data
    @Entity
    @Table(name = "DC_PLANS")
    public class DcPlanEntity {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        @Column(name = "PLAN_ID")
        private Long planId;

        @Column(name = "CASE_NUM")
        private Long caseNum;

        @Column(name = "PLAN_NAME")
        private String planName;
    }


