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
import view.TM.DriverTm;
import view.TM.VehicleTm;

import java.sql.SQLException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.regex.Pattern;

public class VehicleAddController {
    public TextField txtVehicleNumber;
    public TextField txtVehicleId;
    public TextField txtVehicleType;
    public TextField txtVehicleRent;
    public TableView tblVehicleAdd;
    public TableColumn colVehicleAdd;
    public TableColumn colVehicleNumber;
    public TableColumn colVehicleType;
    public TableColumn colVehicleRent;
    public JFXButton btnAddId;

    LinkedHashMap<TextField, Pattern> map = new LinkedHashMap<>();
    Pattern codePattern = Pattern.compile("^(V0)[0-9]{1,}$");
    Pattern vehicleNumberPattern = Pattern.compile("[A-z [0-9]]{2,}");
    Pattern vehicleTypePattern = Pattern.compile("[A-z ]{1,}");
    Pattern rentPattern = Pattern.compile("[0-9.]{3,}");


    public void initialize(){

        btnAddId.setDisable(true);
        driverValidate();

        try {
            txtVehicleId.setText(new VehicleController().getNewVehicleId());
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        List<VehicleTm> vehicle = null;
        try {
            vehicle = new VehicleController().getVehicle();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        if (vehicle.isEmpty()){
            new Alert(Alert.AlertType.WARNING,"empty set").show();
        }


        ObservableList<VehicleTm> list= null;
        try {
            list = FXCollections.observableArrayList(new VehicleController().getVehicle());
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        colVehicleAdd.setCellValueFactory(new PropertyValueFactory<>("vehicleId"));
        colVehicleNumber.setCellValueFactory(new PropertyValueFactory<>("vehicleNumber"));
        colVehicleType.setCellValueFactory(new PropertyValueFactory<>("vehicleType"));
        colVehicleRent.setCellValueFactory(new PropertyValueFactory<>("vehicleRent"));

        tblVehicleAdd.setItems(list);
    }

    private void driverValidate() {
        map.put(txtVehicleId, codePattern);
        map.put(txtVehicleNumber, vehicleNumberPattern);
        map.put(txtVehicleType, vehicleTypePattern);
        map.put(txtVehicleRent, rentPattern);
    }

    public void cancelOcAction(ActionEvent actionEvent) {
        clearTextBox();
    }

    public void addOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        setData();
    }

    private void clearTextBox() {
    txtVehicleNumber.clear();
    txtVehicleType.clear();
    txtVehicleRent.clear();
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

        Vehicle v= new Vehicle(
                txtVehicleId.getText(),txtVehicleNumber.getText(),txtVehicleType.getText(),
                txtVehicleRent.getText()
        );
        new VehicleController().vehicleAdd(v);
        new Alert(Alert.AlertType.CONFIRMATION,"Complete").show();
        tblVehicleAdd.setItems(null);
        tblVehicleAdd.setItems(FXCollections.observableArrayList(new VehicleController().getVehicle()));

        clearTextBox();
        txtVehicleId.setText(new VehicleController().getNewVehicleId());
    }
}
