package controller;

import Db.DbConnection;
import animatefx.animation.Pulse;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.util.Duration;
import model.Driver;
import model.Employee;
import view.TM.DriverTm;
import view.TM.EmployeeTm;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class DriverController implements DriverService{
    public Pane homePane;
    public Pane addPane;
    public Pane searchPane;
    public Pane updatePane;
    public Pane deletePane;
    public AnchorPane driverContext;
    public AnchorPane driverControllerContext;
    public Label lblDate;
    public Label lblTime;

    public void initialize(){
        loadDateAndTime();
    }

    private void loadDateAndTime() {
        //loadDate
        Date date= new Date();
        SimpleDateFormat f= new SimpleDateFormat("yyyy-MM-dd");
        lblDate.setText(f.format(date));

        //loadTime
        Timeline time =new Timeline(new KeyFrame(Duration.ZERO, e ->{
            LocalTime currentTime =LocalTime.now();
            lblTime.setText(
                    currentTime.getHour() + " : " + currentTime.getMinute() +
                            " : " + currentTime.getSecond()

            );
        } ),
                new KeyFrame(Duration.seconds(1))
        );
        time.setCycleCount(Animation.INDEFINITE);
        time.play();
    }

    private void clearPane(){
        homePane.setStyle("-fx-background-color: #357EC7");
        addPane.setStyle("-fx-background-color: #357EC7");
        searchPane.setStyle("-fx-background-color: #357EC7");
        updatePane.setStyle("-fx-background-color: #357EC7");
        deletePane.setStyle("-fx-background-color:  #357EC7");
    }

    public void homeMouseOnAction(MouseEvent mouseEvent) throws IOException {
        clearPane();
        homePane.setStyle("-fx-background-color:#8D3DAF");

        Stage window = (Stage) driverContext.getScene().getWindow();
        window.setScene(new Scene(FXMLLoader.load(getClass().getResource("../view/StoreKeeper.fxml"))));
    }

    public void homeMouseEnteredOnAction(MouseEvent mouseEvent) {
        clearPane();
        new Pulse(homePane).play();
        homePane.setStyle("-fx-background-color: #6495ED");
    }

    public void addDriverMouseOnAction(MouseEvent mouseEvent) throws IOException {
        clearPane();
        addPane.setStyle("-fx-background-color:#151b54");

        /*URL resource = getClass().getResource("../view/DriverAdd.fxml");
        Parent load = FXMLLoader.load(resource);
        driverControllerContext.getChildren().add(load);*/

        Parent fxml =FXMLLoader.load(getClass().getResource("../view/DriverAdd.fxml"));
        driverControllerContext.getChildren().removeAll();
        driverControllerContext.getChildren().setAll(fxml);
    }

    public void addDriverEnteredOnaction(MouseEvent mouseEvent) {
        clearPane();
        new Pulse(addPane).play();
        addPane.setStyle("-fx-background-color: #6495ED");
    }


    public void searchDriverMoseOnAction(MouseEvent mouseEvent) throws IOException {
        clearPane();
        searchPane.setStyle("-fx-background-color:#151B54");

        /*URL resource = getClass().getResource("../view/DriverSearch.fxml");
        Parent load = FXMLLoader.load(resource);
        driverControllerContext.getChildren().add(load);*/

        Parent fxml =FXMLLoader.load(getClass().getResource("../view/DriverSearch.fxml"));
        driverControllerContext.getChildren().removeAll();
        driverControllerContext.getChildren().setAll(fxml);
    }

    public void searchMouseEnteredOnAction(MouseEvent mouseEvent) {
        clearPane();
        new Pulse(searchPane).play();
        searchPane.setStyle("-fx-background-color: #6495ED");
    }


    public void updateDriverMouseOnAction(MouseEvent mouseEvent) throws IOException {
        clearPane();
        updatePane.setStyle("-fx-background-color:#151B54");

        /*URL resource = getClass().getResource("../view/DriverUpdate.fxml");
        Parent load = FXMLLoader.load(resource);
        driverControllerContext.getChildren().add(load);*/

        Parent fxml =FXMLLoader.load(getClass().getResource("../view/DriverUpdate.fxml"));
        driverControllerContext.getChildren().removeAll();
        driverControllerContext.getChildren().setAll(fxml);
    }

    public void updateDriverMouseEnteredOnAction(MouseEvent mouseEvent) {
        clearPane();
        new Pulse(updatePane).play();
        updatePane.setStyle("-fx-background-color: #6495ED");
    }

    public void deleteDriverMouseOnAction(MouseEvent mouseEvent) throws IOException {
        clearPane();
        deletePane.setStyle("-fx-background-color:#151B54");

        /*URL resource = getClass().getResource("../view/DriverDelete.fxml");
        Parent load = FXMLLoader.load(resource);
        driverControllerContext.getChildren().add(load);*/

        Parent fxml =FXMLLoader.load(getClass().getResource("../view/DriverDelete.fxml"));
        driverControllerContext.getChildren().removeAll();
        driverControllerContext.getChildren().setAll(fxml);
    }

    public void deleteDriverMoseEnteredOnAction(MouseEvent mouseEvent) {
        clearPane();
        new Pulse(deletePane).play();
        deletePane.setStyle("-fx-background-color:  #6495ED");
    }

    @Override
    public boolean driverAdd(Driver d) throws SQLException, ClassNotFoundException {
        Connection con = DbConnection.getInstance().getConnection();
        String query="INSERT INTO driver Values(?,?,?,?,?)";
        PreparedStatement stm = con.prepareStatement(query);
        stm.setObject(1,d.getDriverId());
        stm.setObject(2,d.getDriverNIC());
        stm.setObject(3,d.getDriverName());
        stm.setObject(4,d.getDriverAddress());
        stm.setObject(5,d.getDriverPhoneNumber());
        return stm.executeUpdate()>0;
    }

    @Override
    public String getNewDriverId() throws SQLException, ClassNotFoundException {
        PreparedStatement stm = DbConnection.getInstance().getConnection().prepareStatement("SELECT driverID FROM driver ORDER BY driverID DESC LIMIT 1");
        ResultSet resultSet = stm.executeQuery();

            String id = null;
            while (resultSet.next()) {
                String s = resultSet.getString(1);
                int tempId = Integer.parseInt(s.substring(1, 4));
                tempId = ++tempId;

                if (tempId < 10) {
                    id = "D00" + tempId;

                } else if (tempId >= 10 & tempId < 100) {
                    id = "D0" + tempId;
                } else if (tempId > 100) {
                    id = "D" + tempId;
                }


            }
            return id == null ? "D001" : id;
        }


    @Override
    public String getDId() throws SQLException, ClassNotFoundException {
        PreparedStatement stm=DbConnection.getInstance().getConnection().prepareStatement("SELECT driverID FROM driver ORDER BY driverID DESC LIMIT 1");
        ResultSet resultSet = stm.executeQuery();

            return resultSet.getString(1);
        }


    @Override
    public Driver searchDriver(String Id) throws SQLException, ClassNotFoundException {

        PreparedStatement stm = DbConnection.getInstance().getConnection().prepareStatement("SELECT * FROM driver WHERE driverID=?");
        stm.setObject(1,Id);

        ResultSet rst = stm.executeQuery();
        if (rst.next()){
            return new Driver(
                    rst.getString(1),
                    rst.getString(2),
                    rst.getString(3),
                    rst.getString(4),
                    rst.getString(5)
            );
        }else {

        }
        return null;
    }

    @Override
    public ArrayList<DriverTm> getDriver() throws SQLException, ClassNotFoundException {
        PreparedStatement stm = DbConnection.getInstance().getConnection().prepareStatement("SELECT * FROM driver");
        ResultSet resultSet = stm.executeQuery();
        ArrayList<DriverTm> driverList=new ArrayList();
        while(resultSet.next()){

            driverList.add(new DriverTm(resultSet.getString(1),resultSet.getString(2),resultSet.getString(3),resultSet.getString(4),resultSet.getString(5)));

        }

            return driverList;
        }


    @Override
    public boolean driverUpdate(Driver d) throws SQLException, ClassNotFoundException {
        PreparedStatement stm = DbConnection.getInstance().getConnection().prepareStatement
                ("UPDATE driver SET driverNIC=?, driverName=?, driverAddress=?,driverPhoneNumber=? WHERE driverID=?");
        stm.setObject(1,d.getDriverNIC());
        stm.setObject(2,d.getDriverName());
        stm.setObject(3,d.getDriverAddress());
        stm.setObject(4,d.getDriverPhoneNumber());
        stm.setObject(5,d.getDriverId());

        return stm.executeUpdate()>0;
    }

    @Override
    public boolean driverDelete(String Id) throws SQLException, ClassNotFoundException {
        if (DbConnection.getInstance().getConnection().prepareStatement("DELETE FROM driver WHERE driverID='"+Id+"'").executeUpdate()>0){
            return true;
        }else{
            return false;
        }

    }
}
