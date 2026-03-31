package com.car.dao;

import com.car.model.Car;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public interface CarDao {

    List<Car> getgetAllCarsWithOwnerAndCarDetails() throws SQLException;

    Map<String,Integer> getBrandAndTotalCars() throws SQLException;

    List<Car> getAllCarsWithCompleteInfo() throws SQLException;
}
