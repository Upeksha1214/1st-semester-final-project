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
import model.Vehicle;
import utill.Validation;
import view.TM.VehicleTm;

import java.sql.SQLException;
import java.util.LinkedHashMap;
import java.util.regex.Pattern;

public class VehicleUpdateController {
    public TextField txtVehicleNumber;
    public TextField txtVehicleId;
    public TextField txtVehicleType;
    public TextField txtVehicleRent;
    public TableView tblVehicleUpdate;
    public TableColumn colVehicleId;
    public TableColumn colVehicleNumber;
    public TableColumn colVehicleType;
    public TableColumn colVehicleRent;
    public JFXButton btnUpdateId;

    LinkedHashMap<TextField, Pattern> map = new LinkedHashMap<>();
    Pattern codePattern = Pattern.compile("^(V0)[0-9]{1,}$");
    Pattern vehicleNumberPattern = Pattern.compile("[A-z [0-9]]{2,}");
    Pattern vehicleTypePattern = Pattern.compile("[A-z ]{1,}");
    Pattern rentPattern = Pattern.compile("[0-9.]{3,}");

    public void initialize(){

        btnUpdateId.setDisable(true);
        driverValidate();

        ObservableList<VehicleTm> list= null;
        try {
            list = FXCollections.observableArrayList(new VehicleController().getVehicle());
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        colVehicleId.setCellValueFactory(new PropertyValueFactory<>("vehicleId"));
        colVehicleNumber.setCellValueFactory(new PropertyValueFactory<>("vehicleNumber"));
        colVehicleType.setCellValueFactory(new PropertyValueFactory<>("vehicleType"));
        colVehicleRent.setCellValueFactory(new PropertyValueFactory<>("vehicleRent"));

        tblVehicleUpdate.setItems(list);
    }

    private void driverValidate() {
        map.put(txtVehicleId, codePattern);
        map.put(txtVehicleNumber, vehicleNumberPattern);
        map.put(txtVehicleType, vehicleTypePattern);
        map.put(txtVehicleRent, rentPattern);
    }

    public void txtVehicleSearchOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        String vehicleId=txtVehicleId.getText();

        Vehicle vehicle=new VehicleController().searchVehicle(vehicleId);
        if (vehicle==null){
            new Alert(Alert.AlertType.WARNING,"Empty Result set").show();
            clearTextBox();

        }else {
            setData(vehicle);
        }
    }

    private void setData(Vehicle vehicle) {
        txtVehicleId.setText(vehicle.getVehicleId());
        txtVehicleNumber.setText(vehicle.getVehicleNumber());
        txtVehicleType.setText(vehicle.getVehicleType());
        txtVehicleRent.setText(vehicle.getVehicleRent());
    }

    private void clearTextBox() {
        txtVehicleId.clear();
        txtVehicleNumber.clear();
        txtVehicleType.clear();
        txtVehicleRent.clear();
    }

    public void btnCancelOnAction(ActionEvent actionEvent) {
        clearTextBox();
    }

    public void updateOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        if(setData()){
            new Alert(Alert.AlertType.CONFIRMATION,"Updated..").show();
            clearTextBox();
        }else {
            new Alert(Alert.AlertType.WARNING, "Try Again").show();
        }
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
        Vehicle v=new Vehicle(
                txtVehicleId.getText(),txtVehicleNumber.getText(),txtVehicleType.getText(),
                txtVehicleRent.getText()
        );

        if (new VehicleController().vehicleUpdate(v)){
            tblVehicleUpdate.setItems(null);
            tblVehicleUpdate.setItems(FXCollections.observableArrayList(new VehicleController().getVehicle()));
            return true;
        }else {
            return false;
        }
    }
}
