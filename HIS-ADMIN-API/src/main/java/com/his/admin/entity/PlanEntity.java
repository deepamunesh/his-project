package com.his.admin.entity;

import jakarta.persistence.Column;
import java.time.LocalDateTime;
import jakarta.persistence.*;
import lombok.Data;


    @Entity
    @Table(name="PLAN_DTLS")
    @Data
    public class PlanEntity {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        @Column(name="PLAN_ID")
        private Integer planId;

        @Column(name="PLAN_NAME")
        private String planName;

        @Column(name="ACTIVE_SW")
        private String activeSw;

        @Column(name="START_DT")
        private LocalDateTime startDt;

        @Column(name="END_DT")
        private LocalDateTime endDt;

        @Column(name = "CREATED_DT")
        private LocalDateTime createdDt;

        @Column(name="CREATED_BY")
        private String createdBy;

        @Column(name="UPDATED_DT")
        private LocalDateTime updatedDt;

        @Column(name="UPDATED_BY")
        private String updatedBy;

//        @Column(name="DELETED_SW")
//        private String deletedSw;

        public Integer getPlanId() {
            return planId;
        }

        public void setPlanId(Integer planId) {
            this.planId = planId;
        }

        public String getPlanName() {
            return planName;
        }

        public void setPlanName(String planName) {
            this.planName = planName;
        }

        public String getActiveSw() {
            return activeSw;
        }

        public void setActiveSw(String activeSw) {
            this.activeSw = activeSw;
        }

        public LocalDateTime getStartDt() {
            return startDt;
        }

        public void setStartDt(LocalDateTime startDt) {
            this.startDt = startDt;
        }

        public LocalDateTime getEndDt() {
            return endDt;
        }

        public void setEndDt(LocalDateTime endDt) {
            this.endDt = endDt;
        }

        public LocalDateTime getCreatedDt() {
            return createdDt;
        }

        public void setCreatedDt(LocalDateTime createdDt) {
            this.createdDt = createdDt;
        }

        public String getCreatedBy() {
            return createdBy;
        }

        public void setCreatedBy(String createdBy) {
            this.createdBy = createdBy;
        }

        public LocalDateTime getUpdatedDt() {
            return updatedDt;
        }

        public void setUpdatedDt(LocalDateTime updatedDt) {
            this.updatedDt = updatedDt;
        }

        public String getUpdatedBy() {
            return updatedBy;
        }

        public void setUpdatedBy(String updatedBy) {
            this.updatedBy = updatedBy;
        }
    }


