package view.TM;

public class EmployeeTm {
    private String employeeId;
    private String employeeType;
    private String employeeNIC;
    private String employeeName;
    private String employeeAddress;
    private String employeePhoneNumber;
    private String employeeSalary;

    public EmployeeTm(String employeeId, String employeeType, String employeeNIC, String employeeName, String employeeAddress, String employeePhoneNumber, String employeeSalary) {
        this.employeeId = employeeId;
        this.employeeType = employeeType;
        this.employeeNIC = employeeNIC;
        this.employeeName = employeeName;
        this.employeeAddress = employeeAddress;
        this.employeePhoneNumber = employeePhoneNumber;
        this.employeeSalary = employeeSalary;
    }

    public EmployeeTm() {
    }

    public String getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(String employeeId) {
        this.employeeId = employeeId;
    }

    public String getEmployeeType() {
        return employeeType;
    }

    public void setEmployeeType(String employeeType) {
        this.employeeType = employeeType;
    }

    public String getEmployeeNIC() {
        return employeeNIC;
    }

    public void setEmployeeNIC(String employeeNIC) {
        this.employeeNIC = employeeNIC;
    }

    public String getEmployeeName() {
        return employeeName;
    }

    public void setEmployeeName(String employeeName) {
        this.employeeName = employeeName;
    }

    public String getEmployeeAddress() {
        return employeeAddress;
    }

    public void setEmployeeAddress(String employeeAddress) {
        this.employeeAddress = employeeAddress;
    }

    public String getEmployeePhoneNumber() {
        return employeePhoneNumber;
    }

    public void setEmployeePhoneNumber(String employeePhoneNumber) {
        this.employeePhoneNumber = employeePhoneNumber;
    }

    public String getEmployeeSalary() {
        return employeeSalary;
    }

    public void setEmployeeSalary(String employeeSalary) {
        this.employeeSalary = employeeSalary;
    }

    @Override
    public String toString() {
        return "EmployeeTm{" +
                "employeeId='" + employeeId + '\'' +
                ", employeeType='" + employeeType + '\'' +
                ", employeeNIC='" + employeeNIC + '\'' +
                ", employeeName='" + employeeName + '\'' +
                ", employeeAddress='" + employeeAddress + '\'' +
                ", employeePhoneNumber='" + employeePhoneNumber + '\'' +
                ", employeeSalary='" + employeeSalary + '\'' +
                '}';
    }
}

