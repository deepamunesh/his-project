package com.his.reports.service;

import java.io.File;
import java.time.LocalDate;
import java.time.LocalDateTime;

public interface ReportService {
    File generateDailyReport(LocalDate date);
    File generatePlanWiseReport(String planName);
    File generateStatusWiseReport(String status);
    File generateDateRangeReport(LocalDate startDate, LocalDate endDate);
}
