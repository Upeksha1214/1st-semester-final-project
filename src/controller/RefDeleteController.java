package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import model.Ref;
import view.TM.RefTm;

import java.sql.SQLException;

public class RefDeleteController {

    public TextField txtPhoneNumber;
    public TextField txtRefAddress;
    public TextField txtRefName;
    public TextField txtRefNic;
    public TextField txtRefId;
    public TableView tblRefDelete;
    public TableColumn colRefId;
    public TableColumn colRefNic;
    public TableColumn colName;
    public TableColumn colRefAddress;
    public TableColumn colPhoneNumber;

    public void initialize(){
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
        colName.setCellValueFactory(new PropertyValueFactory<>("refName"));
        colRefAddress.setCellValueFactory(new PropertyValueFactory<>("refAddress"));
        colPhoneNumber.setCellValueFactory(new PropertyValueFactory<>("refPhoneNumber"));

        tblRefDelete.setItems(list);
    }

    public void txtSearchOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
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
        txtRefId.setText(null);
        txtRefNic.setText(null);
        txtRefName.setText(null);
        txtRefAddress.setText(null);
        txtPhoneNumber.setText(null);
    }

    public void btnCancelOnAction(ActionEvent actionEvent) {
        clearTextBox();
    }

    public void brnDeleteOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        if (new RefController().refDelete(txtRefId.getText())){
            new Alert(Alert.AlertType.CONFIRMATION,"Deleted").show();
            clearTextBox();
            tblRefDelete.setItems(null);
            tblRefDelete.setItems(FXCollections.observableArrayList(new RefController().getRef()));

        }else{
            new Alert(Alert.AlertType.WARNING,"Try Again");
        }
    }
}
