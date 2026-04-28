package com.example.car_rental.controller;

import com.example.car_rental.dto.*;

import com.example.car_rental.mapper.BuyerMapper;
import com.example.car_rental.model.Buyer;
import com.example.car_rental.model.Car;
import com.example.car_rental.service.BuyerService;
import com.example.car_rental.service.CarService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.time.LocalDate;
import java.util.List;

@RestController     
@RequestMapping("/api/buyer")
@AllArgsConstructor
@CrossOrigin(origins = "http://localhost:5173/")
@Slf4j
public class BuyerController {
    private final BuyerService buyerService;
    private final CarService carService;

    @PostMapping("/sign-up")
    public ResponseEntity<?> addCustomerSignup(@Valid @RequestBody BuyerSignupDto buyerSignupDto){
        log.info("Buyer signup request received");
        buyerService.addBuyerSignup(buyerSignupDto);
        log.info("Buyer signup completed successfully");
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .build();
    }


    @GetMapping("/get-all")
    public CarPageResDto getAllCars(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "6") int size) {
        log.info("Buyer requesting car list - page: {}, size: {}", page, size);
        CarPageResDto response = carService.getAllCars(page, size);

        log.info("Car list fetched successfully for buyer");

        return response;
    }

    @PostMapping("/get/filter")
    public List<CarRespDto> getCarsByFilter(@RequestBody FilterReqBody filterReqBody){
        log.info("Car filter request received with criteria: {}", filterReqBody);

        List<CarRespDto> result = carService.getCarsByFilter(filterReqBody);

        log.info("Filtered cars returned - count: {}", result.size());

        return result;

    }

    // filter cars by brand
    @GetMapping("/get/brand")
    public List<CarRespDto> getCarByBrand(@RequestParam String brand) {
        log.info("Fetching cars by brand: {}", brand);
        return carService.getCarsByBrand(brand);
    }

    // filter cars by model
    @GetMapping("/get/model")
    public List<CarRespDto> getCarByModel(@RequestParam String model) {
        log.info("Fetching cars by model: {}", model);
        return carService.getCarsByModel(model);
    }

    // filter cars by availability
    @GetMapping("/get/available")
    public List<CarRespDto> getCarsIfAvail() {
        log.info("Fetching available cars only");
        return carService.getCarsIfAvail();
    }

    @GetMapping("/get-one")
    public BuyerResDto getBuyerByName(Principal principal){
        String username = principal.getName();
        log.info("Fetching buyer profile for username: {}", username);
        Buyer buyer=buyerService.getByName(username);
        log.info("Buyer profile fetched successfully for username: {}", username);
        return BuyerMapper.mapToDto(buyer);
    }


    @GetMapping("/get/cars/available/v2")
    public List<CarRespDto> getCars(
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate
    ) {

        return carService.getAvailableCars(startDate, endDate);
    }

}
