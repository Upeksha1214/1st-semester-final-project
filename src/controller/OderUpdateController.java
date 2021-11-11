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
import model.Shop;
import utill.Validation;
import view.TM.OrderTm;

import java.sql.SQLException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.regex.Pattern;

public class OderUpdateController {
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
    public ComboBox cmbRefId;
    public ComboBox cmbShopId;
    public ComboBox cmbItemCode;
    public JFXButton btnUpdateId;

    LinkedHashMap<TextField, Pattern> map = new LinkedHashMap<>();
    Pattern codePattern = Pattern.compile("^(O0)[0-9]{1,}$");
    Pattern unitPricePattern = Pattern.compile("^[0-9.]{1,}$");
    Pattern qtyOnHandPattern = Pattern.compile("^[0-9.]{1,}$");
    Pattern qtyPattern = Pattern.compile("^[0-9.]{1,}$");

    public void initialize() throws SQLException, ClassNotFoundException {

        btnUpdateId.setDisable(true);
        orderValidate();

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

        loadRefId();
        loadShopId();
        loadCustID();

        cmbItemCode.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            try {
                txtUnitPrice.setText(new OderController().getUnitPrice((String) newValue));
                txtQTY.setText(new OderController().getCustomerQty((String) newValue));
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }

        });

        txtOderQTY.textProperty().addListener((observable, oldValue, newValue) -> {
            if (txtOderQTY.getText().isEmpty()){
                txtTotal.setText(null);
            }else if(Integer.parseInt(txtQTY.getText())<Integer.parseInt(txtOderQTY.getText())){
                txtTotal.setText(null);

            }else {
                txtTotal.setText(total(Integer.parseInt(newValue)));
            }

        });
    }

    private void orderValidate() {
        map.put(txtOderId, codePattern);
        map.put(txtUnitPrice, unitPricePattern);
        map.put(txtQTY, qtyOnHandPattern);
        map.put(txtOderQTY, qtyPattern);
    }

    public void loadRefId(){
        try {
            List<String> refId=OderController.getAllRefId();
            cmbRefId.getItems().setAll(FXCollections.observableArrayList(refId));
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void loadShopId() {
        List<String>shopId= null;
        try {
            shopId = OderController.getAllShopId();
            cmbShopId.getItems().setAll(FXCollections.observableArrayList(shopId));
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

    }
    public void loadCustID()  {
        List<String>itemCode= null;
        try {
            itemCode = OderController.getAllItemCode();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        cmbItemCode.getItems().setAll(FXCollections.observableArrayList(itemCode));
    }

    public String total(int qty){
        double total=Double.parseDouble(txtUnitPrice.getText()) * qty;
        return String.valueOf(total);
    }

    public void btnCancelOnAction(ActionEvent actionEvent) {
        clearTextBox();
    }

    public void btnUpdateOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        if (setData()){
            new Alert(Alert.AlertType.CONFIRMATION,"Updated..").show();
        }else {
            new Alert(Alert.AlertType.WARNING, "Try Again").show();
        }
    }

    public void txtOrderSearchOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
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
        cmbRefId.setValue(order.getRefId());
        cmbShopId.setValue(order.getShopId());
        cmbItemCode.setValue(order.getItemCode());
        txtUnitPrice.setText(order.getUnitPrice());
        txtQTY.setText(order.getQty());
        txtOderQTY.setText(order.getOrderQTY());
        txtTotal.setText(order.getTotal());
    }

    private void clearTextBox() {
        txtOderId.clear();
        cmbRefId.setValue("");
        cmbShopId.setValue("");
        cmbItemCode.setValue("");
        txtUnitPrice.clear();
        txtQTY.clear();
        txtOderQTY.clear();
        txtTotal.clear();
    }

    public void order_keyRelease(KeyEvent keyEvent) {
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
        Order o=new Order(
                txtOderId.getText(),
                String.valueOf(cmbRefId.getValue()),
                String.valueOf(cmbShopId.getValue()),
                String.valueOf(cmbItemCode.getValue()),
                txtUnitPrice.getText(),
                txtQTY.getText(),
                txtOderQTY.getText(),
                txtTotal.getText()
        );

        if (new OderController().orderUpdate(o)){
            tblOrderAdd.setItems(null);
            tblOrderAdd.setItems(FXCollections.observableArrayList(new OderController().getOrder()));
            return true;
        }else {
            return false;
        }
    }
}
