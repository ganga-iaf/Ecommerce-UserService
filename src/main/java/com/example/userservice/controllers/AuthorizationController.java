package com.example.userservice.controllers;

import com.example.userservice.Exceptions.ExpiredJWTException;
import com.example.userservice.Exceptions.IncorrectPasswordException;
import com.example.userservice.Exceptions.InvalidTokenException;
import com.example.userservice.Exceptions.UserNotFoundException;
import com.example.userservice.dtos.LoginRequestDto;
import com.example.userservice.dtos.LoginResponseDto;
import com.example.userservice.models.Token;
import com.example.userservice.models.User;
import com.example.userservice.services.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthorizationController {
    @Autowired
    private AuthService authService;


    @PostMapping("/login")
    public LoginResponseDto login(@RequestBody LoginRequestDto dto) throws UserNotFoundException, IncorrectPasswordException {
        Token token = authService.login(dto.getUsername(), dto.getPassword());
        LoginResponseDto responseDto=new LoginResponseDto();
        responseDto.setToken(token.getToken());
        return responseDto;
    }

    @PostMapping("/validate")
    public ResponseEntity<Boolean> validateToken(@RequestHeader(HttpHeaders.AUTHORIZATION) String token) throws ExpiredJWTException, InvalidTokenException, UserNotFoundException {
        if(token.startsWith("Bearer ")){
            token=token.replace("Bearer ","");
        }
        User user =authService.validateToken(token);
        if(user==null){
            return new ResponseEntity<>(false, HttpStatus.UNAUTHORIZED);
        }
        return new ResponseEntity<>(true,HttpStatus.OK);
    }

}
