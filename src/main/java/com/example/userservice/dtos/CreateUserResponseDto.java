package com.example.userservice.dtos;

import com.example.userservice.models.User;
import lombok.Data;

@Data
public class CreateUserResponseDto {
    private long id;
    private String firstName;
    private String lastName;
    private String email;
    private String mobileNumber;

    public static CreateUserResponseDto from(User user){
        CreateUserResponseDto responseDto = new CreateUserResponseDto();
        responseDto.setId(user.getId());
        responseDto.setFirstName(user.getFirstName());
        responseDto.setLastName(user.getLastName());
        responseDto.setEmail(user.getEmail());
        responseDto.setMobileNumber(user.getMobileNumber());
        return responseDto;
    }
}
