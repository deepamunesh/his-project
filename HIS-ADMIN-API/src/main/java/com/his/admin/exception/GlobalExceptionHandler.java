package com.his.admin.exception;

import com.his.admin.dto.ErrorResponse;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@RestControllerAdvice
public class GlobalExceptionHandler
{
        @ExceptionHandler(AccountNotFoundException.class)
        public ResponseEntity<ErrorResponse> handleSsnNotfound(AccountNotFoundException ex){
            ErrorResponse response=new ErrorResponse();
            response.setMessage(ex.getMessage());
            response.setStatus(HttpStatus.NOT_FOUND.value());
            response.setLocalDateTime(LocalDateTime.now());

            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }

    @ExceptionHandler(AccessDeniedCustomException.class)
    public ResponseEntity<String> handleUnauthorized(
            AccessDeniedCustomException ex) {

        ErrorResponse response = new ErrorResponse();
        response.setMessage(ex.getMessage());
        //response.setStatus(HttpStatus.FORBIDDEN.value());
        //response.setLocalDateTime(LocalDateTime.now());

        //return ResponseEntity.status(HttpStatus.FORBIDDEN).body(response);
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.CONFLICT);
    }
    @ExceptionHandler(AccountAlreadyExistsException.class)
    public ResponseEntity<String> handleAccountAlreadyExists(AccountAlreadyExistsException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.CONFLICT);
    }

    @ExceptionHandler(UsernameAlreadyExistsException.class)
    public ResponseEntity<String> handleUsernameAlreadyExists(UsernameAlreadyExistsException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.CONFLICT);
    }
    }


