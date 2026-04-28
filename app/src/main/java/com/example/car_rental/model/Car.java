package com.example.car_rental.model;

import com.example.car_rental.enums.CarStatus;
import com.fasterxml.jackson.annotation.JsonManagedReference;
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
@Table(name = "cars")
public class Car {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(name = "car_number",nullable = false)
    private long carNumber;

    private String brand;
    private String model;
    @Column(nullable = false)
    private double pricePerDay;

    @Column(nullable = false)
    private String location;

    @Enumerated(EnumType.STRING)
    private CarStatus carStatus;

    //one car has one car details
    @JsonManagedReference
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "car_details_id")
    private CarDetails carDetails;

    @ManyToOne
    @JoinColumn(name="owner_id",nullable = false)
    private Owner owner;

    @JsonManagedReference
    @OneToOne(mappedBy = "car", cascade = CascadeType.ALL)
    @JoinColumn(name = "document_id")
    private Documents documents;

}
