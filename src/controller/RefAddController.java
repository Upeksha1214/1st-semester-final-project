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
import model.Ref;
import model.Vehicle;
import utill.Validation;
import view.TM.RefTm;
import view.TM.VehicleTm;

import java.sql.SQLException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.regex.Pattern;

public class RefAddController {
    public TextField txtPhoneNumber;
    public TextField txtRefAddress;
    public TextField txtRefName;
    public TextField txtRefNic;
    public TextField txtRefId;
    public TableView tblAddRef;
    public TableColumn colRefId;
    public TableColumn colRefNic;
    public TableColumn colRefName;
    public TableColumn colRefAddress;
    public TableColumn colPhoneNumber;
    public JFXButton btnAddId;

    LinkedHashMap<TextField, Pattern> map = new LinkedHashMap<>();
    Pattern codePattern = Pattern.compile("^(R0)[0-9]{1,}$");
    Pattern nicPattern = Pattern.compile("^[0-9]{12}|[0-9]{9}V$");
    Pattern namePattern = Pattern.compile("[A-z .]{1,}");
    Pattern AddressPattern = Pattern.compile("[A-z, [0-9]]{3,}");
    Pattern numberPattern = Pattern.compile("^(078|070|071|075|077|091|011|074|072|076)?[0-9]{7}$");
    

    public void initialize(){

        btnAddId.setDisable(true);
        refValidate();

        try {
            txtRefId.setText(new RefController().getNewRefId());
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        List<RefTm> ref= null;
        try {
            ref = new RefController().getRef();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        if (ref.isEmpty()){
            new Alert(Alert.AlertType.WARNING,"empty set").show();
        }



        ObservableList<RefTm> list= null;
        try {
            list = FXCollections.observableArrayList(new RefController().getRef());
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        colRefId.setCellValueFactory(new PropertyValueFactory<>("refID"));
        colRefNic.setCellValueFactory(new PropertyValueFactory<>("refNIC"));
        colRefName.setCellValueFactory(new PropertyValueFactory<>("refName"));
        colRefAddress.setCellValueFactory(new PropertyValueFactory<>("refAddress"));
        colPhoneNumber.setCellValueFactory(new PropertyValueFactory<>("refPhoneNumber"));

        tblAddRef.setItems(list);
    }

    private void refValidate() {
        map.put(txtRefId, codePattern);
        map.put(txtRefNic, nicPattern);
        map.put(txtRefName, namePattern);
        map.put(txtRefAddress, AddressPattern);
        map.put(txtPhoneNumber, numberPattern);

    }

    public void btnCancelOnAction(ActionEvent actionEvent) {
        clearTextBox();
    }

    public void btnAddOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
       setData();
    }

    private void clearTextBox() {
        txtRefNic.clear();
        txtRefName.clear();
        txtRefAddress.clear();
        txtPhoneNumber.clear();
    }

    private void setData() throws SQLException, ClassNotFoundException {
        Ref r= new Ref(
                txtRefId.getText(),txtRefNic.getText(),txtRefName.getText(),
                txtRefAddress.getText(),txtPhoneNumber.getText()
        );
        new RefController().refAdd(r);
        new Alert(Alert.AlertType.CONFIRMATION,"Complete").show();
        tblAddRef.setItems(null);
        tblAddRef.setItems(FXCollections.observableArrayList(new RefController().getRef()));

        clearTextBox();
        txtRefId.setText(new RefController().getNewRefId());
    }

    public void ref_keyRelease(KeyEvent keyEvent) {
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
}
