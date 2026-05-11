package com.his.admin.exception;

public class AccountNotFoundException extends RuntimeException {

    public AccountNotFoundException(String userName) {
        super("Account not found with USERNAME : " + userName);
    }

}