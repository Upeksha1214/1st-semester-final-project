package controller;

import model.Store;
import model.Vehicle;
import view.TM.StoreTm;
import view.TM.VehicleTm;

import java.sql.SQLException;
import java.util.List;

public interface VehicleService {

    public boolean vehicleAdd(Vehicle v) throws SQLException, ClassNotFoundException;
    String getNewVehicleId() throws SQLException, ClassNotFoundException;
    String getVehicleId() throws SQLException, ClassNotFoundException;
    public Vehicle searchVehicle(String Id) throws SQLException, ClassNotFoundException;
    public List<VehicleTm>getVehicle() throws SQLException, ClassNotFoundException;
    public boolean vehicleUpdate(Vehicle v) throws SQLException, ClassNotFoundException;
    public boolean vehicleDelete (String Id) throws SQLException, ClassNotFoundException;
}
