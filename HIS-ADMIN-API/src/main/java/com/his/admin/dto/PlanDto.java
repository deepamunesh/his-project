package com.his.admin.dto;

import lombok.Data;

import java.time.LocalDateTime;
@Data
public class PlanDto {
    private Integer planId;
    private String planName;
    private String activeSw;
    private LocalDateTime startDt;
    private LocalDateTime endDt;

//    public Integer getPlanId() {
//        return planId;
//    }
//
//    public void setPlanId(Integer planId) {
//        this.planId = planId;
//    }
//
//    public String getPlanName() {
//        return planName;
//    }
//
//    public void setPlanName(String planName) {
//        this.planName = planName;
//    }
//
//    public LocalDateTime getStartDt() {
//        return startDt;
//    }
//
//    public void setStartDt(LocalDateTime startDt) {
//        this.startDt = startDt;
//    }
//
//    public LocalDateTime getEndDt() {
//        return endDt;
//    }
//
//    public void setEndDt(LocalDateTime endDt) {
//        this.endDt = endDt;
//    }
}
