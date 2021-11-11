package controller;

import model.Store;
import view.TM.StoreTm;

import java.sql.SQLException;
import java.util.List;

public interface StoreService {
    public boolean itemAdd (Store s) throws SQLException, ClassNotFoundException;
    String getNewItemCode() throws SQLException, ClassNotFoundException;
    String getItemCode() throws SQLException, ClassNotFoundException;
    public Store searchItem(String itemCode) throws SQLException, ClassNotFoundException;
    public List<StoreTm> getItem() throws SQLException, ClassNotFoundException;
    public boolean itemUpdate(Store s) throws SQLException, ClassNotFoundException;
    public boolean itemDelete(String itemCode) throws SQLException, ClassNotFoundException;
}
