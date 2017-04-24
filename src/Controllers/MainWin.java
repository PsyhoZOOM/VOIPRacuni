package Controllers;

import classes.Database;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.BorderPane;

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
    URL location;
    ResourceBundle resources;
    FXMLLoader fxmlLoader;
    korisniciWin korisniciWinController;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.location = location;
        this.resources = resources;
        Database db = new Database();
        fxmlLoader = new FXMLLoader(getClass().getResource("/FXML/korisniciWin.fxml"), resources);
        try {
            bPane.setCenter((Node) fxmlLoader.load());
            korisniciWinController = fxmlLoader.getController();
            korisniciWinController.db = db;
            korisniciWinController.setData();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }
}
