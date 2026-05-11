package com.his.ar.exception;

public class InvalidSsnException extends RuntimeException{

    public InvalidSsnException(String message){

        super(message);
    }
}