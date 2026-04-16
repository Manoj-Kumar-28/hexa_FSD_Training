package com.example.car_rental.controller;

import com.example.car_rental.dto.*;
import com.example.car_rental.service.BookingService;
import com.example.car_rental.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/booking")
public class BookingController {


    private final BookingService bookingService;
    @PostMapping("/add/{carId}")
    public ResponseEntity<?> addBooking(@RequestBody BookingReqDto bookingReqDto,
                                     Principal principal,
                                     @PathVariable long carId) {
        bookingService.addBooking(bookingReqDto,principal.getName(),carId);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping("/get-all")

    public BookingPageResDto getAllBookings(@RequestParam(value = "page",required = false,defaultValue = "0") int page,
                                        @RequestParam(value = "size",required = false,defaultValue = "5") int size){
        return bookingService.getAllBookings(page,size);
    }

    @GetMapping("/get/{id}")
    public BookingRespDto getBookingById(@PathVariable long id){
        return bookingService.getBookingById(id);
    }

    @GetMapping("/get/buyer")
    public List<BookingRespDto> getBookingsByUser(Principal principal) {
        return bookingService.getBookingsByUser(principal.getName());
    }

    @GetMapping("/car/{carId}")
    public List<BookingRespDto> getBookingsByCar(@PathVariable long carId) {
        return bookingService.getBookingsByCar(carId);
    }

    //returns nothing as cancellation returns nothing
    @PutMapping("/cancel/{bookingId}")
    public ResponseEntity<?> cancelBooking(@PathVariable long bookingId) {
        bookingService.cancelBooking(bookingId);
        return  ResponseEntity.status(HttpStatus.ACCEPTED).build();
    }
}
