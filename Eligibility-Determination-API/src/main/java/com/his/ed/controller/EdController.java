package com.his.ed.controller;

import com.his.ed.dto.EdEligDto;
import com.his.ed.service.EdService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/ed")
public class EdController {

    @Autowired
    private EdService edService;

    // Trigger eligibility determination
    @PostMapping("/determine/{caseNum}")
    public ResponseEntity<EdEligDto> determineEligibility(@PathVariable Long caseNum) {
        EdEligDto dto = edService.determineEligibility(caseNum);
        return ResponseEntity.ok(dto);
    }
    //get eligibility details
    @GetMapping("/eligibility/{caseNum}")
    public ResponseEntity<EdEligDto> getEligibility(@PathVariable Long caseNum){
        EdEligDto dto=edService.getEligibility(caseNum);
        return ResponseEntity.ok(dto);
    }
}
