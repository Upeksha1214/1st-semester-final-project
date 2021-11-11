package controller;

import animatefx.animation.BounceIn;
import com.jfoenix.controls.JFXTextArea;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Duration;
import model.Employee;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.Date;
import java.util.Scanner;

public class ManagerFormController {
    public AnchorPane mangerFormContext;
    public Label blTime;
    public Label lblDate;
    public TextField txtEmployeeId;
    public TextField txtEmployeetype;
    public TextField txtEmployeeNic;
    public TextField txtEmployeeAddress;
    public TextField txtPhoneNumber;
    public TextField txtSalary;
    public TextField txtEmployeeName;
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
            blTime.setText(
                    currentTime.getHour() + " : " + currentTime.getMinute() +
                            " : " + currentTime.getSecond()

            );
        } ),
                new KeyFrame(Duration.seconds(1))
        );
        time.setCycleCount(Animation.INDEFINITE);
        time.play();
    }

    public void btnEmployeeOnAction(ActionEvent actionEvent) throws IOException {
        Stage window = (Stage) mangerFormContext.getScene().getWindow();
        window.setScene(new Scene(FXMLLoader.load(getClass().getResource("../view/Employee.fxml"))));
    }

    public void btnStoreOnAction(ActionEvent actionEvent) throws IOException {
        Stage window = (Stage) mangerFormContext.getScene().getWindow();
        window.setScene(new Scene(FXMLLoader.load(getClass().getResource("../view/Store.fxml"))));
    }

    public void btnSystemReportOnAction(ActionEvent actionEvent) throws IOException {
        Stage window = (Stage) mangerFormContext.getScene().getWindow();
        window.setScene(new Scene(FXMLLoader.load(getClass().getResource("../view/SystemReport.fxml"))));
    }

    public void btnSalary(ActionEvent actionEvent) throws IOException {
        Stage window = (Stage) mangerFormContext.getScene().getWindow();
        window.setScene(new Scene(FXMLLoader.load(getClass().getResource("../view/Salary.fxml"))));
    }

    public void logoutOnAction(ActionEvent actionEvent) throws IOException {
        Stage window = (Stage) mangerFormContext.getScene().getWindow();
        window.setScene(new Scene(FXMLLoader.load(getClass().getResource("../view/DashBoardForm.fxml"))));
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

    public void SaveOnAction(ActionEvent actionEvent) {
        File file = fileChooser.showSaveDialog(new Stage());
        if(file != null){
            saveSystem(file, txtNotePad.getText());
        }
    }

    private void saveSystem(File file, String content) {
        try {
            PrintWriter printWriter = new PrintWriter(file);
            printWriter.write(content);
            printWriter.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}
