package controller;

import model.Driver;
import model.Employee;
import view.TM.DriverTm;
import view.TM.EmployeeTm;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public interface DriverService {

    public boolean driverAdd(Driver d) throws SQLException, ClassNotFoundException;
    String getNewDriverId() throws SQLException, ClassNotFoundException;
    String getDId() throws SQLException, ClassNotFoundException;
    public Driver searchDriver(String Id) throws SQLException, ClassNotFoundException;
    public ArrayList<DriverTm> getDriver() throws SQLException, ClassNotFoundException;
    public boolean driverUpdate(Driver d) throws SQLException, ClassNotFoundException;
    public boolean driverDelete(String Id) throws SQLException, ClassNotFoundException;

}
