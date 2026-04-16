package com.example.car_rental.mapper;

import com.example.car_rental.dto.OwnerResDto;
import com.example.car_rental.dto.OwnerSignupDto;
import com.example.car_rental.model.Buyer;
import com.example.car_rental.model.Owner;
import jakarta.validation.Valid;

public class OwnerMapper {
    public static Owner mapToEnt(@Valid OwnerSignupDto ownerSignupDto) {
        Owner owner=new Owner();
        owner.setName(ownerSignupDto.name());
        owner.setEmail(ownerSignupDto.email());
        owner.setCity(ownerSignupDto.city());
        return owner;
    }

    public static OwnerResDto mapToDto(Owner owner) {
        return  new OwnerResDto(
                owner.getId(),
                owner.getName(),
                owner.getEmail(),
                owner.getCity()
        );
    }
}
