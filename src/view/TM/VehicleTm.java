package view.TM;

public class VehicleTm {
    private String vehicleId;
    private String vehicleNumber;
    private String vehicleType;
    private String vehicleRent;

    public VehicleTm() {
    }

    public VehicleTm(String vehicleId, String vehicleNumber, String vehicleType, String vehicleRent) {
        this.vehicleId = vehicleId;
        this.vehicleNumber = vehicleNumber;
        this.vehicleType = vehicleType;
        this.vehicleRent = vehicleRent;
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

    @Override
    public String toString() {
        return "VehicleTm{" +
                "vehicleId='" + vehicleId + '\'' +
                ", vehicleNumber='" + vehicleNumber + '\'' +
                ", vehicleType='" + vehicleType + '\'' +
                ", vehicleRent='" + vehicleRent + '\'' +
                '}';
    }
}
