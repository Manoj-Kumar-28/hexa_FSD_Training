package com.example.car_rental.controller;

import com.example.car_rental.dto.BuyerSignupDto;
import com.example.car_rental.dto.CarReqDto;
import com.example.car_rental.dto.CarRespDto;
import com.example.car_rental.dto.OwnerSignupDto;
import com.example.car_rental.service.BuyerService;
import com.example.car_rental.service.CarService;
import com.example.car_rental.service.OwnerService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api/owner")
@AllArgsConstructor
public class OwnerController {

    private final OwnerService ownerService;
    private final CarService carService;

    @PostMapping("/sign-up")
    public ResponseEntity<?> addCustomerSignup(@Valid @RequestBody OwnerSignupDto ownerSignupDto){
        ownerService.addOwnerSignup(ownerSignupDto);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .build();
    }


    // ✅ ADD CAR (OWNER)
    @PostMapping("/add")
    public ResponseEntity<?> addCar(@Valid @RequestBody CarReqDto carReqDto,
                                    Principal principal) {

        carService.addCar(carReqDto ,principal.getName());

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping("/my-cars")
    public List<CarRespDto> getMyCars(Principal principal) {
        return carService.getMyCars(principal.getName());
    }

    //Getting his own car by id
    @GetMapping("/get/{id}")
    public CarRespDto getCarById(@PathVariable long id){
        return carService.getCarById(id);
    }

    //UPDATE OWNER CAR
    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateCar(@PathVariable Long id,
                                       @RequestBody CarReqDto carReqDto,
                                       Principal principal) {

        carService.updateCar(id, carReqDto, principal.getName());
        return ResponseEntity.status(HttpStatus.ACCEPTED).build();
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteCarById(@RequestParam long id,Principal principal) {
        carService.deleteCarById(id,principal.getName());
        return ResponseEntity.status(HttpStatus.GONE).build();
    }
}


