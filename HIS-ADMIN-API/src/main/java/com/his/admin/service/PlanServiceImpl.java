package com.his.admin.service;

import com.his.admin.dto.PlanDto;
import com.his.admin.entity.PlanEntity;
import com.his.admin.repository.PlanRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class PlanServiceImpl {

    @Autowired
    private PlanRepository planRepository;

    public PlanEntity createPlan(PlanDto planDto, String name) {
        PlanEntity planEntity = new PlanEntity();
        planEntity.setPlanName(planDto.getPlanName());
        planEntity.setStartDt(planDto.getStartDt());
        planEntity.setEndDt(planDto.getEndDt());
        planEntity.setActiveSw("Y");
        planEntity.setCreatedBy(name);
        planEntity.setCreatedDt(LocalDateTime.now());
        planEntity.setUpdatedBy(name);
        planEntity.setUpdatedDt(LocalDateTime.now());
        return planRepository.save(planEntity);
    }

    public Page<PlanEntity> getAllPlans(Pageable pageable) {
        return planRepository.findAllActivePlans(pageable);
    }

    public Page<PlanEntity> getActivePlans(Pageable pageable) {
        return planRepository.findActivePlans(pageable);
    }

    public Page<PlanEntity> searchPlans(String keyword, Pageable pageable) {
        return planRepository.searchPlans(keyword, pageable);
    }

    public List<PlanEntity> getExpiringPlans() {
        LocalDate today = LocalDate.now();
        LocalDate expiryDate = today.plusMonths(1);
        return planRepository.findExpiringPlans(today, expiryDate);
    }

    public PlanEntity updatePlan(Integer planId, PlanDto dto, String updatedBy) {
        PlanEntity planEntity = planRepository.findByPlanId(planId)
                .orElseThrow(() -> new RuntimeException("Plan not found"));
//        planEntity.setStartDt(dto.getStartDt());
//        planEntity.setEndDt(dto.getEndDt());
        planEntity.setActiveSw(dto.getActiveSw());
//        planEntity.setUpdatedBy(updatedBy);
//        planEntity.setUpdatedDt(LocalDateTime.now());
        return planRepository.save(planEntity);
    }

    public void deactivatePlan(Integer planId, String updatedBy) {
       PlanEntity plan = planRepository.findByPlanId(planId)
                .orElseThrow(() -> new RuntimeException("Plan not found"));
        plan.setActiveSw("N");
        plan.setUpdatedBy(updatedBy);
        plan.setUpdatedDt(LocalDateTime.now());
        planRepository.save(plan);
    }

    public void deletePlan(Integer planId, String updatedBy) {
        PlanEntity plan = planRepository.findByPlanId(planId)
                .orElseThrow(() -> new RuntimeException("Plan not found"));
        plan.setActiveSw("N");
        plan.setUpdatedBy(updatedBy);
        plan.setUpdatedDt(LocalDateTime.now());
        planRepository.save(plan);
    }
}




