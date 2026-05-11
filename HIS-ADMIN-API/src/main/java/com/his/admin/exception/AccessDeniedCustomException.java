package com.his.admin.exception;

public class AccessDeniedCustomException extends RuntimeException {

    public AccessDeniedCustomException(String message) {
        super(message);
    }
}