package com.car.service;

import com.car.model.Car;
import com.car.model.Owner;
import com.car.repository.CarRepository;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public class CarService {
    CarRepository carRepository = new CarRepository();

    public List<Car> getgetAllCarsWithOwnerAndCarDetails() throws SQLException,ClassNotFoundException {
        List<Car> list=carRepository.getgetAllCarsWithOwnerAndCarDetails();
        return list;
    }

    public Map<String, Integer> getBrandAndTotalCars() throws SQLException {
        Map<String,Integer> map=carRepository.getBrandAndTotalCars();
        return map;
    }

    public List<Car> getAllCarsWithCompleteInfo() throws SQLException {
        List<Car> list=carRepository.getAllCarsWithCompleteInfo();
        return list;
    }

    public List<Owner> getAllOwnerDetails(List<Car> list) {
        return list.stream()
                .map(Car::getOwner)
                .distinct()
                .toList();
    }

    public List<Car> getAllCarsByOwner(List<Car> list, int choice) {
        return list.stream()
                .filter(car -> car.getOwner().getId()==choice)
                .toList();
    }
}
