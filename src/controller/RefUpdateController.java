package controller;

import com.jfoenix.controls.JFXButton;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import model.Ref;
import model.Vehicle;
import utill.Validation;
import view.TM.RefTm;

import java.sql.SQLException;
import java.util.LinkedHashMap;
import java.util.regex.Pattern;

public class RefUpdateController {
    public TextField txtPhoneNumber;
    public TextField txtRefAddress;
    public TextField txtRefName;
    public TextField txtRefNic;
    public TextField txtRefId;
    public TableView tblRefUpdate;
    public TableColumn colRefId;
    public TableColumn colRefNic;
    public TableColumn colRefName;
    public TableColumn colRefAddress;
    public TableColumn colPhoneNumber;
    public JFXButton btnUpdateId;

    LinkedHashMap<TextField, Pattern> map = new LinkedHashMap<>();
    Pattern codePattern = Pattern.compile("^(R0)[0-9]{1,}$");
    Pattern nicPattern = Pattern.compile("^[0-9]{12}|[0-9]{9}V$");
    Pattern namePattern = Pattern.compile("[A-z .]{1,}");
    Pattern AddressPattern = Pattern.compile("[A-z, [0-9]]{3,}");
    Pattern numberPattern = Pattern.compile("^(078|070|071|075|077|091|011|074|072|076)?[0-9]{7}$");

    public void initialize(){
        btnUpdateId.setDisable(true);
        refValidate();

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

        tblRefUpdate.setItems(list);
    }

    private void refValidate() {
        map.put(txtRefId, codePattern);
        map.put(txtRefNic, nicPattern);
        map.put(txtRefName, namePattern);
        map.put(txtRefAddress, AddressPattern);
        map.put(txtPhoneNumber, numberPattern);
    }


    public void txtSearchRefOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        String refId=txtRefId.getText();

        Ref ref=new RefController().searchRef(refId);
        if (ref==null){
            new Alert(Alert.AlertType.WARNING,"Empty Result set").show();
            clearTextBox();

        }else {
            setData(ref);
        }
    }

    private void setData(Ref ref) {
        txtRefId.setText(ref.getRefID());
        txtRefNic.setText(ref.getRefNIC());
        txtRefName.setText(ref.getRefName());
        txtRefAddress.setText(ref.getRefAddress());
        txtPhoneNumber.setText(ref.getRefPhoneNumber());
    }

    private void clearTextBox() {
        txtRefId.clear();
        txtRefNic.clear();
        txtRefName.clear();
        txtRefAddress.clear();
        txtPhoneNumber.clear();
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

    public void ref_keyRelease(KeyEvent keyEvent) {
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
        Ref r=new Ref(
                txtRefId.getText(),txtRefNic.getText(),txtRefName.getText(),
                txtRefAddress.getText(),txtPhoneNumber.getText()
        );

        if (new RefController().refUpdate(r)){

            tblRefUpdate.setItems(null);
            tblRefUpdate.setItems(FXCollections.observableArrayList(new RefController().getRef()));

            /*clearTextBox();*/
            return true;
        }else {
            return false;


        }
    }
}
