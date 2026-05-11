package com.his.ed.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

//public class DcChildEntity {
    @Data
    @Entity
    @Table(name = "DC_CHILDS")
    public class DcChildEntity {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        @Column(name = "CHILD_ID")
        private Long childId;

        @Column(name = "CASE_NUM")
        private Long caseNum;   // FK to DC_CASES

        @Column(name = "CHILD_NAME")
        private String childName;

        @Column(name = "CHILD_DOB")
        private LocalDate childDob;

        @Column(name = "GENDER")
        private String gender;

        @Column(name = "SCHOOL_NAME")
        private String schoolName;

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
