package com.example.car_rental.service;

import com.example.car_rental.dto.CarPageResDto;
import com.example.car_rental.dto.CarReqDto;
import com.example.car_rental.dto.CarRespDto;
import com.example.car_rental.enums.CarStatus;
import com.example.car_rental.exception.ResourceNotFoundException;
import com.example.car_rental.mapper.CarDetailsMapper;
import com.example.car_rental.mapper.CarMapper;
import com.example.car_rental.model.Car;
import com.example.car_rental.model.CarDetails;
import com.example.car_rental.model.Owner;
import com.example.car_rental.repository.CarDetailsRepository;
import com.example.car_rental.repository.CarRepository;
import com.example.car_rental.repository.OwnerRepository;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class CarService {
    private  final CarDetailsService carDetailsService;
    private final CarRepository carRepository;
    private final OwnerRepository ownerRepository;
    private final CarDetailsRepository carDetailsRepository;

    public void addCar(@Valid CarReqDto carReqDto, String username) {

        Owner owner = ownerRepository.getOwnerByUsername(username);
        //get carDetails by id
        CarDetails carDetails= CarDetailsMapper.mapToEntity(carReqDto);

        //convert dto to entity through mapper
        //attach carReqDto and other details
        Car car= CarMapper.mapToEntity(carReqDto);

        car.setOwner(owner);
        car.setCarStatus(CarStatus.AVAILABLE);

        carDetails.setCar(car);
        car.setCarDetails(carDetails);

        //save car
        carRepository.save(car);
    }


    public CarPageResDto getAllCars(int page, int size) {

        Pageable pageable= PageRequest.of(page, size);
        Page<Car> pageTicket=carRepository.findAll(pageable);
        long totalRecords=pageTicket.getTotalElements();
        int totalPages= pageTicket.getTotalPages();
       //Converting pageticket to dtolist

        List<CarRespDto> listDto=pageTicket
                .toList()
                .stream()
                .map(CarMapper::mapToDto)
                .toList();
        return new CarPageResDto(
                listDto,
                totalRecords,
                totalPages
        );
    }

    public CarRespDto getCarById(long id) {
        Car car=carRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("Invalid Id"));
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


    public List<CarRespDto> getCarsByBrand(String brand) {

        if (brand == null || brand.isEmpty()) {
            return List.of();
        }
        List<Car> cars=carRepository.findAllByBrand(brand);
        List<CarRespDto> listDto=cars
                .stream()
                .map(CarMapper::mapToDto)
                .toList();
        return listDto;
    }

    public List<CarRespDto> getCarsByModel(String model) {
        if (model == null || model.isEmpty()) {
            return List.of();
        }
        List<Car> cars=carRepository.findAllByModel(model);
        List<CarRespDto> listDto=cars
                .stream()
                .map(CarMapper::mapToDto)
                .toList();
        return listDto;
    }

    public void updateCar(Long id, CarReqDto carReqDto, String username) {
        // Fetch existing car
        Car car = carRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Car not found with given id"));
        // Ownership check
        if (!car.getOwner().getUser().getUsername().equals(username)) {
            throw new RuntimeException("Unauthorized");
        }

        car=CarMapper.mapToEntity(carReqDto);
        CarDetails carDetails= CarDetailsMapper.mapToEntity(carReqDto);
        // Update fields
        car.setCarStatus(CarStatus.AVAILABLE);
        carDetails.setCar(car);
        car.setCarDetails(carDetails);

        //Save car
        carRepository.save(car);
    }

    public List<CarRespDto> getMyCars(String username) {
        Owner owner = ownerRepository.getOwnerByUsername(username);

        List<Car> cars=carRepository.findAllByOwner(owner);
        List<CarRespDto> listDto=cars
                .stream()
                .map(CarMapper::mapToDto)
                .toList();
        return listDto;
    }

    public void deleteCarById(long id, String username) {
        Car car = carRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Car not found with given id"));
        if(!car.getOwner().getUser().getUsername().equals(username)) {
            throw new RuntimeException("Unauthorized");
        }
        carRepository.deleteById(id);
    }



    public List<CarRespDto> getCarsIfAvail() {
        List<Car> cars = carRepository
                .getCarsIfAvail(CarStatus.AVAILABLE);

        return cars.stream()
                .map(CarMapper::mapToDto)
                .toList();
    }
}
