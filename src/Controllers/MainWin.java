package Controllers;

import classes.Database;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.BorderPane;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by PsyhoZOOM@gmail.com on 4/20/17.
 */
public class MainWin implements Initializable {
    public MenuItem menuOpenCSV;
    public MenuItem meniExit;
    public MenuItem menuAbout;
    public BorderPane bPane;
    public MenuItem menuPrikaziUsluge;
    URL location;
    ResourceBundle resources;
    FXMLLoader fxmlLoader;
    korisniciWin korisniciWinController;
    Database db = new Database();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.location = location;
        this.resources = resources;
        fxmlLoader = new FXMLLoader(getClass().getResource("/FXML/korisniciWin.fxml"), resources);
        try {
            bPane.setCenter((Node) fxmlLoader.load());
            korisniciWinController = fxmlLoader.getController();
            korisniciWinController.db = db;
            korisniciWinController.setData(null);
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    public void showUsluge(ActionEvent actionEvent) {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/FXML/zoneCene.fxml"), resources);

        try {
            Scene scene = new Scene((Parent) fxmlLoader.load());
            zoneCene zoneCeneController = fxmlLoader.getController();
            zoneCeneController.db = db;
            zoneCeneController.showData("");
            Stage stage = new Stage();
            stage.initOwner(bPane.getScene().getWindow());
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setTitle("Izmena Usluge-Zona");
            stage.setScene(scene);
            stage.showAndWait();
            zoneCeneController.showData("");

        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    public void openCSV(ActionEvent actionEvent) {
        FileChooser fileChooser = new FileChooser();
        FileChooser.ExtensionFilter extensionFilter = new FileChooser.ExtensionFilter("CSV Fajl", "*.csv");
        fileChooser.getExtensionFilters().setAll(extensionFilter);
        fileChooser.setTitle("Import CSV fajl");
        fileChooser.showOpenDialog(bPane.getScene().getWindow());
    }
}
