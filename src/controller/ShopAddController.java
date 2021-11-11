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
import model.Shop;
import model.Vehicle;
import utill.Validation;
import view.TM.ShopTm;
import view.TM.VehicleTm;

import java.sql.SQLException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.regex.Pattern;

public class ShopAddController {
    public TableView tblShopAdd;
    public TableColumn colShopId;
    public TableColumn colShopName;
    public TableColumn colPhoneNumber;
    public TableColumn colShopAddress;
    public TextField txtShopId;
    public TextField txtShopName;
    public TextField txtPhoneNumber;
    public TextField txtShopAddress;
    public JFXButton btnAddId;

    LinkedHashMap<TextField, Pattern> map = new LinkedHashMap<>();
    Pattern codePattern = Pattern.compile("^(S0)[0-9]{1,}$");
    Pattern namePattern = Pattern.compile("[A-z .]{1,}");
    Pattern numberPattern = Pattern.compile("^(078|070|071|075|077|091|011|074|072|076)?[0-9]{7}$");
    Pattern AddressPattern = Pattern.compile("[A-z, .[0-9]]{3,}");


    public void initialize() throws SQLException, ClassNotFoundException {
        btnAddId.setDisable(true);
        shopValidate();

        txtShopId.setText(new ShopController().getNewShopId());

        List<ShopTm> shop=new ShopController().getShop();
        if (shop.isEmpty()){
            new Alert(Alert.AlertType.WARNING,"empty set").show();
        }

        ObservableList<ShopTm> list=FXCollections.observableArrayList(new ShopController().getShop());
        colShopId.setCellValueFactory(new PropertyValueFactory<>("shopId"));
        colShopName.setCellValueFactory(new PropertyValueFactory<>("shopName"));
        colPhoneNumber.setCellValueFactory(new PropertyValueFactory<>("shopPhoneNumber"));
        colShopAddress.setCellValueFactory(new PropertyValueFactory<>("shopAddress"));

        tblShopAdd.setItems(list);
    }

    private void shopValidate() {
        map.put(txtShopId, codePattern);
        map.put(txtShopName, namePattern);
        map.put(txtPhoneNumber, numberPattern);
        map.put(txtShopAddress, AddressPattern);
    }

    public void btnCancelOnAction(ActionEvent actionEvent) {
        clearTextBox();
    }

    public void btnAddOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        setData();
    }

    private void clearTextBox() {
        txtShopName.clear();
        txtPhoneNumber.clear();
        txtShopAddress.clear();
    }

    public void shop_keyRelese(KeyEvent keyEvent) {
        Object response = Validation.validate(map, btnAddId);
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
        Shop s= new Shop(
                txtShopId.getText(),txtShopName.getText(),txtPhoneNumber.getText(),
                txtShopAddress.getText()
        );
        new ShopController().shopAdd(s);
        new Alert(Alert.AlertType.CONFIRMATION,"Complete").show();
        tblShopAdd.setItems(null);
        tblShopAdd.setItems(FXCollections.observableArrayList(new ShopController().getShop()));

        clearTextBox();
        txtShopId.setText(new ShopController().getNewShopId());
    }
}
