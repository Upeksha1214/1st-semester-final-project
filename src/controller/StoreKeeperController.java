package controller;

import com.jfoenix.controls.JFXTextArea;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.Date;
import java.util.Scanner;

public class StoreKeeperController {


    public AnchorPane storeKeeperOnAction;
    public Label lblTime;
    public Label lblDate;
    public JFXTextArea txtNotePad;

    FileChooser fileChooser=new FileChooser();

    public void initialize(){
        loadDateAndTime();
        fileChooser.setInitialDirectory(new File("D:\\final project\\Delivery System\\Delivery System\\src\\Sample"));
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

    public void driverOnAction(ActionEvent actionEvent) throws IOException {
        Stage window = (Stage) storeKeeperOnAction.getScene().getWindow();
        window.setScene(new Scene(FXMLLoader.load(getClass().getResource("../view/Driver.fxml"))));
    }

    public void vehicleOnAction(ActionEvent actionEvent) throws IOException {
        Stage window = (Stage) storeKeeperOnAction.getScene().getWindow();
        window.setScene(new Scene(FXMLLoader.load(getClass().getResource("../view/Vehicle.fxml"))));
    }

    public void refOnAction(ActionEvent actionEvent) throws IOException {
        Stage window = (Stage) storeKeeperOnAction.getScene().getWindow();
        window.setScene(new Scene(FXMLLoader.load(getClass().getResource("../view/Ref.fxml"))));
    }

    public void shopOnAction(ActionEvent actionEvent) throws IOException {
        Stage window = (Stage) storeKeeperOnAction.getScene().getWindow();
        window.setScene(new Scene(FXMLLoader.load(getClass().getResource("../view/Shop.fxml"))));
    }

    public void oderDetailsOnAction(ActionEvent actionEvent) throws IOException {
        Stage window = (Stage) storeKeeperOnAction.getScene().getWindow();
        window.setScene(new Scene(FXMLLoader.load(getClass().getResource("../view/Oder.fxml"))));
    }

    public void logOutOnAction(ActionEvent actionEvent) throws IOException {
        Stage window = (Stage) storeKeeperOnAction.getScene().getWindow();
        window.setScene(new Scene(FXMLLoader.load(getClass().getResource("../view/DashBoardForm.fxml"))));
    }

    public void SaveOnAction(ActionEvent actionEvent) {
        File file = fileChooser.showSaveDialog(new Stage());
        if(file != null){
            saveSystem(file, txtNotePad.getText());
        }
    }

    public void openFileOnAction(ActionEvent actionEvent) {
        File file = fileChooser.showOpenDialog(new Stage());

        try {
            Scanner scanner = new Scanner(file);
            while(scanner.hasNextLine()){
                txtNotePad.appendText(scanner.nextLine() + "\n");
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
    public void saveSystem(File file, String content){
        try {
            PrintWriter printWriter = new PrintWriter(file);
            printWriter.write(content);
            printWriter.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

    }
}
