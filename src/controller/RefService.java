package controller;

import model.Ref;
import model.Vehicle;
import view.TM.RefTm;
import view.TM.VehicleTm;

import java.sql.SQLException;
import java.util.List;

public interface RefService {


    public boolean refAdd(Ref r) throws SQLException, ClassNotFoundException;
    String getNewRefId() throws SQLException, ClassNotFoundException;
    String getRefId() throws SQLException, ClassNotFoundException;
    public Ref searchRef(String Id) throws SQLException, ClassNotFoundException;
    public List<RefTm> getRef() throws SQLException, ClassNotFoundException;
    public boolean refUpdate(Ref r) throws SQLException, ClassNotFoundException;
    public boolean refDelete(String Id) throws SQLException, ClassNotFoundException;
}
