package com.example.userservice.Exceptions;

public class ExpiredJWTException extends Exception {
    public ExpiredJWTException(Exception ex) {
        super(ex);
    }
}
