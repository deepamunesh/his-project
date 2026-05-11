package com.his.dc.exception;

public class AccessDeniedCustomException extends RuntimeException {

    public AccessDeniedCustomException(String message) {
        super(message);
    }
}