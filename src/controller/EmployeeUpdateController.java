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
import model.Employee;
import utill.Validation;
import view.TM.EmployeeTm;

import java.sql.SQLException;
import java.util.LinkedHashMap;
import java.util.regex.Pattern;

public class EmployeeUpdateController {

    public TableView tblEmployeeUpdate;
    public TableColumn colEmployeeId;
    public TableColumn colEmployeeType;
    public TableColumn colEmployeeNIC;
    public TableColumn colEmployeeName;
    public TableColumn colEmployeeAddress;
    public TableColumn colPhoneNumber;
    public TableColumn colEmployeeSalary;
    public TextField txtEmPhoneNumber;
    public TextField txtEmployeeId;
    public TextField txtEmployeeType;
    public TextField txtEmployeeNIC;
    public TextField txtEmployeeName;
    public TextField txtEmployeeAddress;
    public TextField txtEmployeeSalary;
    public JFXButton btnUpdateId;

    LinkedHashMap<TextField, Pattern> map = new LinkedHashMap<>();
    Pattern codePattern = Pattern.compile("^(E0)[0-9]{1,}$");
    Pattern typePattern = Pattern.compile("^[a -z0-9 ]{2,}$");
    Pattern nicPattern = Pattern.compile("^[0-9]{12}|[0-9]{9}V$");
    Pattern namePattern = Pattern.compile("[A-z .]{1,}");
    Pattern AddressPattern = Pattern.compile("[A-z,[0-9]]{3,}");
    Pattern numberPattern = Pattern.compile("^(078|070|071|075|077|091|011|074|072|076)?[0-9]{7}$");
    Pattern salaryPattern = Pattern.compile("^[0-9]{4,}$");

    public void initialize(){
        btnUpdateId.isDisable();
        employeeValidate();
        try {
            ObservableList<EmployeeTm> list= FXCollections.observableArrayList(new EmployeeController().getEmployee());

            colEmployeeId.setCellValueFactory(new PropertyValueFactory<>("employeeId"));
            colEmployeeType.setCellValueFactory(new PropertyValueFactory<>("employeeType"));
            colEmployeeNIC.setCellValueFactory(new PropertyValueFactory<>("employeeNIC"));
            colEmployeeName.setCellValueFactory(new PropertyValueFactory<>("employeeName"));
            colEmployeeAddress.setCellValueFactory(new PropertyValueFactory<>("employeeAddress"));
            colPhoneNumber.setCellValueFactory(new PropertyValueFactory<>("employeePhoneNumber"));
            colEmployeeSalary.setCellValueFactory(new PropertyValueFactory<>("employeeSalary"));
            tblEmployeeUpdate.setItems(list);
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void employeeValidate() {
        map.put(txtEmployeeId, codePattern);
        map.put(txtEmployeeType, typePattern);
        map.put(txtEmployeeNIC, nicPattern);
        map.put(txtEmployeeName, namePattern);
        map.put(txtEmployeeAddress, AddressPattern);
        map.put(txtEmPhoneNumber, numberPattern);
        map.put(txtEmployeeSalary, salaryPattern);
    }

    public void txtGetEmployeeSearchOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
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
        txtEmployeeAddress.setText(employee.getEmployeeAddress());
        txtEmPhoneNumber.setText(employee.getEmployeePhoneNumber());
        txtEmployeeSalary.setText(employee.getEmployeeSalary());
    }

    private void clearTextBox() {
        txtEmployeeId.clear();
        txtEmployeeType.clear();
        txtEmployeeNIC.clear();
        txtEmployeeName.clear();
        txtEmployeeAddress.clear();
        txtEmPhoneNumber.clear();
        txtEmployeeSalary.clear();
    }

    public void btnUpdate(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {

        if (setData()){
            new Alert(Alert.AlertType.CONFIRMATION, "Updated..").show();
            clearTextBox();
        }else{
            new Alert(Alert.AlertType.WARNING,"Try Again").show();
        }
    }

    public void employ_keyRelese(KeyEvent keyEvent) {
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
        Employee e=new Employee(
            txtEmployeeId.getText(),txtEmployeeType.getText(),
            txtEmployeeNIC.getText(),txtEmployeeName.getText(),
            txtEmployeeAddress.getText(),txtEmPhoneNumber.getText(),
            txtEmployeeSalary.getText()
    );

        if (new EmployeeController().employeeUpdate(e)) {

            tblEmployeeUpdate.setItems(null);
            tblEmployeeUpdate.setItems(FXCollections.observableArrayList(new EmployeeController().getEmployee()));
            return true;
        }else

        return false;
    }
}
