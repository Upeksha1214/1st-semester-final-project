package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import model.Salary;
import model.Shop;
import view.TM.SalaryTm;

import java.sql.SQLException;

public class SalarySearchController {
    public TableView tblSalaryAdd;
    public TableColumn colSalaryId;
    public TableColumn colEmployeeId;
    public TableColumn colEmployeeSalary;
    public TableColumn colIncentive;
    public TableColumn colOt;
    public TableColumn colTotal;
    public TextField txtEmployeeSalary;
    public TextField txtIncentive;
    public TextField txtOt;
    public TextField txtTotal;
    public TextField txtSalaryId;
    public TextField txtEmployeeId;

    public void initialize() throws SQLException, ClassNotFoundException {
        ObservableList<SalaryTm> list= FXCollections.observableArrayList(new SalaryController().getSalary());
        colSalaryId.setCellValueFactory(new PropertyValueFactory<>("salaryId"));
        colEmployeeId.setCellValueFactory(new PropertyValueFactory<>("employeeId"));
        colEmployeeSalary.setCellValueFactory(new PropertyValueFactory<>("employeeSalary"));
        colIncentive.setCellValueFactory(new PropertyValueFactory<>("incentive"));
        colOt.setCellValueFactory(new PropertyValueFactory<>("ot"));
        colTotal.setCellValueFactory(new PropertyValueFactory<>("total"));

        tblSalaryAdd.setItems(list);
    }

    public void btnSearchTable(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
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
        txtEmployeeId.setText(salary.getEmployeeId());
        txtEmployeeSalary.setText(salary.getEmployeeSalary());
        txtIncentive.setText(salary.getIncentive());
        txtOt.setText(salary.getOt());
        txtTotal.setText(salary.getTotal());
    }

    private void clearTextBox() {
        txtSalaryId.clear();
        txtEmployeeId.clear();
        txtEmployeeSalary.clear();
        txtIncentive.clear();
        txtOt.clear();
        txtTotal.clear();
    }

    public void cancelOnAction(ActionEvent actionEvent) {
        clearTextBox();
    }
}
