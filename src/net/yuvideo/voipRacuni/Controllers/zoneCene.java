package net.yuvideo.voipRacuni.Controllers;

import net.yuvideo.voipRacuni.classes.Database;
import net.yuvideo.voipRacuni.classes.ZoneCene;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;

/**
 * Created by PsyhoZOOM@gmail.com on 4/21/17.
 */
public class zoneCene implements Initializable {
    public Button bNov;
    public Button bIzmeni;
    public Button bObrisi;
    public Button bTrazi;
    public TableView<ZoneCene> tblCeneZone;
    public TableColumn cVrstaUsluge;
    public TableColumn cProviderCena;
    public TableColumn cTelekomCena;
    public TableColumn cCena;
    public TableColumn cPDV;
    public TableColumn cCenaPDV;
    public TableColumn cProviderPDV;
    public TableColumn cProviderCenaPDV;
    public TableColumn cRazlika;
    public TextField tTrazi;
    public Database db;
    private URL location;
    private ResourceBundle resources;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.location = location;
        this.resources = resources;

        cVrstaUsluge.setCellValueFactory(new PropertyValueFactory<ZoneCene, String>("vrstaUsluge"));
        cProviderCena.setCellValueFactory(new PropertyValueFactory<ZoneCene, Double>("providerCena"));
        cProviderPDV.setCellValueFactory(new PropertyValueFactory<ZoneCene, Double>("providerPDV"));
        cProviderCenaPDV.setCellValueFactory(new PropertyValueFactory<ZoneCene, Double>("providerUkupno"));
        cTelekomCena.setCellValueFactory(new PropertyValueFactory<ZoneCene, Double>("competitionCena"));
        cCena.setCellValueFactory(new PropertyValueFactory<ZoneCene, Double>("cena"));
        cPDV.setCellValueFactory(new PropertyValueFactory<ZoneCene, Double>("PDV"));
        cCenaPDV.setCellValueFactory(new PropertyValueFactory<ZoneCene, Double>("cenaPDV"));
        cRazlika.setCellValueFactory(new PropertyValueFactory<ZoneCene, Double>("razlika"));



    }

    public void showNov(ActionEvent actionEvent) {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/FXML/editUsluge.fxml"), resources);
        Stage stage = new Stage();
        try {
            Scene scene = new Scene(fxmlLoader.load());
            editUsluge editZoneController = fxmlLoader.getController();
            editZoneController.db = db;
            stage.initOwner(bNov.getScene().getWindow());
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setTitle("Nova zona");
            stage.setScene(scene);
            stage.showAndWait();
            setTableData("");

        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    public void showIzmeni(ActionEvent actionEvent) {
        ZoneCene zoneCene = tblCeneZone.getSelectionModel().getSelectedItem();
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/FXML/editUsluge.fxml"), resources);
        Stage stage = new Stage();
        try {
            Scene scene = new Scene(fxmlLoader.load());
            editUsluge editZoneController = fxmlLoader.getController();
            editZoneController.db = db;
            editZoneController.zoneCene = zoneCene;
            editZoneController.setUpdate = true;
            editZoneController.showData();
            stage.initOwner(bIzmeni.getScene().getWindow());
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setTitle("Izmena zone");
            stage.setScene(scene);
            editZoneController.showData();
            stage.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }

        setTableData("");
    }

    public void showObrisi(ActionEvent actionEvent) {
        if (tblCeneZone.getSelectionModel().getSelectedIndex() == -1) {
            return;
        }
        String query = "DELETE FROM zoneCene WHERE id=?";
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = db.connection.prepareStatement(query);
            preparedStatement.setInt(1, tblCeneZone.getSelectionModel().getSelectedItem().getId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        setTableData("");


    }


    public void traziKorisnik(ActionEvent actionEvent) {
        showData(tTrazi.getText());

    }

    public void showData(String s) {
        setTableData(s);
    }

    private void setTableData(String search) {
        PreparedStatement ps = null;
        ResultSet rs = null;
        ZoneCene zoneCene;
        ArrayList<ZoneCene> zoneCenesArrayList = new ArrayList<ZoneCene>();
        String query = "SELECT * FROM zoneCene WHERE vrstaUsluge LIKE ?";
        try {
            ps = db.connection.prepareStatement(query);
            ps.setString(1, search + "%");
            rs = ps.executeQuery();
            if (rs.isBeforeFirst()) {
                while (rs.next()) {
                    zoneCene = new ZoneCene();
                    zoneCene.setId(rs.getInt("id"));
                    zoneCene.setVrstaUsluge(rs.getString("vrstaUsluge"));
                    zoneCene.setProviderCena(rs.getDouble("providerCena"));
                    zoneCene.setProviderPDV(rs.getDouble("providerPDV"));
                    zoneCene.setProviderUkupno(rs.getDouble("providerCena") + rs.getDouble("providerCena") * rs.getDouble("providerPDV") / 100);
                    zoneCene.setCena(rs.getDouble("cena"));
                    zoneCene.setPDV(rs.getDouble("PDV"));
                    zoneCene.setCenaPDV(rs.getDouble("cena") + rs.getDouble("cena") * rs.getDouble("PDV") / 100);
                    zoneCene.setCompetitionCena(rs.getDouble("otherCena"));
                    Double otherCena = rs.getDouble("otherCena");
                    Double nasaCena = rs.getDouble("cena") + rs.getDouble("cena") * rs.getDouble("PDV") / 100;
                    Double razlikeCena = 0.00;
                    if (otherCena > nasaCena) {
                        razlikeCena = otherCena - nasaCena;
                    } else {
                        razlikeCena = nasaCena - otherCena;
                    }
                    zoneCene.setRazlika(razlikeCena);
                    zoneCenesArrayList.add(zoneCene);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        ObservableList data = FXCollections.observableArrayList(zoneCenesArrayList);
        tblCeneZone.setItems(data);
    }


}
