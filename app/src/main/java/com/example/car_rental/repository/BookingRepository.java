package com.example.car_rental.repository;

import com.example.car_rental.enums.BookingStatus;
import com.example.car_rental.model.Booking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface BookingRepository extends JpaRepository<Booking,Long> {

    @Query("select b from Booking b where b.buyer.user.username=?1")
    List<Booking> getBookingsByUser(String username);

    @Query("select b from Booking b where b.car.id=?1")
    List<Booking> getBookingsByCar(long carId);

    @Query("select b from Booking b where b.car.owner.user.username=?1")
    List<Booking> getBookingsByOwner(String username);
    @Query("""
        select sum(b.totalAmount) from Booking b where b.car.owner.user.username=?1
    """)
    Double getTotalRevenue(String username);
    @Query("select sum(b.totalAmount) from Booking b")
    double getRevenue();

    boolean existsByCarId(long id);

    @Query("select b from Booking b where b.buyer.id=?1")
    List<Booking> getBookingsByBuyerId(long id);

    List<Booking> findByStatus(BookingStatus status);
}
