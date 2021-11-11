package controller;

import Db.DbConnection;
import animatefx.animation.Pulse;
import animatefx.animation.Swing;
import controller.EmployeeService;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.util.Duration;
import model.Employee;
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

public class EmployeeController implements EmployeeService{

    public AnchorPane employeeHomeContext;
    public AnchorPane employeeContext;
    public Pane homePane;
    public Pane addPane;
    public Pane searchPane;
    public Pane updatePane;
    public Pane deletePane;
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

    public void homeOnMouseAction(MouseEvent mouseEvent) throws IOException {
        clearPane();
        homePane.setStyle("-fx-background-color:#151B54");

        Stage window = (Stage) employeeHomeContext.getScene().getWindow();
        window.setScene(new Scene(FXMLLoader.load(getClass().getResource("../view/ManagerForm.fxml"))));


    }

    public void homeOnMouseEntered(MouseEvent mouseEvent) {
        clearPane();
        new Pulse(homePane).play();
        homePane.setStyle("-fx-background-color: #6495ED");
    }

    public void addOnMouseAction(MouseEvent mouseEvent) throws IOException {
        clearPane();
        addPane.setStyle("-fx-background-color:#151B54");

        /*URL resource = getClass().getResource("../view/EmployeeAdd.fxml");
        Parent load = FXMLLoader.load(resource);
        employeeContext.getChildren().add(load);*/
        Parent fxml =FXMLLoader.load(getClass().getResource("../view/EmployeeAdd.fxml"));
        employeeContext.getChildren().removeAll();
        employeeContext.getChildren().setAll(fxml);

    }

    public void addMouseEnterdOnAction(MouseEvent mouseEvent) {
        clearPane();
        new Pulse(addPane).play();
        addPane.setStyle("-fx-background-color: #6495ED");

    }

    public void searchMouseOnAction(MouseEvent mouseEvent) throws IOException {
        clearPane();
        searchPane.setStyle("-fx-background-color:#151B54");

        /*URL resource = getClass().getResource("../view/EmployeeSearch.fxml");
        Parent load = FXMLLoader.load(resource);
        employeeContext.getChildren().add(load);*/

        Parent fxml =FXMLLoader.load(getClass().getResource("../view/EmployeeSearch.fxml"));
        employeeContext.getChildren().removeAll();
        employeeContext.getChildren().setAll(fxml);
    }

    public void searchMouseEnter(MouseEvent mouseEvent) {
        clearPane();
        new Pulse(searchPane).play();
        searchPane.setStyle("-fx-background-color: #6495ED");
    }

    public void updateOnAction(MouseEvent mouseEvent) throws IOException {
        clearPane();
        updatePane.setStyle("-fx-background-color:#151B54");

        /*URL resource = getClass().getResource("../view/EmployeeUpdate.fxml");
        Parent load = FXMLLoader.load(resource);
        employeeContext.getChildren().add(load);*/
        Parent fxml =FXMLLoader.load(getClass().getResource("../view/EmployeeUpdate.fxml"));
        employeeContext.getChildren().removeAll();
        employeeContext.getChildren().setAll(fxml);
    }

    public void updateMouseEnterOnAction(MouseEvent mouseEvent) {
        clearPane();
        new Pulse(updatePane).play();
        updatePane.setStyle("-fx-background-color: #6495ED");
    }

    public void deleteOnAction(MouseEvent mouseEvent) throws IOException {
        clearPane();
        deletePane.setStyle("-fx-background-color:#151B54");

        /*URL resource = getClass().getResource("../view/EmployeeDelete.fxml");
        Parent load = FXMLLoader.load(resource);
        employeeContext.getChildren().add(load);*/
        Parent fxml =FXMLLoader.load(getClass().getResource("../view/EmployeeDelete.fxml"));
        employeeContext.getChildren().removeAll();
        employeeContext.getChildren().setAll(fxml);
    }

    public void deleteMouseEnterOnAction(MouseEvent mouseEvent) {
        clearPane();
        new Pulse(deletePane).play();
        deletePane.setStyle("-fx-background-color:  #6495ED");
    }




