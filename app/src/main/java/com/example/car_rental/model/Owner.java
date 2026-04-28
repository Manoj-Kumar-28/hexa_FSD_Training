package com.example.car_rental.model;

import com.example.car_rental.enums.OwnerStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Owner {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;

    @Column(name = "email", nullable = false)
    private String email;
    private String city;

    @Column(name = "phone_number")
    private long phoneNumber;

    @Enumerated(EnumType.STRING)
    private OwnerStatus status;

    @OneToOne
    @JoinColumn(name = "user_id",nullable = false)
    private User user;
}
