package com.example.car_rental.service;

import com.example.car_rental.dto.AdminReqDto;
import com.example.car_rental.enums.Role;
import com.example.car_rental.model.User;
import com.example.car_rental.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class AdminService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public void addAdmin(AdminReqDto adminReqDto) {
        User user=new User();
        user.setUsername(adminReqDto.username());
        user.setPassword(passwordEncoder.encode(adminReqDto.password()));
        user.setRole(Role.ADMIN);
        userRepository.save(user);
    }
}
