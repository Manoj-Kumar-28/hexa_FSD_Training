package com.example.car_rental.dto;

import com.example.car_rental.enums.CarStatus;

public record CarRespDto(
        long carNumber,
        String brand,
        String model,
        CarStatus carStatus,
        double pricePerDay,
        String location,
        int seats
) {
}
