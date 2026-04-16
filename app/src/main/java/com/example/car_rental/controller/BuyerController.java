package com.example.car_rental.controller;

import com.example.car_rental.dto.BuyerSignupDto;

import com.example.car_rental.dto.CarPageResDto;
import com.example.car_rental.dto.CarRespDto;
import com.example.car_rental.service.BuyerService;
import com.example.car_rental.service.CarService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/buyer")
@AllArgsConstructor
public class BuyerController {
    private final BuyerService buyerService;
    private final CarService carService;

    @PostMapping("/sign-up")
    public ResponseEntity<?> addCustomerSignup(@Valid @RequestBody BuyerSignupDto buyerSignupDto){
        buyerService.addBuyerSignup(buyerSignupDto);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .build();
    }


    @GetMapping("/get-all")
    public CarPageResDto getAllCars(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size) {

        return carService.getAllCars(page, size);
    }


    // filter cars by brand
    @GetMapping("/get/brand")
    public List<CarRespDto> getCarByBrand(@RequestParam String brand) {
        return carService.getCarsByBrand(brand);
    }

    // filter cars by model
    @GetMapping("/get/model")
    public List<CarRespDto> getCarByModel(@RequestParam String model) {
        return carService.getCarsByModel(model);
    }

    // filter cars by availability
    @GetMapping("/get/available")
    public List<CarRespDto> getCarsIfAvail() {
        return carService.getCarsIfAvail();
    }


}