    @Override
    public boolean employeeAdd(Employee e) throws SQLException, ClassNotFoundException {
        Connection con = DbConnection.getInstance().getConnection();
        String query="INSERT INTO employee Values(?,?,?,?,?,?,?)";
        PreparedStatement stm = con.prepareStatement(query);
        stm.setObject(1,e.getEmployeeId());
        stm.setObject(2,e.getEmployeeType());
        stm.setObject(3,e.getEmployeeNIC());
        stm.setObject(4,e.getEmployeeName());
        stm.setObject(5,e.getEmployeeAddress());
        stm.setObject(6,e.getEmployeePhoneNumber());
        stm.setObject(7,e.getEmployeeSalary());
        return stm.executeUpdate()>0;
    }

    @Override
    public String getNewEmployeeId() throws SQLException, ClassNotFoundException {
        PreparedStatement stm=DbConnection.getInstance().getConnection().prepareStatement("SELECT employeeId FROM employee ORDER BY employeeId DESC LIMIT 1");
        ResultSet resultSet = stm.executeQuery();
        String id=null;
        while(resultSet.next()){
            String s=resultSet.getString(1);
            int tempId= Integer.parseInt(s.substring(1,4));
            tempId=++tempId;

            if (tempId<10){
                id="E00"+tempId;

            }else if(tempId>=10 & tempId<100){
                id="E0"+tempId;
            }else if(tempId>100){
                id="E"+tempId;
            }


        }
        return id==null ? "E001" : id;

    }

    @Override
    public String getEmId() throws SQLException, ClassNotFoundException {
        PreparedStatement stm=DbConnection.getInstance().getConnection().prepareStatement("SELECT employeeId FROM employee ORDER BY employeeId DESC LIMIT 1");
        ResultSet resultSet = stm.executeQuery();
        resultSet.next();


        return resultSet.getString(1);
    }

    @Override
    public Employee searchEmployee(String Id) throws SQLException, ClassNotFoundException {
        PreparedStatement stm = DbConnection.getInstance()
                .getConnection().prepareStatement("SELECT * FROM employee WHERE employeeId=?");
        stm.setObject(1,Id);

        ResultSet rst = stm.executeQuery();
        if (rst.next()) {
            return new Employee(
                    rst.getString(1),
                    rst.getString(2),
                    rst.getString(3),
                    rst.getString(4),
                    rst.getString(5),
                    rst.getString(6),
                    rst.getString(7)
            );

        } else {
            return null;
        }
    }

    @Override
    public ArrayList<EmployeeTm> getEmployee() throws SQLException, ClassNotFoundException {
        PreparedStatement stm=DbConnection.getInstance().getConnection().prepareStatement("SELECT * FROM employee");
        ResultSet resultSet = stm.executeQuery();
        ArrayList<EmployeeTm> list=new ArrayList<>();
        while (resultSet.next()){
            list.add(new EmployeeTm(resultSet.getString(1),resultSet.getString(2),resultSet.getString(3),resultSet.getString(4),resultSet.getString(5),resultSet.getString(6),resultSet.getString(7)));
        }
        return list;
    }

    @Override
    public boolean employeeUpdate(Employee e) throws SQLException, ClassNotFoundException {

        PreparedStatement stm = DbConnection.getInstance().getConnection().prepareStatement("UPDATE employee SET employeeType=?, employeeNIC=?, employeeName=?,employeeAddress=?,employeePhoneNumber=?,employeeSalary=? WHERE employeeId=?");
        stm.setObject(1,e.getEmployeeType());
        stm.setObject(2,e.getEmployeeNIC());
        stm.setObject(3,e.getEmployeeName());
        stm.setObject(4,e.getEmployeeAddress());
        stm.setObject(5,e.getEmployeePhoneNumber());
        stm.setObject(6,e.getEmployeeSalary());
        stm.setObject(7,e.getEmployeeId());
        return stm.executeUpdate()>0;
    }

    @Override
    public boolean employeeDelete(String Id) throws SQLException, ClassNotFoundException {
        if (DbConnection.getInstance().getConnection().prepareStatement("DELETE FROM employee WHERE employeeId='"+Id+"'").executeUpdate()>0){
            return true;
        }else{
            return false;
        }
    }


}
