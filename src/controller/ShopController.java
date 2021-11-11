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
import model.Ref;
import model.Shop;
import view.TM.RefTm;
import view.TM.ShopTm;

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

public class  ShopController implements ShopService{
    public Pane homePane;
    public Pane addPane;
    public Pane searchPane;
    public Pane updatePane;
    public Pane deletePane;
    public AnchorPane shopContext;
    public AnchorPane shopControllerContext;
    public Label lblTime;
    public Label lblDate;

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
        addPane.setStyle("-fx-background-color:  #357EC7");
        searchPane.setStyle("-fx-background-color: #357EC7");
        updatePane.setStyle("-fx-background-color:  #357EC7");
        deletePane.setStyle("-fx-background-color: #357EC7");
    }

    public void homeMouseOnAction(MouseEvent mouseEvent) throws IOException {
        clearPane();
        homePane.setStyle("-fx-background-color:#151B54");

        Stage window = (Stage) shopContext.getScene().getWindow();
        window.setScene(new Scene(FXMLLoader.load(getClass().getResource("../view/StoreKeeper.fxml"))));
    }

    public void homeMouseEnteredOnAction(MouseEvent mouseEvent) {
        clearPane();
        new Pulse(homePane).play();
        homePane.setStyle("-fx-background-color: #6495ED");
    }

    public void addItemMouseOnAction(MouseEvent mouseEvent) throws IOException {
        clearPane();
        addPane.setStyle("-fx-background-color:#151B54");

       /* URL resource = getClass().getResource("../view/ShopAdd.fxml");
        Parent load = FXMLLoader.load(resource);
        shopControllerContext.getChildren().add(load);*/
        Parent fxml =FXMLLoader.load(getClass().getResource("../view/ShopAdd.fxml"));
        shopControllerContext.getChildren().removeAll();
        shopControllerContext.getChildren().setAll(fxml);
    }

    public void addItemEnteredOnAction(MouseEvent mouseEvent) {
        clearPane();
        new Pulse(addPane).play();
        addPane.setStyle("-fx-background-color: #6495ED");
    }

    public void searchItemMoseOnAction(MouseEvent mouseEvent) throws IOException {
        clearPane();
        searchPane.setStyle("-fx-background-color:#151B54");

        Parent fxml =FXMLLoader.load(getClass().getResource("../view/ShopSearch.fxml"));
        shopControllerContext.getChildren().removeAll();
        shopControllerContext.getChildren().setAll(fxml);
    }

    public void searchItemMouseEnteredOnAction(MouseEvent mouseEvent) {
        clearPane();
        new Pulse(searchPane).play();
        searchPane.setStyle("-fx-background-color: #6495ED");
    }

    public void updateItemMouseOnAction(MouseEvent mouseEvent) throws IOException {
        clearPane();
        updatePane.setStyle("-fx-background-color:#151B54");

        Parent fxml =FXMLLoader.load(getClass().getResource("../view/ShopUpdate.fxml"));
        shopControllerContext.getChildren().removeAll();
        shopControllerContext.getChildren().setAll(fxml);
    }

    public void updateMouseEnteredOnAction(MouseEvent mouseEvent) {
        clearPane();
        new Pulse(updatePane).play();
        updatePane.setStyle("-fx-background-color: #6495ED");
    }

    public void deleteItemMouseOnAction(MouseEvent mouseEvent) throws IOException {
        clearPane();
        deletePane.setStyle("-fx-background-color:#151B54");

        Parent fxml =FXMLLoader.load(getClass().getResource("../view/ShopDelete.fxml"));
        shopControllerContext.getChildren().removeAll();
        shopControllerContext.getChildren().setAll(fxml);
    }

    public void deleteItemMouseEnteredOnAction(MouseEvent mouseEvent) {
        clearPane();
        new Pulse(deletePane).play();
        deletePane.setStyle("-fx-background-color:  #6495ED");
    }

    @Override
    public boolean shopAdd(Shop s) throws SQLException, ClassNotFoundException {
        Connection con = DbConnection.getInstance().getConnection();
        String query="INSERT INTO shop Values(?,?,?,?)";
        PreparedStatement stm = con.prepareStatement(query);
        stm.setObject(1,s.getShopId());
        stm.setObject(2,s.getShopName());
        stm.setObject(3,s.getShopPhoneNumber());
        stm.setObject(4,s.getShopAddress());
        return stm.executeUpdate()>0;

    }

    @Override
    public String getNewShopId() throws SQLException, ClassNotFoundException {
        PreparedStatement stm = DbConnection.getInstance().getConnection().
                prepareStatement("SELECT shopID FROM shop ORDER BY shopID DESC LIMIT 1");
        ResultSet resultSet = stm.executeQuery();

        String id = null;
        while (resultSet.next()) {
            String r = resultSet.getString(1);
            int tempId = Integer.parseInt(r.substring(1, 4));
            tempId = ++tempId;

            if (tempId < 10) {
                id = "S00" + tempId;

            } else if (tempId >= 10 & tempId < 100) {
                id = "S0" + tempId;
            } else if (tempId > 100) {
                id = "S" + tempId;
            }


        }
        return id == null ? "S001" : id;


    }

    @Override
    public String getShopId() throws SQLException, ClassNotFoundException {
        PreparedStatement stm=DbConnection.getInstance().getConnection().prepareStatement("SELECT shopID FROM shop ORDER BY shopID DESC LIMIT 1");
        ResultSet resultSet = stm.executeQuery();

        return resultSet.getString(1);

    }

    @Override
    public Shop searchShop(String Id) throws SQLException, ClassNotFoundException {
        PreparedStatement stm = DbConnection.getInstance().getConnection().
                prepareStatement("SELECT * FROM shop WHERE shopID=?");
        stm.setObject(1,Id);

        ResultSet rst = stm.executeQuery();
        if (rst.next()){
            return new Shop(
                    rst.getString(1),
                    rst.getString(2),
                    rst.getString(3),
                    rst.getString(4)
            );
        }else
            return null;

    }

    @Override
    public List<ShopTm> getShop() throws SQLException, ClassNotFoundException {
        PreparedStatement stm = DbConnection.getInstance().getConnection().prepareStatement("SELECT * FROM shop");
        ResultSet resultSet = stm.executeQuery();
        ArrayList<ShopTm> refList=new ArrayList();
        while(resultSet.next()){


            refList.add(new ShopTm(resultSet.getString(1),resultSet.getString(2),
                    resultSet.getString(3),resultSet.getString(4)));
        }

        return refList ;


    }

    @Override
    public boolean shopUpdate(Shop s) throws SQLException, ClassNotFoundException {
        PreparedStatement stm = DbConnection.getInstance().getConnection().prepareStatement
                ("UPDATE shop SET shopName=?, shopPhoneNumber=?,  shopAddress =? WHERE shopId=?");
        stm.setObject(1,s.getShopName());
        stm.setObject(2,s.getShopPhoneNumber());
        stm.setObject(3,s.getShopAddress());
        stm.setObject(4,s.getShopId());



        return stm.executeUpdate()>0;

    }

    @Override
    public boolean shopDelete(String Id) throws SQLException, ClassNotFoundException {
        if (DbConnection.getInstance().getConnection().prepareStatement
                ("DELETE FROM shop WHERE shopID='"+Id+"'").executeUpdate()>0){
            return true;
        }else{
            return false;
        }
    }
}
