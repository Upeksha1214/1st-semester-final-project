import animatefx.animation.*;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.File;
import java.io.IOException;

public class AppInitializer extends Application {

    public static void main(String[] args) {
        launch(args);
        File file = new File("src\\Sample\\NotePade.txt");
        try {
            file.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void start(Stage primaryStage) throws IOException {
        /*primaryStage.setScene(new Scene(FXMLLoader.load(getClass().getResource("view/DashBoardForm.fxml"))));*/
        FXMLLoader loader=new FXMLLoader(getClass().getResource("view/DashBoardForm.fxml"));
        Parent root =loader.load();
        Scene scene=new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.setMaximized(true );
        primaryStage.setTitle("Saviya Yogurt Delivery System(v-1.0.0)");
        primaryStage.show();
        /*new (root)*//*.setDelay(Duration.millis(0.))*//*.play();*/

    }
}
