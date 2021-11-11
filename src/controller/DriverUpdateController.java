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

import java.sql.SQLException;
import java.util.LinkedHashMap;
import java.util.regex.Pattern;

public class DriverUpdateController {
    public TableView tblDriverUpdate;
    public TableColumn colDriverId;
    public TableColumn colDriverNic;
    public TableColumn colDriverName;
    public TableColumn colDriverAddress;
    public TableColumn colPhoneNumber;
    public TextField txtDriverId;
    public TextField txtDriverNic;
    public TextField txtDriverName;
    public TextField txtDriverAddress;
    public TextField txtPhoneNumber;
    public JFXButton btnUpdateId;

    LinkedHashMap<TextField, Pattern> map = new LinkedHashMap<>();
    Pattern codePattern = Pattern.compile("^(D0)[0-9]{1,}$");
    Pattern nicPattern = Pattern.compile("^[0-9]{12}|[0-9]{9}V$");
    Pattern namePattern = Pattern.compile("[A-z .]{1,}");
    Pattern AddressPattern = Pattern.compile("[A-z,[0-9]]{3,}");
    Pattern numberPattern = Pattern.compile("^(078|070|071|075|077|091|011|074|072|076)?[0-9]{7}$");

    public void initialize() throws SQLException, ClassNotFoundException {

        btnUpdateId.isDisable();
        driverValidate();

        ObservableList<DriverTm> list= FXCollections.observableArrayList(new DriverController().getDriver());
        colDriverId.setCellValueFactory(new PropertyValueFactory<>("driverId"));
        colDriverNic.setCellValueFactory(new PropertyValueFactory<>("driverNIC"));
        colDriverName.setCellValueFactory(new PropertyValueFactory<>("driverName"));
        colDriverAddress.setCellValueFactory(new PropertyValueFactory<>("driverAddress"));
        colPhoneNumber.setCellValueFactory(new PropertyValueFactory<>("contactNumber"));
        tblDriverUpdate.setItems(list);
    }

    private void driverValidate() {
        map.put(txtDriverId, codePattern);
        map.put(txtDriverNic, nicPattern);
        map.put(txtDriverName, namePattern);
        map.put(txtDriverAddress, AddressPattern);
        map.put(txtPhoneNumber, numberPattern);
    }

    public void txtDriverIdOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        String driverId=txtDriverId.getText();

        Driver driver=new DriverController().searchDriver(driverId);
        if (driver==null){
            new Alert(Alert.AlertType.WARNING,"Empty Result set").show();
            clearTextBox();

        }else {
            searchData(driver);
        }
    }

    private void searchData(Driver driver) {
        txtDriverId.setText(driver.getDriverId());
        txtDriverNic.setText(driver.getDriverNIC());
        txtDriverName.setText(driver.getDriverName());
        txtDriverAddress.setText(driver.getDriverAddress());
        txtPhoneNumber.setText(driver.getDriverPhoneNumber());
    }

    private void clearTextBox() {
        txtDriverId.clear();
        txtDriverNic.clear();
        txtDriverName.clear();
        txtDriverAddress.clear();
        txtPhoneNumber.clear();

    }

    public void updateOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        if (setData()){
            new Alert(Alert.AlertType.CONFIRMATION,"Updated..").show();
            clearTextBox();
        }else {
            new Alert(Alert.AlertType.WARNING,"Try Again").show();
        }
    }

    public void cancelOnAction(ActionEvent actionEvent) {
        clearTextBox();
    }

    public void employ_keyRelease(KeyEvent keyEvent) {
        Object response = Validation.validate(map, btnUpdateId);
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

    private boolean setData() throws SQLException, ClassNotFoundException {
        Driver d=new Driver(
                txtDriverId.getText(),txtDriverNic.getText(),txtDriverName.getText(),
                txtDriverAddress.getText(),txtPhoneNumber.getText()
        );

        if (new DriverController().driverUpdate(d)){
            tblDriverUpdate.setItems(null);
            tblDriverUpdate.setItems(FXCollections.observableArrayList(new DriverController().getDriver()));
            return true;
        }else {
            return false;
        }
    }
}
