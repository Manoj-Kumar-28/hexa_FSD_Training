package com.example.car_rental.mapper;

import com.example.car_rental.dto.CarReqDto;
import com.example.car_rental.dto.CarRespDto;
import com.example.car_rental.model.Car;
import jakarta.validation.Valid;

public class CarMapper {
    public static Car mapToEntity(@Valid CarReqDto carReqDto) {
        Car car=new Car();
        car.setCarNumber(carReqDto.carNumber());
        car.setBrand(carReqDto.brand());
        car.setModel(carReqDto.model());
        car.setPricePerDay(carReqDto.pricePerDay());
        car.setLocation(carReqDto.location());
        return car;

    }

    public static CarRespDto mapToDto(Car car) {
        return new CarRespDto(
                car.getCarNumber(),
                car.getBrand(),
                car.getModel(),
                car.getCarStatus(),
                car.getPricePerDay(),
                car.getLocation(),
                car.getCarDetails().getSeats()
        );
    }
}
