package com.example.car_rental.dto;

import com.example.car_rental.enums.CarStatus;

public record OwnerStatsResDto(
        int totalCars,
        int totalBookings,
        double totalRevenue,
        int available,
        int booked,
        int inactive
) {
}
