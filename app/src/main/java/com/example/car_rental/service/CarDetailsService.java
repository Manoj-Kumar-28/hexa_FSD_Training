package com.example.car_rental.service;

import com.example.car_rental.dto.CarReqDto;
import com.example.car_rental.exception.ResourceNotFoundException;
import com.example.car_rental.model.CarDetails;
import com.example.car_rental.repository.CarDetailsRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
@Slf4j
public class CarDetailsService {
    private final CarDetailsRepository carDetailsRepository;

    public CarDetails getDetailsById(long carDetailsId) {
        log.info("Fetching car details by id: {}", carDetailsId);
        CarDetails carDetails=carDetailsRepository.findById(carDetailsId)
                .orElseThrow(()-> new ResourceNotFoundException("Invalid Id given"));
        log.info("Car details fetched successfully for id: {}", carDetailsId);
        return carDetails;
    }

    public CarDetails updateEntity(CarDetails carDetails, CarReqDto carReqDto) {
        log.info("Updating car details - carDetailsId: {}", carDetails.getId());
        carDetails.setColor(carReqDto.color());
        carDetails.setFuelType(carReqDto.fuelType());
        carDetails.setSeats(carReqDto.seats());
        log.info("Car details update completed - carDetailsId: {}", carDetails.getId());
        return carDetails;
    }
}
