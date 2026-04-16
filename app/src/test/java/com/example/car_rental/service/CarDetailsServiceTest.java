package com.example.car_rental.service;

import com.example.car_rental.exception.ResourceNotFoundException;
import com.example.car_rental.model.Car;
import com.example.car_rental.model.CarDetails;
import com.example.car_rental.repository.CarDetailsRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class CarDetailsServiceTest {
    @InjectMocks
    private CarDetailsService carDetailsService;
    @Mock
    private CarDetailsRepository carDetailsRepository;
    @Test
    public void getCarDetailsByIdTestIfExists(){
        Assertions.assertNotNull(carDetailsService);

        CarDetails carDetails = new CarDetails();
        Car car = new Car();
        car.setId(1L);
        carDetails.setId(1L);
        carDetails.setSeats(3);
        carDetails.setFuelType("Fuel");
        carDetails.setColor("Blue");
        carDetails.setCar(car);

        CarDetails carDetails1 = new CarDetails();
        Car car1 = new Car();
        car.setId(2L);
        carDetails1.setId(2L);
        carDetails1.setSeats(5);
        carDetails1.setFuelType("Fuel");
        carDetails1.setColor("Red");
        carDetails1.setCar(car1);


        when(carDetailsRepository.findById(1L)).thenReturn(Optional.of(carDetails));
        Assertions.assertEquals(carDetails,carDetailsService.getDetailsById(1L));
        Assertions.assertNotEquals(carDetails1, carDetailsService.getDetailsById(1L));

        Mockito.verify(carDetailsRepository, Mockito.times(2)).findById(1L);
    }

    @Test
    public void getCarDetailsByIdTestIfNotExists(){
        //Mocking Arrangement,if 10L passes then return empty object
        when(carDetailsRepository.findById(10L)).thenReturn(Optional.empty());

        //making the call and checking if exception is thrown
        Exception e= Assertions.assertThrows(ResourceNotFoundException.class,()->{
            carDetailsService.getDetailsById(10L);
        });

        //checking if the message given by exception is equal to what we have given
        Assertions.assertEquals("Invalid Id given",e.getMessage());
    }


}
