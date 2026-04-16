package com.example.car_rental.mapper;

import com.example.car_rental.dto.CarReqDto;
import com.example.car_rental.model.CarDetails;
import jakarta.validation.Valid;

public class CarDetailsMapper {
    public static CarDetails mapToEntity(@Valid CarReqDto carReqDto) {
        CarDetails carDetails=new CarDetails();
        carDetails.setColor(carReqDto.color());
        carDetails.setFuelType(carReqDto.fuelType());
        carDetails.setSeats(carReqDto.seats());
        return carDetails;
    }
}
