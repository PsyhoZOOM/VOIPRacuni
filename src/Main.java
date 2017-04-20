import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * Created by zoom on 4/20/17.
 */
public class Main extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/FXML/MainWin.fxml"));
        primaryStage.setTitle("Fiksna Telefonija Stampa Racuna");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }
}
