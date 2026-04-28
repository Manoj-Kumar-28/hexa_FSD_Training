package com.example.car_rental.repository;

import com.example.car_rental.dto.CarRespDto;
import com.example.car_rental.enums.CarStatus;
import com.example.car_rental.model.Car;
import com.example.car_rental.model.Owner;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;

public interface CarRepository extends JpaRepository<Car,Long> {
    List<Car> findAllByBrand(String brand);

    List<Car> findAllByModel(String model);

    List<Car> findAllByOwner(Owner owner);

    @Query("""
        select c from Car c where c.carStatus=?1 
        """)
    List<Car> getCarsIfAvail(CarStatus carStatus);

    @Query("""
    select c from Car c
    where (?1 is null or c.location = ?1)
      and (?2 is null or c.brand = ?2)
      and (?3 is null or c.model = ?3)
      and (?4 is null or c.carDetails.fuelType = ?4)
      and (?5 is null or c.carDetails.seats = ?5)
      and c.carStatus = 'AVAILABLE'
""")
    List<Car> getCarsByFilter(String location, String brand, String model,
            String fuelType, Integer seats);

    @Query("""
        select c from Car c where c.owner.user.username=?1
""")
    List<Car> getCarsByOwner(String username);

    @Query(" select c from Car c where c.owner.name=?1")
    Car getCarByUsername(String name);

    @Query(" select c from Car c where c.owner.id=?1")
    List<Car> getCarsByOwnerId(long id);

    @Query("""
    SELECT c FROM Car c
    WHERE c.id NOT IN (
        SELECT b.car.id FROM Booking b
        WHERE NOT (
            b.dropDateTime < :startDate OR b.pickupDateTime > :endDate
        )
    )
    """)
    List<Car> findAvailableCars(LocalDate startDate, LocalDate endDate);
}
