package com.springboot.TaskManagement.controller;

import com.springboot.TaskManagement.dto.ManagerSignupDto;
import com.springboot.TaskManagement.service.ManagerService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/manager")
@AllArgsConstructor
public class ManagerController {
    private final ManagerService managerService;
    @PostMapping("/sign-up")
    public ResponseEntity<?> addCustomerSignup(@Valid @RequestBody ManagerSignupDto managerSignupDto){
        managerService.addManagerSignup(managerSignupDto);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .build();
    }
}
