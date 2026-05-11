package com.his.dc.entity;

import jakarta.persistence.*;
import lombok.Data;

    @Data
    @Entity
    @Table(name = "DC_CASES")
    public class DcCaseEntity {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        @Column(name = "CASE_NUM")
        private Long caseNum;

        @Column(name = "APP_ID")
        private Long appId;

        @Column(name = "FNAME")
        private String fName;

        @Column(name = "LNAME")
        private String lName;

        @Column(name = "PLAN_NAME")
        private String planName;

    }

