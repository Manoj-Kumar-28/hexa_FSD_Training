package com.example.car_rental.repository;

import com.example.car_rental.dto.OwnerResDto;
import com.example.car_rental.enums.OwnerStatus;
import com.example.car_rental.model.Owner;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface OwnerRepository extends JpaRepository<Owner, Long> {

    @Query("""
        select o from Owner o where o.user.username=?1
        """)
    Owner getOwnerByUsername(String username);


    List<OwnerResDto> findByStatus(OwnerStatus status);
}
