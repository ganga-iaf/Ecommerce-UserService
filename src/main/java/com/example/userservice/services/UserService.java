package com.example.userservice.services;

import com.example.userservice.dtos.CreateUserRequestDto;
import com.example.userservice.models.Token;
import com.example.userservice.models.User;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface UserService {
    User createUser(String firstName, String lastName, String email, String mobileNumber , Date dob , String password);
    List<User> getAllUsers();
    Optional<User> getUserById(Long userId);
    Token login(String email, String password);
    User validateToken(String token);
    void logout(String token);
}
