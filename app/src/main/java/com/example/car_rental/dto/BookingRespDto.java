package com.example.car_rental.dto;

import com.example.car_rental.enums.BookingStatus;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record BookingRespDto(
        long bookingId,
        LocalDateTime pickupDateTime,
        LocalDateTime dropDateTime,
        String pickupLocation,
        String dropLocation,
        BigDecimal totalAmount,
        BookingStatus bookingStatus,
        long carNumber,
        String model,
        String firstName
) {
}
