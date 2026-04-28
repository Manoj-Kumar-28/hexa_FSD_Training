package com.example.car_rental.dto;

import com.example.car_rental.enums.Role;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record UserReqDTO(
        String firstName,
        String lastName,

        @NotBlank
        @NotNull
        String email,

        @NotBlank
        @NotNull
        @Size(min = 3, max = 50)
        String username,
        @NotBlank
        @NotNull
        String password,
        @NotBlank
        @NotNull
        String phoneNumber

) {
}

