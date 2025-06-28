package com.example.userservice.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

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
   @Column(name = "email",unique = true)
   private String email;
   @Column(name = "mobile",unique = true)
   private String mobileNumber;
   @Column(name = "password")
   private String password;
   @Column(name = "date_of_birth")
   private Date dob;
   @Column(name = "is_suspended")
   private boolean isSuspended;
   @OneToMany(mappedBy = "user",fetch = FetchType.LAZY)
   //@Fetch(FetchMode.SUBSELECT)
   @JsonIgnore
   private List<Address> addresses;
}
