package com.example.car_rental.dto;

import java.time.LocalDateTime;

public record BookingReqDto(
        LocalDateTime pickupDateTime,
        LocalDateTime dropDateTime,
        String pickupLocation,
        String dropLocation
) {

}
