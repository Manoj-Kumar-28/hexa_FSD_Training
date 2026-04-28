package com.example.car_rental.model;


import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "car_details")
public class CarDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "fuel_type")
    private String fuelType;
    private String color;
    private int seats;

    @JsonBackReference
    @OneToOne(mappedBy = "carDetails")
    private Car car;
}
