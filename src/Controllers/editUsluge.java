package Controllers;

import classes.Database;
import classes.ZoneCene;
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
 * Created by PsyhoZOOM@gmail.com on 4/24/17.
 */
public class editUsluge implements Initializable {
    public TextField tNazivUsluge;
    public TextField tProviderCena;
    public TextField tProviderPVD;
    public TextField tTelekomCena;
    public TextField tCena;
    public TextField tPDV;
    public Button bSnimi;
    public Database db;
    public ZoneCene zoneCene;
    public boolean setUpdate = false;
    private URL location;
    private ResourceBundle resources;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.location = location;
        this.resources = resources;
    }

    public void saveData(ActionEvent actionEvent) {
        if (setUpdate) {
            upateData();
        } else {
            saveData();
        }
    }

    private void saveData() {
        String query = "INSERT INTO zoneCene (vrstaUsluge, providerCena, providerPDV, cena, PDV, otherCena) VALUES (?,?,?,?,?,?)";
        PreparedStatement ps = null;

        try {
            ps = db.connection.prepareStatement(query);
            ps.setString(1, tNazivUsluge.getText());
            ps.setDouble(2, Double.parseDouble(tProviderCena.getText()));
            ps.setDouble(3, Double.parseDouble(tProviderPVD.getText()));
            ps.setDouble(4, Double.parseDouble(tCena.getText()));
            ps.setDouble(5, Double.parseDouble(tPDV.getText()));
            ps.setDouble(6, Double.parseDouble(tTelekomCena.getText()));
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }


        Stage stage = (Stage) bSnimi.getScene().getWindow();
        stage.close();

    }

    private void upateData() {
        String query = "UPDATE zoneCene SET vrstaUsluge=?, providerCena=?, providerPDV=?, cena=?, PDV=?, otherCena=? WHERE id=?";
        PreparedStatement ps = null;
        try {
            ps = db.connection.prepareStatement(query);
            ps.setString(1, tNazivUsluge.getText());
            ps.setDouble(2, Double.parseDouble(tProviderCena.getText()));
            ps.setDouble(3, Double.parseDouble(tProviderPVD.getText()));
            ps.setDouble(4, Double.parseDouble(tCena.getText()));
            ps.setDouble(5, Double.parseDouble(tPDV.getText()));
            ps.setDouble(6, Double.parseDouble(tTelekomCena.getText()));
            ps.setInt(7, zoneCene.getId());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        Stage stager = (Stage) bSnimi.getScene().getWindow();
        stager.close();
    }


    public void showData() {
        tNazivUsluge.setText(zoneCene.getVrstaUsluge());
        tProviderCena.setText(String.valueOf(zoneCene.getProviderCena()));
        tProviderPVD.setText(String.valueOf(zoneCene.getProviderPDV()));
        tTelekomCena.setText(String.valueOf(zoneCene.getCompetitionCena()));
        tCena.setText(String.valueOf(zoneCene.getCena()));
        tPDV.setText(String.valueOf(zoneCene.getPDV()));

    }
}
