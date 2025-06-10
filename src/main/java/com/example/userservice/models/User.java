package com.example.userservice.models;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@Entity(name = "users")
public class User extends BaseModel {
   @Column(name = "first_name")
   private String firstName;
   @Column(name = "last_name")
   private String lastName;
   @Column(name = "email")
   private String email;
   @Column(name = "mobile")
   private String mobileNumber;
   @Column(name = "password")
   private String password;
   @Column(name = "date_of_birth")
   private Date dob;
   @Column(name = "is_suspended")
   private boolean isSuspended;
   @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
   private List<Address> addresses;
}
