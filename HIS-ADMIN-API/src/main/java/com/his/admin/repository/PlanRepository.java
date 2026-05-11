package com.his.admin.repository;

import com.his.admin.dto.PlanDto;
import com.his.admin.entity.PlanEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
public interface PlanRepository extends JpaRepository<PlanEntity, Long> {

    Optional<PlanEntity> findByPlanId(Integer planId);

    @Query("SELECT p FROM PlanEntity p")
    Page<PlanEntity> findAllActivePlans(Pageable pageable);

    @Query("SELECT p FROM PlanEntity p WHERE p.activeSw = 'Y'")
    Page<PlanEntity> findActivePlans(Pageable pageable);

    @Query("SELECT p FROM PlanEntity p WHERE p.planName LIKE %:keyword%")
    Page<PlanEntity> searchPlans(@Param("keyword") String keyword, Pageable pageable);

    @Query("SELECT p FROM PlanEntity p WHERE p.endDt BETWEEN :today AND :expiryDate")
    List<PlanEntity> findExpiringPlans(@Param("today") LocalDate today, @Param("expiryDate") LocalDate expiryDate);
}

//@Repository
//public interface PlanRepository extends JpaRepository<PlanEntity, Integer> {
//
//    Optional<PlanEntity> findByPlanName(String planName);
//
//    Optional<PlanEntity> findByPlanNameIgnoreCase(String planName);
//
//    boolean existsByPlanNameIgnoreCase(String planName);
//
//    List<PlanEntity> findByActiveSw(String activeSw);
//
//    List<PlanEntity> findByActiveSwOrderByStartDtDesc(String activeSw);
//
//    Page<PlanEntity> findByActiveSw(String activeSw, Pageable pageable);
//
//    Optional<PlanEntity> findByPlanIdAndActiveSw(Integer planId, String activeSw);
//
//    List<PlanEntity> findByPlanNameContainingIgnoreCase(String keyword);
//
//    List<PlanEntity> findByStartDtBetween(LocalDateTime start, LocalDateTime end);
//
//    @Query("FROM PlanEntity p WHERE p.activeSw = 'Y' " +
//            "AND CURRENT_TIMESTAMP BETWEEN p.startDt AND p.endDt")
//    List<PlanEntity> findCurrentlyActivePlans();
//}