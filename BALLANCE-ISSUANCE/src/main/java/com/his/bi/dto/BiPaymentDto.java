package com.his.bi.dto;

import lombok.Data;
import java.time.LocalDate;

@Data
public class BiPaymentDto {
    private Long paymentId;
    private Long caseNum;
    private Integer eligId;
    private String planName;
    private Integer benefitAmt;
    private LocalDate paymentDate;
    private String status;
    private String fileName;
}
