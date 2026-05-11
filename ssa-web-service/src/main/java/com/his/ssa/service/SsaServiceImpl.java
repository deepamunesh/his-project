package com.his.ssa.service;

import com.his.ssa.dto.SsaResponse;
import com.his.ssa.entity.SsaMasterEntity;
import com.his.ssa.exception.SsaNotFoundException;
import com.his.ssa.repository.SsaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Optional;

@Service
public class SsaServiceImpl implements com.his.sss.service.SsaService {
    @Autowired
    private SsaRepository ssaRepository;

    @Override
    public SsaResponse validateSsn(String ssnNo){
                                                                 //123456789
        Optional<SsaMasterEntity> entity=ssaRepository.findBySsnNo(ssnNo);

        if(entity.isPresent())
        {
                SsaMasterEntity ss=entity.get();
                LocalDate dob=ss.getDob();
                String firstName=ss.getFirstName();
                String gender=ss.getGender();
                String lastName =ss.getLastName();
                String ssNo =ss.getSsnNo();
                String stateName =ss.getStateName();
                String phNo =ss.getPhNo();


                SsaResponse response=new SsaResponse();
                response.setStatus("VALID");
                response.setValid(true);   //  ADD THIS LINE
                response.setDob(dob);
                response.setFirstName(firstName);
                response.setGender(gender);
                response.setLastName(lastName);
                response.setSsnNo(ssNo);
                response.setStateName(stateName);
                response.setPhNo(phNo);


                return response;
            }
        // If not found or inactive
       // SsaResponse response = new SsaResponse();
       // response.setStatus("Invalid SSN Number");//return response;
       throw new SsaNotFoundException("Invalid SSN Number");
    }
}