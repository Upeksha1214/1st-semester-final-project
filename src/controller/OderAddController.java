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
import view.TM.ShopTm;

import java.sql.SQLException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.regex.Pattern;

public class OderAddController {

    public TextField txtQTY;
    public TableColumn colOderId;
    public TableColumn colRefId;
    public TableColumn colShopId;
    public TableColumn colItemCode;
    public TableColumn colQTY;
    public TableColumn colTotal;
    public TextField txtOderId;
    public ComboBox<String> cmbRefId;
    public ComboBox cmbShopId;
    public TextField txtTotal;
    public ComboBox cmbItemCode;
    public TableColumn colUnitPrice;
    public TextField txtUnitPrice;
    public TableColumn colOderQTY;
    public TextField txtOderQTY;
    public TableView tblOrderAdd;
    public JFXButton btnAddId;


    LinkedHashMap<TextField, Pattern> map = new LinkedHashMap<>();
    Pattern codePattern = Pattern.compile("^(O0)[0-9]{1,}$");
    Pattern unitPricePattern = Pattern.compile("^[0-9.]{1,}$");
    Pattern qtyOnHandPattern = Pattern.compile("^[0-9]{1,}$");
    Pattern qtyPattern = Pattern.compile("^[0-9]{1,}$");
/*
    Pattern totalPattern = Pattern.compile("[0-9.]");
*/
    public void initialize() throws SQLException, ClassNotFoundException {
        btnAddId.setDisable(true);
        orderValidate();

        txtOderId.setText(new OderController().getNewOrderId());
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


        List<OrderTm> order=new OderController().getOrder();
        if (order.isEmpty()){
            new Alert(Alert.AlertType.WARNING,"empty set").show();
        }

        ObservableList<OrderTm> list=FXCollections.observableArrayList(new OderController().getOrder());
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

    private void orderValidate() {
        map.put(txtOderId, codePattern);
        map.put(txtUnitPrice, unitPricePattern);
        map.put(txtQTY, qtyOnHandPattern);
        map.put(txtOderQTY, qtyPattern);
/*
        map.put(txtTotal, totalPattern);
*/
    }

    public void loadRefId(){
        try {
            List<String>refId=OderController.getAllRefId();
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


    public void btnAddOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        setData();
    }

    private void clearTextBox() {
        cmbShopId.setValue("");
        cmbRefId.setValue("");
        cmbItemCode.setValue("");
        txtUnitPrice.clear();
        txtQTY.clear();
        txtOderQTY.clear();
        txtTotal.clear();
    }

    public void btnCancelOnAction(ActionEvent actionEvent) {
        clearTextBox();
    }

    public void order_keyRelease(KeyEvent keyEvent) {
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

        int qty=Integer.parseInt(txtQTY.getText());
        int orderQty=Integer.parseInt(txtOderQTY.getText());

        Order o= new Order(
                txtOderId.getText(),
                cmbRefId.getSelectionModel().getSelectedItem(),
                String.valueOf(cmbShopId.getValue()),
                String.valueOf(cmbItemCode.getValue()),
                txtUnitPrice.getText(),
                txtQTY.getText(),
                txtOderQTY.getText(),
                txtTotal.getText()
        );
        new OderController().order(o);
        if(qty<orderQty){
            new Alert(Alert.AlertType.WARNING,"Invalid Qty").show();
        }else
        new Alert(Alert.AlertType.CONFIRMATION,"Complete").show();

        tblOrderAdd.setItems(null);
        tblOrderAdd.setItems(FXCollections.observableArrayList(new OderController().getOrder()));
        clearTextBox();

        txtOderId.setText(new OderController().getNewOrderId());
    }

    public void txtOrderQtyOnAction(KeyEvent keyEvent) {
        //ModifyQty();
    }
}
