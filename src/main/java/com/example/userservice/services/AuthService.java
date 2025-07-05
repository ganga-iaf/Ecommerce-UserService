package com.example.userservice.services;

import com.example.userservice.Exceptions.ExpiredJWTException;
import com.example.userservice.Exceptions.IncorrectPasswordException;
import com.example.userservice.Exceptions.InvalidTokenException;
import com.example.userservice.Exceptions.UserNotFoundException;
import com.example.userservice.models.Token;
import com.example.userservice.models.User;

public interface AuthService {
    Token login(String email, String password) throws UserNotFoundException, IncorrectPasswordException;
    User validateToken(String token) throws ExpiredJWTException, InvalidTokenException, UserNotFoundException;
}
