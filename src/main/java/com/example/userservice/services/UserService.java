package com.example.userservice.services;

import com.example.userservice.Exceptions.*;
import com.example.userservice.dtos.CreateUserRequestDto;
import com.example.userservice.models.Token;
import com.example.userservice.models.User;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface UserService {
    User createUser(String firstName, String lastName, String email, String mobileNumber , Date dob , String password) throws UserAlreadyExistsException;
    List<User> getAllUsers();
    User getUserById(Long userId) throws UserNotFoundException;
    Token login(String email, String password) throws UserNotFoundException, IncorrectPasswordException;
    User validateToken(String token) throws ExpiredJWTException, InvalidTokenException;
    void logout(String token);
}
