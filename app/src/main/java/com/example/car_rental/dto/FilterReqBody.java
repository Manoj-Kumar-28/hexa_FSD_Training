package com.example.car_rental.dto;

public record FilterReqBody (
        String location,
        String brand,
        String model,
        String fuelType,
        int seats
){
}
