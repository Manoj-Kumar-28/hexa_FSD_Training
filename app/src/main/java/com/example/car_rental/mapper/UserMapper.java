package com.example.car_rental.mapper;

import com.example.car_rental.dto.*;
import com.example.car_rental.model.User;
import jakarta.validation.Valid;

import java.time.LocalDate;
import java.util.List;

public class UserMapper {

    public static User mapToEntity(UserReqDTO userReqDTO) {
        User user=new User();

        user.setUsername(userReqDTO.username());
        user.setPassword(userReqDTO.password());

        return user;
    }

    public static UserRespDto mapToDto(User user) {
        return new UserRespDto(
                user.getId(),
                user.getUsername(),
                user.getRole(),
                user.getCreatedAt()
        );
    }


    public static User mapToEnt(@Valid BuyerSignupDto buyerSignupDto) {
        User user=new User();

        user.setUsername(buyerSignupDto.userName());
        user.setPassword(buyerSignupDto.password());
        user.setCreatedAt(LocalDate.now());
        user.setUpdatedAt(LocalDate.now());

        return user;
    }

    public static User mapToEnti(@Valid OwnerSignupDto ownerSignupDto) {
        User user=new User();

        user.setUsername(ownerSignupDto.userName());
        user.setPassword(ownerSignupDto.password());

        return user;
    }

}
