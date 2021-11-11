package controller;

import Db.DbConnection;
import animatefx.animation.Pulse;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.util.Duration;
import model.Store;
import view.TM.StoreTm;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class StoreController implements StoreService {
    public Pane homePane;
    public Pane addPane;
    public Pane searchPane;
    public Pane updatePane;
    public Pane deletePane;
    public AnchorPane storeContext;
    public AnchorPane itemContext;
    public Label lblDate;
    public Label lblTime;

    public void initialize(){
        loadDateAndTime();
    }

    private void loadDateAndTime() {
        //loadDate
        Date date= new Date();
        SimpleDateFormat f= new SimpleDateFormat("yyyy-MM-dd");
        lblDate.setText(f.format(date));

        //loadTime
        Timeline time =new Timeline(new KeyFrame(Duration.ZERO, e ->{
            LocalTime currentTime =LocalTime.now();
            lblTime.setText(
                    currentTime.getHour() + " : " + currentTime.getMinute() +
                            " : " + currentTime.getSecond()

            );
        } ),
                new KeyFrame(Duration.seconds(1))
        );
        time.setCycleCount(Animation.INDEFINITE);
        time.play();
    }

    private void clearPane(){
        homePane.setStyle("-fx-background-color: #357EC7");
        addPane.setStyle("-fx-background-color: #357EC7");
        searchPane.setStyle("-fx-background-color: #357EC7");
        updatePane.setStyle("-fx-background-color: #357EC7");
        deletePane.setStyle("-fx-background-color:  #357EC7");
    }

    public void homeOnAction(MouseEvent mouseEvent) throws IOException {
        clearPane();
        homePane.setStyle("-fx-background-color:#151B54");

        Stage window = (Stage) storeContext.getScene().getWindow();
        window.setScene(new Scene(FXMLLoader.load(getClass().getResource("../view/ManagerForm.fxml"))));

    }

    public void homeMouseEnteredOnAction(MouseEvent mouseEvent) {
        clearPane();
        new Pulse(homePane).play();
        homePane.setStyle("-fx-background-color: #6495ED");
    }

    public void addItemMoseOnAction(MouseEvent mouseEvent) throws IOException {
        clearPane();
        addPane.setStyle("-fx-background-color:#151B54");

        Parent fxml =FXMLLoader.load(getClass().getResource("../view/StoreAddItem.fxml"));
        itemContext.getChildren().removeAll();
        itemContext.getChildren().setAll(fxml);
    }

    public void addItemMouseEnteredOnAction(MouseEvent mouseEvent) {
        clearPane();
        new Pulse(addPane).play();
        addPane.setStyle("-fx-background-color: #6495ED");
    }

    public void searchItemMouseOnAction(MouseEvent mouseEvent) throws IOException {
        clearPane();
        searchPane.setStyle("-fx-background-color:#151B54");

        Parent fxml =FXMLLoader.load(getClass().getResource("../view/StoreSearchItem.fxml"));
        itemContext.getChildren().removeAll();
        itemContext.getChildren().setAll(fxml);
    }

    public void searchItemMouseEnteredOnAction(MouseEvent mouseEvent) {
        clearPane();
        new Pulse(searchPane).play();
        searchPane.setStyle("-fx-background-color: #6495ED");
    }

    public void updateItemMouseEnteredOnAction(MouseEvent mouseEvent)  {
        clearPane();
        new Pulse(updatePane).play();
        updatePane.setStyle("-fx-background-color: #6495ED");
    }

    public void updateItemMouseOnAction(MouseEvent mouseEvent) throws IOException {
        clearPane();
        updatePane.setStyle("-fx-background-color:#151B54");

        Parent fxml =FXMLLoader.load(getClass().getResource("../view/StoreUpdateItem.fxml"));
        itemContext.getChildren().removeAll();
        itemContext.getChildren().setAll(fxml);
    }

    public void deleteItemMouseOnAction(MouseEvent mouseEvent) throws IOException {
        clearPane();
        deletePane.setStyle("-fx-background-color:#151B54");

        Parent fxml =FXMLLoader.load(getClass().getResource("../view/StoreDeleteItem.fxml"));
        itemContext.getChildren().removeAll();
        itemContext.getChildren().setAll(fxml);
    }

    public void deleteItemMouseEnteredOnAction(MouseEvent mouseEvent) {
        clearPane();
        new Pulse(deletePane).play();
        deletePane.setStyle("-fx-background-color:  #6495ED");
    }

    @Override
    public boolean itemAdd(Store s) throws SQLException, ClassNotFoundException {


        Connection con = DbConnection.getInstance().getConnection();
        String query="INSERT INTO store Values(?,?,?,?,?)";
        PreparedStatement stm=con.prepareStatement(query);
        stm.setObject(1,s.getItemCode());
        stm.setObject(2,s.getDescription());
        stm.setObject(3,s.getUnitPrice());
        stm.setObject(4,s.getQtyOnHand());
        stm.setObject(5,s.getDisconnect());
        return stm.executeUpdate()>0;
    }

    @Override
    public String getNewItemCode() throws SQLException, ClassNotFoundException {
        PreparedStatement stm=DbConnection.getInstance().getConnection().prepareStatement("SELECT itemCode FROM store ORDER BY itemCode DESC LIMIT 1");
        ResultSet resultSet = stm.executeQuery();
        String id=null;
        while(resultSet.next()){
            String s=resultSet.getString(1);
            int tempId= Integer.parseInt(s.substring(1,4));
            tempId=++tempId;

            if (tempId<10){
                id="I00"+tempId;

            }else if(tempId>=10 & tempId<100){
                id="I0"+tempId;
            }else if(tempId>100){
                id="I"+tempId;
            }


        }
        return id==null ? "I001" : id;

    }

    @Override
    public String getItemCode() throws SQLException, ClassNotFoundException {

        PreparedStatement stm=DbConnection.getInstance().getConnection().prepareStatement("SELECT itemCode FROM Store ORDER BY itemCode DESC LIMIT 1");
        ResultSet resultSet = stm.executeQuery();
        resultSet.next();

        return resultSet.getString(1);

    }

    @Override
    public Store searchItem(String itemCode) throws SQLException, ClassNotFoundException {
        PreparedStatement stm = DbConnection.getInstance()
                .getConnection().prepareStatement("SELECT * FROM Store WHERE itemCode=?");
        stm.setObject(1,itemCode);

        ResultSet rst = stm.executeQuery();
        if (rst.next()) {
            return new Store(
                    rst.getString(1),
                    rst.getString(2),
                    rst.getString(3),
                    rst.getString(4),
                    rst.getString(5)

            );

        } else {
            return null;
        }

    }

    @Override
    public List<StoreTm> getItem() throws SQLException, ClassNotFoundException {
        PreparedStatement stm=DbConnection.getInstance().getConnection().prepareStatement("SELECT * FROM store");
        ResultSet resultSet = stm.executeQuery();
        ArrayList<StoreTm> list=new ArrayList<>();
        while (resultSet.next()){
            list.add(new StoreTm(resultSet.getString(1),resultSet.getString(2),resultSet.getString(3),resultSet.getString(4),resultSet.getString(5)));
        }
        return list;
    }

    @Override
    public boolean itemUpdate(Store s) throws SQLException, ClassNotFoundException {


        PreparedStatement stm= DbConnection.getInstance().getConnection().prepareStatement("UPDATE store SET description=?, unitPrice=?, qtyOnHand=?,disconnect=? WHERE itemCode=?");

        stm.setObject(1,s.getDescription());
        stm.setObject(2,s.getUnitPrice());
        stm.setObject(3,s.getQtyOnHand());
        stm.setObject(4,s.getDisconnect());
        stm.setObject(5,s.getItemCode());

        return stm.executeUpdate()>0;
    }

    @Override
    public boolean itemDelete(String itemCode) throws SQLException, ClassNotFoundException {
        if (DbConnection.getInstance().getConnection().prepareStatement("DELETE FROM store WHERE itemCode='"+itemCode+"'").executeUpdate()>0){
            return true;
        }else {
            return false;
        }
    }
}
