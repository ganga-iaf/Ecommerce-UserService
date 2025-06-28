package com.example.userservice.repositories;

import com.example.userservice.models.Address;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface AddressRepository extends JpaRepository<Address,Long> {
    List<Address> findByUser_Id(long userId);
    Optional<Address> findById(long id);
}
