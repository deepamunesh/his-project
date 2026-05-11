package com.his.reports.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "REPORT_LOGS")
public class ReportLogEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "REPORT_ID")
    private Long reportId;

    @Column(name = "REPORT_TYPE")
    private String reportType;

    @Column(name = "GENERATED_DATE")
    private LocalDateTime generatedDate;

    @Column(name = "FILE_NAME")
    private String fileName;

    @Column(name = "STATUS")
    private String status;
}
