package com.example.car_rental.repository;

import com.example.car_rental.model.CarDetails;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CarDetailsRepository extends JpaRepository<CarDetails,Long> {
}
