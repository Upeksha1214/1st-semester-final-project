package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import model.Order;
import model.Shop;
import view.TM.OrderTm;

import java.sql.SQLException;

public class OderSearchController {
    public TextField txtUnitPrice;
    public TextField txtQTY;
    public TableView tblOrderAdd;
    public TableColumn colOderId;
    public TableColumn colRefId;
    public TableColumn colShopId;
    public TableColumn colItemCode;
    public TableColumn colUnitPrice;
    public TableColumn colQTY;
    public TableColumn colOderQTY;
    public TableColumn colTotal;
    public TextField txtOderId;
    public TextField txtTotal;
    public TextField txtOderQTY;
    public TextField txtRefId;
    public TextField txtShopId;
    public TextField txtItemCode;

    public void initialize() throws SQLException, ClassNotFoundException {
        ObservableList<OrderTm> list= FXCollections.observableArrayList(new OderController().getOrder());
        colOderId.setCellValueFactory(new PropertyValueFactory<>("orderId"));
        colRefId.setCellValueFactory(new PropertyValueFactory<>("refId"));
        colShopId.setCellValueFactory(new PropertyValueFactory<>("shopId"));
        colItemCode.setCellValueFactory(new PropertyValueFactory<>("itemCode"));
        colUnitPrice.setCellValueFactory(new PropertyValueFactory<>("unitPrice"));
        colQTY.setCellValueFactory(new PropertyValueFactory<>("qty"));
        colOderQTY.setCellValueFactory(new PropertyValueFactory<>("orderQTY"));
        colTotal.setCellValueFactory(new PropertyValueFactory<>("total"));

        tblOrderAdd.setItems(list);
    }

    public void btnSearchOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        String orderId=txtOderId.getText();

        Order order =new OderController().searchOrder(orderId);
        if (order==null){
            new Alert(Alert.AlertType.WARNING,"Empty Result set").show();
            clearTextBox();

        }else {
            setData(order);
        }
    }

    private void setData(Order order) {
        txtOderId.setText(order.getOrderId());
        txtRefId.setText(order.getRefId());
        txtShopId.setText(order.getShopId());
        txtItemCode.setText(order.getItemCode());
        txtUnitPrice.setText(order.getUnitPrice());
        txtQTY.setText(order.getQty());
        txtOderQTY.setText(order.getOrderQTY());
        txtTotal.setText(order.getTotal());

    }

    private void clearTextBox() {
        txtOderId.setText(null);
        txtRefId.setText(null);
        txtShopId.setText(null);
        txtItemCode.setText(null);
        txtUnitPrice.setText(null);
        txtQTY.setText(null);
        txtOderQTY.setText(null);
        txtTotal.setText(null);
    }

    public void btnCancelOnAction(ActionEvent actionEvent) {
        clearTextBox();
    }
}
