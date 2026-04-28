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
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
@Slf4j
public class BookingService {

    private final CarRepository carRepository;
    private final BookingRepository bookingRepository;

    private final BuyerService buyerService;
    private final CarService carService;

    public void addBooking(BookingReqDto bookingReqDto, String username, long carId) {

        log.info("Booking request started - user: {}, carId: {}", username, carId);
        Buyer buyer=buyerService.getByName(username);

        Car car=carRepository.findById(carId)
                .orElseThrow(()->new ResourceNotFoundException("User not Found"));

        if (car.getCarStatus() != CarStatus.AVAILABLE) {
            log.warn("Booking rejected - car not available: carId={}, status={}", carId, car.getCarStatus());
            throw new RuntimeException("Car is not available");
        }
        Booking booking=BookingMapper.mapToEntity(bookingReqDto);
        booking.setBuyer(buyer);
        booking.setCar(car);
        //set other details
        long days = ChronoUnit.DAYS.between(bookingReqDto.pickupDateTime(), bookingReqDto.dropDateTime());
        if (days == 0){
            days = 1; // minimum 1 day
        }
        BigDecimal total = BigDecimal.valueOf(days * car.getPricePerDay());
        booking.setTotalAmount(total);
        booking.setStatus(BookingStatus.ACTIVE);
        //recently added
        car.setCarStatus(CarStatus.BOOKED);

        bookingRepository.save(booking);
        log.info("Booking created successfully - user: {}, carId: {}, totalAmount: {}, days: {}",
                username, carId, total, days);
    }

    public BookingPageResDto getAllBookings(int page, int size) {
        carService.updateCarAvailability();
        log.info("Fetching all bookings ");

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
        log.info("Bookings fetched successfully ");
        return new BookingPageResDto(
                listDto,
                totalRecords,
                totalPages
        );
    }

    public BookingRespDto getBookingById(long id) {
        carService.updateCarAvailability();
        log.info("Fetching booking by id: {}", id);
        Booking booking=bookingRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("Invalid Id"));

        BookingRespDto bookingRespDto=BookingMapper.mapToDto(booking);
        return bookingRespDto;
    }

    public List<BookingRespDto> getBookingsByUser(String username) {
        carService.updateCarAvailability();
        log.info("Fetching bookings for user: {}", username);
        List<Booking> list=bookingRepository.getBookingsByUser(username);
        log.info("Found {} bookings for user: {}", list.size(), username);
        return list
                .stream()
                .map(BookingMapper::mapToDto)
                .toList();
    }

    public List<BookingRespDto> getBookingsByCar(long carId) {
        carService.updateCarAvailability();
        log.info("Fetching bookings for carId: {}", carId);
        List<Booking> list=bookingRepository.getBookingsByCar(carId);
        return list
                .stream()
                .map(BookingMapper::mapToDto)
                .toList();
    }

    public void cancelBooking(long bookingId,String username) {
        carService.updateCarAvailability();
        log.info("Cancel booking request - bookingId: {}", bookingId);

        Booking booking=bookingRepository.findById(bookingId)
                .orElseThrow(()->new ResourceNotFoundException("Invalid Id"));
        booking.setStatus(BookingStatus.CANCELLED);
        long carId=booking.getCar().getId();
        Optional<Car> car=carRepository.findById(carId);
        car.get().setCarStatus(CarStatus.AVAILABLE);
        log.info("Car status reset to AVAILABLE - carId: {}", carId);
        bookingRepository.save(booking);
    }


    public List<BookingRespDto> getBookingsByOwner(String name) {
        carService.updateCarAvailability();
        log.info("Generating bookings by owner");
        List<Booking> list=bookingRepository.getBookingsByOwner(name);
        return list
                .stream()
                .map(BookingMapper::mapToDto)
                .toList();
    }


    public List<BookingStatsDto> getBookingStats() {
        log.info("Generating booking statistics");
        List<Booking> bookings=bookingRepository.findAll();

        int active=bookings.stream().filter(b->
                (b.getStatus().equals(BookingStatus.ACTIVE))).toList().size();

        int completed=bookings.stream().filter(b->
                (b.getStatus().equals(BookingStatus.COMPLETED))).toList().size();

        int pending=bookings.stream().filter(b->
                (b.getStatus().equals(BookingStatus.PENDING))).toList().size();

        int cancelled=bookings.stream().filter(b->
                (b.getStatus().equals(BookingStatus.CANCELLED))).toList().size();

        BookingStatsDto bookingStatsDto1=new BookingStatsDto(
                "Active",
                active
        );
        BookingStatsDto bookingStatsDto2=new BookingStatsDto(
                "Completed",
                completed
        );
        BookingStatsDto bookingStatsDto3=new BookingStatsDto(
                "Pending",
                pending
        );
        BookingStatsDto bookingStatsDto4=new BookingStatsDto(
                "Cancelled",
                cancelled
        );
        log.info("Booking stats generated - active: {}, completed: {}, pending: {}, cancelled: {}",
                active, completed, pending, cancelled);
        return List.of(bookingStatsDto1,bookingStatsDto2,bookingStatsDto3,bookingStatsDto4);
    }

    public List<BookingRespDto> getBookingsByBuyerId(long id) {
        carService.updateCarAvailability();
        List<Booking> list1=bookingRepository.getBookingsByBuyerId(id);
        return list1
                .stream()
                .map(BookingMapper::mapToDto)
                .toList();
    }
}
