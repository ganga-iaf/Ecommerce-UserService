package com.example.userservice.controllers;

import com.example.userservice.Exceptions.*;
import com.example.userservice.dtos.*;
import com.example.userservice.models.User;
import com.example.userservice.services.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    //User might exist already
    @PostMapping("/signup")
    public ResponseEntity<CreateUserResponseDto> signup(@RequestBody @Valid CreateUserRequestDto requestDto) throws UserAlreadyExistsException, PasswordsNotMatchException {
            String firstName = requestDto.getFirstName();
            String lastName = requestDto.getLastName();
            String email = requestDto.getEmail();
            String mobileNumber = requestDto.getMobileNumber();
            Date dob=requestDto.getDateOfBirth();
            String password = requestDto.getPassword();
            String confirmPassword = requestDto.getConfirmPassword();
            if(!password.equals(confirmPassword)){
                throw new PasswordsNotMatchException("Password and Confirm passwords aren't matching.");
            }
            User user = userService.createUser(firstName,lastName,email,mobileNumber,dob,password);
            CreateUserResponseDto responseDto = CreateUserResponseDto.from(user);
            return new ResponseEntity<>(responseDto,HttpStatus.CREATED);
    }

    @GetMapping("/all")
    public ResponseEntity<List<UserResponseDto>> getAllUsers() {
        List<User> users=userService.getAllUsers();
        List<UserResponseDto> responseDtos=new ArrayList<>();
        for(User user:users){
            responseDtos.add(UserResponseDto.from(user));
        }
        return new ResponseEntity<>(responseDtos,HttpStatus.OK);
    }

    @GetMapping("/{userId}")
    public ResponseEntity<UserResponseDto> getUserById(@PathVariable("userId") Long userId) throws UserNotFoundException {
        User user=userService.getUserById(userId);
        return new ResponseEntity<>(UserResponseDto.from(user),HttpStatus.FOUND);
    }

    @GetMapping("/exists")
    public ResponseEntity<UserExistanceResponseDto> checkUserExistsWithEmail(@RequestParam("email") String email){
           boolean isExists=userService.isUserExists(email);
           UserExistanceResponseDto responseDto=new UserExistanceResponseDto();
           responseDto.setEmail(email);
           responseDto.setExists(isExists);
           return new ResponseEntity<>(responseDto,HttpStatus.OK);
    }
}




