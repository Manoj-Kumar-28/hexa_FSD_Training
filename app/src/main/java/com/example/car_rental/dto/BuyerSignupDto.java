package com.example.car_rental.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record BuyerSignupDto(
        @NotBlank
        String name,
        @NotBlank
        @Email
        String email,
        String city,

        @NotBlank
        @NotNull
        @Size(min = 3, max = 20)
        String userName,
        String password
) {
}
