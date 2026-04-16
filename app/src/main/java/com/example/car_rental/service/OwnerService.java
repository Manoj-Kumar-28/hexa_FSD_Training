package com.example.car_rental.service;

import com.example.car_rental.dto.*;
import com.example.car_rental.enums.Role;
import com.example.car_rental.mapper.BuyerMapper;
import com.example.car_rental.mapper.OwnerMapper;
import com.example.car_rental.mapper.UserMapper;
import com.example.car_rental.model.Buyer;
import com.example.car_rental.model.Owner;
import com.example.car_rental.model.User;
import com.example.car_rental.repository.OwnerRepository;
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
public class OwnerService {
    private final OwnerRepository ownerRepository;
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

    public void addOwnerSignup(@Valid OwnerSignupDto ownerSignupDto) {
        Owner owner= OwnerMapper.mapToEnt(ownerSignupDto);

        User user= UserMapper.mapToEnti(ownerSignupDto);
        user.setRole(Role.OWNER);
        user.setPassword(passwordEncoder.encode(ownerSignupDto.password()));
        userService.insertUser(user);
        owner.setUser(user);

        ownerRepository.save(owner);
    }

    public OwnerPageResDto getAllOwners(int page, int size) {
        Pageable pageable= PageRequest.of(page, size);
        Page<Owner> pageTicket=ownerRepository.findAll(pageable);
        long totalRecords=pageTicket.getTotalElements();
        int totalPages= pageTicket.getTotalPages();


        List<OwnerResDto> listDto=pageTicket
                .toList()
                .stream()
                .map(OwnerMapper::mapToDto)
                .toList();

        return new OwnerPageResDto(
                listDto,
                totalRecords,
                totalPages
        );
    }
}
