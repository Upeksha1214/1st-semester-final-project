package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import model.Driver;
import model.Store;
import view.TM.DriverTm;

import java.sql.SQLException;

public class DriverSearchController {
    public TableView tblDriverSearch;
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

    public void initialize() throws SQLException, ClassNotFoundException {
        ObservableList<DriverTm> list= FXCollections.observableArrayList(new DriverController().getDriver());
        colDriverId.setCellValueFactory(new PropertyValueFactory<>("driverId"));
        colDriverNic.setCellValueFactory(new PropertyValueFactory<>("driverNIC"));
        colDriverName.setCellValueFactory(new PropertyValueFactory<>("driverName"));
        colDriverAddress.setCellValueFactory(new PropertyValueFactory<>("driverAddress"));
        colPhoneNumber.setCellValueFactory(new PropertyValueFactory<>("contactNumber"));
        tblDriverSearch.setItems(list);
    }

    public void txtSearchDriverOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {

        String driverId=txtDriverId.getText();

        Driver driver=new DriverController().searchDriver(driverId);
        if (driver==null){
            new Alert(Alert.AlertType.WARNING,"Empty Result set").show();
            clearTextBox();

        }else {
            setData(driver);
        }
    }

    private void setData(Driver driver) {
        txtDriverId.setText(driver.getDriverId());
        txtDriverNic.setText(driver.getDriverNIC());
        txtDriverName.setText(driver.getDriverName());
        txtDriverAddress.setText(driver.getDriverAddress());
        txtPhoneNumber.setText(driver.getDriverPhoneNumber());
    }

    private void clearTextBox() {
        txtDriverId.setText(null );
        txtDriverNic.setText(null);
        txtDriverName.setText(null);
        txtDriverAddress.setText(null);
        txtPhoneNumber.setText(null);
    }

    public void cancelOnAction(ActionEvent actionEvent) {
        clearTextBox();
    }
}
