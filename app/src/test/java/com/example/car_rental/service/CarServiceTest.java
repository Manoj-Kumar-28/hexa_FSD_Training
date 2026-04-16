package com.example.car_rental.service;

import com.example.car_rental.dto.CarRespDto;
import com.example.car_rental.enums.CarStatus;
import com.example.car_rental.exception.ResourceNotFoundException;
import com.example.car_rental.model.Car;
import com.example.car_rental.model.CarDetails;
import com.example.car_rental.repository.CarRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class CarServiceTest {

    @InjectMocks
    private CarService carService;
    @Mock
    private CarRepository carRepository;
    @Test
    public void getCarByIdTestIfExists() {

        Assertions.assertNotNull(carService);

        Car car=new Car();

        CarDetails carDetails=new CarDetails();

        carDetails.setSeats(4);
        car.setCarNumber(1L);
        car.setBrand("Test");
        car.setModel("Test");
        car.setCarStatus(CarStatus.AVAILABLE);
        car.setPricePerDay(2000);

        car.setCarDetails(carDetails);

        when(carRepository.findById(1L)).thenReturn(Optional.of(car));

        CarRespDto dto=new CarRespDto(
                car.getCarNumber(),
                car.getBrand(),
                car.getModel(),
                car.getCarStatus(),
                car.getPricePerDay(),
                car.getLocation(),
                car.getCarDetails().getSeats()
        );

        CarRespDto dto1=new CarRespDto(
                car.getCarNumber(),
                car.getBrand(),
                car.getModel(),
                CarStatus.INACTIVE,
                car.getPricePerDay(),
                car.getLocation(),
                car.getCarDetails().getSeats()
        );

        Assertions.assertEquals(dto,carService.getCarById(1L));
        Assertions.assertNotEquals(dto1,carService.getCarById(1L));

        Mockito.verify(carRepository, Mockito.times(2)).findById(1L);
    }

    @Test
    public void getCarByIdTestWhenNotExists(){

        //Mocking Arrangement,if 10L passes then return empty object
        when(carRepository.findById(10L)).thenReturn(Optional.empty());

        //making the call and checking if exception is thrown
        Exception e=Assertions.assertThrows(ResourceNotFoundException.class,()->{
            carService.getCarById(10L);
        });

        //checking if the message given by exception is equal to what we have given
        Assertions.assertEquals("Invalid Id",e.getMessage());
    }

    @Test
    public void getAllTicketsTeest(){

        //Making multiple tickets and adding to the list
        Car car=new Car();

        CarDetails carDetails=new CarDetails();

        carDetails.setSeats(4);
        car.setCarNumber(1L);
        car.setBrand("Test");
        car.setModel("Test");
        car.setCarStatus(CarStatus.AVAILABLE);
        car.setPricePerDay(2000);
        car.setLocation("Hyderabad");
        car.setCarDetails(carDetails);

        Car car2=new Car();

        CarDetails carDetails2=new CarDetails();
        carDetails.setSeats(4);
        car2.setCarNumber(1L);
        car2.setBrand("Test");
        car2.setModel("Test");
        car2.setCarStatus(CarStatus.AVAILABLE);
        car2.setPricePerDay(2000);
        car2.setLocation("Chennai");
        car2.setCarDetails(carDetails2);

        List<Car> list= List.of(car,car2);
        /*
        //creating a page ticket for page
        Page<Car> pageTicket=new PageImpl<>(list);
        int page=0;
        int size=2;

        Pageable pageable= PageRequest.of(page,size);

        when(carRepository.findAll(pageable)).thenReturn(pageTicket);
        Assertions.assertEquals(2,carService.getAllCars(0,2).data().size());
        */


        Page<Car> pageTicket1=new PageImpl<>(list.subList(0,1));
        int page=0;
        int size=1;

        Pageable pageable1= PageRequest.of(page,size);

        when(carRepository.findAll(pageable1)).thenReturn(pageTicket1);
        Assertions.assertEquals(1,carService.getAllCars(0,1).data().size());


    }
}
