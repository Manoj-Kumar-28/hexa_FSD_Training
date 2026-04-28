package com.example.car_rental.dto;

import java.util.List;

public record OwnerPageResDto(
        List<OwnerResDto> data,
        long totalRecords,
        int totalPages
) {
}
