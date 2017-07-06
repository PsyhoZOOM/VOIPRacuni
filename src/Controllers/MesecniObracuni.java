package Controllers;

import classes.Database;
import javafx.fxml.Initializable;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by PsyhoZOOM on 7/4/17.
 */
public class MesecniObracuni implements Initializable {
    public Database db;
    private URL localtion;
    private ResourceBundle resources;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.localtion = location;
        this.resources = resources;
    }
}
