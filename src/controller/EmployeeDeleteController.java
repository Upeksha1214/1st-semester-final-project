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

public class EmployeeDeleteController {

    public TableView tblEmployeeDelete;
    public TableColumn colEmployeeId;
    public TableColumn colEmployeeType;
    public TableColumn colEmployeeNic;
    public TableColumn colEmployeeName;
    public TableColumn colEmployeeAddress;
    public TableColumn colEmPhoneNumber;
    public TableColumn colEmployeeSalary;
    public TextField txtEmPhoneNumber;
    public TextField txtEmployeeId;
    public TextField txtEmployeeType;
    public TextField txtEmployeeNIC;
    public TextField txtEmployeeName;
    public TextField txtEmployeeAdders;
    public TextField txtEmployeeSalary;

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
            tblEmployeeDelete.setItems(list);
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void btnCancleOnAction(ActionEvent actionEvent) {
        clearTextBox();
    }

    public void getEmployeeOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
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
        txtEmPhoneNumber.setText(employee.getEmployeePhoneNumber());
        txtEmployeeSalary.setText(employee.getEmployeeSalary());
    }

    private void clearTextBox() {
        txtEmployeeId.clear();
        txtEmployeeType.clear();
        txtEmployeeNIC.clear();
        txtEmployeeName.clear();
        txtEmployeeAdders.clear();
        txtEmPhoneNumber.clear();
        txtEmployeeSalary.clear();
    }

    public void btnDeleteOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        if (new EmployeeController().employeeDelete(txtEmployeeId.getText())){
            new Alert(Alert.AlertType.CONFIRMATION, "Deleted").show();
            tblEmployeeDelete.setItems(null);
            tblEmployeeDelete.setItems(FXCollections.observableArrayList(new EmployeeController().getEmployee()));
        }else{
            new Alert(Alert.AlertType.WARNING, "Try Again").show();
        }
    }
}
