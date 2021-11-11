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
import view.TM.DriverTm;

import java.sql.SQLException;

public class DriverDeleteController {
    public TableView tblDriverDelete;
    public TableColumn colDriverId;
    public TableColumn colDriverNic;
    public TableColumn colDriverName;
    public TableColumn colDriverAddress;
    public TableColumn colPhoneNumber;
    public TextField txtDriverId;
    public TextField txtDriverNic;
    public TextField txtDriverName;
    public TextField txtDriverAddress;
    public TextField txtDPhoneNumber;


    public void initialize(){
        {
            ObservableList<DriverTm> list= null;
            try {
                list = FXCollections.observableArrayList(new DriverController().getDriver());
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
            colDriverId.setCellValueFactory(new PropertyValueFactory<>("driverId"));
            colDriverNic.setCellValueFactory(new PropertyValueFactory<>("driverNIC"));
            colDriverName.setCellValueFactory(new PropertyValueFactory<>("driverName"));
            colDriverAddress.setCellValueFactory(new PropertyValueFactory<>("driverAddress"));
            colPhoneNumber.setCellValueFactory(new PropertyValueFactory<>("contactNumber"));
            tblDriverDelete.setItems(list);
        }
    }

    public void txtDriverIdSearchOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
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
        txtDPhoneNumber.setText(driver.getDriverPhoneNumber());
    }

    private void clearTextBox() {
        txtDriverId.setText(null );
        txtDriverNic.setText(null);
        txtDriverName.setText(null);
        txtDriverAddress.setText(null);
        txtDPhoneNumber.setText(null);
    }

    public void deleteOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {

        if (new DriverController().driverDelete(txtDriverId.getText())){
            new Alert(Alert.AlertType.CONFIRMATION,"Deleted").show();
            tblDriverDelete.setItems(null);
            tblDriverDelete.setItems(FXCollections.observableArrayList(new DriverController().getDriver()));
            clearTextBox();
        }else{
            new Alert(Alert.AlertType.WARNING,"Try Again");
            clearTextBox();
        }
    }

    public void cancelOnAction(ActionEvent actionEvent) {
        clearTextBox();
    }
}
