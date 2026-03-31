package com.car.repository;

import com.car.dao.CarDao;
import com.car.model.Car;
import com.car.model.CarDetails;
import com.car.model.Owner;
import com.car.utility.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class CarRepository implements CarDao {

    DBConnection dbConnection=DBConnection.getInstance();
    public List<Car> getgetAllCarsWithOwnerAndCarDetails() throws SQLException {
        List<Car> list=new ArrayList<Car>();

        Connection conn=dbConnection.dbConnect();
        System.out.println(dbConnection);

        String sql="select c.id,c.brand,c.model,o.name as ownerName,cd.year_of_purchase,cd.mileage " +
                "from car c " +
                "join car_details cd on c.car_details_id=cd.id " +
                "join owner o on  c.owner_id=o.id " +
                "order by c.id ";
        PreparedStatement stmt =conn.prepareStatement(sql);
        ResultSet rs=stmt.executeQuery();
        while(rs.next()){
            Car car=new Car();
            int id=rs.getInt("id");
            car.setId(id);
            String brand=rs.getString("brand");
            car.setBrand(brand);
            String model=rs.getString("model");
            car.setModel(model);

            Owner owner=new Owner();
            String name=rs.getString("ownerName");
            owner.setName(name);
            car.setOwner(owner);

            CarDetails details=new CarDetails();
            int year=rs.getInt("year_of_purchase");
            details.setYear_of_purchase(year);
            int mileage=rs.getInt("mileage");
            details.setMileage(mileage);

            car.setDetails(details);

            list.add(car);
        }
        dbConnection.dbClose();
        return list;
    }

    public Map<String, Integer> getBrandAndTotalCars() throws SQLException {
        Map<String,Integer> map=new LinkedHashMap<>();

        Connection conn=dbConnection.dbConnect();
        System.out.println(dbConnection);
        String sql="select c.brand as brand_name,count(c.id) as totalCars \n" +
                "from car c\n" +
                "join car_details cd on c.car_details_id=cd.id\n" +
                "join owner o on  c.owner_id=o.id\n" +
                "group by c.brand";
        PreparedStatement stmt=conn.prepareStatement(sql);
        ResultSet rs= stmt.executeQuery();
        while(rs.next()){
            String brand=rs.getString("brand_name");
            int count=rs.getInt("totalCars");
            map.put(brand,count);
        }


        dbConnection.dbClose();
        return map;
    }

    @Override
    public List<Car> getAllCarsWithCompleteInfo() throws SQLException {
        List<Car> list=new ArrayList<Car>();

        Connection conn=dbConnection.dbConnect();
        System.out.println(dbConnection);

        String sql="select c.id,c.brand,c.model,o.id as ownerId,o.name as ownerName,cd.year_of_purchase,cd.mileage " +
                "from car c " +
                "join car_details cd on c.car_details_id=cd.id " +
                "join owner o on  c.owner_id=o.id " +
                "order by c.id ";
        PreparedStatement stmt =conn.prepareStatement(sql);
        ResultSet rs=stmt.executeQuery();
        while(rs.next()){
            Car car=new Car();
            int id=rs.getInt("id");
            car.setId(id);
            String brand=rs.getString("brand");
            car.setBrand(brand);
            String model=rs.getString("model");
            car.setModel(model);

            Owner owner=new Owner();
            String name=rs.getString("ownerName");
            owner.setName(name);
            int ownerId=rs.getInt("ownerId");
            owner.setId(ownerId);
            car.setOwner(owner);

            CarDetails details=new CarDetails();
            int year=rs.getInt("year_of_purchase");
            details.setYear_of_purchase(year);
            int mileage=rs.getInt("mileage");
            details.setMileage(mileage);

            car.setDetails(details);

            list.add(car);
        }
        dbConnection.dbClose();
        return list;
    }
}
