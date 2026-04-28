package com.example.car_rental.repository;

import com.example.car_rental.model.Car;
import com.example.car_rental.model.Review;
import org.springframework.data.domain.Example;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ReviewRepository extends JpaRepository<Review, Long> {
    @Query("select r from Review r where r.car.id=?1")
    List<Review> getReviewsByCarId(long carId);
}
