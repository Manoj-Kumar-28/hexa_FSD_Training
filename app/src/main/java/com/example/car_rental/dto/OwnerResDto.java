package com.example.car_rental.dto;

import com.example.car_rental.enums.OwnerStatus;

public record OwnerResDto(
        long id,
        String name,
        String email,
        String city,
        long phoneNumber,
        OwnerStatus status
) {
}
