package Controllers;

import classes.Database;
import classes.Users;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
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

    public void novKorisnik(ActionEvent actionEvent) {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/FXML/editKorisnik.fxml"), resources);
        try {
            Scene scene = new Scene((Parent) fxmlLoader.load());
            editKorisnik editKorisnikController = fxmlLoader.getController();
            editKorisnikController.db = db;
            Stage stage = new Stage();
            stage.initOwner(bNovKorisnik.getScene().getWindow());
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setTitle("Nov korisnik");
            stage.setScene(scene);
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
            Scene scene = new Scene((Parent) fxmlLoader.load());
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
}
