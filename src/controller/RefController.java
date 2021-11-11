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
import model.Vehicle;
import view.TM.RefTm;
import view.TM.VehicleTm;

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

public class RefController implements RefService {

    public Pane homePane;
    public Pane addRefPane;
    public Pane searchPane;
    public Pane updatePane;
    public Pane DeletePane;
    public AnchorPane refContext;
    public AnchorPane refControllerContext;
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
        addRefPane.setStyle("-fx-background-color: #357EC7");
        searchPane.setStyle("-fx-background-color: #357EC7");
        updatePane.setStyle("-fx-background-color: #357EC7");
        DeletePane.setStyle("-fx-background-color: #357EC7");
    }

    public void homeMouseOnAction(MouseEvent mouseEvent) throws IOException {
        clearPane();
        homePane.setStyle("-fx-background-color:#151B54");

        Stage window = (Stage) refContext.getScene().getWindow();
        window.setScene(new Scene(FXMLLoader.load(getClass().getResource("../view/StoreKeeper.fxml"))));
    }

    public void homeEnterOnAction(MouseEvent mouseEvent) {
        clearPane();
        new Pulse(homePane).play();
        homePane.setStyle("-fx-background-color: #6495ED");
    }

    public void addRefMouseOnAction(MouseEvent mouseEvent) throws IOException {
        clearPane();
        addRefPane.setStyle("-fx-background-color:#151B54");

        /*URL resource = getClass().getResource("../view/RefAdd.fxml");
        Parent load = FXMLLoader.load(resource);
        refControllerContext.getChildren().add(load);*/

        Parent fxml =FXMLLoader.load(getClass().getResource("../view/RefAdd.fxml"));
        refControllerContext.getChildren().removeAll();
        refControllerContext.getChildren().setAll(fxml);
    }

    public void addRefEnteredOnAction(MouseEvent mouseEvent) {
        clearPane();
        new Pulse(addRefPane).play();
        addRefPane.setStyle("-fx-background-color: #6495ED");

    }

    public void searchRefMoseOnAction(MouseEvent mouseEvent) throws IOException {
        clearPane();
        searchPane.setStyle("-fx-background-color:#151B54");

        /*URL resource = getClass().getResource("../view/RefSearch.fxml");
        Parent load = FXMLLoader.load(resource);
        refControllerContext.getChildren().add(load);*/
        Parent fxml =FXMLLoader.load(getClass().getResource("../view/RefSearch.fxml"));
        refControllerContext.getChildren().removeAll();
        refControllerContext.getChildren().setAll(fxml);

    }

    public void searchRefEnteredOnAction(MouseEvent mouseEvent) {
        clearPane();
        new Pulse(searchPane).play();
        searchPane.setStyle("-fx-background-color: #6495ED");
    }

    public void updateRefMouseOnAction(MouseEvent mouseEvent) throws IOException {
        clearPane();
        updatePane.setStyle("-fx-background-color:#151B54");

        /*URL resource = getClass().getResource("../view/RefUpdate.fxml");
        Parent load = FXMLLoader.load(resource);
        refControllerContext.getChildren().add(load);*/

        Parent fxml =FXMLLoader.load(getClass().getResource("../view/RefUpdate.fxml"));
        refControllerContext.getChildren().removeAll();
        refControllerContext.getChildren().setAll(fxml);
    }

    public void updateRedEnteredOnAction(MouseEvent mouseEvent) {
        clearPane();
        new Pulse(updatePane).play();
        updatePane.setStyle("-fx-background-color: #6495ED");
    }

    public void deleteRefMouseOnAction(MouseEvent mouseEvent) throws IOException {
        clearPane();
        DeletePane.setStyle("-fx-background-color:#151B54");

        /*URL resource = getClass().getResource("../view/RefDelete.fxml");
        Parent load = FXMLLoader.load(resource);
        refControllerContext.getChildren().add(load);*/

        Parent fxml =FXMLLoader.load(getClass().getResource("../view/RefDelete.fxml"));
        refControllerContext.getChildren().removeAll();
        refControllerContext.getChildren().setAll(fxml);
    }

    public void deleteRefEnteredOnAction(MouseEvent mouseEvent) {
        clearPane();
        new Pulse(DeletePane).play();
        DeletePane.setStyle("-fx-background-color:  #6495ED");
    }

    @Override
    public boolean refAdd(Ref r) throws SQLException, ClassNotFoundException {
        Connection con = DbConnection.getInstance().getConnection();
        String query="INSERT INTO ref Values(?,?,?,?,?)";
        PreparedStatement stm = con.prepareStatement(query);
        stm.setObject(1,r.getRefID());
        stm.setObject(2,r.getRefNIC());
        stm.setObject(3,r.getRefName());
        stm.setObject(4,r.getRefAddress());
        stm.setObject(5,r.getRefPhoneNumber());
        return stm.executeUpdate()>0;

    }

    @Override
    public String getNewRefId() throws SQLException, ClassNotFoundException {
        PreparedStatement stm = DbConnection.getInstance().getConnection().
                prepareStatement("SELECT refID FROM ref ORDER BY refID DESC LIMIT 1");
        ResultSet resultSet = stm.executeQuery();

        String id = null;
        while (resultSet.next()) {
            String r = resultSet.getString(1);
            int tempId = Integer.parseInt(r.substring(1, 4));
            tempId = ++tempId;

            if (tempId < 10) {
                id = "R00" + tempId;

            } else if (tempId >= 10 & tempId < 100) {
                id = "R0" + tempId;
            } else if (tempId > 100) {
                id = "R" + tempId;
            }


        }
        return id == null ? "R001" : id;

    }

    @Override
    public String getRefId() throws SQLException, ClassNotFoundException {
        PreparedStatement stm=DbConnection.getInstance().getConnection().prepareStatement("SELECT refID FROM ref ORDER BY refID DESC LIMIT 1");
        ResultSet resultSet = stm.executeQuery();

        return resultSet.getString(1);

    }

    @Override
    public Ref searchRef(String Id) throws SQLException, ClassNotFoundException {
        PreparedStatement stm = DbConnection.getInstance().getConnection().
                prepareStatement("SELECT * FROM ref WHERE refID=?");
        stm.setObject(1,Id);

        ResultSet rst = stm.executeQuery();
        if (rst.next()){
            return new Ref(
                    rst.getString(1),
                    rst.getString(2),
                    rst.getString(3),
                    rst.getString(4),
                    rst.getString(5)
            );
        }else
            return null;

    }

    @Override
    public List<RefTm> getRef() throws SQLException, ClassNotFoundException {
        PreparedStatement stm = DbConnection.getInstance().getConnection().prepareStatement("SELECT * FROM ref");
        ResultSet resultSet = stm.executeQuery();
        ArrayList<RefTm> refList=new ArrayList();
        while(resultSet.next()){

            refList.add(new RefTm(resultSet.getString(1),resultSet.getString(2),
                    resultSet.getString(3),resultSet.getString(4),resultSet.getString(5)));

        }

        return refList ;

    }

    @Override
    public boolean refUpdate(Ref r) throws SQLException, ClassNotFoundException {
        PreparedStatement stm = DbConnection.getInstance().getConnection().prepareStatement
                ("UPDATE ref SET refNIC=?, refName=?,  refAddress =?,refPhoneNumber=? WHERE refID=?");
        stm.setObject(1,r.getRefNIC());
        stm.setObject(2,r.getRefName());
        stm.setObject(3,r.getRefAddress());
        stm.setObject(4,r.getRefPhoneNumber());
        stm.setObject(5,r.getRefID());


        return stm.executeUpdate()>0;

    }

    @Override
    public boolean refDelete(String Id) throws SQLException, ClassNotFoundException {
        if (DbConnection.getInstance().getConnection().prepareStatement
                ("DELETE FROM ref WHERE refID='"+Id+"'").executeUpdate()>0){
            return true;
        }else{
            return false;
        }

    }
}
