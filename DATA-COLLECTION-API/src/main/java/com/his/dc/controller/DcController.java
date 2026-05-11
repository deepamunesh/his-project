package com.his.dc.controller;


import com.his.dc.dto.DcCaseDto;
import com.his.dc.dto.DcPlanDto;
import com.his.dc.service.DcService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/dc")
public class DcController {

    @Autowired
    private DcService dcService;

    // 1. Create Case (allowed only for CASE_WORKER via SecurityConfig + DcService rule)
    @PostMapping("/create-case")
    public ResponseEntity<String> createCase(@RequestBody DcCaseDto dto, Authentication auth) {
        String username = (auth != null) ? auth.getName() : "SYSTEM";
        Long caseNum = dcService.createCase(dto, username);
        return ResponseEntity.ok("Case created successfully with Case Number: " + caseNum);
    }

    // 2. Get Case Details (allowed for CASE_WORKER and ADMIN via SecurityConfig + DcService rule)
    @GetMapping("/case/{caseNum}")
    public ResponseEntity<DcCaseDto> getCase(@PathVariable Long caseNum) {
        DcCaseDto caseDto = dcService.getCaseDetails(caseNum);
        return ResponseEntity.ok(caseDto);
    }

    //3 get case details
    @GetMapping("/plan/{planId}")
    public ResponseEntity<DcPlanDto>getPlan(@PathVariable Long planId){
        DcPlanDto planDto=dcService.getPlanDetails(planId);
        return ResponseEntity.ok(planDto);
    }
}


