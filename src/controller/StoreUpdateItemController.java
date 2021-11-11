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
import view.TM.StoreTm;

import java.sql.SQLException;
import java.util.LinkedHashMap;
import java.util.regex.Pattern;

public class StoreUpdateItemController {
    public TextField txtQtyOnHand;
    public TextField txtUnitPrice;
    public TextField txtDescription;
    public TextField txtItemCode;
    public TextField txtDiscount;
    public TableView tblItemUpdate;
    public TableColumn colItemCode;
    public TableColumn colDescription;
    public TableColumn colUnitPrice;
    public TableColumn colQtyOnHand;
    public TableColumn colDiscount;
    public JFXButton btnUpdateId;

    LinkedHashMap<TextField, Pattern> map = new LinkedHashMap<>();
    Pattern codePattern = Pattern.compile("^(I0)[0-9]{1,}$");
    Pattern descriptionPattern = Pattern.compile("^[a -z0-9 ]{2,}$");
    Pattern unitPricePattern = Pattern.compile("^[0-9.]{1,}$");
    Pattern qtyOnHandPattern = Pattern.compile("^[0-9.]{1,}$");
    Pattern discountPattern = Pattern.compile("^[0-9.]{1,}$");

    public void initialize(){
        btnUpdateId.isDisable();
        updateItemValidation();
        try {
            ObservableList<StoreTm> list= FXCollections.observableArrayList(new StoreController().getItem());
            colItemCode.setCellValueFactory(new PropertyValueFactory<>("itemCode"));
            colDescription.setCellValueFactory(new PropertyValueFactory<>("description"));
            colUnitPrice.setCellValueFactory(new PropertyValueFactory<>("unitPrice"));
            colQtyOnHand.setCellValueFactory(new PropertyValueFactory<>("qtyOnHand"));
            colDiscount.setCellValueFactory(new PropertyValueFactory<>("disconnect"));
            tblItemUpdate.setItems(list);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void updateItemValidation() {
        map.put(txtItemCode, codePattern);
        map.put(txtDescription, descriptionPattern);
        map.put(txtUnitPrice, unitPricePattern);
        map.put(txtQtyOnHand, qtyOnHandPattern);
        map.put(txtDiscount, discountPattern);
    }

    public void txtItemCodeOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        String itemCode=txtItemCode.getText();

        Store store=new StoreController().searchItem(itemCode);
        if (store==null) {
            new Alert(Alert.AlertType.WARNING, "Empty Result set").show();
            clearTextBox();
        }else {
            setData(store);
        }
    }

    private void setData(Store store) {
        txtItemCode.setText(store.getItemCode());
        txtDescription.setText(store.getDescription());
        txtUnitPrice.setText(store.getUnitPrice());
        txtQtyOnHand.setText(store.getUnitPrice());
        txtDiscount.setText(store.getDisconnect());
    }

    private void clearTextBox() {
        txtItemCode.setText(null);
        txtDescription.setText(null);
        txtUnitPrice.setText(null);
        txtQtyOnHand.setText(null);
        txtDiscount.setText(null);
    }

    public void UpdateOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        if(setData()){
            new Alert(Alert.AlertType.CONFIRMATION, "Updated..").show();
            clearTextBox();
        }else {
            new Alert(Alert.AlertType.WARNING,"Try Again").show();
        }
    }

    public void cancelOnAction(ActionEvent actionEvent) {
        clearTextBox();
    }

    public void item_keyRelease(KeyEvent keyEvent) {
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
        Store s=new Store(
                txtItemCode.getText(),txtDescription.getText(),
                txtUnitPrice.getText(),txtQtyOnHand.getText(),
                txtDiscount.getText()
        );


        if (new StoreController().itemUpdate(s)) {

            tblItemUpdate.setItems(null);
            tblItemUpdate.setItems(FXCollections.observableArrayList(new StoreController().getItem()));

            return true;
        }else
            return false;
    }
}
