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
import model.Vehicle;
import view.TM.DriverTm;
import view.TM.VehicleTm;

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

public class VehicleController implements VehicleService{
    public Pane homePane;
    public Pane addPane;
    public Pane searchPane;
    public Pane updatePane;
    public Pane deletePane;
    public AnchorPane vehicleContext;
    public AnchorPane vehicleControllerContext;
    public Label lblTime;
    public Label lblDate;

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

        Stage window = (Stage) vehicleContext.getScene().getWindow();
        window.setScene(new Scene(FXMLLoader.load(getClass().getResource("../view/StoreKeeper.fxml"))));
    }

    public void homeEnteredOnAction(MouseEvent mouseEvent) {
        clearPane();
        new Pulse(homePane).play();
        homePane.setStyle("-fx-background-color: #6495ED");
    }

    public void addVehicleMouseOnAction(MouseEvent mouseEvent) throws IOException {
        clearPane();
        addPane.setStyle("-fx-background-color:#151B54");

        Parent fxml =FXMLLoader.load(getClass().getResource("../view/VehicleAdd.fxml"));
        vehicleControllerContext.getChildren().removeAll();
        vehicleControllerContext.getChildren().setAll(fxml);
    }

    public void addVehicleEnteredMouseOnAction(MouseEvent mouseEvent) {
        clearPane();
        new Pulse(addPane).play();
        addPane.setStyle("-fx-background-color: #6495ED");
    }

    public void searchMouseOnAction(MouseEvent mouseEvent) throws IOException {
        clearPane();
        searchPane.setStyle("-fx-background-color:#151B54");

        Parent fxml =FXMLLoader.load(getClass().getResource("../view/VehicleSearch.fxml"));
        vehicleControllerContext.getChildren().removeAll();
        vehicleControllerContext.getChildren().setAll(fxml);
    }
    public void searchEnterdMouseOnAction(MouseEvent mouseEvent) {
        clearPane();
        new Pulse(searchPane).play();
        searchPane.setStyle("-fx-background-color: #6495ED");
    }

    public void updateVehicleMouseOnAction(MouseEvent mouseEvent) throws IOException {
        clearPane();
        updatePane.setStyle("-fx-background-color:#151B54");

        Parent fxml =FXMLLoader.load(getClass().getResource("../view/VehicleUpdate.fxml"));
        vehicleControllerContext.getChildren().removeAll();
        vehicleControllerContext.getChildren().setAll(fxml);
    }

    public void updateVehicleEnteredMouseOnAction(MouseEvent mouseEvent) {
        clearPane();
        new Pulse(updatePane).play();
        updatePane.setStyle("-fx-background-color: #6495ED");
    }

    public void deleteVehicleMouseOnAction(MouseEvent mouseEvent) throws IOException {
        clearPane();
        deletePane.setStyle("-fx-background-color:#151B54");

        Parent fxml =FXMLLoader.load(getClass().getResource("../view/VehicleDelete.fxml"));
        vehicleControllerContext.getChildren().removeAll();
        vehicleControllerContext.getChildren().setAll(fxml);
    }

    public void deleteVehicleMouseEnteredOnAction(MouseEvent mouseEvent) {
        clearPane();
        new Pulse(deletePane).play();
        deletePane.setStyle("-fx-background-color:  #6495ED");
    }



    @Override
    public boolean vehicleAdd(Vehicle v) throws SQLException, ClassNotFoundException {

        Connection con = DbConnection.getInstance().getConnection();
        String query="INSERT INTO vehicle Values(?,?,?,?)";
        PreparedStatement stm = con.prepareStatement(query);
        stm.setObject(1,v.getVehicleId());
        stm.setObject(2,v.getVehicleNumber());
        stm.setObject(3,v.getVehicleType());
        stm.setObject(4,v.getVehicleRent());
        return stm.executeUpdate()>0;
    }

    @Override
    public String getNewVehicleId() throws SQLException, ClassNotFoundException {

            PreparedStatement stm = DbConnection.getInstance().getConnection().
            prepareStatement("SELECT vehicleID FROM vehicle ORDER BY vehicleID DESC LIMIT 1");
            ResultSet resultSet = stm.executeQuery();

            String id = null;
            while (resultSet.next()) {
                String s = resultSet.getString(1);
                int tempId = Integer.parseInt(s.substring(1, 4));
                tempId = ++tempId;

                if (tempId < 10) {
                    id = "V00" + tempId;

                } else if (tempId >= 10 & tempId < 100) {
                    id = "V0" + tempId;
                } else if (tempId > 100) {
                    id = "V" + tempId;
                }


            }
            return id == null ? "V001" : id;


    }

    @Override
    public String getVehicleId() throws SQLException, ClassNotFoundException {
        PreparedStatement stm=DbConnection.getInstance().getConnection().prepareStatement("SELECT vehicleID FROM vehicle ORDER BY vehicleID DESC LIMIT 1");
        ResultSet resultSet = stm.executeQuery();

        return resultSet.getString(1);
    }

    @Override
    public Vehicle searchVehicle(String Id) throws SQLException, ClassNotFoundException {

        PreparedStatement stm = DbConnection.getInstance().getConnection().
                prepareStatement("SELECT * FROM vehicle WHERE vehicleID=?");
        stm.setObject(1,Id);

        ResultSet rst = stm.executeQuery();
        if (rst.next()){
            return new Vehicle(
                    rst.getString(1),
                    rst.getString(2),
                    rst.getString(3),
                    rst.getString(4)
            );
        }else
            return null;
    }

    @Override
    public List<VehicleTm> getVehicle() throws SQLException, ClassNotFoundException {
        PreparedStatement stm = DbConnection.getInstance().getConnection().prepareStatement("SELECT * FROM vehicle");
        ResultSet resultSet = stm.executeQuery();
        ArrayList<VehicleTm> vehicleList=new ArrayList();
        while(resultSet.next()){

            vehicleList.add(new VehicleTm(resultSet.getString(1),resultSet.getString(2),
                    resultSet.getString(3),resultSet.getString(4)));

        }

        return vehicleList ;
    }


    @Override
    public boolean vehicleUpdate(Vehicle v) throws SQLException, ClassNotFoundException {
        PreparedStatement stm = DbConnection.getInstance().getConnection().prepareStatement
                ("UPDATE vehicle SET vehicleNumber=?, vehicleType=?,  vehicleRent =? WHERE vehicleID=?");
        stm.setObject(1,v.getVehicleNumber());
        stm.setObject(2,v.getVehicleType());
        stm.setObject(3,v.getVehicleRent());
        stm.setObject(4,v.getVehicleId());


        return stm.executeUpdate()>0;

    }

    @Override
    public boolean vehicleDelete(String Id) throws SQLException, ClassNotFoundException {
        if (DbConnection.getInstance().getConnection().prepareStatement
                ("DELETE FROM vehicle WHERE vehicleID='"+Id+"'").executeUpdate()>0){
            return true;
        }else{
            return false;
        }
    }
}
