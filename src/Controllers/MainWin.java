package Controllers;

import javafx.fxml.Initializable;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by zoom on 4/20/17.
 */
public class MainWin implements Initializable {
    URL location;
    ResourceBundle resources;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.location = location;
        this.resources = resources;

    }
}
