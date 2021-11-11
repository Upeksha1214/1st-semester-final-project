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
import model.Order;
import model.Shop;
import view.TM.OrderTm;
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

public class OderController {
    public AnchorPane orderContext;
    public Pane homePane;
    public Pane addPane;
    public Pane searchPane;
    public Pane updatePane;
    public Pane DeletePane;
    public AnchorPane orderControllerContext;
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

    private void clearPane() {
        homePane.setStyle("-fx-background-color: #357EC7");
        addPane.setStyle("-fx-background-color: #357EC7");
        searchPane.setStyle("-fx-background-color: #357EC7");
        updatePane.setStyle("-fx-background-color: #357EC7");
        DeletePane.setStyle("-fx-background-color:  #357EC7");
    }

    public void honeMouseClickOnAction(MouseEvent mouseEvent) throws IOException {
        clearPane();
        homePane.setStyle("-fx-background-color:#151B54");

        Stage window = (Stage) orderContext.getScene().getWindow();
        window.setScene(new Scene(FXMLLoader.load(getClass().getResource("../view/StoreKeeper.fxml"))));
    }

    public void homeEnterdMouseOnAction(MouseEvent mouseEvent) {
        clearPane();
        new Pulse(homePane).play();
        homePane.setStyle("-fx-background-color: #6495ED");
    }

    public void oderAddMouseCliclkOnAction(MouseEvent mouseEvent) throws IOException {
        clearPane();
        addPane.setStyle("-fx-background-color:#151B54");

        /*URL resource = getClass().getResource("../view/OderAdd.fxml");
        Parent load = FXMLLoader.load(resource);
        orderControllerContext.getChildren().add(load);*/

        Parent fxml =FXMLLoader.load(getClass().getResource("../view/OderAdd.fxml"));
        orderControllerContext.getChildren().removeAll();
        orderControllerContext.getChildren().setAll(fxml);
    }

    public void oderAddMouseEnterdOnAction(MouseEvent mouseEvent) {
        clearPane();
        new Pulse(addPane).play();
        addPane.setStyle("-fx-background-color: #6495ED");
    }

    public void oderSearchMouseClickOnAction(MouseEvent mouseEvent) throws IOException {
        clearPane();
        searchPane.setStyle("-fx-background-color:#151B54");

       /* URL resource = getClass().getResource("../view/OderSearch.fxml");
        Parent load = FXMLLoader.load(resource);
        orderControllerContext.getChildren().add(load);*/
        Parent fxml =FXMLLoader.load(getClass().getResource("../view/OderSearch.fxml"));
        orderControllerContext.getChildren().removeAll();
        orderControllerContext.getChildren().setAll(fxml);

    }

    public void oderSearchEnteredMouseOnAction(MouseEvent mouseEvent) {
        clearPane();
        new Pulse(searchPane).play();
        searchPane.setStyle("-fx-background-color: #6495ED");
    }

    public void oderUpdateMouseClickOnAction(MouseEvent mouseEvent) throws IOException {
        clearPane();
        updatePane.setStyle("-fx-background-color:#151B54");

        /*URL resource = getClass().getResource("../view/OderUpdate.fxml");
        Parent load = FXMLLoader.load(resource);
        orderControllerContext.getChildren().add(load);*/

        Parent fxml =FXMLLoader.load(getClass().getResource("../view/OderUpdate.fxml"));
        orderControllerContext.getChildren().removeAll();
        orderControllerContext.getChildren().setAll(fxml);
    }

    public void oderUpdateEnterdMouseonAction(MouseEvent mouseEvent) {
        clearPane();
        new Pulse(updatePane).play();
        updatePane.setStyle("-fx-background-color: #6495ED");
    }

    public void oderDeleteMouseClickOnAction(MouseEvent mouseEvent) throws IOException {
        clearPane();
        DeletePane.setStyle("-fx-background-color:#151B54");

        /*URL resource = getClass().getResource("../view/OderDelete.fxml");
        Parent load = FXMLLoader.load(resource);
        orderControllerContext.getChildren().add(load);*/

        Parent fxml =FXMLLoader.load(getClass().getResource("../view/OderDelete.fxml"));
        orderControllerContext.getChildren().removeAll();
        orderControllerContext.getChildren().setAll(fxml);
    }

    public void oderDeleteMouseEntereddOnAction(MouseEvent mouseEvent) {
        clearPane();
        new Pulse(DeletePane).play();
        DeletePane.setStyle("-fx-background-color:  #6495ED");
    }

    public static List<String> getAllRefId() throws SQLException, ClassNotFoundException {
        Connection con = DbConnection.getInstance().getConnection();
        PreparedStatement stm = con.prepareStatement("SELECT refID FROM ref");
        ResultSet rst = stm.executeQuery();
        List<String> refID = new ArrayList<>();
        while (rst.next()) {
            refID.add(rst.getString("refId"));
        }
        return refID;
    }

    public String getNewOrderId() throws SQLException, ClassNotFoundException {
        PreparedStatement stm = DbConnection.getInstance().getConnection().prepareStatement("SELECT orderID FROM orderDetails ORDER BY orderID DESC LIMIT 1");
        ResultSet resultSet = stm.executeQuery();
        String id = null;
        while (resultSet.next()) {
            String s = resultSet.getString(1);
            int tempId = Integer.parseInt(s.substring(1, 4));
            tempId = ++tempId;

            if (tempId < 10) {
                id = "O00" + tempId;

            } else if (tempId >= 10 & tempId < 100) {
                id = "O0" + tempId;
            } else if (tempId > 100) {
                id = "O" + tempId;
            }


        }
        return id == null ? "O001" : id;
    }

