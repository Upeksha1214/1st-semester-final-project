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
import model.Salary;
import model.Shop;
import view.TM.EmployeeTm;
import view.TM.SalaryTm;

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

public class SalaryController {
    public Pane homePane;
    public Pane addPane;
    public Pane searchPane;
    public Pane updatePane;
    public Pane deletePane;
    public AnchorPane salaryContext;
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

    public void homeMouseClickedOnAction(MouseEvent mouseEvent) throws IOException {
        clearPane();
        homePane.setStyle("-fx-background-color:#151B54");

        Stage window = (Stage) salaryContext.getScene().getWindow();
        window.setScene(new Scene(FXMLLoader.load(getClass().getResource("../view/ManagerForm.fxml"))));
    }

    public void homeMouseEnterOnAction(MouseEvent mouseEvent) {
        clearPane();
        new Pulse(homePane).play();
        homePane.setStyle("-fx-background-color: #6495ED");
    }

    public void addSalaryMouseClickedOnAction(MouseEvent mouseEvent) throws IOException {
        clearPane();
        addPane.setStyle("-fx-background-color:#151B54");

        /*URL resource = getClass().getResource("../view/SalaryAdd.fxml");
        Parent load = FXMLLoader.load(resource);
        salaryContext.getChildren().add(load);*/
        Parent fxml =FXMLLoader.load(getClass().getResource("../view/SalaryAdd.fxml"));
        salaryContext.getChildren().removeAll();
        salaryContext.getChildren().setAll(fxml);

    }

    public void addSalaryMouseEnterOnAction(MouseEvent mouseEvent) {
        clearPane();
        new Pulse(addPane).play();
        addPane.setStyle("-fx-background-color: #6495ED");
    }

    public void searchSalaryMouseClickOnAction(MouseEvent mouseEvent) throws IOException {
        clearPane();
        searchPane.setStyle("-fx-background-color:#151B54");

        Parent fxml =FXMLLoader.load(getClass().getResource("../view/SalarySearch.fxml"));
        salaryContext.getChildren().removeAll();
        salaryContext.getChildren().setAll(fxml);
    }

    public void searchMouseEnteredOnAction(MouseEvent mouseEvent) {
        clearPane();
        new Pulse(searchPane).play();
        searchPane.setStyle("-fx-background-color: #6495ED");
    }

    public void updateSalaryMouseClickOnAction(MouseEvent mouseEvent) throws IOException {
        clearPane();
        updatePane.setStyle("-fx-background-color:#151B54");

        /*URL resource = getClass().getResource("../view/SalaryUpdate.fxml");
        Parent load = FXMLLoader.load(resource);
        salaryContext.getChildren().add(load);*/
        Parent fxml =FXMLLoader.load(getClass().getResource("../view/SalaryUpdate.fxml"));
        salaryContext.getChildren().removeAll();
        salaryContext.getChildren().setAll(fxml);
    }

    public void UpdateMouseEnteredOnAction(MouseEvent mouseEvent) {
        clearPane();
        new Pulse(updatePane).play();
        updatePane.setStyle("-fx-background-color: #6495ED");
    }

    public void deleteSalaryMouseClickOnAction(MouseEvent mouseEvent) throws IOException {
        clearPane();
        deletePane.setStyle("-fx-background-color:#151B54");

        /*URL resource = getClass().getResource("../view/SalaryDelete.fxml");
        Parent load = FXMLLoader.load(resource);
        salaryContext.getChildren().add(load);*/

        Parent fxml =FXMLLoader.load(getClass().getResource("../view/SalaryDelete.fxml"));
        salaryContext.getChildren().removeAll();
        salaryContext.getChildren().setAll(fxml);
    }

    public void deleteMouseOnAction(MouseEvent mouseEvent) {
        clearPane();
        new Pulse(deletePane).play();
        deletePane.setStyle("-fx-background-color:  #6495ED");
    }

