package view.TM;

public class SalaryTm {
    private String salaryId;
    private String employeeId;
    private String employeeSalary;
    private String incentive;
    private String ot;
    private String total;

    public SalaryTm() {
    }

    public SalaryTm(String salaryId, String employeeId, String employeeSalary, String incentive, String ot, String total) {
        this.salaryId = salaryId;
        this.employeeId = employeeId;
        this.employeeSalary = employeeSalary;
        this.incentive = incentive;
        this.ot = ot;
        this.total = total;
    }

    public String getSalaryId() {
        return salaryId;
    }

    public void setSalaryId(String salaryId) {
        this.salaryId = salaryId;
    }

    public String getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(String employeeId) {
        this.employeeId = employeeId;
    }

    public String getEmployeeSalary() {
        return employeeSalary;
    }

    public void setEmployeeSalary(String employeeSalary) {
        this.employeeSalary = employeeSalary;
    }

    public String getIncentive() {
        return incentive;
    }

    public void setIncentive(String incentive) {
        this.incentive = incentive;
    }

    public String getOt() {
        return ot;
    }

    public void setOt(String ot) {
        this.ot = ot;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    @Override
    public String toString() {
        return "Salary{" +
                "salaryId='" + salaryId + '\'' +
                ", employeeId='" + employeeId + '\'' +
                ", employeeSalary='" + employeeSalary + '\'' +
                ", incentive='" + incentive + '\'' +
                ", ot='" + ot + '\'' +
                ", total='" + total + '\'' +
                '}';
    }
}
