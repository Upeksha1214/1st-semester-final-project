package model;

public class Driver {
    private String driverId;
    private String driverNIC;
    private String driverName;
    private String driverAddress;
    private String driverPhoneNumber;

    public Driver(String driverId, String driverNIC, String driverName, String driverAddress, String driverPhoneNumber) {
        this.driverId = driverId;
        this.driverNIC = driverNIC;
        this.driverName = driverName;
        this.driverAddress = driverAddress;
        this.driverPhoneNumber = driverPhoneNumber;
    }

    public Driver() {
    }



    public String getDriverId() {
        return driverId;
    }

    public void setDriverId(String driverId) {
        this.driverId = driverId;
    }

    public String getDriverNIC() {
        return driverNIC;
    }

    public void setDriverNIC(String driverNIC) {
        this.driverNIC = driverNIC;
    }

    public String getDriverName() {
        return driverName;
    }

    public void setDriverName(String driverName) {
        this.driverName = driverName;
    }

    public String getDriverAddress() {
        return driverAddress;
    }

    public void setDriverAddress(String driverAddress) {
        this.driverAddress = driverAddress;
    }

    public String getDriverPhoneNumber() {
        return driverPhoneNumber;
    }

    public void setDriverPhoneNumber(String driverPhoneNumber) {
        this.driverPhoneNumber = driverPhoneNumber;
    }

    @Override
    public String toString() {
        return "Driver{" +
                "driverId='" + driverId + '\'' +
                ", driverNIC='" + driverNIC + '\'' +
                ", driverName='" + driverName + '\'' +
                ", driverAddress='" + driverAddress + '\'' +
                ", driverPhoneNumber='" + driverPhoneNumber + '\'' +
                '}';
    }
}
