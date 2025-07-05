package com.example.userservice.dtos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserExistanceResponseDto {
    private boolean exists;
    private String email;
}
