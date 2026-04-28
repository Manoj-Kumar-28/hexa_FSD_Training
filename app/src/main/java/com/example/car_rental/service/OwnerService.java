package com.example.car_rental.service;

import com.example.car_rental.dto.*;
import com.example.car_rental.enums.CarStatus;
import com.example.car_rental.enums.OwnerStatus;
import com.example.car_rental.enums.Role;
import com.example.car_rental.mapper.BuyerMapper;
import com.example.car_rental.mapper.OwnerMapper;
import com.example.car_rental.mapper.UserMapper;
import com.example.car_rental.model.*;
import com.example.car_rental.repository.BookingRepository;
import com.example.car_rental.repository.CarRepository;
import com.example.car_rental.repository.OwnerRepository;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@AllArgsConstructor
@Slf4j
public class OwnerService {
    private final OwnerRepository ownerRepository;
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;
    private final CarRepository carRepository;
    private final BookingRepository bookingRepository;

    public void addOwnerSignup(@Valid OwnerSignupDto ownerSignupDto) {
        log.info("Adding new owner signup request");
        try {
            Owner owner= OwnerMapper.mapToEnt(ownerSignupDto);

            User user= UserMapper.mapToEnti(ownerSignupDto);
            user.setRole(Role.OWNER);
            user.setPassword(passwordEncoder.encode(ownerSignupDto.password()));
            user.setCreatedAt(LocalDate.now());
            user.setUpdatedAt(LocalDate.now());
            userService.insertUser(user);
            owner.setUser(user);

            ownerRepository.save(owner);
        }catch (Exception e){
            e.printStackTrace();
            throw e;
        }
        log.info("Added owner signup request successfully");
    }

    public OwnerPageResDto getAllOwners(int page, int size) {
        log.info("Fetching all owners - page: {}, size: {}", page, size);

        Pageable pageable= PageRequest.of(page, size);
        Page<Owner> pageTicket=ownerRepository.findAll(pageable);
        long totalRecords=pageTicket.getTotalElements();
        int totalPages= pageTicket.getTotalPages();


        List<OwnerResDto> listDto=pageTicket
                .toList()
                .stream()
                .map(OwnerMapper::mapToDto)
                .toList();

        log.info("Owners fetched successfully - total: {}", pageTicket.getTotalElements());

        return new OwnerPageResDto(
                listDto,
                totalRecords,
                totalPages
        );
    }

    public Owner getOwnerByName(String username) {
        log.info("Fetching owner by username: {}", username);

        Owner owner = ownerRepository.getOwnerByUsername(username);

        if (owner == null) {
            log.warn("Owner not found for username: {}", username);
        }

        return owner;
    }

    public OwnerStatsResDto getOwnerStats(String username) {
        log.info("Generating stats for owner: {}", username);

        List<Car> cars=carRepository.getCarsByOwner(username);
        int totalCars=cars.size();
        List<Booking> bookings=bookingRepository.getBookingsByOwner(username);
        int totalBookings=bookings.size();
        Double revenue = bookingRepository.getTotalRevenue(username);
        double totalRevenue = (revenue != null) ? revenue : 0.0;

        int available_cars=cars
                .stream()
                .filter(c->(c.getCarStatus().equals(CarStatus.AVAILABLE)))
                .toList()
                .size();
        int booked_cars=cars
                .stream()
                .filter(c->(c.getCarStatus().equals(CarStatus.BOOKED)))
                .toList()
                .size();
        int inactive_cars=cars
                .stream()
                .filter(c->(c.getCarStatus().equals(CarStatus.INACTIVE)))
                .toList()
                .size();

        log.info("Owner stats generated successfully");
        return new OwnerStatsResDto(
                totalCars,
                totalBookings,
                totalRevenue,
                available_cars,
                booked_cars,
                inactive_cars
        );
    }

    public List<OwnerResDto> getAllPendingOwners() {
        log.info("Fetching pending owners");

        OwnerStatus status=OwnerStatus.PENDING;
        List<OwnerResDto> result = ownerRepository.findByStatus(status);

        log.info("Pending owners fetched - count: {}", result.size());

        return result;
    }

    public void updateOwnerStatus(long id, String status) {
        log.info("Updating owner status - id: {}, status: {}", id, status);

        System.out.println("searching"+id);
        Owner owner=ownerRepository
                .findById(id).orElseThrow(()->new RuntimeException("Owner not found"));
        System.out.println("Exists: " + ownerRepository.existsById(id));
        owner.setStatus(OwnerStatus.valueOf(status));
        ownerRepository.save(owner);
        log.info("Owner status updated successfully - id: {}, newStatus: {}", id, status);

    }


}
