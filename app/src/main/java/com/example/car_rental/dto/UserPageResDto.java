package com.example.car_rental.dto;

import java.util.List;

public record UserPageResDto(
        List<UserRespDto> data,
        long totalRecords,
        int totalPages
) {
}
