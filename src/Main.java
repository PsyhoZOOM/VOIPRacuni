import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.util.Locale;

/**
 * Created by zoom on 4/20/17.
 */
public class Main extends Application {
    public static void main(String[] args) {
        launch(args);

    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        Locale.setDefault(new Locale("rs", "RS"));
        Parent root = FXMLLoader.load(getClass().getResource("/FXML/MainWin.fxml"));
        primaryStage.setTitle("Fiksna Telefonija Stampa Racuna");
        primaryStage.setScene(new Scene(root));
        primaryStage.getIcons().add(new Image(getClass().getResourceAsStream("YuVideoLogo.png")));

        primaryStage.show();
    }

    public void init(String args[]) {
        launch(args);
    }
}
