package com.example.car_rental.service;

import com.example.car_rental.dto.*;
import com.example.car_rental.enums.Role;
import com.example.car_rental.mapper.BuyerMapper;
import com.example.car_rental.mapper.UserMapper;
import com.example.car_rental.model.Buyer;
import com.example.car_rental.model.User;
import com.example.car_rental.repository.BuyerRepository;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class BuyerService {

    private final BuyerRepository buyerRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserService userService;

    public void addBuyerSignup(@Valid BuyerSignupDto buyerSignupDto) {
        //Get Buyer details from Buyer mapper
        Buyer buyer= BuyerMapper.mapToEnt(buyerSignupDto);
        //Get user details
        User user= UserMapper.mapToEnt(buyerSignupDto);
        //add extra details
        user.setRole(Role.BUYER);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user=userService.insertUser(user);
        //save user in buyer
        buyer.setUser(user);
        //save buyer in repository
        buyerRepository.save(buyer);
    }


    public BuyerPageResDto getAllBuyers(int page, int size) {
        Pageable pageable= PageRequest.of(page, size);
        Page<Buyer> pageTicket=buyerRepository.findAll(pageable);
        long totalRecords=pageTicket.getTotalElements();
        int totalPages= pageTicket.getTotalPages();


        List<BuyerResDto> listDto=pageTicket
                .toList()
                .stream()
                .map(BuyerMapper::mapToDto)
                .toList();

        return new BuyerPageResDto(
                listDto,
                totalRecords,
                totalPages
        );
    }

    public Buyer getByName(String username) {
        return buyerRepository.getByName(username);
    }
}
