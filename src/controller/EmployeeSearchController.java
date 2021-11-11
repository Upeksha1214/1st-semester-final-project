package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import model.Employee;
import view.TM.EmployeeTm;

import java.sql.SQLException;

public class EmployeeSearchController {
    public TextField txtEmployeePhoneNumber;
    public TextField txtEmployeeId;
    public TextField txtEmployeeType;
    public TextField txtEmployeeNIC;
    public TextField txtEmployeeName;
    public TextField txtEmployeeAdders;
    public TextField txtEmployeeSalary;
    public TableView tblEmployeeSearch;
    public TableColumn colEmployeeId;
    public TableColumn colEmployeeType;
    public TableColumn colEmployeeNic;
    public TableColumn colEmployeeName;
    public TableColumn colEmployeeAddress;
    public TableColumn colEmPhoneNumber;
    public TableColumn colEmployeeSalary;

    public void btnSearchONAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {

        String Id =txtEmployeeId.getText();

        Employee employee=new EmployeeController().searchEmployee(Id);
        if (employee==null){
            new Alert(Alert.AlertType.WARNING,"Empty Result set").show();
            clearTextBox();
        }else {
            setData(employee);
        }
    }

    private void setData(Employee employee) {
        txtEmployeeId.setText(employee.getEmployeeId());
        txtEmployeeType.setText(employee.getEmployeeType());
        txtEmployeeNIC.setText(employee.getEmployeeNIC());
        txtEmployeeName.setText(employee.getEmployeeName());
        txtEmployeeAdders.setText(employee.getEmployeeAddress());
        txtEmployeePhoneNumber.setText(employee.getEmployeePhoneNumber());
        txtEmployeeSalary.setText(employee.getEmployeeSalary());
    }
    public void initialize(){
        try {
            ObservableList<EmployeeTm> list= FXCollections.observableArrayList(new EmployeeController().getEmployee());

            colEmployeeId.setCellValueFactory(new PropertyValueFactory<>("employeeId"));
            colEmployeeType.setCellValueFactory(new PropertyValueFactory<>("employeeType"));
            colEmployeeNic.setCellValueFactory(new PropertyValueFactory<>("employeeNIC"));
            colEmployeeName.setCellValueFactory(new PropertyValueFactory<>("employeeName"));
            colEmployeeAddress.setCellValueFactory(new PropertyValueFactory<>("employeeAddress"));
            colEmPhoneNumber.setCellValueFactory(new PropertyValueFactory<>("employeePhoneNumber"));
            colEmployeeSalary.setCellValueFactory(new PropertyValueFactory<>("employeeSalary"));
            tblEmployeeSearch.setItems(list);
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
    private void clearTextBox(){
        txtEmployeeId.setText(null);
        txtEmployeeType.setText(null);
        txtEmployeeNIC.setText(null);
        txtEmployeeName.setText(null);
        txtEmployeeAdders.setText(null);
        txtEmployeePhoneNumber.setText(null);
        txtEmployeeSalary.setText(null);
    }

}
