package com.his.ar.service;

import com.his.ar.dto.AppRegRequest;
import com.his.ar.dto.SsaResponse;
import org.springframework.stereotype.Service;

@Service
public interface ArService {
    SsaResponse createApplication(AppRegRequest request);
}
