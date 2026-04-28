package com.example.car_rental.service;

import com.example.car_rental.dto.AdminReqDto;
import com.example.car_rental.dto.AdminResDto;
import com.example.car_rental.dto.AdminStatsDto;
import com.example.car_rental.enums.Role;
import com.example.car_rental.model.*;
import com.example.car_rental.repository.BookingRepository;
import com.example.car_rental.repository.CarRepository;
import com.example.car_rental.repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
@Slf4j
public class AdminService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserService userService;
    private final CarRepository carRepository;
    private final BookingRepository bookingRepository;

    public void addAdmin(AdminReqDto adminReqDto) {
        log.info("Creating new admin user with username: {}", adminReqDto.username());
        User user=new User();
        user.setUsername(adminReqDto.username());
        user.setPassword(passwordEncoder.encode(adminReqDto.password()));
        user.setRole(Role.ADMIN);
        userRepository.save(user);
        log.info("Admin user created successfully: {}", adminReqDto.username());

    }

    public AdminResDto getAdminByUsername(String username) {
        log.info("Fetching admin details for username: {}", username);
        User user=(User)userService.loadUserByUsername(username);
        log.info("Admin details fetched successfully for username: {}", username);
        return new AdminResDto(
                user.getId(),
                user.getUsername()
        );
    }

    public AdminStatsDto getStats() {
        log.info("Generating admin dashboard statistics");

        List<Car> cars=carRepository.findAll();
        List<User> users=userRepository.findAll();
        List<Booking> bookings=bookingRepository.findAll();
        List<User> owners=userRepository.getByRoleOwner(Role.OWNER);
        List<User> buyers=userRepository.getByRoleBuyer(Role.BUYER);

        int carCount=cars.size();
        int userCount=users.size();
        int bookingCount=bookings.size();
        int ownerCount=owners.size();
        int buyerCount=buyers.size();
        double revenue=bookingRepository.getRevenue();
        log.info("Stats calculated successfully - cars: {}, users: {}, bookings: {}, revenue: {}",
                carCount, userCount, bookingCount, revenue);

        return new AdminStatsDto(
                carCount,
                userCount,
                ownerCount,
                buyerCount,
                bookingCount,
                revenue
        );
    }
}
