package com.example.userservice.dtos;

import lombok.Data;

@Data
public class UserResponseDto {
    private long id;
    private String firstName;
    private String lastName;
    private String email;
    private String mobileNumber;
}
