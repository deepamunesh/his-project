package com.his.ssa.Controller;

import com.his.ssa.dto.SsaResponse;
import com.his.sss.service.SsaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/ssa")
public class SsaController{
@Autowired
private SsaService ssaService;
@GetMapping("/validate/{ssn}")
    public ResponseEntity<SsaResponse> validateSsa( @PathVariable("ssn") String ssn){
    SsaResponse response=ssaService.validateSsn(ssn);
    return ResponseEntity.ok(response);
    }
}

