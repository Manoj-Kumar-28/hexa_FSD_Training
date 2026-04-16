package com.example.car_rental.repository;

import com.example.car_rental.enums.CarStatus;
import com.example.car_rental.model.Car;
import com.example.car_rental.model.Owner;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CarRepository extends JpaRepository<Car,Long> {
    List<Car> findAllByBrand(String brand);

    List<Car> findAllByModel(String model);

    List<Car> findAllByOwner(Owner owner);

    @Query("""
        select c from Car c where c.carStatus=?1 
        """)
    List<Car> getCarsIfAvail(CarStatus carStatus);
}
