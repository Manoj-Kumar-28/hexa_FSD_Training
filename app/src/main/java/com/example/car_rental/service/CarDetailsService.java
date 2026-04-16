package com.example.car_rental.service;

import com.example.car_rental.dto.CarReqDto;
import com.example.car_rental.exception.ResourceNotFoundException;
import com.example.car_rental.model.CarDetails;
import com.example.car_rental.repository.CarDetailsRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class CarDetailsService {
    private final CarDetailsRepository carDetailsRepository;

    public CarDetails getDetailsById(long carDetailsId) {
        CarDetails carDetails=carDetailsRepository.findById(carDetailsId)
                .orElseThrow(()-> new ResourceNotFoundException("Invalid Id given"));
        return carDetails;
    }
}
