package controller;

import com.sun.deploy.panel.JreFindDialog;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Duration;
import model.UserAccount;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.Date;
import java.util.List;

public class DashBoardFormController {
    public TextField txtUsernName;
    public PasswordField pswdPassword;
    public AnchorPane loginContext;
    public Label lblDate;
    public Label lblTime;

    public void initialize(){
        loadDateAndTime();
    }

    public void btnLoginOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException, IOException {
        if (txtUsernName.getText().isEmpty()){
            new Alert(Alert.AlertType.WARNING, "Wrong Username", ButtonType.CLOSE).show();
        }else {
            UserAccount account = new UserAccountController().getAccount(txtUsernName.getText());
            switch (account.getRole()){
                case "Manager":
                    if (txtUsernName.getText().equalsIgnoreCase(account.getUserName())& pswdPassword.getText().equalsIgnoreCase(account.getPassword())){
                        Stage window = (Stage) loginContext.getScene().getWindow();
                        window.setScene(new Scene(FXMLLoader.load(getClass().getResource("../view/ManagerForm.fxml"))));
                    }break;
                case "StoreKeeper":
                    if (txtUsernName.getText().equalsIgnoreCase(account.getUserName())& pswdPassword.getText().equalsIgnoreCase(account.getPassword())){
                        Stage window = (Stage) loginContext.getScene().getWindow();
                        window.setScene(new Scene(FXMLLoader.load(getClass().getResource("../view/StoreKeeper.fxml"))));
                    }

            }
        }


    }
    private void loadDateAndTime() {

        //loadDate
        Date date= new Date();
        SimpleDateFormat f= new SimpleDateFormat("yyyy-MM-dd");
        lblDate.setText(f.format(date));

        //loadTime
        Timeline time =new Timeline(new KeyFrame(Duration.ZERO,e ->{
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
}
