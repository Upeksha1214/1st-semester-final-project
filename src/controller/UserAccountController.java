package controller;

import Db.DbConnection;
import model.UserAccount;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserAccountController implements UserAccountService{

    @Override
    public UserAccount getAccount(String userName) throws SQLException, ClassNotFoundException {
        PreparedStatement statement = new DbConnection().getConnection().prepareStatement("SELECT * FROM loginForm WHERE userName=?");
        statement.setObject(1,userName);
        ResultSet resultSet = statement.executeQuery();
        while(resultSet.next()){

            return new UserAccount(resultSet.getString(1),resultSet.getString(2),
                    resultSet.getString(3));

        }
        return null;
    }
}
