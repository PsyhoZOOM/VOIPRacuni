package net.yuvideo.voipRacuni.Controllers;

import net.yuvideo.voipRacuni.classes.Database;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;
import net.yuvideo.voipRacuni.classes.Paketi;

/**
 * Created by PsyhoZOOM@gmail.com on 5/2/17.
 */
public class editPaket implements Initializable {
    public TableView<Paketi> tblPaketi;
    public TableColumn cNaziv;
    public TableColumn cPretplata;
    public TableColumn cPDV;
    public TextField tNaziv;
    public TextField tPretplata;
    public TextField tPDV;
    public Button bNov;
    public Button bSnimi;
    public Button bOsvezi;
    public Button bDelete;
    public Database db;
    public TextField tBesplatniMinutiFiksna;
    private ResourceBundle resources;
    private URL location;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.location = location;
        this.resources = resources;

        cNaziv.setCellValueFactory(new PropertyValueFactory<Paketi, String>("naziv"));
        cPretplata.setCellValueFactory(new PropertyValueFactory<Paketi, Double>("pretplata"));
        cPDV.setCellValueFactory(new PropertyValueFactory<Paketi, Double>("PDV"));

        tblPaketi.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Paketi>() {
            @Override
            public void changed(ObservableValue<? extends Paketi> observable, Paketi oldValue, Paketi newValue) {
                if (tblPaketi.getSelectionModel().getSelectedIndex() == -1)
                    return;
                tNaziv.setText(newValue.getNaziv());
                tPretplata.setText(String.valueOf(newValue.getPretplata()));
                tPDV.setText(String.valueOf(newValue.getPDV()));
                tBesplatniMinutiFiksna.setText(String.valueOf(newValue.getBesplatniMinutiFiksna()));
            }
        });

    }


    public void snimiNov(ActionEvent actionEvent) {
        PreparedStatement ps;
        String query = "INSERT INTO paketi (naziv, pretplata, PDV, besplatniMinutiFiksna) VALUES (?,?,?,?)";
        try {
            ps = db.connection.prepareStatement(query);
            ps.setString(1, tNaziv.getText());
            ps.setDouble(2, Double.parseDouble(tPretplata.getText()));
            ps.setDouble(3, Double.parseDouble(tPDV.getText()));
            ps.setInt(4, Integer.parseInt(tBesplatniMinutiFiksna.getText()));
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        showData("");
    }

    public void snimi(ActionEvent actionEvent) {
        if (tblPaketi.getSelectionModel().getSelectedIndex() == -1)
            return;

        PreparedStatement ps;
        String query = "UPDATE paketi set naziv=?, pretplata=?, PDV=?, besplatniMinutiFiksna=? WHERE id=?";
        try {
            ps = db.connection.prepareStatement(query);
            ps.setString(1, tNaziv.getText());
            ps.setDouble(2, Double.parseDouble(tPretplata.getText()));
            ps.setDouble(3, Double.parseDouble(tPDV.getText()));
            ps.setInt(4, Integer.parseInt(tBesplatniMinutiFiksna.getText()));
            ps.setInt(5, tblPaketi.getSelectionModel().getSelectedItem().getId());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        showData("");

    }

    public void showData(String s) {
        setTable();
    }

    private void setTable() {
        PreparedStatement ps;
        ResultSet rs;
        String query = "SELECT * FROM paketi";
        ArrayList<Paketi> paketiArrayList = new ArrayList<Paketi>();
        Paketi paketi;
        try {
            ps = db.connection.prepareStatement(query);
            rs = ps.executeQuery();
            if (rs.isBeforeFirst()) {
                while (rs.next()) {
                    paketi = new Paketi();
                    paketi.setId(rs.getInt("id"));
                    paketi.setNaziv(rs.getString("naziv"));
                    paketi.setPDV(rs.getDouble("PDV"));
                    paketi.setPretplata(rs.getDouble("pretplata"));
                    paketi.setBesplatniMinutiFiksna(rs.getInt("besplatniMinutiFiksna"));
                    paketiArrayList.add(paketi);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        ObservableList data = FXCollections.observableArrayList(paketiArrayList);
        tblPaketi.setItems(data);

    }

    public void osveziTable(ActionEvent actionEvent) {
        showData("");
    }

    public void deleteData(ActionEvent actionEvent) {
        if (tblPaketi.getSelectionModel().getSelectedIndex() == -1) {
            return;
        }
        PreparedStatement ps;
        try {
            ps = db.connection.prepareStatement("DELETE FROM paketi WHERE id=?");
            ps.setInt(1, tblPaketi.getSelectionModel().getSelectedItem().getId());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        showData("");

    }
}
