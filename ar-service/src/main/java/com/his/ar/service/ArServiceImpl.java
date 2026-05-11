package com.his.ar.service;


import com.his.ar.dto.AppRegRequest;
import com.his.ar.dto.SsaResponse;
import com.his.ar.entity.AppRegEntity;
import com.his.ar.exception.DuplicateApplicationException;
import com.his.ar.exception.InvalidSsnException;
import com.his.ar.exception.InvalidStateException;
import com.his.ar.repository.AppRegRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

@Service
public class ArServiceImpl implements ArService {

    @Autowired
    private AppRegRepository appRegRepository;

    @Autowired
    private RestTemplate restTemplate;

    private static final String SSA_URL = "http://localhost:8082/ssa/validate/";


    public SsaResponse createApplication(AppRegRequest request) {
        SsaResponse ssaResponse;
        try {
            ssaResponse = restTemplate.getForObject(
                    SSA_URL + request.getSsnNo(),
                    SsaResponse.class
            );
        } catch (HttpClientErrorException ex) {
            if (ex.getStatusCode() == HttpStatus.NOT_FOUND) {
                throw new InvalidSsnException("SSN not found: " + request.getSsnNo());
            }
            throw new RuntimeException("Failed to validate SSN", ex);
        }

        if (ssaResponse == null) {
            throw new InvalidSsnException("Invalid SSN number: " + request.getSsnNo());
        }

        if (!"Kentucky".equalsIgnoreCase(ssaResponse.getStateName())) {
            throw new InvalidStateException("Citizen is not eligible. Only Kentucky state 's citizens are allowed.");
        }
        //  Duplicate Check
        if (appRegRepository.existsBySsnNo(request.getSsnNo())) {
            throw new DuplicateApplicationException("Application already exists for this SSN");
        }

        //  Save Application
        AppRegEntity entity = new AppRegEntity();
        BeanUtils.copyProperties(request, entity);

        appRegRepository.save(entity);

        // 4Return Required Format
        SsaResponse response = new SsaResponse();

        String dob=ssaResponse.getDob();
        String firstName=ssaResponse.getFirstName();
        String lastName=ssaResponse.getLastName();
        String gender= ssaResponse.getGender();
        String stateName=ssaResponse.getStateName();
        String ssnNo=ssaResponse.getSsnNo();

        response.setDob(dob);
        response.setFirstName(firstName);
        response.setLastName(lastName);
        response.setGender(gender);
        response.setSsnNo(ssnNo);
        response.setStateName(stateName);

        return response;
    }

}



























