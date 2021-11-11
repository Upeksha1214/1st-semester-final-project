package controller;

import model.UserAccount;

import java.sql.SQLException;
import java.util.List;

public interface UserAccountService {
    public UserAccount getAccount(String userName) throws SQLException, ClassNotFoundException;
}
