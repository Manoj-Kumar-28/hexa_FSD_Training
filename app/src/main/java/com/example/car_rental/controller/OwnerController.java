package com.example.car_rental.controller;

import com.example.car_rental.dto.*;
import com.example.car_rental.mapper.OwnerMapper;
import com.example.car_rental.model.Car;
import com.example.car_rental.model.Owner;
import com.example.car_rental.service.BookingService;
import com.example.car_rental.service.BuyerService;
import com.example.car_rental.service.CarService;
import com.example.car_rental.service.OwnerService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api/owner")
@AllArgsConstructor
@CrossOrigin(origins = "http://localhost:5173/")
@Slf4j
public class OwnerController {

    private final OwnerService ownerService;
    private final CarService carService;
    private final BookingService bookingService;

    @PostMapping("/sign-up")
    public ResponseEntity<?> addCustomerSignup(@Valid @RequestBody OwnerSignupDto ownerSignupDto){
        log.info("Owner signup request received");
        ownerService.addOwnerSignup(ownerSignupDto);
        log.info("Owner signup completed successfully");
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .build();
    }


    // ADD CAR (OWNER)
    @PostMapping("/add")
    public ResponseEntity<?> addCar(@Valid @RequestBody CarReqDto carReqDto,
                                    Principal principal) {
        log.info("Add car request received - owner: {}", principal.getName());
        Car savedCar=carService.addCar(carReqDto ,principal.getName());
        log.info("Car added successfully - owner: {}, carId: {}", principal.getName(), savedCar.getId());
        return ResponseEntity.status(HttpStatus.CREATED).body(savedCar);
    }

    @GetMapping("/my-cars/v2")
    public List<CarRespDto> getMyCars(Principal principal) {
        String username = principal.getName();

        log.info("Fetching cars for owner: {}", username);
        List<CarRespDto> cars = carService.getMyCars(username);
        log.info("Fetched cars for owner: {}", username);
        return cars;
    }

    //Getting his own car by id
    @GetMapping("/get/{id}")
    public CarRespDto getCarById(@PathVariable long id){
        log.info("Fetching car by id: {}", id);
        return carService.getCarById(id);
    }

    //UPDATE OWNER CAR
    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateCar(@PathVariable Long id, @RequestBody CarReqDto carReqDto, Principal principal) {

        String username = principal.getName();
        log.info("Update car request received - owner: {}, carId: {}", username, id);

        carService.updateCar(id, carReqDto, username);

        log.info("Car updated successfully - owner: {}, carId: {}", username, id);
        return ResponseEntity.status(HttpStatus.ACCEPTED).build();
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteCarById(@PathVariable long id,Principal principal) {
        log.info("Delete car request received - owner");
        carService.deleteCarById(id,principal.getName());
        log.info("Car deleted successfully - owner");
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @GetMapping("/get-one")
    public OwnerResDto getOwnerByName(Principal principal){
        String username=principal.getName();
        log.info("Fetching owner profile - username: {}", username);
        Owner owner=ownerService.getOwnerByName(username);
        log.info("Owner profile fetched successfully - username: {}", username);
        return OwnerMapper.mapToDto(owner);
    }

    @GetMapping("/stats")
    public OwnerStatsResDto getOwnerStats(Principal principal){
        String username=principal.getName();
        log.info("Fetching owner stats - username: {}", username);
        return ownerService.getOwnerStats(username);
    }


    @GetMapping("/my-bookings")
    public List<BookingRespDto> getMyBookings(Principal principal) {
        log.info("Fetching bookings for owner: {}", principal.getName());
        return bookingService.getBookingsByOwner(principal.getName());
    }


    @PutMapping("/cars/{id}/inactive")
    public ResponseEntity<String> makeCarInactive(@PathVariable Long id) {

        carService.makeCarInactive(id);

        return ResponseEntity.ok("Car marked as INACTIVE");
    }
}


