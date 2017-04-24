package Controllers;

import classes.Database;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ResourceBundle;

/**
 * Created by PsyhoZOOM@gmail.com on 4/21/17.
 */
public class editKorisnik implements Initializable {
    public Button bClose;
    public Button bSnimi;
    public TextField tImePrezime;
    public TextField tAdresa;
    public TextField tMesto;
    public TextField tPostBr;
    public TextField tbrUgovora;
    public Database db;
    private URL location;
    private ResourceBundle resources;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.location = location;
        this.resources = resources;
    }

    public void snimiKorisnika(ActionEvent actionEvent) {
        PreparedStatement ps;
        String query = "INSERT INTO korisnici (imePrezime, adresa, mesto, postbr, brUgovora) VALUES (?,?,?,?,?)";
        try {
            ps = db.connection.prepareStatement(query);
            ps.setString(1, tImePrezime.getText());
            ps.setString(2, tAdresa.getText());
            ps.setString(3, tMesto.getText());
            ps.setString(4, tPostBr.getText());
            ps.setString(5, tbrUgovora.getText());
            ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        Stage window = (Stage) bClose.getScene().getWindow();
        window.close();

    }
}
