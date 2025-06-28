package com.example.userservice.dtos;

import com.example.userservice.models.Address;
import lombok.Data;

import java.util.List;

@Data
public class UserResponseDto {
    private long id;
    private String firstName;
    private String lastName;
    private String email;
    private String mobileNumber;
    private List<Address> addresses;
}
