package controller;

import com.jfoenix.controls.JFXButton;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import model.Order;
import model.Salary;
import utill.Validation;
import view.TM.SalaryTm;

import java.sql.SQLException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.regex.Pattern;

public class SalaryUpdateController {
    public ComboBox cmbEmployeeId;
    public TextField txtSalaryId;
    public TextField txtTotal;
    public TextField txtOt;
    public TextField txtIncentive;
    public TextField txtEmployeeSalary;
    public TableView tblSalaryAdd;
    public TableColumn colSalaryId;
    public TableColumn colEmployeeId;
    public TableColumn colEmployeeSalary;
    public TableColumn colIncentive;
    public TableColumn colOt;
    public TableColumn colTotal;
    public JFXButton btnUpdateId;

    LinkedHashMap<TextField, Pattern> map = new LinkedHashMap<>();
    Pattern codePattern = Pattern.compile("^(S0)[0-9]{1,}$");
    Pattern incentivePattern = Pattern.compile("^[0-9.]{1,}$");
    Pattern otPattern = Pattern.compile("^[0-9.]{1,}$");
    Pattern totalPattern = Pattern.compile("^[0-9.]{1,}$");

    public void initialize() throws SQLException, ClassNotFoundException {

        btnUpdateId.isDisable();
        salaryValidate();

        loadEmployeeId();

        cmbEmployeeId.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            try {
                txtEmployeeSalary.setText(new SalaryController().getEmployeeSalary((String) newValue));
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }

        });

        txtOt.textProperty().addListener((observable, oldValue, newValue) -> {
            if (txtOt.getText().isEmpty()){
                txtTotal.setText(null);

            }else {
                txtTotal.setText(total());
            }

        });


        ObservableList<SalaryTm> list= FXCollections.observableArrayList(new SalaryController().getSalary());
        colSalaryId.setCellValueFactory(new PropertyValueFactory<>("salaryId"));
        colEmployeeId.setCellValueFactory(new PropertyValueFactory<>("employeeId"));
        colEmployeeSalary.setCellValueFactory(new PropertyValueFactory<>("employeeSalary"));
        colIncentive.setCellValueFactory(new PropertyValueFactory<>("incentive"));
        colOt.setCellValueFactory(new PropertyValueFactory<>("ot"));
        colTotal.setCellValueFactory(new PropertyValueFactory<>("total"));

        tblSalaryAdd.setItems(list);
    }

    private void salaryValidate() {
        map.put(txtSalaryId, codePattern);
        map.put(txtIncentive, incentivePattern);
        map.put(txtOt, otPattern);
        map.put(txtTotal, totalPattern);
    }

    public void loadEmployeeId(){
        List<String> employeeId= null;
        try {
            employeeId = SalaryController.getAllEmployeeId();
            cmbEmployeeId.getItems().setAll(FXCollections.observableArrayList(employeeId));
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void txtSearchOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        String salaryId=txtSalaryId.getText();

        Salary salary=new SalaryController().searchSalary(salaryId);
        if (salary==null){
            new Alert(Alert.AlertType.WARNING,"Empty Result set").show();
            clearTextBox();

        }else {
            setData(salary);
        }
    }

    private void setData(Salary salary) {
        txtSalaryId.setText(salary.getSalaryId());
        cmbEmployeeId.setValue(salary.getEmployeeId());
        txtEmployeeSalary.setText(salary.getEmployeeSalary());
        txtIncentive.setText(salary.getIncentive());
        txtOt.setText(salary.getOt());
        txtTotal.setText(salary.getTotal());
    }

    private void clearTextBox() {
        txtSalaryId.clear();
        cmbEmployeeId.getItems().clear();
        txtEmployeeSalary.clear();
        txtIncentive.clear();
        txtOt.clear();
        txtTotal.clear();

    }

    public void cancelOnAction(ActionEvent actionEvent) {
        clearTextBox();
    }

    public void btnUpdateTable(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        if(setData()){
            new Alert(Alert.AlertType.CONFIRMATION,"Updated..").show();
            clearTextBox();
        }else {
            new Alert(Alert.AlertType.WARNING, "Try Again").show();
        }
    }

    public String total(){
        double total=Double.parseDouble(txtEmployeeSalary.getText())+
                Double.parseDouble(txtIncentive.getText())+Double.parseDouble(txtOt.getText());
        return String.valueOf(total);
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
        Salary s=new Salary(
                txtSalaryId.getText(),
                String.valueOf(cmbEmployeeId.getValue()),
                txtEmployeeSalary.getText(),
                txtIncentive.getText(),
                txtOt.getText(),
                txtTotal.getText()
        );

        if (new SalaryController().salaryUpdate(s)){

            tblSalaryAdd.setItems(null);
            tblSalaryAdd.setItems(FXCollections.observableArrayList(new SalaryController().getSalary()));
            return true;
        }else {
            return false;
        }
    }
}
