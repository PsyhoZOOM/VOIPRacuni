package Controllers;

import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by PsyhoZOOM@gmail.com on 4/21/17.
 */
public class stampaRacuna implements Initializable {
    public Button bClose;
    public Button bPrikazi;
    public Button bPrint;
    public TableView tblData;
    public TableColumn cIme;
    public TableColumn cNazivUsluge;
    public TableColumn cDatum;
    public DatePicker dtpOd;
    public DatePicker dtpDo;
    private URL location;
    private ResourceBundle resources;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.location = location;
        this.resources = resources;
    }

    public void showForPrint(ActionEvent actionEvent) {
    }

    public void printData(ActionEvent actionEvent) {
    }
}
