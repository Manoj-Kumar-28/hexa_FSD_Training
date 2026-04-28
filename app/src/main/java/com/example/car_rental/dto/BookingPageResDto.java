package com.example.car_rental.dto;

import java.util.List;

public record BookingPageResDto(
        List<BookingRespDto> data,
        long totalRecords,
        int totalPages
) {
}
