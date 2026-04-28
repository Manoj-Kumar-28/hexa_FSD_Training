package com.example.car_rental.service;

import com.example.car_rental.dto.CarPageResDto;
import com.example.car_rental.dto.CarReqDto;
import com.example.car_rental.dto.CarRespDto;
import com.example.car_rental.dto.FilterReqBody;
import com.example.car_rental.enums.BookingStatus;
import com.example.car_rental.enums.CarStatus;
import com.example.car_rental.exception.ResourceNotFoundException;
import com.example.car_rental.mapper.CarDetailsMapper;
import com.example.car_rental.mapper.CarMapper;
import com.example.car_rental.model.Booking;
import com.example.car_rental.model.Car;
import com.example.car_rental.model.CarDetails;
import com.example.car_rental.model.Owner;
import com.example.car_rental.repository.BookingRepository;
import com.example.car_rental.repository.CarDetailsRepository;
import com.example.car_rental.repository.CarRepository;
import com.example.car_rental.repository.OwnerRepository;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@AllArgsConstructor
@Slf4j
public class CarService {
    private  final CarDetailsService carDetailsService;
    private final CarRepository carRepository;
    private final OwnerRepository ownerRepository;
    private final BookingRepository bookingRepository;

    public Car addCar(@Valid CarReqDto carReqDto, String username) {
        log.info("Add car request started - owner: {}", username);

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
        log.info("Car added successfully, owner: {}", username);

        //save car
        return carRepository.save(car);
    }


    public CarPageResDto getAllCars(int page, int size) {
        updateCarAvailability();
        log.info("Fetching all cars - page: {}, size: {}", page, size);

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
        log.info("Cars fetched successfully - total: {}", pageTicket.getTotalElements());
        return new CarPageResDto(
                listDto,
                totalRecords,
                totalPages
        );
    }

    public CarRespDto getCarById(long id) {
        updateCarAvailability();
        log.info("Fetching car by id: {}", id);
        Car car=carRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("Invalid Id"));
        CarRespDto carRespDto=CarMapper.mapToDto(car);
        return carRespDto;
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
        log.info("Update car request started - carId: {}, owner: {}", id, username);

        // Fetch existing car
        Car car = carRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Car not found with given id"));
        // Ownership check
        if (!car.getOwner().getUser().getUsername().equals(username)) {
            log.warn("Unauthorized update attempt - carId: {}, user: {}", id, username);
            throw new RuntimeException("Unauthorized");
        }

        CarMapper.updateEntity(car,carReqDto);
        CarDetails carDetails= car.getCarDetails();
        carDetailsService.updateEntity(carDetails,carReqDto);
        // Update fields
        car.setCarStatus(CarStatus.AVAILABLE);
        carDetails.setCar(car);
        car.setCarDetails(carDetails);

        //Save car
        carRepository.save(car);
        log.info("Car updated successfully - carId: {}, owner: {}", id, username);
    }

    public List<CarRespDto> getMyCars(String username) {
        updateCarAvailability();
        log.info("Fetching cars for owner: {}", username);
        Owner owner = ownerRepository.getOwnerByUsername(username);

        List<Car> cars=carRepository.findAllByOwner(owner);
        List<CarRespDto> listDto=cars
                .stream()
                .map(CarMapper::mapToDto)
                .toList();
        log.info("Owner {} has {} cars", username, cars.size());
        return listDto;
    }

    public void deleteCarById(long id, String username) {
        log.info("Delete car request started - carId: {}, user: {}", id, username);
        Car car = carRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Car not found with given id"));
        if(!car.getOwner().getUser().getUsername().equals(username)) {
            log.warn("Unauthorized delete attempt - carId: {}, user: {}", id, username);
            throw new RuntimeException("Unauthorized");
        }
        if (bookingRepository.existsByCarId(id)) {
            log.warn("Delete blocked - active bookings exist for carId: {}", id);
            throw new RuntimeException("Cannot delete car with active bookings");
        }
        carRepository.deleteById(id);
        log.info("Car deleted successfully - carId: {}", id);
    }



    public List<CarRespDto> getCarsIfAvail() {
        updateCarAvailability();
        log.info("Fetching available cars");

        List<Car> cars = carRepository
                .getCarsIfAvail(CarStatus.AVAILABLE);
        log.info("Available cars found: {}", cars.size());

        return cars.stream()
                .map(CarMapper::mapToDto)
                .toList();
    }

    public List<CarRespDto> getCarsByFilter(FilterReqBody filterReqBody) {
        updateCarAvailability();
        log.info("Car filter request received");

        if (
                filterReqBody.location().isEmpty() &&
                        filterReqBody.brand().isEmpty() &&
                        filterReqBody.model().isEmpty() &&
                        filterReqBody.fuelType().isEmpty() &&
                        filterReqBody.seats() == 0
        ) {
            log.warn("Empty filter request received - returning empty result");
            return List.of();
        }

        String location = filterReqBody.location().isEmpty() ? null : filterReqBody.location();
        String brand = filterReqBody.brand().isEmpty() ? null : filterReqBody.brand();
        String model = filterReqBody.model().isEmpty() ? null : filterReqBody.model();
        String fuelType = filterReqBody.fuelType().isEmpty() ? null : filterReqBody.fuelType();
        Integer seats = filterReqBody.seats() == 0 ? null : filterReqBody.seats();

        List<Car> cars = carRepository.getCarsByFilter(location, brand, model, fuelType,seats);
        log.info("Filtered cars result count: {}", cars.size());

        return cars.stream()
                .map(CarMapper::mapToDto)
                .toList();
    }

    public List<CarRespDto> getCarsByOwnerId(long id) {
        updateCarAvailability();
        List<Car> list=carRepository.getCarsByOwnerId(id);
        return list.stream()
                .map(CarMapper::mapToDto)
                .toList();
    }

    public void updateCarAvailability() {
        List<Booking> activeBookings = bookingRepository.findByStatus(BookingStatus.ACTIVE);

        for (Booking b : activeBookings) {
            if (b.getDropDateTime().isBefore(LocalDate.now()) ||
                    b.getDropDateTime().isEqual(LocalDate.now())) {

                b.setStatus(BookingStatus.COMPLETED);

                Car car = b.getCar();
                car.setCarStatus(CarStatus.AVAILABLE);

                bookingRepository.save(b);
                carRepository.save(car);
            }
        }
    }
    public void makeCarInactive(Long carId) {

        Car car = carRepository.findById(carId)
                .orElseThrow(() -> new RuntimeException("Car not found"));

        car.setCarStatus(CarStatus.INACTIVE);

        carRepository.save(car);
    }

    public List<CarRespDto> getAvailableCars(LocalDate startDate, LocalDate endDate) {

        List<Car> cars;

        if (startDate == null || endDate == null) {
            cars = carRepository.findAll();
        } else {
            cars = carRepository.findAvailableCars(startDate, endDate);
        }
        return cars.stream().map(CarMapper::mapToDto).toList();
    }
}
