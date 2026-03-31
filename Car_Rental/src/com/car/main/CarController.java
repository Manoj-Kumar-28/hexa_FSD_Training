package com.car.main;

import com.car.model.Car;
import com.car.model.Owner;
import com.car.service.CarService;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class CarController {

    public static void main(String[] args) {

        CarService carService = new CarService();
        Scanner sc = new Scanner(System.in);

        while(true){
            System.out.println("-------Welcome to Car Rental Platform---------");
            System.out.println("1. Get all Cars");
            System.out.println("2. Get Total Cars with brand");
            System.out.println("3. Get Cars by Owner Id");
            System.out.println("0. Exit");

            int input = sc.nextInt();
            if(input==0){
                break;
            }
            switch (input){
                case 1:
                    System.out.println("-------------List of Cars----------------");
                    try {
                        List<Car> list= carService.getgetAllCarsWithOwnerAndCarDetails();
                        list.forEach(car->{
                            System.out.println("Car Id: "+car.getId());
                            System.out.println("Brand: "+car.getBrand());
                            System.out.println("Model: "+car.getModel());
                            System.out.println("Owner: "+car.getOwner().getName());
                            System.out.println("Year of Purchase: "+car.getDetails().getYear_of_purchase());
                            System.out.println("Mileage: "+car.getDetails().getMileage());
                            System.out.println(" ");
                        });
                    } catch (ClassNotFoundException|SQLException e) {
                        throw new RuntimeException(e);
                    }

                    break;
                case 2:

                    System.out.println("--------------Total Cars of brand------------");

                    try {
                        Map<String,Integer> map=  carService.getBrandAndTotalCars();
                        System.out.println("Brand "+"\t\t"+" Total Cars");
                        for(Map.Entry<String,Integer> entry:map.entrySet()){
                            System.out.println(entry.getKey()+"\t\t"+entry.getValue());
                        }
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }

                    break;
                case 3:

                    List<Car> list= null;
                    try {
                        list = carService.getAllCarsWithCompleteInfo();
                        List<Owner> listOfOwners=carService.getAllOwnerDetails(list);
                        System.out.println("Owner Id "+"\t\t"+" Owner Name");
                        listOfOwners.forEach(owner -> {
                            System.out.println(owner.getId()+"\t\t"+owner.getName());
                        });

                        System.out.println("Please select the Owner ID to fetch details...");
                        int choice=sc.nextInt();
                        List<Car> listOfCars=carService.getAllCarsByOwner(list,choice);
                        listOfCars.forEach(car -> {
                            System.out.println("Car Id: "+car.getId());
                            System.out.println("Brand: "+car.getBrand());
                            System.out.println("Model: "+car.getModel());
                            System.out.println("Owner: "+car.getOwner().getName());
                            System.out.println("Year of Purchase: "+car.getDetails().getYear_of_purchase());
                            System.out.println("Mileage: "+car.getDetails().getMileage());
                            System.out.println(" ");
                        });
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }

                    break;

                default:
                    System.out.println("Invalid input");
            }
        }
    }
}
