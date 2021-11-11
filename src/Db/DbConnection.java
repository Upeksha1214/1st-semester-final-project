package Db;

import com.sun.xml.internal.ws.api.model.wsdl.WSDLOutput;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DbConnection {

    private static DbConnection dbConnection=null;

    private Connection connection;

    public DbConnection() throws SQLException, ClassNotFoundException {
        Class.forName("com.mysql.cj.jdbc.Driver");
        connection = DriverManager.getConnection(
                "jdbc:mysql://127.0.0.1:3306/saviyayougurt",
                "root",
                "1234"
        );
        System.out.println("1");
    }


    public static DbConnection getInstance() throws SQLException, ClassNotFoundException {
        if (dbConnection==null){
            dbConnection= new DbConnection();
        }
        return dbConnection;
    }
    public Connection getConnection(){
        return connection;
    }

}
