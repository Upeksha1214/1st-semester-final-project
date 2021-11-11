package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import model.Shop;
import view.TM.ShopTm;

import java.sql.SQLException;

public class ShopDeleteController {
    public TableView tblShopDelete;
    public TableColumn colShopId;
    public TableColumn colShopName;
    public TableColumn colPhoneNumber;
    public TableColumn colAddress;
    public TextField txtShopId;
    public TextField txtShopName;
    public TextField txtPhoneNumber;
    public TextField txtShopAddress;


    public void initialize() throws SQLException, ClassNotFoundException {
        ObservableList<ShopTm> list= FXCollections.observableArrayList(new ShopController().getShop());
        colShopId.setCellValueFactory(new PropertyValueFactory<>("shopId"));
        colShopName.setCellValueFactory(new PropertyValueFactory<>("shopName"));
        colPhoneNumber.setCellValueFactory(new PropertyValueFactory<>("shopPhoneNumber"));
        colAddress.setCellValueFactory(new PropertyValueFactory<>("shopAddress"));

        tblShopDelete.setItems(list);
    }
    public void btnCancelOnAction(ActionEvent actionEvent) {
        clearTextBox();
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
        txtShopId.setText(null);
        txtShopName.setText(null);
        txtPhoneNumber.setText(null);
        txtShopAddress.setText(null);
    }

    public void btnDeleteOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        if (new ShopController().shopDelete(txtShopId.getText())){
            new Alert(Alert.AlertType.CONFIRMATION,"Deleted").show();
            clearTextBox();
            tblShopDelete.setItems(null);
            tblShopDelete.setItems(FXCollections.observableArrayList(new ShopController().getShop()));

        }else{
            new Alert(Alert.AlertType.WARNING,"Try Again");
        }
    }
}
