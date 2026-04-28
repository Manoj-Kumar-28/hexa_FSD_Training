package com.example.car_rental.controller;

import com.example.car_rental.dto.*;
import com.example.car_rental.service.BookingService;
import com.example.car_rental.service.UserService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/booking")
@CrossOrigin(origins = "http://localhost:5173/")
@Slf4j
public class BookingController {
    private final BookingService bookingService;


    @PostMapping("/add/{carId}")
    public ResponseEntity<?> addBooking(@RequestBody BookingReqDto bookingReqDto,
                                     Principal principal,
                                     @PathVariable long carId) {
        log.info("Booking creation request received - user: {}, carId: {}", principal.getName(), carId);
        bookingService.addBooking(bookingReqDto,principal.getName(),carId);
        log.info("Booking successfully created - user: {}, carId: {}", principal.getName(), carId);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping("/get-all")

    public BookingPageResDto getAllBookings(@RequestParam(value = "page",required = false,defaultValue = "0") int page,
                                        @RequestParam(value = "size",required = false,defaultValue = "5") int size){
        log.info("Fetching all bookings - page: {}, size: {}", page, size);
        return bookingService.getAllBookings(page,size);
    }

    @GetMapping("/get/{id:\\d+}")
    public BookingRespDto getBookingById(@PathVariable long id){
        log.info("Fetching booking by id: {}", id);

        return bookingService.getBookingById(id);
    }

    @GetMapping("/get/buyer/v2")
    public List<BookingRespDto> getBookingsByUser(Principal principal) {
        log.info("Fetching bookings for user: {}", principal.getName());
        return bookingService.getBookingsByUser(principal.getName());
    }

    @GetMapping("/get/buyer/{id}/v1")
    public List<BookingRespDto> getBookingsByBuyerId(@PathVariable long id) {
        log.info("Fetching bookings for user");
        return bookingService.getBookingsByBuyerId(id);
    }

    @GetMapping("/car/{carId}")
    public List<BookingRespDto> getBookingsByCar(@PathVariable long carId) {
        log.info("Fetching bookings for carId: {}", carId);
        return bookingService.getBookingsByCar(carId);
    }

    //returns nothing as cancellation returns nothing
    @PutMapping("/cancel/{bookingId}")
    public ResponseEntity<?> cancelBooking(@PathVariable long bookingId,Principal principal) {
        log.info("Cancel booking request received - bookingId: {}, user: {}", bookingId, principal.getName());
        bookingService.cancelBooking(bookingId,principal.getName());
        log.info("Booking cancelled successfully - bookingId: {}, user: {}", bookingId, principal.getName());
        return  ResponseEntity.status(HttpStatus.ACCEPTED).build();
    }
}
