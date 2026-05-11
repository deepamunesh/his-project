package com.his.dc.service;

import com.his.dc.dto.*;
import com.his.dc.entity.*;
import com.his.dc.repository.*;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class DcService {

    @Autowired
    private DcCaseRepository caseRepo;
    @Autowired
    private DcPlanRepository planRepo;
    @Autowired
    private DcIncomeRepository incomeRepo;
    @Autowired
    private DcEducationRepository educationRepo;
    @Autowired
    private DcChildRepository childRepo;

    @Transactional
    public Long createCase(DcCaseDto dto, String createdBy) {
        // 🔑 Business Rule: Only CASE_WORKER can create cases
        String currentRole = SecurityContextHolder.getContext()
                .getAuthentication()
                .getAuthorities()
                .iterator()
                .next()
                .getAuthority();

        if (!"ROLE_CASE_WORKER".equals(currentRole)) {
            throw new AccessDeniedException("Only Case Workers can create cases");
        }

        // 1. Save case
        DcCaseEntity caseEntity = new DcCaseEntity();
        caseEntity.setAppId(dto.getAppId());
        caseEntity.setFName(dto.getFName());
        caseEntity.setLName(dto.getLName());
        caseEntity.setPlanName(dto.getPlanName());
//        caseEntity.setCreatedBy(createdBy);
//        caseEntity.setCreatedDt(LocalDateTime.now());
        DcCaseEntity savedCase = caseRepo.save(caseEntity);

        Long caseNum = savedCase.getCaseNum();

        // 2. Save plan selection
        DcPlanEntity planEntity = new DcPlanEntity();
        planEntity.setCaseNum(caseNum);
        planEntity.setPlanName(dto.getPlanName());
//        planEntity.setCreatedBy(createdBy);
//        planEntity.setCreatedDt(LocalDateTime.now());
        planRepo.save(planEntity);

        // 3. Save income details
        if (dto.getIncome() != null) {
            DcIncomeEntity incomeEntity = new DcIncomeEntity();
            incomeEntity.setCaseNum(caseNum);
            incomeEntity.setAnnualIncome(dto.getIncome().getAnnualIncome());
            incomeEntity.setOtherIncome(dto.getIncome().getOtherIncome());
            incomeEntity.setEmploymentStatus(dto.getIncome().getEmploymentStatus());
            incomeEntity.setCreatedBy(createdBy);
            incomeEntity.setCreatedDt(LocalDateTime.now());
            incomeRepo.save(incomeEntity);
        }

        // 4. Save education details
        if (dto.getEducation() != null) {
            DcEducationEntity eduEntity = new DcEducationEntity();
            eduEntity.setCaseNum(caseNum);
            eduEntity.setQualification(dto.getEducation().getQualification());
            eduEntity.setYearOfPassing(dto.getEducation().getYearOfPassing());
            eduEntity.setUniversityName(dto.getEducation().getUniversityName());
            eduEntity.setCreatedBy(createdBy);
            eduEntity.setCreatedDt(LocalDateTime.now());
            educationRepo.save(eduEntity);
        }

        // 5. Save children details
        if (dto.getChildren() != null) {
            for (DcChildDto childDto : dto.getChildren()) {
                DcChildEntity childEntity = new DcChildEntity();
                childEntity.setCaseNum(caseNum);
                childEntity.setChildName(childDto.getChildName());
                childEntity.setChildDob(childDto.getChildDob());
                childEntity.setGender(childDto.getGender());
                childEntity.setSchoolName(childDto.getSchoolName());
                childEntity.setCreatedBy(createdBy);
                childEntity.setCreatedDt(LocalDateTime.now());
                childRepo.save(childEntity);
            }
        }

        return caseNum;
    }

    public DcCaseDto getCaseDetails(Long caseNum) {
        // 🔑 Business Rule: Only CASE_WORKER or ADMIN can view case details
        String currentRole = SecurityContextHolder.getContext()
                .getAuthentication()
                .getAuthorities()
                .iterator()
                .next()
                .getAuthority();

        if (!("ROLE_CASE_WORKER".equals(currentRole) || "ROLE_ADMIN".equals(currentRole))) {
            throw new AccessDeniedException("Not allowed to view case details");
        }

        DcCaseEntity caseEntity = caseRepo.findById(caseNum)
                .orElseThrow(() -> new RuntimeException("Case not found"));

        DcCaseDto dto = new DcCaseDto();
        dto.setAppId(caseEntity.getAppId());
        dto.setFName(caseEntity.getFName());
        dto.setLName(caseEntity.getLName());

        // Plan
        DcPlanEntity planEntity = planRepo.findByCaseNum(caseNum);
        if (planEntity != null) {
            dto.setPlanName(planEntity.getPlanName());
        }

        // Income
        DcIncomeEntity incomeEntity = incomeRepo.findByCaseNum(caseNum);
        if (incomeEntity != null) {
            DcIncomeDto incomeDto = new DcIncomeDto();
            incomeDto.setAnnualIncome(incomeEntity.getAnnualIncome());
            incomeDto.setOtherIncome(incomeEntity.getOtherIncome());
            incomeDto.setEmploymentStatus(incomeEntity.getEmploymentStatus());
            dto.setIncome(incomeDto);
        }

        // Education
        DcEducationEntity eduEntity = educationRepo.findByCaseNum(caseNum);
        if (eduEntity != null) {
            DcEducationDto eduDto = new DcEducationDto();
            eduDto.setQualification(eduEntity.getQualification());
            eduDto.setYearOfPassing(eduEntity.getYearOfPassing());
            eduDto.setUniversityName(eduEntity.getUniversityName());
            dto.setEducation(eduDto);
        }

        // Children
        List<DcChildEntity> childEntities = childRepo.findByCaseNum(caseNum);
        if (childEntities != null && !childEntities.isEmpty()) {
            List<DcChildDto> childDtos = childEntities.stream().map(child -> {
                DcChildDto childDto = new DcChildDto();
                childDto.setChildName(child.getChildName());
                childDto.setChildDob(child.getChildDob());
                childDto.setGender(child.getGender());
                childDto.setSchoolName(child.getSchoolName());
                return childDto;
            }).collect(Collectors.toList());
            dto.setChildren(childDtos);
        }

        return dto;
    }


    // method for plan
    public DcPlanDto getPlanDetails(Long planId) {
        DcPlanEntity plan = planRepo.findByPlanId(planId);
        //.orElseThrow(() -> new RuntimeException("PlanId is not found"));
        DcPlanDto planDto = new DcPlanDto();
        Long casNum = plan.getCaseNum();
        if (plan != null) {
            planDto.setPlanId(plan.getPlanId());
            planDto.setCaseNum(plan.getCaseNum());
            planDto.setPlanName(plan.getPlanName());

            DcIncomeEntity incomeEntity = incomeRepo.findByCaseNum(casNum);
            if (incomeEntity != null) {
                DcIncomeDto incomeDto = new DcIncomeDto();
                incomeDto.setAnnualIncome(incomeEntity.getAnnualIncome());
                incomeDto.setOtherIncome(incomeEntity.getOtherIncome());
                incomeDto.setEmploymentStatus(incomeEntity.getEmploymentStatus());
                planDto.setIncome(incomeDto);
            }

            // For Education
            DcEducationEntity educationEntity = educationRepo.findByCaseNum(casNum);
            if (educationEntity != null) {
                DcEducationDto educationDto = new DcEducationDto();
                educationDto.setUniversityName(educationEntity.getUniversityName());
                educationDto.setQualification(educationEntity.getQualification());
                educationDto.setYearOfPassing(educationEntity.getYearOfPassing());
                planDto.setEducation(educationDto);
            }

            //For Children

            List<DcChildEntity> childEntity = childRepo.findByCaseNum(casNum);
            if (childEntity != null) {
                List<DcChildDto> childDtos = childEntity.stream().map(child -> {
                    DcChildDto childDto = new DcChildDto();
                    childDto.setChildName(child.getChildName());
                    childDto.setChildDob(child.getChildDob());
                    childDto.setGender(child.getGender());
                    childDto.setSchoolName(child.getSchoolName());
                    return childDto;
                }).collect(Collectors.toList());
                planDto.setChildren(childDtos);
            }
        }
        return planDto;
    }
}