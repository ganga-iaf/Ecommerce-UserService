package com.example.userservice.controllers;

import com.example.userservice.dtos.*;
import com.example.userservice.models.Token;
import com.example.userservice.models.User;
import com.example.userservice.services.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;
    @PostMapping("/signup")
    public ResponseEntity<CreateUserResponseDto> addUser(@RequestBody @Valid CreateUserRequestDto requestDto) {
            String firstName = requestDto.getFirstName();
            String lastName = requestDto.getLastName();
            String email = requestDto.getEmail();
            String mobileNumber = requestDto.getMobileNumber();
            Date dob=requestDto.getDateOfBirth();
            String password = requestDto.getPassword();
            String confirmPassword = requestDto.getConfirmPassword();
            if(!password.equals(confirmPassword)){
                return  ResponseEntity.badRequest().build();
            }
            User user = userService.createUser(firstName,lastName,email,mobileNumber,dob,password);
            CreateUserResponseDto responseDto = createUserResponseDto(user);
            return new ResponseEntity<CreateUserResponseDto>(responseDto,HttpStatus.CREATED);
    }

    @GetMapping("/all")
    public ResponseEntity<List<UserResponseDto>> getAllUsers() {
        List<User> users=userService.getAllUsers();
        List<UserResponseDto> responseDtos=new ArrayList<>();
        for(User user:users){
            responseDtos.add(from(user));
        }
        return new ResponseEntity<List<UserResponseDto>>(responseDtos,HttpStatus.OK);
    }

    private UserResponseDto from(User user){
        UserResponseDto userResponseDto=new UserResponseDto();
        userResponseDto.setId(user.getId());
        userResponseDto.setFirstName(user.getFirstName());
        userResponseDto.setLastName(user.getLastName());
        userResponseDto.setEmail(user.getEmail());
        userResponseDto.setMobileNumber(user.getMobileNumber());
        userResponseDto.setAddresses(user.getAddresses());
        return userResponseDto;
    }

    @GetMapping("/{userId}")
    public ResponseEntity<UserResponseDto> getUserById(@PathVariable("userId") Long userId) {
        Optional<User> optionalUser=userService.getUserById(userId);
        UserResponseDto userResponseDto=null;
        if(optionalUser.isEmpty()){
            MultiValueMap<String, String> multiValueMap=new LinkedMultiValueMap<>();
            multiValueMap.add("error","User not found with id "+userId);
            return new ResponseEntity<UserResponseDto>(userResponseDto,multiValueMap ,HttpStatus.NOT_FOUND);
        }
        userResponseDto=new UserResponseDto();
        return new ResponseEntity<UserResponseDto>(from(optionalUser.get()),HttpStatus.FOUND);
    }

    private CreateUserResponseDto createUserResponseDto(User user){
        CreateUserResponseDto responseDto = new CreateUserResponseDto();
        responseDto.setId(user.getId());
        responseDto.setFirstName(user.getFirstName());
        responseDto.setLastName(user.getLastName());
        responseDto.setEmail(user.getEmail());
        responseDto.setMobileNumber(user.getMobileNumber());
        return responseDto;
    }

    @PostMapping("/login")
    public LoginResponseDto login(@RequestBody LoginRequestDto dto){
        Token token = userService.login(dto.getUsername(), dto.getPassword());
        LoginResponseDto responseDto=new LoginResponseDto();
        responseDto.setToken(token.getToken());
        return responseDto;
    }

    @PostMapping("/logout")
    public ResponseEntity<Void> logout(@RequestBody LogoutRequestDto dto){

    }

    @GetMapping("/validate/{token}")
    public ResponseEntity<Boolean> validateToken(@PathVariable("token") String token){
        User user =userService.validateToken(token);
        if(user==null){
            return new ResponseEntity<>(false,HttpStatus.UNAUTHORIZED);
        }
        return new ResponseEntity<>(true,HttpStatus.OK);
    }

}




