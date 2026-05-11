package com.his.bi.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDate;

@Data
@Entity
@Table(name = "BI_PAYMENTS")
public class BiPaymentEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "PAYMENT_ID")
    private Long paymentId;

    @Column(name = "CASE_NUM")
    private Long caseNum;

    @Column(name = "ELIG_ID")
    private Integer eligId;

    @Column(name = "PLAN_NAME")
    private String planName;

    @Column(name = "BENEFIT_AMT")
    private Integer benefitAmt;

    @Column(name = "PAYMENT_DATE")
    private LocalDate paymentDate;

    @Column(name = "STATUS")
    private String status;

    @Column(name = "FILE_NAME")
    private String fileName;
}
