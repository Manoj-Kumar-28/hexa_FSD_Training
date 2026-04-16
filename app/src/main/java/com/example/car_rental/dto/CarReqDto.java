package com.example.car_rental.dto;

public record CarReqDto(
        long carNumber,
        String brand,
        String model,
        double pricePerDay,
        String location,
        String fuelType,
        String color,
        int seats

) {
}
