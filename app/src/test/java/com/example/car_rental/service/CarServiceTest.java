package com.example.car_rental.service;

import com.example.car_rental.dto.CarRespDto;
import com.example.car_rental.dto.FilterReqBody;
import com.example.car_rental.enums.BookingStatus;
import com.example.car_rental.enums.CarStatus;
import com.example.car_rental.exception.ResourceNotFoundException;
import com.example.car_rental.model.Car;
import com.example.car_rental.model.CarDetails;
import com.example.car_rental.model.Documents;
import com.example.car_rental.model.Owner;
import com.example.car_rental.repository.BookingRepository;
import com.example.car_rental.repository.CarRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CarServiceTest {

    @InjectMocks
    @Spy
    private CarService carService;
    @Mock
    private CarRepository carRepository;
    @Mock
    private BookingRepository bookingRepository;
    @Test
    public void getCarByIdTestIfExists() {

        Assertions.assertNotNull(carService);

        Car car=new Car();
        Owner owner=new Owner();
        CarDetails carDetails=new CarDetails();
        Documents documents=new Documents();
        documents.setCarImage("test.img");

        car.setDocuments(documents);

        owner.setName("Test");
        owner.setPhoneNumber(12234);

        carDetails.setColor("Test");
        carDetails.setFuelType("Test");
        carDetails.setSeats(4);
        car.setCarNumber(1L);
        car.setBrand("Test");
        car.setModel("Test");
        car.setCarStatus(CarStatus.AVAILABLE);
        car.setPricePerDay(2000);

        car.setCarDetails(carDetails);
        car.setOwner(owner);

        when(carRepository.findById(1L)).thenReturn(Optional.of(car));
        when(bookingRepository.findByStatus(BookingStatus.ACTIVE))
                .thenReturn(List.of());

        CarRespDto dto=new CarRespDto(
                car.getId(),
                car.getCarNumber(),
                car.getBrand(),
                car.getModel(),
                car.getCarStatus(),
                car.getPricePerDay(),
                car.getLocation(),
                car.getCarDetails().getFuelType(),
                car.getCarDetails().getColor(),
                car.getCarDetails().getSeats(),
                car.getOwner().getName(),
                car.getOwner().getPhoneNumber(),
                car.getDocuments().getCarImage()
        );

        CarRespDto dto1=new CarRespDto(
                car.getId(),
                car.getCarNumber(),
                car.getBrand(),
                car.getModel(),
                CarStatus.INACTIVE,
                car.getPricePerDay(),
                car.getLocation(),
                car.getCarDetails().getFuelType(),
                car.getCarDetails().getColor(),
                car.getCarDetails().getSeats(),
                car.getOwner().getName(),
                car.getOwner().getPhoneNumber(),
                car.getDocuments().getCarImage()
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
     void getAllCarsTest(){

        //Making multiple tickets and adding to the list
        Car car1=new Car();

        Owner owner=new Owner();

        owner.setName("Test");
        owner.setPhoneNumber(12234);

        CarDetails carDetails=new CarDetails();

        carDetails.setColor("Test");
        carDetails.setFuelType("Test");
        carDetails.setSeats(4);
        car1.setCarNumber(1L);
        car1.setBrand("Test");
        car1.setModel("Test");
        car1.setCarStatus(CarStatus.AVAILABLE);
        car1.setPricePerDay(2000);
        car1.setLocation("Hyderabad");
        car1.setCarDetails(carDetails);
        car1.setOwner(owner);

        Car car2=new Car();

        Owner owner2=new Owner();

        owner2.setName("Test");
        owner2.setPhoneNumber(12234);

        CarDetails carDetails2=new CarDetails();
        carDetails2.setColor("Test");
        carDetails2.setFuelType("Test");
        carDetails2.setSeats(4);
        car2.setCarNumber(1L);
        car2.setBrand("Test");
        car2.setModel("Test");
        car2.setCarStatus(CarStatus.AVAILABLE);
        car2.setPricePerDay(2000);
        car2.setLocation("Chennai");
        car2.setCarDetails(carDetails2);
        car2.setOwner(owner2);

        List<Car> list= List.of(car1,car2);
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
        when(bookingRepository.findByStatus(BookingStatus.ACTIVE))
                .thenReturn(List.of());
        Assertions.assertEquals(1,carService.getAllCars(0,1).data().size());


    }


    @Test
    void getCarsByFilterWithValuesReturnsFilteredCars() {

        FilterReqBody filter = new FilterReqBody(
                "Hyderabad", "Toyota", "", "Petrol", 5
        );

        Owner owner = new Owner();
        owner.setName("Test Owner");
        owner.setPhoneNumber(123456);

        Car car = new Car();
        car.setId(1L);
        car.setBrand("Toyota");
        car.setModel("Innova");
        car.setLocation("Hyderabad");
        car.setCarStatus(CarStatus.AVAILABLE);
        car.setPricePerDay(2000);

        CarDetails details = new CarDetails();
        details.setFuelType("Petrol");
        details.setSeats(5);
        details.setColor("White");

        car.setCarDetails(details);
        car.setOwner(owner);

        List<Car> cars = List.of(car);

        Mockito.doNothing().when(carService).updateCarAvailability();

        when(carRepository.getCarsByFilter(
                "Hyderabad",
                "Toyota",
                null,
                "Petrol",
                5
        )).thenReturn(cars);

        List<CarRespDto> result = carService.getCarsByFilter(filter);

        Assertions.assertEquals(1, result.size());
        Assertions.assertEquals("Toyota", result.get(0).brand());
        Assertions.assertEquals("Hyderabad", result.get(0).location());

        // verify repo call
        Mockito.verify(carRepository, Mockito.times(1))
                .getCarsByFilter("Hyderabad", "Toyota", null, "Petrol", 5);
    }
}
