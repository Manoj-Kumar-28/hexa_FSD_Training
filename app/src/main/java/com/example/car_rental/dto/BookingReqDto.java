package com.example.car_rental.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;

public record BookingReqDto(
        LocalDate pickupDateTime,
        LocalDate dropDateTime,
        String pickupLocation,
        String dropLocation,
        String paymentMethod
) {

}
