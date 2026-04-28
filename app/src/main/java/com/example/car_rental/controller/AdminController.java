package com.example.car_rental.controller;

import com.example.car_rental.dto.*;

import com.example.car_rental.service.*;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api/admin")
@AllArgsConstructor
@CrossOrigin(origins = "http://localhost:5173/")
@Slf4j
public class AdminController {
    private final UserService userService;
    private final CarService carService;
    private final BuyerService buyerService;
    private final OwnerService ownerService;
    private final AdminService adminService;
    private final BookingService bookingService;

    @PostMapping("/add")
    public void addAdmin(@RequestBody AdminReqDto adminReqDto) {
        log.info("Request received to add new admin");
        adminService.addAdmin(adminReqDto);
        log.info("Admin added successfully");
    }

    //Get All Users
    @GetMapping("/get-all")
    public UserPageResDto getAllUsers(@RequestParam(value = "page",required = false,defaultValue = "0") int page,
                                      @RequestParam(value = "size",required = false,defaultValue = "5") int size){
        log.info("Fetching all users - page: {}, size: {}", page, size);
        return userService.getAllUsers(page,size);

    }

    //Get User By Id
    @GetMapping("/user/{id}")
    public UserRespDto getUserbyId(@PathVariable long id){
        log.info("Fetching user by id: {}", id);
        return userService.getUserbyId(id);
    }


    //Get all Cars
    @GetMapping("/cars")
    public CarPageResDto getAllCars(@RequestParam(value = "page",required = false,defaultValue = "0") int page,
                                    @RequestParam(value = "size",required = false,defaultValue = "5") int size){
        log.info("Fetching all cars - page: {}, size: {}", page, size);
        return carService.getAllCars(page,size);
    }
    //Get cars by id
    @GetMapping("/car/{id}")
    public CarRespDto getCarById(@PathVariable long id){
        log.info("Fetching car by id: {}", id);
        return carService.getCarById(id);
    }

    //Get list of buyers
    @GetMapping("/buyers")
    public BuyerPageResDto getAllBuyers(@RequestParam(value = "page",required = false,defaultValue = "0") int page,
                                        @RequestParam(value = "size",required = false,defaultValue = "5") int size){
        log.info("Fetching all buyers - page: {}, size: {}", page, size);
        return buyerService.getAllBuyers(page,size);
    }

    //Get list of Owners
    @GetMapping("/owners")
    public OwnerPageResDto getAllOwners(@RequestParam(value = "page",required = false,defaultValue = "0") int page,
                                        @RequestParam(value = "size",required = false,defaultValue = "5") int size){
        log.info("Fetching all owners - page: {}, size: {}", page, size);
        return ownerService.getAllOwners(page,size);
    }

    @GetMapping("/get-one")
    public AdminResDto getAdminByUsername(Principal principal){
        String username = principal.getName();
        log.info("Fetching admin details for username: {}", username);
        return adminService.getAdminByUsername(username);
    }

    @GetMapping("/stats")
    public AdminStatsDto getStats(){
        log.info("Fetching admin statistics");
        return adminService.getStats();
    }

    @GetMapping("/owner/pending")
    public List<OwnerResDto> getAllPendingOwners(){
        log.info("Fetching all pending owners");
        return ownerService.getAllPendingOwners();
    }

    @PutMapping("/owner/{id}/status")
    public ResponseEntity<?> updateOwnerStatus(@PathVariable long id, @RequestParam String status){
        log.info("Updating owner status - id: {}, status: {}", id, status);
        ownerService.updateOwnerStatus(id,status);
        log.info("Owner status updated successfully - id: {}", id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/bookingStats")
    public List<BookingStatsDto> getBookingStats(){
        log.info("Fetching booking statistics");
        return bookingService.getBookingStats();
    }

    @GetMapping("/owner/cars/{id}/v1")
    public List<CarRespDto> getCarsByOwnerId(@PathVariable long id) {

        log.info("Fetching cars for owner");
        List<CarRespDto> cars = carService.getCarsByOwnerId(id);
        log.info("Fetched cars for owner");
        return cars;
    }
}
