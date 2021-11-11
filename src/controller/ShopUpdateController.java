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

import java.sql.SQLException;
import java.util.LinkedHashMap;
import java.util.regex.Pattern;

public class ShopUpdateController {
    public TableView tblShopUpdate;
    public TableColumn colShopId;
    public TableColumn colShopName;
    public TableColumn colPhoneNumber;
    public TableColumn colShopAddress;
    public TextField txtShopId;
    public TextField txtShopName;
    public TextField txtPhoneNumber;
    public TextField txtShopAddress;
    public JFXButton btnUpdateId;


    LinkedHashMap<TextField, Pattern> map = new LinkedHashMap<>();
    Pattern codePattern = Pattern.compile("^(S0)[0-9]{1,}$");
    Pattern namePattern = Pattern.compile("[A-z .]{1,}");
    Pattern numberPattern = Pattern.compile("^(078|070|071|075|077|091|011|074|072|076)?[0-9]{7}$");
    Pattern AddressPattern = Pattern.compile("[A-z, .[0-9]]{3,}");

    public void initialize() throws SQLException, ClassNotFoundException {
        btnUpdateId.setDisable(true);
        shopValidate();

        ObservableList<ShopTm> list= FXCollections.observableArrayList(new ShopController().getShop());
        colShopId.setCellValueFactory(new PropertyValueFactory<>("shopId"));
        colShopName.setCellValueFactory(new PropertyValueFactory<>("shopName"));
        colPhoneNumber.setCellValueFactory(new PropertyValueFactory<>("shopPhoneNumber"));
        colShopAddress.setCellValueFactory(new PropertyValueFactory<>("shopAddress"));

        tblShopUpdate.setItems(list);
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

    public void btnUpdateOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        if(setData()){
            new Alert(Alert.AlertType.CONFIRMATION,"Updated..").show();
            clearTextBox();
        }else {
            new Alert(Alert.AlertType.WARNING, "Try Again").show();
        }
    }

    public void txtSearchShopOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        String shopId=txtShopId.getText();

        Shop shop=new ShopController().searchShop(shopId);
        if (shop==null){
            new Alert(Alert.AlertType.WARNING,"Empty Result set").show();
            clearTextBox();

        }else {
            setData(shop);
        }
    }

    private void setData(Shop shop) {
        txtShopId.setText(shop.getShopId());
        txtShopName.setText(shop.getShopName());
        txtPhoneNumber.setText(shop.getShopPhoneNumber());
        txtShopAddress.setText(shop.getShopAddress());
    }

    private void clearTextBox() {
        txtShopId.clear();
        txtShopName.clear();
        txtPhoneNumber.clear();
        txtShopAddress.clear();
    }

    public void shop_keyRelease(KeyEvent keyEvent) {
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

    private boolean setData() throws SQLException, ClassNotFoundException {Shop s=new Shop(
            txtShopId.getText(),txtShopName.getText(),txtPhoneNumber.getText(),
            txtShopAddress.getText()
    );

        if (new ShopController().shopUpdate(s)){

            tblShopUpdate.setItems(null);
            tblShopUpdate.setItems(FXCollections.observableArrayList(new ShopController().getShop()));
            return true;
        }else {
            return false;
        }

    }
}
