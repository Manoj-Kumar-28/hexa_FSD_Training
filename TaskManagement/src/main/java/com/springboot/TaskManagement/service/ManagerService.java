package com.springboot.TaskManagement.service;

import com.springboot.TaskManagement.dto.ManagerSignupDto;
import com.springboot.TaskManagement.enums.Role;
import com.springboot.TaskManagement.mapper.ManagerMapper;
import com.springboot.TaskManagement.model.Manager;
import com.springboot.TaskManagement.model.User;
import com.springboot.TaskManagement.repository.ManaagerRepository;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
@AllArgsConstructor
public class ManagerService {
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;
    private final ManaagerRepository manaagerRepository;


    public void addManagerSignup(@Valid ManagerSignupDto managerSignupDto) {
        Manager manager= ManagerMapper.mapToEntity(managerSignupDto);

        User user=new User();
        user.setUsername(managerSignupDto.userName());
        user.setCreatedAt(LocalDate.now());
        user.setUpdatedAt(LocalDate.now());
        user.setRole(Role.MANAGER);
        user.setPassword(passwordEncoder.encode(managerSignupDto.password()));
        userService.insertUser(user);
        manager.setUser(user);

        manaagerRepository.save(manager);
    }
}
