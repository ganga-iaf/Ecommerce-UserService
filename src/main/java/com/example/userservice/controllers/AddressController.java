package com.example.userservice.controllers;

import com.example.userservice.models.Address;
import com.example.userservice.repositories.AddressRepository;
import com.example.userservice.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/address")
public class AddressController {
    @Autowired
    UserRepository userRepository;
    @Autowired
    AddressRepository addressRepository;

    @GetMapping("/{addressId}")
    public ResponseEntity<Address>  findById(@PathVariable long addressId) {
        Optional<Address> address = addressRepository.findById(addressId);
        if (address.isPresent()) {
            return ResponseEntity.ok(address.get());
        }
        return ResponseEntity.notFound().build();
    }
}
