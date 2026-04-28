package com.example.car_rental.dto;

import com.example.car_rental.enums.Role;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public record UserRespDto(
        long id,
        String username,
        Role role,
        LocalDate createdAt
) {
}
