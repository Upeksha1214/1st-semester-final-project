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
import model.Store;
import utill.Validation;
import view.TM.DriverTm;
import view.TM.EmployeeTm;
import view.TM.StoreTm;

import java.sql.SQLException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.regex.Pattern;

public class StoreAddItemController {
    public TextField txtQtyOnHand;
    public TextField txtUnitPrice;
    public TextField txtDescription;
    public TextField txtItemCode;
    public TextField txtDiscount;
    public TableView tblItemAdd;
    public TableColumn colItemCode;
    public TableColumn colDescription;
    public TableColumn colUnitPrice;
    public TableColumn colQtyOnHand;
    public TableColumn colDiscount;
    public JFXButton btnAddItemId;


    LinkedHashMap<TextField, Pattern> map = new LinkedHashMap<>();
    Pattern codePattern = Pattern.compile("^(I0)[0-9]{1,}$");
    Pattern descriptionPattern = Pattern.compile("^[a -z0-9 ]{2,}$");
    Pattern unitPricePattern = Pattern.compile("^[0-9.]{1,}$");
    Pattern qtyOnHandPattern = Pattern.compile("^[0-9.]{1,}$");
    Pattern discountPattern = Pattern.compile("^[0-9.]{1,}$");


    public void initialize(){
        btnAddItemId.isDisable();
        addItemValidation();

        List<StoreTm> storeTms = null;
        try {
            storeTms = new StoreController().getItem();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        if (storeTms.isEmpty()){
            new Alert(Alert.AlertType.WARNING,"empty set").show();
        }

        try {
            txtItemCode.setText(new StoreController().getNewItemCode());
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        try {
            ObservableList<StoreTm> list=FXCollections.observableArrayList(new StoreController().getItem());
            colItemCode.setCellValueFactory(new PropertyValueFactory<>("itemCode"));
            colDescription.setCellValueFactory(new PropertyValueFactory<>("description"));
            colUnitPrice.setCellValueFactory(new PropertyValueFactory<>("unitPrice"));
            colQtyOnHand.setCellValueFactory(new PropertyValueFactory<>("qtyOnHand"));
            colDiscount.setCellValueFactory(new PropertyValueFactory<>("disconnect"));
            tblItemAdd.setItems(list);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void addItemValidation() {
        map.put(txtItemCode, codePattern);
        map.put(txtDescription, descriptionPattern);
        map.put(txtUnitPrice, unitPricePattern);
        map.put(txtQtyOnHand, qtyOnHandPattern);
        map.put(txtDiscount, discountPattern);
    }

    private void clearTextBox() {
        txtDescription.clear();
        txtUnitPrice.clear();
        txtQtyOnHand.clear();
        txtDiscount.clear();
    }

    public void btnAddItem(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        setData();
    }

    public void btnCancleOnAction(ActionEvent actionEvent) {
        clearTextBox();
    }

    public void item_keyRelease(KeyEvent keyEvent) {
        Object response = Validation.validate(map, btnAddItemId);
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
        Store s = new Store(
                txtItemCode.getText(), txtDescription.getText(), txtUnitPrice.getText(),
                txtQtyOnHand.getText(), txtDiscount.getText()
        );

        new StoreController().itemAdd(s);
        new Alert(Alert.AlertType.CONFIRMATION, "Complete").show();
        tblItemAdd.setItems(null);
        tblItemAdd.setItems(FXCollections.observableArrayList(new StoreController().getItem()));


        clearTextBox();

        txtItemCode.setText(new StoreController().getNewItemCode());



    }
}



