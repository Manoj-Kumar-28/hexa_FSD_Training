package com.example.car_rental.repository;

import com.example.car_rental.model.Booking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface BookingRepository extends JpaRepository<Booking,Long> {

    @Query("select b from Booking b where b.buyer.user.username=?1")
    List<Booking> getBookingsByUser(String username);

    @Query("select b from Booking b where b.car.id=?1")
    List<Booking> getBookingsByCar(long carId);
}
