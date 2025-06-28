package com.example.userservice.models;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Entity
@Getter
@Setter
public class Token extends BaseModel {
    private String token;
    private Date expiryAt;
    @ManyToOne
    private User  user;
}
