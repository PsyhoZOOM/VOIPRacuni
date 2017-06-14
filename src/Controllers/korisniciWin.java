package Controllers;

import classes.Database;
import classes.Users;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
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
public class korisniciWin implements Initializable {
    public Button bNovKorisnik;
    public Button bEditKorisnik;
    public Button bObrisiKorisnik;
    public TableView<Users> tblKorisnici;
    public TableColumn cCustomerID;
    public TableColumn cIme;
    public TableColumn cAdresa;
    public TableColumn cMesto;
    public TableColumn cPostBr;
    public TableColumn cBrUgovora;
    public Database db;
    public Button bTrazi;
    public TextField tTrazi;
    public TableColumn cPozivNaBroj;
    public TableColumn cBrojTelefona;
    public TableColumn cPaket;
    public Button bUplate;
    public TableColumn cFirma;
    URL location;
    ResourceBundle resources;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.location = location;
        this.resources = resources;
        cIme.setCellValueFactory(new PropertyValueFactory<Users, String>("ime"));
        cAdresa.setCellValueFactory(new PropertyValueFactory<Users, String>("adresa"));
        cMesto.setCellValueFactory(new PropertyValueFactory<Users, String>("mesto"));
        cPostBr.setCellValueFactory(new PropertyValueFactory<Users, String>("postBr"));
        cBrUgovora.setCellValueFactory(new PropertyValueFactory<Users, String>("brUgovora"));
        cCustomerID.setCellValueFactory(new PropertyValueFactory<Users, String>("customerId"));
        cPozivNaBroj.setCellValueFactory(new PropertyValueFactory<Users, String>("pozivNaBroj"));
        cPaket.setCellValueFactory(new PropertyValueFactory<Users, String>("nazivUsluge"));
        cBrojTelefona.setCellValueFactory(new PropertyValueFactory<Users, String>("brojTelefona"));
        cFirma.setCellValueFactory(new PropertyValueFactory<Users, String>("nazivFirme"));



       tblKorisnici.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                if (event.getCode().equals(KeyCode.ENTER)) {
                    showUplate();
                }
            }
        });

        tblKorisnici.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if (event.getClickCount() == 2) {
                    showUplate();
                }
            }
        });

    }

    private void showUplate() {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/FXML/KorisnikUplate.fxml"), resources);
        try {
            Scene scene = new Scene(fxmlLoader.load());
            korisnikUplate korisnikUplateController = fxmlLoader.getController();
            korisnikUplateController.db = this.db;
            korisnikUplateController.user = tblKorisnici.getSelectionModel().getSelectedItem();
            korisnikUplateController.setData();
            Stage stage = new Stage();
            stage.initOwner(bUplate.getScene().getWindow());
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setTitle("Uplate korisnika: " + tblKorisnici.getSelectionModel().getSelectedItem().getIme());
            stage.setScene(scene);
            stage.showAndWait();


        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void setData(String search) {
        tblKorisnici.setItems(setTableData(search));
    }

    private ObservableList setTableData(String search) {
        PreparedStatement ps = null;

        String query = "SELECT * FROM korisnici WHERE imePrezime LIKE  ? OR adresa LIKE ? OR mesto LIKE ? OR postbr LIKE ? or brUgovora LIKE  ? OR customerID LIKE ?";
        ArrayList<Users> usersArrayList = new ArrayList<Users>();
        try {
            ps = db.connection.prepareStatement(query);
            ps.setString(1, search + "%");
            ps.setString(2, search + "%");
            ps.setString(3, search + "%");
            ps.setString(4, search + "%");
            ps.setString(5, search + "%");
            ps.setString(6, search + "%");

            ResultSet rs = ps.executeQuery();

            if (rs.isBeforeFirst()) {
                Users user;
                while (rs.next()) {
                    user = new Users();
                    user.setId(rs.getInt("id"));
                    user.setIme(rs.getString("imePrezime"));
                    user.setAdresa(rs.getString("adresa"));
                    user.setMesto(rs.getString("mesto"));
                    user.setPostBr(rs.getString("postbr"));
                    user.setBrUgovora(rs.getString("brUgovora"));
                    user.setCustomerId(rs.getString("customerID"));
                    user.setPozivNaBroj(rs.getString("pozivNaBroj"));
                    user.setNazivPaketaID(rs.getInt("paketID"));
                    user.setNazivUsluge(getNazivPaketa(rs.getInt("paketID")));
                    user.setBrojTelefona(rs.getString("brojTelefona"));
                    user.setStampa(rs.getBoolean("stampa"));
                    user.setMbr(rs.getString("mbr"));
                    user.setPib(rs.getString("pib"));
                    user.setFirma(rs.getBoolean("firma"));
                    user.setNazivFirme(rs.getString("nazivFirme"));
                    user.setDatumPrikljucka(rs.getString("datumPrikljucka"));
                    usersArrayList.add(user);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }


        try {
            ps.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        ObservableList userObservable = FXCollections.observableArrayList(usersArrayList);
        return userObservable;


    }

    private String getNazivPaketa(int id) {
        PreparedStatement ps;
        ResultSet rs;
        String query = "SELECT * FROM  paketi WHERE id=?";
        String paket = "";

        try {
            ps = db.connection.prepareStatement(query);
            ps.setInt(1, id);
            rs = ps.executeQuery();
            if (rs.isBeforeFirst()) {
                rs.next();
                paket = rs.getString("naziv");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return paket;

    }

    public void novKorisnik(ActionEvent actionEvent) {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/FXML/editKorisnik.fxml"), resources);
        try {
            Scene scene = new Scene(fxmlLoader.load());
            editKorisnik editKorisnikController = fxmlLoader.getController();
            editKorisnikController.db = db;
            Stage stage = new Stage();
            stage.initOwner(bNovKorisnik.getScene().getWindow());
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setTitle("Nov korisnik");
            stage.setScene(scene);
            editKorisnikController.setData();

            stage.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }
        setData("");

    }

    public void editKorisnik(ActionEvent actionEvent) {
        if (tblKorisnici.getSelectionModel().getSelectedIndex() == -1) {
            return;
        }

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/FXML/editKorisnik.fxml"), resources);
        try {
            Scene scene = new Scene(fxmlLoader.load());
            editKorisnik editKorisnikController = fxmlLoader.getController();
            editKorisnikController.db = db;
            editKorisnikController.editUser = true;
            editKorisnikController.user = tblKorisnici.getSelectionModel().getSelectedItem();
            editKorisnikController.setData();

            Stage stage = new Stage();
            stage.initOwner(bEditKorisnik.getScene().getWindow());
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setTitle("Izmena korisnika " + tblKorisnici.getSelectionModel().getSelectedItem().getIme() + " " + tblKorisnici.getSelectionModel().getSelectedItem().getCustomerId());
            stage.setScene(scene);
            stage.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }
        setData("");

    }

    public void obrisiKorinisk(ActionEvent actionEvent) {
    }

    public void traziKorisnika(ActionEvent actionEvent) {
        setData(tTrazi.getText());
    }

    public void bshowUplate(ActionEvent actionEvent) {
        showUplate();
    }
}
