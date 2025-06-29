package com.example.userservice.Exceptions;

public class PasswordsNotMatchException extends Exception {
    public PasswordsNotMatchException(String message) {
        super(message);
    }
}
