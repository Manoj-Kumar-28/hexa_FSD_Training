package com.example.car_rental.service;

import com.example.car_rental.dto.UserPageResDto;
import com.example.car_rental.dto.UserReqDTO;
import com.example.car_rental.dto.UserRespDto;
import com.example.car_rental.enums.Role;
import com.example.car_rental.exception.ResourceNotFoundException;
import com.example.car_rental.mapper.UserMapper;
import com.example.car_rental.model.User;
import com.example.car_rental.repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@AllArgsConstructor
@Slf4j
public class UserService implements UserDetailsService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public void addUser(UserReqDTO userReqDTO) {
        log.info("Adding new user");
        //Convert dto to user
        User user= UserMapper.mapToEntity(userReqDTO);
        //update other fields
        user.setRole(Role.ADMIN);
        user.setCreatedAt(LocalDate.now());
        user.setUpdatedAt(LocalDate.now());
        user.setPassword(passwordEncoder.encode(userReqDTO.password()));
        //save user from repository
        userRepository.save(user);
        log.info("User created successfully");
    }

    public UserPageResDto getAllUsers(int page, int size) {
        log.info("Getting all users");
        Pageable pageable= PageRequest.of(page, size);
        Page<User> pageTicket=userRepository.findAll(pageable);
        long totalRecords=pageTicket.getTotalElements();
        int totalPages= pageTicket.getTotalPages();


        List<UserRespDto> listDto=pageTicket
                .toList()
                .stream()
                .map(UserMapper::mapToDto)
                .toList();

        log.info("Total users found: {}", totalRecords);
        return new UserPageResDto(
                listDto,
                totalRecords,
                totalPages
        );
    }

    public UserRespDto getUserbyId(long id) {
        log.info("Fetching user by id: {}", id);

        User user=userRepository.findById(id)
                .orElseThrow(()->new ResourceNotFoundException("Invalid Id provided"));
        log.info("User fetched successfully");
        return new UserRespDto(
                user.getId(),
                user.getUsername(),
                user.getRole(),
                user.getCreatedAt()
        );
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        log.info("Loading user for authentication - username: {}", username);

        User user=userRepository.getUserByUsername(username);
        log.info("User loaded successfully for authentication - username: {}", username);

        return user;
    }

    public User insertUser(User user) {
        log.info("Saving user entity - username: {}", user.getUsername());

        User savedUser = userRepository.save(user);

        log.info("User saved successfully - userId: {}", savedUser.getId());

        return savedUser;
    }
}
