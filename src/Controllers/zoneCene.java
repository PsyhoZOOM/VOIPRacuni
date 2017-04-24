package Controllers;

import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by PsyhoZOOM@gmail.com on 4/21/17.
 */
public class zoneCene implements Initializable {
    public Button bNov;
    public Button bIzmeni;
    public Button bObrisi;
    public Button bTrazi;
    public TableView tblCeneZone;
    public TableColumn cVrstaUsluge;
    public TableColumn cProviderCena;
    public TableColumn cProviderCenaPDV;
    public TableColumn cTelekomCena;
    public TableColumn cTelekomRazlika;
    public TableColumn cCena;
    public TableColumn cPDV;
    public TableColumn cCenaPDV;
    private URL location;
    private ResourceBundle resources;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.location = location;
        this.resources = resources;
    }

    public void showNov(ActionEvent actionEvent) {
    }

    public void showIzmeni(ActionEvent actionEvent) {
    }

    public void showObrisi(ActionEvent actionEvent) {
    }

    public void tTrazi(ActionEvent actionEvent) {
    }

    public void traziKorisnik(ActionEvent actionEvent) {
    }
}
