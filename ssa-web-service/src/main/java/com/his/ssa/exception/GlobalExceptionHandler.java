package com.his.ssa.exception;

import com.his.ssa.dto.SsaResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
@RestControllerAdvice
public class GlobalExceptionHandler
{
        @ExceptionHandler(SsaNotFoundException.class)
        public ResponseEntity<SsaResponse> handleSsnNotfound(SsaNotFoundException ex){
            SsaResponse response=new SsaResponse();
            response.setStatus(ex.getMessage());
            response.setValid(false);   // ADD THIS LINE
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
    }


