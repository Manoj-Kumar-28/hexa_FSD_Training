package com.example.car_rental.service;


import com.example.car_rental.dto.BookingStatsDto;
import com.example.car_rental.enums.BookingStatus;
import com.example.car_rental.model.Booking;
import com.example.car_rental.repository.BookingRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class BookingServiceTest {

    @InjectMocks
    private BookingService bookingService;

    @Mock
    private BookingRepository bookingRepository;

    @Mock
    private CarService carService;

    @Test
    void getBookingStatsTest() {

        Booking b1 = new Booking();
        b1.setStatus(BookingStatus.ACTIVE);

        Booking b2 = new Booking();
        b2.setStatus(BookingStatus.COMPLETED);

        Booking b3 = new Booking();
        b3.setStatus(BookingStatus.CANCELLED);

        Booking b4 = new Booking();
        b4.setStatus(BookingStatus.PENDING);

        Booking b5 = new Booking();
        b5.setStatus(BookingStatus.ACTIVE);

        List<Booking> bookings = List.of(b1, b2, b3, b4, b5);

        when(bookingRepository.findAll()).thenReturn(bookings);

        List<BookingStatsDto> result = bookingService.getBookingStats();

        Assertions.assertEquals(4, result.size());

        Assertions.assertEquals("Active", result.get(0).status());
        Assertions.assertEquals(2, result.get(0).count());

        Assertions.assertEquals("Completed", result.get(1).status());
        Assertions.assertEquals(1, result.get(1).count());

        Assertions.assertEquals("Pending", result.get(2).status());
        Assertions.assertEquals(1, result.get(2).count());

        Assertions.assertEquals("Cancelled", result.get(3).status());
        Assertions.assertEquals(1, result.get(3).count());

        Mockito.verify(bookingRepository, Mockito.times(1)).findAll();
        Mockito.verify(carService, Mockito.times(2)).updateCarAvailability();
    }
}