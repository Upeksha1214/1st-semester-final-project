package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import model.Vehicle;
import view.TM.VehicleTm;

import java.sql.SQLException;

public class VehicleDeleteController {
    public TextField txtVehicleNumber;
    public TextField txtVehicleId;
    public TextField txtVehicleType;
    public TextField txtVehicleRent;
    public TableView tblVehicleDelete;
    public TableColumn colVehicleId;
    public TableColumn colVehicleNumber;
    public TableColumn colVehicleType;
    public TableColumn colVehicleRent;

    public void initialize(){
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

        tblVehicleDelete.setItems(list);
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
        txtVehicleId.setText(null);
        txtVehicleNumber.setText(null);
        txtVehicleType.setText(null);
        txtVehicleRent.setText(null);
    }

    public void btnCancelOnAction(ActionEvent actionEvent) {
        clearTextBox();
    }

    public void btnDeleteOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        if (new VehicleController().vehicleDelete(txtVehicleId.getText())){
            new Alert(Alert.AlertType.CONFIRMATION,"Deleted").show();
            clearTextBox();
            tblVehicleDelete.setItems(null);
            tblVehicleDelete.setItems(FXCollections.observableArrayList(new VehicleController().getVehicle()));


        }else{
            new Alert(Alert.AlertType.WARNING,"Try Again");
        }
    }
}
