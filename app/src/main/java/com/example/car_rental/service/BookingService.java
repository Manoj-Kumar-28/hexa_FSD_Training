package com.example.car_rental.service;

import com.example.car_rental.dto.*;
import com.example.car_rental.enums.BookingStatus;
import com.example.car_rental.enums.CarStatus;
import com.example.car_rental.exception.ResourceNotFoundException;
import com.example.car_rental.mapper.BookingMapper;
import com.example.car_rental.model.Booking;
import com.example.car_rental.model.Buyer;
import com.example.car_rental.model.Car;
import com.example.car_rental.model.User;
import com.example.car_rental.repository.BookingRepository;
import com.example.car_rental.repository.CarRepository;
import com.example.car_rental.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.Duration;
import java.util.List;

@Service
@AllArgsConstructor
public class BookingService {

    private final UserRepository userRepository;
    private final CarRepository carRepository;
    private final BookingRepository bookingRepository;
    private final UserService userService;
    private final BuyerService buyerService;


    public void addBooking(BookingReqDto bookingReqDto, String username, long carId) {

        Buyer buyer=buyerService.getByName(username);

        Car car=carRepository.findById(carId)
                .orElseThrow(()->new ResourceNotFoundException("User not Found"));

        if (car.getCarStatus() != CarStatus.AVAILABLE) {
            throw new RuntimeException("Car is not available");
        }
        Booking booking=BookingMapper.mapToEntity(bookingReqDto);
        booking.setBuyer(buyer);
        booking.setCar(car);
        //set other details
        long days = Duration.between(bookingReqDto.pickupDateTime(), bookingReqDto.dropDateTime()).toDays();
        if (days == 0){
            days = 1; // minimum 1 day
        }
        BigDecimal total = BigDecimal.valueOf(days * car.getPricePerDay());
        booking.setTotalAmount(total);
        booking.setStatus(BookingStatus.PENDING);

        bookingRepository.save(booking);

    }

    public BookingPageResDto getAllBookings(int page, int size) {

        Pageable pageable= PageRequest.of(page, size);
        Page<Booking> pageTicket=bookingRepository.findAll(pageable);
        long totalRecords=pageTicket.getTotalElements();
        int totalPages= pageTicket.getTotalPages();
        //Converting pageticket to dtolist

        List<BookingRespDto> listDto=pageTicket
                .toList()
                .stream()
                .map(BookingMapper::mapToDto)
                .toList();
        return new BookingPageResDto(
                listDto,
                totalRecords,
                totalPages
        );
    }

    public BookingRespDto getBookingById(long id) {
        Booking booking=bookingRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("Invalid Id"));
        return new BookingRespDto(
                booking.getBookingId(),
                booking.getPickupDateTime(),
                booking.getDropDateTime(),
                booking.getPickupLocation(),
                booking.getDropLocation(),
                booking.getTotalAmount(),
                booking.getStatus(),
                booking.getCar().getCarNumber(),
                booking.getCar().getModel(),
                booking.getBuyer().getName()
        );
    }

    public List<BookingRespDto> getBookingsByUser(String username) {
        List<Booking> list=bookingRepository.getBookingsByUser(username);
        return list
                .stream()
                .map(BookingMapper::mapToDto)
                .toList();
    }

    public List<BookingRespDto> getBookingsByCar(long carId) {
        List<Booking> list=bookingRepository.getBookingsByCar(carId);
        return list
                .stream()
                .map(BookingMapper::mapToDto)
                .toList();
    }

    public void cancelBooking(long bookingId) {
        Booking booking=bookingRepository.findById(bookingId)
                .orElseThrow(()->new ResourceNotFoundException("Invalid Id"));
        booking.setStatus(BookingStatus.CANCELLED);
        System.out.println("BookingId: "+bookingId+" cancelled successfully");
        bookingRepository.save(booking);
    }
}