    public static List<String> getAllShopId() throws SQLException, ClassNotFoundException {
        Connection con = DbConnection.getInstance().getConnection();
        PreparedStatement stm = con.prepareStatement("SELECT shopID FROM shop");
        ResultSet rst = stm.executeQuery();
        List<String> refID = new ArrayList<>();
        while (rst.next()) {
            refID.add(rst.getString("shopId"));
        }
        return refID;
    }

    public String getUnitPrice(String unitPrice) throws SQLException, ClassNotFoundException {
        Connection con = DbConnection.getInstance().getConnection();
        PreparedStatement stm = con.prepareStatement("SELECT unitPrice FROM store WHERE itemCode=?");
        stm.setObject(1, unitPrice);
        ResultSet rst = stm.executeQuery();
        if (rst.next()) {
            return rst.getString("unitPrice");

        }
        return null;
    }

    public static List<String> getAllItemCode() throws SQLException, ClassNotFoundException {
        Connection con = DbConnection.getInstance().getConnection();
        PreparedStatement stm = con.prepareStatement("SELECT itemCode FROM store");
        ResultSet rst = stm.executeQuery();
        List<String> itemCode = new ArrayList<>();
        while (rst.next()) {
            itemCode.add(rst.getString("itemCode"));
        }
        return itemCode;
    }

    public String getCustomerQty(String Qty) throws SQLException, ClassNotFoundException {
        Connection con = DbConnection.getInstance().getConnection();
        PreparedStatement stm = con.prepareStatement("SELECT qtyOnHand FROM store WHERE itemCode=?");
        stm.setObject(1, Qty);
        ResultSet rst = stm.executeQuery();
        if (rst.next()) {
            return rst.getString("qtyOnHand");

        }
        return null;
    }

    public boolean order(Order o) throws SQLException, ClassNotFoundException {
        Connection con = null;
        try {
            con = DbConnection.getInstance().getConnection();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        String query = "INSERT INTO orderDetails Values(?,?,?,?,?,?,?,?)";
        try {
            con.setAutoCommit(false);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        PreparedStatement stm = con.prepareStatement(query);
        stm.setObject(1, o.getOrderId());
        stm.setObject(2, o.getRefId());
        stm.setObject(3, o.getShopId());
        stm.setObject(4, o.getItemCode());
        stm.setObject(5, o.getUnitPrice());
        stm.setObject(6, o.getQty());
        stm.setObject(7, o.getOrderQTY());
        stm.setObject(8, o.getTotal());

        try {
            con.rollback();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        try {
            con.commit();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        con.setAutoCommit(true);
        if(stm.executeUpdate() > 0) {
            if (updateQty(o.getItemCode(), Integer.parseInt(o.getOrderQTY()))) {

            }else{
                return false;
            }
        }else{
            return false;
        }
       // return stm.executeUpdate() > 0;
        return true;

    }
    public List<OrderTm> getOrder() throws SQLException, ClassNotFoundException {
        PreparedStatement stm = DbConnection.getInstance().getConnection().prepareStatement("SELECT * FROM orderDetails");
        ResultSet resultSet = stm.executeQuery();
        ArrayList<view.TM.OrderTm> refList=new ArrayList();
        while(resultSet.next()){


            refList.add(new view.TM.OrderTm(resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getString(3),
                    resultSet.getString(4),
                    resultSet.getString(5),
                    resultSet.getString(6),
                    resultSet.getString(7),
                    resultSet.getString(8)
                    ));
        }

        return refList ;


    }
    public Order searchOrder(String Id) throws SQLException, ClassNotFoundException {
        PreparedStatement stm = DbConnection.getInstance().getConnection().
                prepareStatement("SELECT * FROM orderDetails WHERE orderID=?");
        stm.setObject(1,Id);

        ResultSet rst = stm.executeQuery();
        if (rst.next()){
            return new Order(
                    rst.getString(1),
                    rst.getString(2),
                    rst.getString(3),
                    rst.getString(4),
                    rst.getString(5),
                    rst.getString(6),
                    rst.getString(7),
                    rst.getString(8)
            );
        }else
            return null;

    }
    public boolean orderUpdate(Order o) throws SQLException, ClassNotFoundException {
        PreparedStatement stm = DbConnection.getInstance().getConnection().prepareStatement
                ("UPDATE orderDetails SET refID=?, " +
                        "shopID=?,  itemCode =?,unitPrice=?,qtyOnHand=?,qty=?,total=? WHERE orderID=?");
        stm.setObject(1,o.getRefId());
        stm.setObject(2,o.getShopId());
        stm.setObject(3,o.getItemCode());
        stm.setObject(4,o.getUnitPrice());
        stm.setObject(5,o.getQty());
        stm.setObject(6,o.getOrderQTY());
        stm.setObject(7,o.getTotal());
        stm.setObject(8,o.getOrderId());

        return stm.executeUpdate()>0;

    }
    public boolean orderDelete(String Id) throws SQLException, ClassNotFoundException {
        if (DbConnection.getInstance().getConnection().prepareStatement
                ("DELETE FROM orderDetails WHERE orderID='"+Id+"'").executeUpdate()>0){
            return true;
        }else{
            return false;
        }
    }
    public boolean Delete(String Id) throws SQLException, ClassNotFoundException {
        if (DbConnection.getInstance().getConnection().prepareStatement
                ("DELETE FROM orderDetails WHERE orderID='"+Id+"'").executeUpdate()>0){
            return true;
        }else{
            return false;
        }
    }

    private boolean updateQty(String itemCode, int qty) throws SQLException, ClassNotFoundException {
        PreparedStatement stm = DbConnection.getInstance().getConnection()
                .prepareStatement
                        ("UPDATE store SET qtyOnHand=(qtyOnHand-" + qty
                                + ") WHERE itemCode='" + itemCode + "'");
        return stm.executeUpdate() > 0;
    }


}
