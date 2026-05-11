package com.his.admin.controller;

import com.his.admin.dto.PlanDto;
import com.his.admin.entity.PlanEntity;
import com.his.admin.service.PlanServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;


import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/plan")
public class PlanController {

    @Autowired
    private PlanServiceImpl planService;

    @PostMapping("/create")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<PlanEntity> createPlan(@RequestBody PlanDto dto, Authentication auth) {
        PlanEntity planEntity=planService.createPlan(dto, auth.getName());
        System.out.println("Name from Authentication::::::::  "+auth.getName());

        return ResponseEntity.ok(planEntity);
    }

    @GetMapping("/all")
    @PreAuthorize("hasAnyRole('SUPER_ADMIN','ADMIN','CASE_WORKER')")
    public ResponseEntity<Page<PlanEntity>> getAllPlans(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size,
            @RequestParam(defaultValue = "planName") String sortBy,
            @RequestParam(defaultValue = "asc") String direction) {
        Pageable pageable = PageRequest.of(page, size,
                direction.equals("asc") ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending());
        return ResponseEntity.ok(planService.getAllPlans(pageable));
    }

    @GetMapping("/active")
    @PreAuthorize("hasAnyRole('SUPER_ADMIN','ADMIN','CASE_WORKER')")
    public ResponseEntity<Page<PlanEntity>> getActivePlans(Pageable pageable) {
        return ResponseEntity.ok(planService.getActivePlans(pageable));
    }

    @GetMapping("/search")
    @PreAuthorize("hasAnyRole('SUPER_ADMIN','ADMIN','CASE_WORKER')")
    public ResponseEntity<Page<PlanEntity>> searchPlans(
            @RequestParam String keyword,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size,
            @RequestParam(defaultValue = "planName") String sortBy,
            @RequestParam(defaultValue = "asc") String direction) {
        Pageable pageable = PageRequest.of(page, size,
                direction.equals("asc") ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending());
        return ResponseEntity.ok(planService.searchPlans(keyword, pageable));
    }

    @GetMapping("/expiring")
    @PreAuthorize("hasAnyRole('SUPER_ADMIN','ADMIN','CASE_WORKER')")
    public ResponseEntity<List<PlanEntity>> getExpiringPlans() {
        return ResponseEntity.ok(planService.getExpiringPlans());
    }

    @PutMapping("/update/{planId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<PlanEntity> updatePlan(@PathVariable Integer planId,
                                           @RequestBody PlanDto dto,
                                           Authentication auth) {
        PlanEntity planEntity=planService.updatePlan(planId, dto, auth.getName());
        System.out.println("Logged IN User:::::: "+auth.getName());
        return ResponseEntity.ok(planEntity);
    }

    @PutMapping("/deactivate/{planId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> deactivatePlan(@PathVariable Integer planId, Authentication auth) {
        planService.deactivatePlan(planId, auth.getName());
        return ResponseEntity.ok("Plan with ID " + planId + " has been deactivated successfully by " + auth.getName());
    }

    @DeleteMapping("/delete/{planId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> deletePlan(@PathVariable Integer planId, Authentication auth) {
        planService.deletePlan(planId, auth.getName());
        return ResponseEntity.ok("plan with ID "+ planId + " has been delete successfully by "+ auth.getName());
    }
}

//@RestController
//@RequestMapping("/admin/plans")
//public class PlanController {
//
//    private final PlanService planService;
//
//    public PlanController(PlanService planService) {
//        this.planService = planService;
//    }
//
//    //  CREATE PLAN
//    @PostMapping("/create/{role}/{username}")
//    public String createPlan(@RequestBody PlanDto plan,
//                             @PathVariable String role,
//                             @PathVariable String username) {
//
//        return planService.createPlan(plan, role, username);
//    }
//
//    // UPDATE PLAN
//    @PutMapping("/update/{role}/{username}")
//    public String updatePlan(@RequestBody PlanDto plan,
//                             @PathVariable String role,
//                             @PathVariable String username) {
//
//        return planService.updatePlan(plan, role, username);
//    }
//
//    //  SOFT DELETE PLAN
//    @DeleteMapping("/deactivate/{planId}/{role}/{username}")
//    public String deactivatePlan(@PathVariable Integer planId,
//                                 @PathVariable String role,
//                                 @PathVariable String username) {
//
//        return planService.deactivatePlan(planId, role, username);
//    }
//
//    //  GET ALL ACTIVE PLANS
//    @GetMapping("/activePlans")
//    public List<PlanDto> getAllActivePlans() {
//
//        return planService.getAllActivePlans();
//    }
//
//    //  GET SORTED ACTIVE PLANS
//    @GetMapping("/active/sorted")
//    public List<PlanDto> getActivePlansSorted() {
//
//        return planService.getActivePlansSorted();
//    }
//
//    //  PAGINATION
//    @GetMapping("/active/page")
//    public Page<PlanDto> getActivePlansWithPagination(
//            @RequestParam int page,
//            @RequestParam int size) {
//
//        return planService.getActivePlansWithPagination(page, size);
//    }
//
//    //  SEARCH BY NAME
//    @GetMapping("/search")
//    public List<PlanDto> searchPlans(@RequestParam String keyword) {
//
//        return planService.searchPlansByName(keyword);
//    }
//
//    //  FIND PLAN BY NAME
//    @GetMapping("/name/{planName}")
//    public PlanDto getPlanByName(@PathVariable String planName) {
//
//        return planService.getPlanByName(planName);
//    }
//
//    //  FILTER BETWEEN DATES
//    @GetMapping("/between-dates")
//    public List<PlanDto> getPlansBetweenDates(
//            @RequestParam
//            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
//            LocalDateTime start,
//
//            @RequestParam
//            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
//            LocalDateTime end) {
//
//        return planService.getPlansBetweenDates(start, end);
//    }
//
//    //  CURRENTLY ACTIVE (Date Based)
//    @GetMapping("/currently-active")
//    public List<PlanDto> getCurrentlyActivePlans() {
//
//        return planService.getCurrentlyActivePlans();
//    }
//}