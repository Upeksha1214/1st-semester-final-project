package view.TM;

public class DriverTm {
    private String driverId;
    private String driverNIC;
    private String driverName;
    private String driverAddress;
    private String contactNumber;

    public DriverTm() {
    }

    public DriverTm(String driverId, String driverNIC, String driverName, String driverAddress, String contactNumber) {
        this.driverId = driverId;
        this.driverNIC = driverNIC;
        this.driverName = driverName;
        this.driverAddress = driverAddress;
        this.contactNumber = contactNumber;
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

    public String getContactNumber() {
        return contactNumber;
    }

    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
    }
}
