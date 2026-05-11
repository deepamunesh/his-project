package com.his.correspondence.entity;

//public class CoTriggerEntity {

    //package com.his.co.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
    @Data
    @Entity
    @Table(name = "CO_TRIGGERS")
    public class CoTriggerEntity {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Integer triggerId;

        private Long caseNum;

        private String triggerType;   // MISSING_DOC, ELIG_RESULT, RENEWAL
        private String noticeText;
        private String status;        // PENDING, SENT, FAILED
        private String planName;
        private String docType;

        private LocalDateTime createdDate = LocalDateTime.now();
        private LocalDateTime processedDate;

        // getters & setters
    }






//}
