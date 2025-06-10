package com.example.userservice.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.Date;


@Data
public class CreateUserRequestDto {
    @NotBlank(message = "First name should not be empty or null")
    private String firstName;
    @NotBlank(message = "Last name should not be empty or null")
    private String lastName;
    @NotBlank(message = "Email should not be empty or null")
    private String email;
    @NotBlank(message = "Mobile should not be empty or null")
    private String mobileNumber;
    @NotNull(message = "Date of birth number should not be empty or null")
    private Date dateOfBirth;
    @NotBlank(message = "Password should not be empty or null")
    private String password;
    @NotBlank(message = "Confirm password should not be empty or null")
    private String confirmPassword;
}
