package com.car.model;

public class Car {

    private int id;
    private int registration_no;
    private int chassis_no;
    private String brand;
    private String model;
    private String variant;

    private Owner owner;
    private CarDetails details;


    public Car() {
    }

    public Car(int id, int registration_no, int chassis_no, String brand, String model, String variant, Owner owner, CarDetails details) {
        this.id = id;
        this.registration_no = registration_no;
        this.chassis_no = chassis_no;
        this.brand = brand;
        this.model = model;
        this.variant = variant;
        this.owner = owner;
        this.details = details;
    }

    @Override
    public String toString() {
        return "Car{" +
                "id=" + id +
                ", registration_no=" + registration_no +
                ", chassis_no=" + chassis_no +
                ", brand='" + brand + '\'' +
                ", model='" + model + '\'' +
                ", variant='" + variant + '\'' +
                ", owner=" + owner +
                ", details=" + details +
                '}';
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getRegistration_no() {
        return registration_no;
    }

    public void setRegistration_no(int registration_no) {
        this.registration_no = registration_no;
    }

    public int getChassis_no() {
        return chassis_no;
    }

    public void setChassis_no(int chassis_no) {
        this.chassis_no = chassis_no;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getVariant() {
        return variant;
    }

    public void setVariant(String variant) {
        this.variant = variant;
    }

    public Owner getOwner() {
        return owner;
    }

    public void setOwner(Owner owner) {
        this.owner = owner;
    }

    public CarDetails getDetails() {
        return details;
    }

    public void setDetails(CarDetails details) {
        this.details = details;
    }
}