    public String getNewSalaryId() throws SQLException, ClassNotFoundException {
        PreparedStatement stm = DbConnection.getInstance().getConnection().prepareStatement("SELECT salaryId FROM salary ORDER BY salaryId DESC LIMIT 1");
        ResultSet resultSet = stm.executeQuery();
        String id = null;
        while (resultSet.next()) {
            String s = resultSet.getString(1);
            int tempId = Integer.parseInt(s.substring(1, 4));
            tempId = ++tempId;

            if (tempId < 10) {
                id = "S00" + tempId;

            } else if (tempId >= 10 & tempId < 100) {
                id = "S0" + tempId;
            } else if (tempId > 100) {
                id = "s" + tempId;
            }


        }
        return id == null ? "S001" : id;
    }

    public static List<String> getAllEmployeeId() throws SQLException, ClassNotFoundException {
        Connection con = DbConnection.getInstance().getConnection();
        PreparedStatement stm = con.prepareStatement("SELECT employeeId FROM employee");
        ResultSet rst = stm.executeQuery();
        List<String> employeeId = new ArrayList<>();
        while (rst.next()) {
            employeeId.add(rst.getString("employeeId"));
        }
        return employeeId;
    }

    public String getEmployeeSalary(String employeeSalary) throws SQLException, ClassNotFoundException {
        Connection con = DbConnection.getInstance().getConnection();
        PreparedStatement stm = con.prepareStatement("SELECT employeeSalary FROM employee WHERE employeeId=?");
        stm.setObject(1, employeeSalary);
        ResultSet rst = stm.executeQuery();
        if (rst.next()) {
            return rst.getString("employeeSalary");

        }
        return null;
    }

    public boolean salaryAdd( Salary s) throws SQLException, ClassNotFoundException {
        Connection con = DbConnection.getInstance().getConnection();
        String query="INSERT INTO salary Values(?,?,?,?,?,?)";
        PreparedStatement stm = con.prepareStatement(query);
        stm.setObject(1,s.getSalaryId());
        stm.setObject(2,s.getEmployeeId());
        stm.setObject(3,s.getEmployeeSalary());
        stm.setObject(4,s.getIncentive());
        stm.setObject(5,s.getOt());
        stm.setObject(6,s.getTotal());
        return stm.executeUpdate()>0;

    }

    public ArrayList<SalaryTm> getSalary() throws SQLException, ClassNotFoundException {
        PreparedStatement stm = DbConnection.getInstance().getConnection().prepareStatement("SELECT * FROM salary");
        ResultSet resultSet = stm.executeQuery();
        ArrayList<SalaryTm> list = new ArrayList<>();
        while (resultSet.next()) {
            list.add(new SalaryTm(resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getString(3),
                    resultSet.getString(4),
                    resultSet.getString(5),
                    resultSet.getString(6)));
        }
        return list;
    }

    public Salary searchSalary(String Id) throws SQLException, ClassNotFoundException {
        PreparedStatement stm = DbConnection.getInstance().getConnection().
                prepareStatement("SELECT * FROM salary WHERE salaryId=?");
        stm.setObject(1,Id);

        ResultSet rst = stm.executeQuery();
        if (rst.next()){
            return new Salary(
                    rst.getString(1),
                    rst.getString(2),
                    rst.getString(3),
                    rst.getString(4),
                    rst.getString(5),
                    rst.getString(6)
            );
        }else
            return null;

    }
    public boolean salaryUpdate(Salary s) throws SQLException, ClassNotFoundException {
        PreparedStatement stm = DbConnection.getInstance().getConnection().prepareStatement
                ("UPDATE salary SET employeeId=?, employeeSalary=?,  incentive =?, ot=?, total=? WHERE salaryId=?");
        stm.setObject(1,s.getEmployeeId());
        stm.setObject(2,s.getEmployeeSalary());
        stm.setObject(3,s.getIncentive());
        stm.setObject(4,s.getOt());
        stm.setObject(5,s.getTotal());
        stm.setObject(6,s.getSalaryId());

        return stm.executeUpdate()>0;

    }

    public boolean salaryDelete(String Id) throws SQLException, ClassNotFoundException {
        if (DbConnection.getInstance().getConnection().prepareStatement
                ("DELETE FROM salary WHERE salaryId='"+Id+"'").executeUpdate()>0){
            return true;
        }else{
            return false;
        }
    }
}
