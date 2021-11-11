package controller;

import com.jfoenix.controls.JFXButton;

import javafx.beans.binding.Bindings;
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
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.regex.Pattern;

public class EmployeeAddController {
    
    public TextField txtEmployeeTelNumber;
    public TextField txtEmployeeId;
    public TextField txtEmployeeType;
    public TextField txtEmployeeNic;
    public TextField txtEmployeeName;
    public TextField txtEmployeeAddress;
    public TextField txtEmployeeSalary;
    public TableView<EmployeeTm> tblEmployeeAdd;
    public TableColumn colEmployeeId;
    public TableColumn colEmployeeType;
    public TableColumn colEmployeeNic;
    public TableColumn colEmployeeName;
    public TableColumn colEmployeeAddress;
    public TableColumn colEmPhoneNumber;
    public TableColumn colEmployeeSalary;
    public JFXButton btnAddTableId;


    LinkedHashMap<TextField, Pattern> map = new LinkedHashMap<>();
    Pattern codePattern = Pattern.compile("^(E0)[0-9]{1,}$");
    Pattern typePattern = Pattern.compile("^[a -z0-9 ]{2,}$");
    Pattern nicPattern = Pattern.compile("^[0-9]{12}|[0-9]{9}V$");
    Pattern namePattern = Pattern.compile("[A-z .]{1,}");
    Pattern AddressPattern = Pattern.compile("[A-z,[0-9]]{3,}");
    Pattern numberPattern = Pattern.compile("^(078|070|071|075|077|091|011|074|072|076)?[0-9]{7}$");
    Pattern salaryPattern = Pattern.compile("^[0-9]{4,}$");


    public void initialize(){
        btnAddTableId.setDisable(true);
        employeeValidate();

        try {
            loadEmployeeDetailsToTable(new EmployeeController().getEmployee());
        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
        }
        /*if (Bindings.isEmpty(tblEmployeeAdd.getItems())){
            new Alert(Alert.AlertType.WARNING,"empty set").show();
        }*/

        try {
            txtEmployeeId.setText(new EmployeeController().getNewEmployeeId());
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void loadEmployeeDetailsToTable(ArrayList<EmployeeTm> employee) {
        ObservableList<EmployeeTm> obList=FXCollections.observableArrayList();
        employee.forEach(e->{
            obList.add(new EmployeeTm(e.getEmployeeId(),e.getEmployeeType(),e.getEmployeeNIC(),e.getEmployeeName(),e.getEmployeeAddress(),e.getEmployeePhoneNumber(),e.getEmployeeSalary()));   
        });
        if(obList.isEmpty()){
            new Alert(Alert.AlertType.WARNING,"empty set").show();
            return;
        }
        tblEmployeeAdd.setItems(obList);
        initcols();
    }

    private void initcols() {
        colEmployeeId.setCellValueFactory(new PropertyValueFactory<>("employeeId"));
        colEmployeeType.setCellValueFactory(new PropertyValueFactory<>("employeeType"));
        colEmployeeNic.setCellValueFactory(new PropertyValueFactory<>("employeeNIC"));
        colEmployeeName.setCellValueFactory(new PropertyValueFactory<>("employeeName"));
        colEmployeeAddress.setCellValueFactory(new PropertyValueFactory<>("employeeAddress"));
        colEmPhoneNumber.setCellValueFactory(new PropertyValueFactory<>("employeePhoneNumber"));
        colEmployeeSalary.setCellValueFactory(new PropertyValueFactory<>("employeeSalary"));
    }

    private void employeeValidate() {
        map.put(txtEmployeeId, codePattern);
        map.put(txtEmployeeType, typePattern);
        map.put(txtEmployeeNic, nicPattern);
        map.put(txtEmployeeName, namePattern);
        map.put(txtEmployeeAddress, AddressPattern);
        map.put(txtEmployeeTelNumber, numberPattern);
        map.put(txtEmployeeSalary, salaryPattern);
    }


    public void btnAddTable(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        setData();

    }

    private void clearTextBox(){
        txtEmployeeType.clear();
        txtEmployeeNic.clear();
        txtEmployeeName.clear();
        txtEmployeeAddress.clear();
        txtEmployeeTelNumber.clear();
        txtEmployeeSalary.clear();
    }


    public void cancelOnAction(ActionEvent actionEvent) {
        clearTextBox();
    }

    public void employ_keyRelese(KeyEvent keyEvent)  {
        Object response = Validation.validate(map, btnAddTableId);
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
        Employee e = new Employee(
                txtEmployeeId.getText(), txtEmployeeType.getText(), txtEmployeeNic.getText(),
                txtEmployeeName.getText(), txtEmployeeAddress.getText(), txtEmployeeTelNumber.getText(),
                txtEmployeeSalary.getText()
        );


        new EmployeeController().employeeAdd(e);

        new Alert(Alert.AlertType.CONFIRMATION,"Complete").show();
        loadEmployeeDetailsToTable(new EmployeeController().getEmployee());

        clearTextBox();

        txtEmployeeId.setText(new EmployeeController().getNewEmployeeId());
    }

    public void phoneNumberKeyReleasedOnAction(KeyEvent keyEvent) {
    }
}
