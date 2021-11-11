package controller;

import model.Ref;
import model.Shop;
import view.TM.RefTm;
import view.TM.ShopTm;

import java.sql.SQLException;
import java.util.List;

public interface ShopService {
    public boolean shopAdd(Shop s) throws SQLException, ClassNotFoundException;
    String getNewShopId() throws SQLException, ClassNotFoundException;
    String getShopId() throws SQLException, ClassNotFoundException;
    public Shop searchShop(String Id) throws SQLException, ClassNotFoundException;
    public List<ShopTm> getShop() throws SQLException, ClassNotFoundException;
    public boolean shopUpdate(Shop s) throws SQLException, ClassNotFoundException;
    public boolean shopDelete(String Id) throws SQLException, ClassNotFoundException;
}
