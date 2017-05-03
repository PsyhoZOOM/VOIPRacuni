package Controllers;

import classes.Database;
import classes.Users;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Callback;

import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
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
    public TableColumn cStampaChkBox;
    public Database db;
    private URL location;
    private ResourceBundle resources;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.location = location;
        this.resources = resources;

        cStampaChkBox.setCellFactory(new Callback<TableColumn, TableCell>() {
            @Override
            public TableCell call(TableColumn param) {
                TableCell cell = new TableCell<Users, String>() {
                    @Override
                    public void updateItem(String item, boolean bool) {
                        super.updateItem(item, bool);
                        if (bool) {
                            setText(item);
                            new CheckBox("Štampaj");
                        } else {
                            setText(null);
                        }
                    }
                };
                return cell;
            }
        });

        cStampaChkBox.setCellValueFactory(new PropertyValueFactory<Users, Boolean>("stampa"));
        cIme.setCellValueFactory(new PropertyValueFactory<Users, String>("ime"));
        cNazivUsluge.setCellFactory(new PropertyValueFactory<Users, String>("nazivUsluge"));
        cDatum.setCellValueFactory(new PropertyValueFactory<Users, String>("mesec"));


    }


    private void setData() {
        PreparedStatement ps;
        ResultSet rs;
        String query = "SELECT * FROM korisnici";
        Users users;
        ArrayList<Users> usresArrayList = new ArrayList<Users>();


        try {
            ps = db.connection.prepareStatement(query);
            rs = ps.executeQuery();
            if (rs.isBeforeFirst()) {
                while (rs.next()) {
                    users = new Users();
                    users.setId(rs.getInt("id"));
                    users.setIme(rs.getString("imePrezime"));
                    users.setAdresa(rs.getString("adresa"));
                    users.setMesto(rs.getString("mesto"));
                    users.setPostBr(rs.getString("postBr"));
                    users.setBrUgovora(rs.getString("brUgovora"));
                    users.setCustomerId(rs.getString("customerID"));
                    users.setPozivNaBroj(rs.getString("pozivNaBroj"));
                    users.setUserPaketID(getUserPaket(rs.getInt("id")));
                    usresArrayList.add(users);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        ObservableList data = FXCollections.observableArrayList(usresArrayList);
        tblData.setItems(data);
    }


    private int getUserPaket(int idPaket) {
        PreparedStatement ps;
        ResultSet rs;
        String query = "SELECT * FROM paketi WHERE id=?";
        int paketID = 0;

        try {
            ps = db.connection.prepareStatement(query);
            ps.setInt(1, idPaket);
            rs = ps.executeQuery();
            if (rs.isBeforeFirst()) {
                rs.next();
                paketID = rs.getInt("id");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return paketID;
    }


    private String getPaketName(int idPaket) {
        PreparedStatement ps;
        ResultSet rs;
        String query = "SELECT * FROM paketi WHER id=?";
        String paketName = null;

        try {
            ps = db.connection.prepareStatement(query);
            ps.setInt(1, idPaket);
            rs = ps.executeQuery();
            if (rs.isBeforeFirst()) {
                rs.next();
                paketName = rs.getString("naziv");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return paketName;
    }


    public void showForPrint(ActionEvent actionEvent) {
    }

    public void printData(ActionEvent actionEvent) {
    }
}
