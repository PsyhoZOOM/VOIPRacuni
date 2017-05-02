package Controllers;

import classes.Database;
import classes.Zone;
import classes.ZoneUsluge;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Callback;
import javafx.util.StringConverter;

import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;

/**
 * Created by PsyhoZOOM@gmail.com on 5/1/17.
 */
public class editZone implements Initializable {
    public TextField tNazivZone;
    public TextField tOpisZone;
    public Button bDodaj;
    public TableView<Zone> tblZone;
    public TableColumn cNaziv;
    public TableColumn cOpis;
    public TableColumn cUsluga;
    public Button bObrisi;
    public ComboBox<ZoneUsluge> cmbUsluga;
    public Button bOsvezi;
    public Database db;
    private URL location;
    private ResourceBundle resoources;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.location = location;
        this.resoources = resources;

        cNaziv.setCellValueFactory(new PropertyValueFactory<Zone, String>("naziv"));
        cOpis.setCellValueFactory(new PropertyValueFactory<Zone, String>("opis"));
        cUsluga.setCellValueFactory(new PropertyValueFactory<Zone, String>("usluga"));


        cmbUsluga.setCellFactory(new Callback<ListView<ZoneUsluge>, ListCell<ZoneUsluge>>() {
            @Override
            public ListCell<ZoneUsluge> call(ListView<ZoneUsluge> param) {
                return new ListCell<ZoneUsluge>() {
                    @Override
                    protected void updateItem(ZoneUsluge item, boolean empty) {
                        super.updateItem(item, empty);

                        if (item == null || empty) {
                            setText(null);
                        } else {
                            setText(item.getVrstaUsluge());
                        }

                    }
                };
            }
        });


        cmbUsluga.setConverter(new StringConverter<ZoneUsluge>() {
            @Override
            public String toString(ZoneUsluge object) {
                return object.getVrstaUsluge();
            }

            @Override
            public ZoneUsluge fromString(String string) {
                return null;
            }
        });


    }

    public void addZone(ActionEvent actionEvent) {
        PreparedStatement ps;
        String query = "INSERT INTO zone ('naziv', 'opis', 'usluga', 'uslugaID') VALUES (?,?,?,?)";
        try {
            ps = db.connection.prepareStatement(query);
            ps.setString(1, tNazivZone.getText());
            ps.setString(2, tOpisZone.getText());
            ps.setString(3, cmbUsluga.getSelectionModel().getSelectedItem().getVrstaUsluge());
            ps.setInt(4, cmbUsluga.getSelectionModel().getSelectedItem().getId());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        showTableZone();

    }

    public void deleteZone(ActionEvent actionEvent) {
        if (tblZone.getSelectionModel().getSelectedIndex() == -1) {
            return;
        }
        PreparedStatement ps;
        String query = "DELETE FROM zone WHERE id=?";
        try {
            ps = db.connection.prepareStatement(query);
            ps.setInt(1, tblZone.getSelectionModel().getSelectedItem().getId());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        showTableZone();
    }

    public void showData(String s) {
        showDataUsluge();
        showTableZone();
    }

    private void showTableZone() {
        PreparedStatement ps;
        ResultSet rs;
        String query;

        Zone zone;
        ArrayList<Zone> zoneArrayList = new ArrayList();

        query = "SELECT * FROM zone";
        try {
            ps = db.connection.prepareStatement(query);
            rs = ps.executeQuery();
            if (rs.isBeforeFirst()) {
                while (rs.next()) {
                    zone = new Zone();
                    zone.setId(rs.getInt("id"));
                    zone.setNaziv(rs.getString("naziv"));
                    zone.setOpis(rs.getString("opis"));
                    zone.setUsluga(rs.getString("usluga"));
                    zone.setUslugaID(rs.getInt("uslugaID"));
                    zoneArrayList.add(zone);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        ObservableList data = FXCollections.observableArrayList(zoneArrayList);
        tblZone.setItems(data);
    }

    private void showDataUsluge() {

        PreparedStatement ps;
        ResultSet rs;
        String query;

        ZoneUsluge zoneUsluge;
        ArrayList<ZoneUsluge> zoneUslugeArrayList = new ArrayList();

        query = "SELECT * FROM zoneCene";
        try {
            ps = db.connection.prepareStatement(query);
            rs = ps.executeQuery();
            if (rs.isBeforeFirst()) {
                while (rs.next()) {
                    zoneUsluge = new ZoneUsluge();
                    zoneUsluge.setId(rs.getInt("id"));
                    zoneUsluge.setVrstaUsluge(rs.getString("vrstaUsluge"));
                    zoneUsluge.setProviderCena(rs.getDouble("providerCena"));
                    zoneUsluge.setProviderPDV(rs.getDouble("providerPDV"));
                    zoneUsluge.setCena(rs.getDouble("cena"));
                    zoneUsluge.setPDV(rs.getDouble("PDV"));
                    zoneUsluge.setCenaPDV(rs.getDouble("cenaPDV"));
                    zoneUsluge.setCompetitionCena(rs.getDouble("otherCena"));
                    zoneUslugeArrayList.add(zoneUsluge);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }


        ObservableList data = FXCollections.observableArrayList(zoneUslugeArrayList);
        cmbUsluga.setItems(data);
    }

    public void refreshTable(ActionEvent actionEvent) {
        showTableZone();
    }
}