//@Service
//public class PlanServiceImpl implements PlanService {
//
//    private final PlanRepository planRepository;
//
//    public PlanServiceImpl(PlanRepository planRepository) {
//        this.planRepository = planRepository;
//    }
//
//    // CREATE PLAN
//
//    public String createPlan(Plan plan, String role, String username) {
//
//        if (!role.equals("SUPER_ADMIN")) {
//            return "Only SUPER_ADMIN can create plans";
//        }
//
//        if (planRepository.existsByPlanNameIgnoreCase(plan.getPlanName())) {
//            return "Plan already exists";
//        }
//
//        PlanEntity entity = new PlanEntity();
//        BeanUtils.copyProperties(plan, entity);
//
//        entity.setActiveSw("Y");
//        entity.setCreatedBy(username);
//        entity.setCreatedDt(LocalDateTime.now());
//        entity.setUpdatedBy(username);
//        entity.setUpdatedDt(LocalDateTime.now());
//
//        planRepository.save(entity);
//
//        return "Plan created successfully";
//    }
//
//    //  UPDATE PLAN
//
//    public String updatePlan(Plan dto, String role, String username) {
//
//        if (!role.equals("SUPER_ADMIN")) {
//            return "Only SUPER_ADMIN can update plans";
//        }
//
//        PlanEntity entity = planRepository
//                .findByPlanIdAndActiveSw(dto.getPlanId(), "Y")
//                .orElseThrow(() -> new RuntimeException("Active Plan not found"));
//
//        entity.setPlanName(dto.getPlanName());
//        entity.setStartDt(dto.getStartDt());
//        entity.setEndDt(dto.getEndDt());
//        entity.setUpdatedBy(username);
//        entity.setUpdatedDt(LocalDateTime.now());
//
//        planRepository.save(entity);
//
//        return "Plan updated successfully";
//    }
//
//    // SOFT DELETE
//    @Override
//    public String deactivatePlan(Integer planId, String role, String username) {
//
//        if (!role.equals("SUPER_ADMIN")) {
//            return "Only SUPER_ADMIN can deactivate plans";
//        }
//
//        PlanEntity entity = planRepository
//                .findByPlanIdAndActiveSw(planId, "Y")
//                .orElseThrow(() -> new RuntimeException("Active Plan not found"));
//
//        entity.setActiveSw("N");
//        entity.setUpdatedBy(username);
//        entity.setUpdatedDt(LocalDateTime.now());
//
//        planRepository.save(entity);
//
//        return "Plan deactivated successfully";
//    }
//
//    // GET ALL ACTIVE
//
//    public List<Plan> getAllActivePlans() {
//
//        return planRepository.findByActiveSw("Y")
//                .stream()
//                .map(this::convertToDTO)
//                .collect(Collectors.toList());
//    }
//
//    // SORTED ACTIVE
//
//    public List<Plan> getActivePlansSorted() {
//
//        return planRepository.findByActiveSwOrderByStartDtDesc("Y")
//                .stream()
//                .map(this::convertToDTO)
//                .collect(Collectors.toList());
//    }
//
//    // PAGINATION
//
//    public Page<Plan> getActivePlansWithPagination(int page, int size) {
//
//        Pageable pageable = PageRequest.of(page, size);
//        Page<PlanEntity> entityPage =
//                planRepository.findByActiveSw("Y", pageable);
//
//        return entityPage.map(this::convertToDTO);
//    }
//
//    // ✅ SEARCH BY NAME
//
//    public List<Plan> searchPlansByName(String keyword) {
//
//        return planRepository.findByPlanNameContainingIgnoreCase(keyword)
//                .stream()
//                .map(this::convertToDTO)
//                .collect(Collectors.toList());
//    }
//
//    //  BETWEEN DATES
//    @Override
//    public List<Plan> getPlansBetweenDates(LocalDateTime start,
//                                              LocalDateTime end) {
//
//        return planRepository.findByStartDtBetween(start, end)
//                .stream()
//                .map(this::convertToDTO)
//                .collect(Collectors.toList());
//    }
//
//    //  CURRENTLY ACTIVE (Date based)
//    @Override
//    public List<Plan> getCurrentlyActivePlans() {
//
//        return planRepository.findCurrentlyActivePlans()
//                .stream()
//                .map(this::convertToDTO)
//                .collect(Collectors.toList());
//    }
//
//    // FIND BY NAME
//    @Override
//    public Plan getPlanByName(String planName) {
//
//        PlanEntity entity = planRepository.findByPlanName(planName)
//                .orElseThrow(() -> new RuntimeException("Plan not found"));
//
//        return convertToDTO(entity);
//    }
//
//    // ENTITY → DTO
//    private Plan convertToDTO(PlanEntity entity) {
//        Plan dto = new Plan();
//        BeanUtils.copyProperties(entity, dto);
//        return dto;
//    }
//}