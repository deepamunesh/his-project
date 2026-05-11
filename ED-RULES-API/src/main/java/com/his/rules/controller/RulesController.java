package com.his.rules.controller;


import com.his.rules.dto.CitizenDataDto;
import com.his.rules.dto.EligibilityResponseDto;
import com.his.rules.service.RulesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/ed-rules")
public class RulesController {

    @Autowired
    private RulesService rulesService;

    @PostMapping("/check")
    public EligibilityResponseDto checkEligibility(@RequestBody CitizenDataDto data){
        return rulesService.evaluate(data);
}
}
