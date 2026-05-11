package com.his.reports.repository;

import com.his.reports.entity.ReportLogEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReportLogRepository extends JpaRepository<ReportLogEntity, Long> {
}
