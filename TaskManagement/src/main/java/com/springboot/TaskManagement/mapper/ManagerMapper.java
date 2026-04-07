package com.springboot.TaskManagement.mapper;

import com.springboot.TaskManagement.dto.ManagerSignupDto;
import com.springboot.TaskManagement.model.Manager;
import jakarta.validation.Valid;

public class ManagerMapper {
    public static Manager mapToEntity( ManagerSignupDto managerSignupDto) {
        Manager manager = new Manager();
        manager.setManagerId(managerSignupDto.managerId());
        manager.setName(managerSignupDto.name());
        manager.setEmail(managerSignupDto.email());
        manager.setCity(managerSignupDto.city());
        return manager;

    }
}
