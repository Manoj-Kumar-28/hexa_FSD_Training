package com.example.car_rental.repository;

import com.example.car_rental.enums.Role;
import com.example.car_rental.model.Buyer;
import com.example.car_rental.model.Owner;
import com.example.car_rental.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;


public interface UserRepository extends JpaRepository<User,Long> {
    @Query("""
        select u from User u where u.username=?1
        """)
    User getUserByUsername(String username);

    @Query("""
        select u from User u where u.role=?1
        """)
    List<User> getByRoleOwner(Role role);

    @Query("""
        select u from User u where u.role=?1
        """)
    List<User> getByRoleBuyer(Role role);
}
