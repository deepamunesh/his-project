package com.his.reports.repository;

import com.his.reports.entity.EdEligEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface EdEligRepository extends JpaRepository<EdEligEntity, Integer> {

        List<EdEligEntity> findByPlanStartDateBetween(LocalDateTime start, LocalDateTime end);
        List<EdEligEntity> findByPlanStatus(String status);
        List<EdEligEntity> findByPlanName(String planName);
}
