package net.yuvideo.voipRacuni.Controllers;

import net.yuvideo.voipRacuni.classes.CSVZoneData;
import net.yuvideo.voipRacuni.classes.Database;
import net.yuvideo.voipRacuni.classes.Zone;
import net.yuvideo.voipRacuni.classes.ZoneCene;
import com.csvreader.CsvReader;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.FileChooser;
import javafx.util.Callback;
import javafx.util.StringConverter;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
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
    public ComboBox<ZoneCene> cmbZona;
    public Button bOsvezi;
    public Database db;
    public Button bIzmeni;
    public Button bImport;
    private URL location;
    private ResourceBundle resoources;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.location = location;
        this.resoources = resources;

        cNaziv.setCellValueFactory(new PropertyValueFactory<Zone, String>("naziv"));
        cOpis.setCellValueFactory(new PropertyValueFactory<Zone, String>("opis"));
        cUsluga.setCellValueFactory(new PropertyValueFactory<Zone, String>("zona"));


        cmbZona.setCellFactory(new Callback<ListView<ZoneCene>, ListCell<ZoneCene>>() {
            @Override
            public ListCell<ZoneCene> call(ListView<ZoneCene> param) {
                return new ListCell<ZoneCene>() {
                    @Override
                    protected void updateItem(ZoneCene item, boolean empty) {
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


        cmbZona.setConverter(new StringConverter<ZoneCene>() {
            @Override
            public String toString(ZoneCene object) {
                return object.getVrstaUsluge();
            }

            @Override
            public ZoneCene fromString(String string) {
                return null;
            }
        });


        tblZone.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Zone>() {
            @Override
            public void changed(ObservableValue<? extends Zone> observable, Zone oldValue, Zone newValue) {
                if (newValue == null)
                    return;
                tNazivZone.setText(tblZone.getSelectionModel().getSelectedItem().getNaziv());
                tOpisZone.setText(tblZone.getSelectionModel().getSelectedItem().getOpis());
                cmbZona.getSelectionModel().selectFirst();
                ObservableList<ZoneCene> zc = cmbZona.getItems();
                for (int i = 0; i < zc.size(); i++) {
                    if (newValue.getZonaID() == zc.get(i).getId())
                        cmbZona.setValue(zc.get(i));
                }

            }
        });

    }

    public void addZone(ActionEvent actionEvent) {
        PreparedStatement ps;
        String query = "INSERT INTO zone (naziv, opis, zona, zonaID) VALUES (?,?,?,?)";
        try {
            ps = db.connection.prepareStatement(query);
            ps.setString(1, tNazivZone.getText());
            ps.setString(2, tOpisZone.getText());
            ps.setString(3, cmbZona.getSelectionModel().getSelectedItem().getVrstaUsluge());
            ps.setInt(4, cmbZona.getSelectionModel().getSelectedItem().getId());
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
                    zone.setZona(rs.getString("zona"));
                    zone.setZonaID(rs.getInt("zonaID"));
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

        ZoneCene zoneCene;
        ArrayList<ZoneCene> zoneCeneArrayList = new ArrayList();

        query = "SELECT * FROM zoneCene";
        try {
            ps = db.connection.prepareStatement(query);
            rs = ps.executeQuery();
            if (rs.isBeforeFirst()) {
                while (rs.next()) {
                    zoneCene = new ZoneCene();
                    zoneCene.setId(rs.getInt("id"));
                    zoneCene.setVrstaUsluge(rs.getString("vrstaUsluge"));
                    zoneCene.setProviderCena(rs.getDouble("providerCena"));
                    zoneCene.setProviderPDV(rs.getDouble("providerPDV"));
                    zoneCene.setCena(rs.getDouble("cena"));
                    zoneCene.setPDV(rs.getDouble("PDV"));
                    zoneCene.setCenaPDV(rs.getDouble("cenaPDV"));
                    zoneCene.setCompetitionCena(rs.getDouble("otherCena"));
                    zoneCeneArrayList.add(zoneCene);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }


        ObservableList data = FXCollections.observableArrayList(zoneCeneArrayList);
        cmbZona.setItems(data);
    }

    public void refreshTable(ActionEvent actionEvent) {
        showTableZone();
    }

    public void izmeniZonu(ActionEvent actionEvent) {
        if (cmbZona.getSelectionModel().isEmpty() || tblZone.getSelectionModel().getSelectedIndex() == -1)
            return;
        PreparedStatement ps;
        String query = "UPDATE zone set naziv=?, opis=?, zona=?, zonaID=? WHERE id=?";

        try {
            ps = db.connection.prepareStatement(query);
            ps.setString(1, tNazivZone.getText());
            ps.setString(2, tOpisZone.getText());
            ps.setString(3, cmbZona.getSelectionModel().getSelectedItem().getVrstaUsluge());
            ps.setInt(4, cmbZona.getSelectionModel().getSelectedItem().getId());
            ps.setInt(5, tblZone.getSelectionModel().getSelectedItem().getId());
            ps.executeUpdate();
            ps.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        showTableZone();
    }

    public void importCSVZone(ActionEvent actionEvent) {
        FileChooser fileChooser = new FileChooser();
        FileChooser.ExtensionFilter extensionFilter = new FileChooser.ExtensionFilter("CSV", "*.csv");
        fileChooser.getExtensionFilters().addAll(extensionFilter);

        File file = fileChooser.showOpenDialog(bImport.getScene().getWindow());


        CSVZoneData csvZoneData;
        ArrayList<CSVZoneData> csvZoneDatas = new ArrayList<>();

        try {
            CsvReader csvReader = new CsvReader(new FileReader(file));
            csvReader.setDelimiter(',');
            csvReader.readHeaders();
            while (csvReader.readRecord()) {
                csvZoneData = new CSVZoneData();
                csvZoneData.setCountry(csvReader.get("Country"));
                csvZoneData.setDescription(csvReader.get("Description"));
                csvZoneData.setZona(csvReader.get("Zona"));
                csvZoneData.setCenaZoneID(Integer.parseInt(csvReader.get("CenaZoneID")));
                csvZoneDatas.add(csvZoneData);
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


        PreparedStatement ps;
        String query = "INSERT INTO zone (naziv, opis, zona, zonaID) VALUES (?,?,?,?)";

        try {
            ps = db.connection.prepareStatement(query);
            for (int i = 0; i < csvZoneDatas.size(); i++) {
                ps.setString(1, csvZoneDatas.get(i).getCountry());
                ps.setString(2, csvZoneDatas.get(i).getDescription());
                ps.setString(3, csvZoneDatas.get(i).getZona());
                ps.setInt(4, csvZoneDatas.get(i).getCenaZoneID());
                ps.executeUpdate();

            }

        } catch (SQLException e) {
            e.printStackTrace();
        }


    }
}
