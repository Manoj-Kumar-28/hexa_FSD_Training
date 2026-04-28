package com.example.car_rental.controller;

import com.example.car_rental.dto.UserDto;
import com.example.car_rental.model.User;
import com.example.car_rental.service.UserService;
import com.example.car_rental.utility.JwtUtility;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.HashMap;
import java.util.Map;

@RestController
@AllArgsConstructor
@RequestMapping("/api/auth")
@CrossOrigin(origins = "http://localhost:5173/")
@Slf4j
public class AuthController {

    private final JwtUtility jwtUtility;
    private final UserService userService;
    @GetMapping("/login")
    public ResponseEntity<?> login(Principal principal){
        String loggedInUser = principal.getName();
        log.info("Login request received for user: {}", loggedInUser);
        Map<String,String> map = new HashMap<>();
        map.put("token", jwtUtility.generateToken(loggedInUser));
        log.info("JWT token generated successfully for user: {}", loggedInUser);
        return ResponseEntity.status(HttpStatus.OK)
                .body(map);
    }

    @GetMapping("/user-details")
    public ResponseEntity<?> getUserDetails(Principal principal){
        String username=principal.getName();
        User user= (User)userService.loadUserByUsername(username);
        log.info("User details fetched successfully for username: {}", username);
        return ResponseEntity.ok(new UserDto(
                username,
                user.getRole().toString()
        ));
    }
}
