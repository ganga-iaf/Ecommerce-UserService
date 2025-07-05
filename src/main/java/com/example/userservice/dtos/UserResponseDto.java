package com.example.userservice.dtos;

import com.example.userservice.models.Address;
import com.example.userservice.models.User;
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

    public static UserResponseDto from(User user){
        UserResponseDto userResponseDto=new UserResponseDto();
        userResponseDto.setId(user.getId());
        userResponseDto.setFirstName(user.getFirstName());
        userResponseDto.setLastName(user.getLastName());
        userResponseDto.setEmail(user.getEmail());
        userResponseDto.setMobileNumber(user.getMobileNumber());
        userResponseDto.setAddresses(user.getAddresses());
        return userResponseDto;
    }
}
