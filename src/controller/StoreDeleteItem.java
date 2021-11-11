package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import model.Store;
import view.TM.StoreTm;

import java.sql.SQLException;

public class StoreDeleteItem {

    public TextField txtQtyOnHand;
    public TextField txtUnitPrice;
    public TextField txtDescription;
    public TextField txtItemCode;
    public TextField txtDiscount;
    public TableView tblItemDelete;
    public TableColumn colItemCode;
    public TableColumn colDescription;
    public TableColumn colUnitPrice;
    public TableColumn colQtyOnHand;
    public TableColumn colDiscount;

    public void initialize(){


        try {
            ObservableList<StoreTm> list= FXCollections.observableArrayList(new StoreController().getItem());
            colItemCode.setCellValueFactory(new PropertyValueFactory<>("itemCode"));
            colDescription.setCellValueFactory(new PropertyValueFactory<>("description"));
            colUnitPrice.setCellValueFactory(new PropertyValueFactory<>("unitPrice"));
            colQtyOnHand.setCellValueFactory(new PropertyValueFactory<>("qtyOnHand"));
            colDiscount.setCellValueFactory(new PropertyValueFactory<>("disconnect"));
            tblItemDelete.setItems(list);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void deleteOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        if (new StoreController().itemDelete(txtItemCode.getText())){
            new Alert(Alert.AlertType.CONFIRMATION, "Deleted").show();
            tblItemDelete.setItems(null);
            tblItemDelete.setItems(FXCollections.observableArrayList(new StoreController().getItem()));
            clearTextBox();
        }else{
            new Alert(Alert.AlertType.WARNING, "Try Again").show();
            clearTextBox();
        }
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
}
