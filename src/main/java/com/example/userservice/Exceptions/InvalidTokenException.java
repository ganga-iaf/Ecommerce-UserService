package com.example.userservice.Exceptions;

public class InvalidTokenException extends Exception {
    public InvalidTokenException(Exception ex) {
        super(ex);
    }
}
