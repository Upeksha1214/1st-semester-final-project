package controller;

import com.jfoenix.controls.JFXButton;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import model.Salary;
import model.Shop;
import utill.Validation;
import view.TM.SalaryTm;
import view.TM.ShopTm;

import java.sql.SQLException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.regex.Pattern;

public class SalaryAddController {

    public TableColumn colEmployeeId;
    public TableColumn colEmployeeSalary;
    public TextField txtEmployeeSalary;
    public TableColumn colIncentive;
    public TableColumn colOt;
    public TableColumn colTotal;
    public TextField txtTotal;
    public TextField txtSalaryId;
    public TextField txtOt;
    public ComboBox cmbEmployeeId;
    public TextField txtIncentive;
    public TableColumn colSalaryId;
    public TableView tblSalaryAdd;
    public JFXButton btnAddTableId;


    LinkedHashMap<TextField, Pattern> map = new LinkedHashMap<>();
    Pattern codePattern = Pattern.compile("^(S0)[0-9]{1,}$");
    Pattern incentivePattern = Pattern.compile("^[0-9.]{1,}$");
    Pattern otPattern = Pattern.compile("^[0-9.]{1,}$");
    Pattern totalPattern = Pattern.compile("^[0-9.]{1,}$");


    public void initialize() throws SQLException, ClassNotFoundException {
        btnAddTableId.isDisable();
        salaryValidate();

        txtSalaryId.setText(new SalaryController().getNewSalaryId());
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

        ObservableList<SalaryTm> list=FXCollections.observableArrayList(new SalaryController().getSalary());
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

    public String total(){
        double total=Double.parseDouble(txtEmployeeSalary.getText())+
                Double.parseDouble(txtIncentive.getText())+Double.parseDouble(txtOt.getText());
        return String.valueOf(total);
    }

    public void btnAddTable(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        setData();
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

    public void employ_keyRelease(KeyEvent keyEvent) {
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
        Salary s= new Salary(
                txtSalaryId.getText(),String.valueOf(cmbEmployeeId.getValue()),txtEmployeeSalary.getText(),
                txtIncentive.getText(),txtOt.getText(),txtTotal.getText()
        );
        new SalaryController().salaryAdd(s);
        new Alert(Alert.AlertType.CONFIRMATION,"Complete").show();
        tblSalaryAdd.setItems(null);
        tblSalaryAdd.setItems(FXCollections.observableArrayList(new SalaryController().getSalary()));

        clearTextBox();
        txtSalaryId.setText(new SalaryController().getNewSalaryId());
    }
}
