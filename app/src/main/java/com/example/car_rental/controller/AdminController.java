package com.example.car_rental.controller;

import com.example.car_rental.dto.*;

import com.example.car_rental.service.*;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin")
@AllArgsConstructor
public class AdminController {
    private final UserService userService;
    private final CarService carService;
    private final BuyerService buyerService;
    private final OwnerService ownerService;
    private final AdminService adminService;

    @PostMapping("/add")
    public void addAdmin(@RequestBody AdminReqDto adminReqDto) {
        adminService.addAdmin(adminReqDto);
    }



    //Get All Users
    @GetMapping("/get-all")
    public UserPageResDto getAllUsers(@RequestParam(value = "page",required = false,defaultValue = "0") int page,
                                      @RequestParam(value = "size",required = false,defaultValue = "5") int size){
        return userService.getAllUsers(page,size);
    }

    //Get User By Id
    @GetMapping("/user/{id}")
    public UserRespDto getUserbyId(@PathVariable long id){
        return userService.getUserbyId(id);
    }


    //Get all Cars
    @GetMapping("/cars")
    public CarPageResDto getAllCars(@RequestParam(value = "page",required = false,defaultValue = "0") int page,
                                    @RequestParam(value = "size",required = false,defaultValue = "5") int size){
        return carService.getAllCars(page,size);
    }
    //Get cars by id
    @GetMapping("/car/{id}")
    public CarRespDto getCarById(@PathVariable long id){
        return carService.getCarById(id);
    }

    //Get list of buyers
    @GetMapping("/buyers")
    public BuyerPageResDto getAllBuyers(@RequestParam(value = "page",required = false,defaultValue = "0") int page,
                                        @RequestParam(value = "size",required = false,defaultValue = "5") int size){
        return buyerService.getAllBuyers(page,size);
    }

    //Get list of Owners
    @GetMapping("/owners")
    public OwnerPageResDto getAllOwners(@RequestParam(value = "page",required = false,defaultValue = "0") int page,
                                        @RequestParam(value = "size",required = false,defaultValue = "5") int size){
        return ownerService.getAllOwners(page,size);
    }
}
