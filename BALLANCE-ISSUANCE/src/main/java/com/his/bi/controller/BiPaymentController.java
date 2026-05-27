package com.his.bi.controller;

import com.his.bi.dto.BiPaymentDto;
import com.his.bi.service.BiPaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/bi")
public class BiPaymentController {

    @Autowired
    private BiPaymentService biService;

    @GetMapping("/payments")
    public ResponseEntity<List<BiPaymentDto>> getAllPayments() {

        return ResponseEntity.ok(biService.getAllPayments());
    }

    @PostMapping("/generate")
    public ResponseEntity<String> generatePayments() {
        biService.generateMonthlyPayments();
        return ResponseEntity.ok("Monthly payment file generated successfully!");
    }
}
