package Model;

public class Vehicle {

    private String vehicleNumber;
    private String vehicleType;
    private String fuelType;
    private String color;

    public Vehicle() {
    }

    public Vehicle(String vehicleNumber, String vehicleType, String fuelType, String color) {
        this.vehicleNumber = vehicleNumber;
        this.vehicleType = vehicleType;
        this.fuelType = fuelType;
        this.color = color;
    }

    public String getVehicleNumber() {
        return vehicleNumber;
    }

    public void setVehicleNumber(String vehicleNumber) {
        this.vehicleNumber = vehicleNumber;
    }

    public String getVehicleType() {
        return vehicleType;
    }

    public void setVehicleType(String vehicleType) {
        this.vehicleType = vehicleType;
    }

    public String getFuelType() {
        return fuelType;
    }

    public void setFuelType(String fuelType) {
        this.fuelType = fuelType;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }
}
