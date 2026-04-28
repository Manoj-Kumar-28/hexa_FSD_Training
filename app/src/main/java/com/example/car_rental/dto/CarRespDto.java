package com.example.car_rental.dto;

import com.example.car_rental.enums.CarStatus;

public record CarRespDto(
        long id,
        long carNumber,
        String brand,
        String model,
        CarStatus carStatus,
        double pricePerDay,
        String location,
        String fuelType,
        String color,
        int seats,
        String ownerName,
        long phoneNumber,

        String carImage
) {
}
