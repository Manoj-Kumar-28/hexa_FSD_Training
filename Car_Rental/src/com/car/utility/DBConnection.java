package com.car.utility;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {

    private Connection conn;
    private static final DBConnection dbConnection;

    static{
        dbConnection = new DBConnection();
    }

    public static DBConnection getInstance(){
        return dbConnection;
    }

    public Connection dbConnect() {
        try {

            // load the driver
            Class.forName("com.mysql.cj.jdbc.Driver");
            //Establish the connection
            conn= DriverManager.getConnection("jdbc:mysql://localhost:3306/hex_car_rental","root","Manoj@123");

        }catch (ClassNotFoundException | SQLException e){
            throw new RuntimeException(e);
        }
        return conn;
    }


    public void dbClose(){
        try {
            conn.close();
        }catch (SQLException e){
            throw new RuntimeException(e);
        }
    }

}
