package com.example.userservice.services;

import com.example.userservice.Exceptions.UserAlreadyExistsException;
import com.example.userservice.Exceptions.UserNotFoundException;
import com.example.userservice.models.User;

import java.util.Date;
import java.util.List;

public interface UserService {
    User createUser(String firstName, String lastName, String email, String mobileNumber , Date dob , String password) throws UserAlreadyExistsException;
    List<User> getAllUsers();
    User getUserById(Long userId) throws UserNotFoundException;
    boolean isUserExists(String email);
}
