package com.example.car_rental.dto;

public record AdminStatsDto(
        int totalCars,
        int totalUsers,
        int totalOwners,
        int totalBuyers,
        int totalBookings,
        double revenue
) {
}
