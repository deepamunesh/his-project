package com.his.ar.controller;

import com.his.ar.dto.AppRegRequest;
import com.his.ar.dto.SsaResponse;
import com.his.ar.service.ArService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/ar")
public class ArController {

    @Autowired
    public ArService arService;

    @PostMapping("/create")
    public ResponseEntity<SsaResponse> createApplication(@RequestBody AppRegRequest request){
        SsaResponse ssaResponse = arService.createApplication(request);
        return ResponseEntity.ok(ssaResponse);
    }
}
