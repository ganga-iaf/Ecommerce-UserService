package com.example.userservice.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity(name="address")
public class Address extends BaseModel {
    @Column(name = "address")
    private String address;
    @Column(name = "landmark")
    private String landmark;
    @Column(name = "street")
    private String street;
    @Column(name = "locality_city")
    private String locality;
    @Column(name = "pincode")
    private String pincode;
    @Column(name = "state")
    private String state;
    @Column(name = "country")
    private String country;
    @Column(name = "is_primary")
    private boolean isPrimary;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
}
