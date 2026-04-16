package com.example.car_rental.mapper;

import com.example.car_rental.dto.BuyerResDto;
import com.example.car_rental.dto.BuyerSignupDto;
import com.example.car_rental.model.Buyer;
import jakarta.validation.Valid;

public class BuyerMapper {
    public static Buyer mapToEnt(@Valid BuyerSignupDto buyerSignupDto) {
        Buyer buyer=new Buyer();
        buyer.setName(buyerSignupDto.name());
        buyer.setEmail(buyerSignupDto.email());
        buyer.setCity(buyerSignupDto.city());
        return buyer;
    }

    public static BuyerResDto mapToDto(Buyer buyer) {
        return  new BuyerResDto(
                buyer.getId(),
                buyer.getName(),
                buyer.getEmail(),
                buyer.getCity()
        );
    }
}
