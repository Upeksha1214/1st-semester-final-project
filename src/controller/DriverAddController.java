package controller;

import com.jfoenix.controls.JFXButton;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import model.Driver;
import model.Employee;
import utill.Validation;
import view.TM.DriverTm;
import view.TM.EmployeeTm;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.regex.Pattern;

public class DriverAddController {
    public TableView<DriverTm> tblDriverAdd;
    public TableColumn colDriverId;
    public TableColumn colDriverNic;
    public TableColumn colDriverName;
    public TableColumn colDriverAddress;
    public TableColumn colPhoneNumber;
    public TextField txtDriverId;
    public TextField txtDriverName;
    public TextField txtDriverNice;
    public TextField txtDriverAddress;
    public TextField txtDPhoneNumber;
    public JFXButton btnAddId;


    LinkedHashMap<TextField, Pattern> map = new LinkedHashMap<>();
    Pattern codePattern = Pattern.compile("^(D0)[0-9]{1,}$");
    Pattern nicPattern = Pattern.compile("^[0-9]{12}|[0-9]{9}V$");
    Pattern namePattern = Pattern.compile("[A-z .]{1,}");
    Pattern AddressPattern = Pattern.compile("[A-z, [0-9]]{3,}");
    Pattern numberPattern = Pattern.compile("^(078|070|071|075|077|091|011|074|072|076)?[0-9]{7}$");

    public void initialize(){
        btnAddId.isDisable();
        driverValidate();

        try {
            txtDriverId.setText(new DriverController().getNewDriverId());
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }


        try {

            List<DriverTm> driver = new DriverController().getDriver();
            if (driver.isEmpty()){
                new Alert(Alert.AlertType.WARNING,"empty set").show();
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        try {
            ObservableList<DriverTm> list=FXCollections.observableArrayList(new DriverController().getDriver());
            colDriverId.setCellValueFactory(new PropertyValueFactory<>("driverId"));
            colDriverNic.setCellValueFactory(new PropertyValueFactory<>("driverNIC"));
            colDriverName.setCellValueFactory(new PropertyValueFactory<>("driverName"));
            colDriverAddress.setCellValueFactory(new PropertyValueFactory<>("driverAddress"));
            colPhoneNumber.setCellValueFactory(new PropertyValueFactory<>("contactNumber"));
            tblDriverAdd.setItems(list);


        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void driverValidate() {
        map.put(txtDriverId, codePattern);
        map.put(txtDriverNice, nicPattern);
        map.put(txtDriverName, namePattern);
        map.put(txtDriverAddress, AddressPattern);
        map.put(txtDPhoneNumber, numberPattern);
    }

    public void btnAdd(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        setData();
    }


    private void clearTextBox() {
        
        txtDriverNice.clear();
        txtDriverName.clear();
        txtDriverAddress.clear();
        txtDPhoneNumber.clear();
    }

    public void btnCancle(ActionEvent actionEvent) {
        clearTextBox();
    }

    public void employ_keyRelease(KeyEvent keyEvent) {
        Object response = Validation.validate(map, btnAddId);
        if (keyEvent.getCode() == KeyCode.ENTER) {
            if (response instanceof TextField) {
                TextField errorText = (TextField) response;
                errorText.requestFocus();
            } else if (response instanceof Boolean) {
                try {
                    setData();
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }

            }
        }
    }

    private void setData() throws SQLException, ClassNotFoundException {
        Driver d = new Driver(
                txtDriverId.getText(), txtDriverNice.getText(), txtDriverName.getText(),
                txtDriverAddress.getText(), txtDPhoneNumber.getText()
        );

        new DriverController().driverAdd(d);
        new Alert(Alert.AlertType.CONFIRMATION, "Complete").show();
        tblDriverAdd.setItems(null);
        tblDriverAdd.setItems(FXCollections.observableArrayList(new DriverController().getDriver()));

        clearTextBox();
        txtDriverId.setText(new DriverController().getNewDriverId());

    }
}
