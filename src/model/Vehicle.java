package model;

public class Vehicle {
    private String vehicleId;
    private String vehicleNumber;
    private String vehicleType;
    private String vehicleRent;

    public Vehicle() {
    }

    public Vehicle(String vehicleId, String vehicleNumber, String vehicleType, String vehicleRent) {
        this.vehicleId = vehicleId;
        this.vehicleNumber = vehicleNumber;
        this.vehicleType = vehicleType;
        this.vehicleRent = vehicleRent;
    }

    @Override
    public String toString() {
        return "Vehicle{" +
                "vehicleId='" + getVehicleId() + '\'' +
                ", vehicleNumber='" + getVehicleNumber() + '\'' +
                ", vehicleType='" + getVehicleType() + '\'' +
                ", vehicleRent='" + getVehicleRent() + '\'' +
                '}';
    }

    public String getVehicleId() {
        return vehicleId;
    }

    public void setVehicleId(String vehicleId) {
        this.vehicleId = vehicleId;
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

    public String getVehicleRent() {
        return vehicleRent;
    }

    public void setVehicleRent(String vehicleRent) {
        this.vehicleRent = vehicleRent;
    }
}
