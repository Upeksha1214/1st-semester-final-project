package controller;

import model.Employee;
import view.TM.EmployeeTm;

import java.sql.SQLException;
import java.util.List;

public interface EmployeeService {
    public boolean employeeAdd (Employee e) throws SQLException, ClassNotFoundException;
    String getNewEmployeeId() throws SQLException, ClassNotFoundException;
    String getEmId() throws SQLException, ClassNotFoundException;
    public Employee searchEmployee(String Id) throws SQLException, ClassNotFoundException;
    public List<EmployeeTm> getEmployee() throws SQLException, ClassNotFoundException;
    public boolean employeeUpdate(Employee e) throws SQLException, ClassNotFoundException;
    public boolean employeeDelete(String Id) throws SQLException, ClassNotFoundException;

}
