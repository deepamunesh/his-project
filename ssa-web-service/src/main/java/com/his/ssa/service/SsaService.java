package com.his.sss.service;

import com.his.ssa.dto.SsaResponse;
import org.springframework.stereotype.Service;

@Service
public interface SsaService {
    SsaResponse validateSsn(String ssnNo);
}