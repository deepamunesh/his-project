package com.his.dc.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

//public class DcEducationEntity {
    @Data
    @Entity
    @Table(name = "DC_EDUCATION_DTLS")
    public class DcEducationEntity {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        @Column(name = "EDU_ID")
        private Long eduId;

        @Column(name = "CASE_NUM")
        private Long caseNum;   // FK to DC_CASES

        @Column(name = "QUALIFICATION")
        private String qualification; // e.g. High School, Graduate, Postgraduate

        @Column(name = "YEAR_OF_PASSING")
        private Integer yearOfPassing;

        @Column(name = "UNIVERSITY_NAME")
        private String universityName;

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
