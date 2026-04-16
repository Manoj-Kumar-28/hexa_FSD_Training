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
public class UserService implements UserDetailsService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public void addUser(UserReqDTO userReqDTO) {
        //Convert dto to user
        User user= UserMapper.mapToEntity(userReqDTO);
        //update other fields
        user.setRole(Role.ADMIN);
        user.setCreatedAt(LocalDate.now());
        user.setUpdatedAt(LocalDate.now());
        user.setPassword(passwordEncoder.encode(userReqDTO.password()));
        //save user from repository
        userRepository.save(user);
    }

    public UserPageResDto getAllUsers(int page, int size) {
        Pageable pageable= PageRequest.of(page, size);
        Page<User> pageTicket=userRepository.findAll(pageable);
        long totalRecords=pageTicket.getTotalElements();
        int totalPages= pageTicket.getTotalPages();


        List<UserRespDto> listDto=pageTicket
                .toList()
                .stream()
                .map(UserMapper::mapToDto)
                .toList();

        return new UserPageResDto(
                listDto,
                totalRecords,
                totalPages
        );
    }

    public UserRespDto getUserbyId(long id) {
        User user=userRepository.findById(id)
                .orElseThrow(()->new ResourceNotFoundException("Invalid Id provided"));
        return new UserRespDto(
                user.getId(),
                user.getUsername(),
                user.getRole(),
                user.getCreatedAt()
        );
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user=userRepository.getUserByUsername(username);
        return user;
    }

    public User insertUser(User user) {
        return userRepository.save(user);
    }
}
