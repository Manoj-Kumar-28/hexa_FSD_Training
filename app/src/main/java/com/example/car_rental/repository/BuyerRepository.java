package com.example.car_rental.repository;

import com.example.car_rental.model.Buyer;
import com.example.car_rental.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface BuyerRepository extends JpaRepository<Buyer, Long> {
    List<Buyer> user(User user);

    @Query("""
        select b from Buyer b where b.user.username=?1
        """)
    Buyer getByName(String username);
}
