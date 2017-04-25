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
    public TableColumn cBrojevi;
    public TableColumn cIme;
    public TableColumn cAdresa;
    public TableColumn cMesto;
    public TableColumn cPostBr;
    public TableColumn cBrUgovora;
    public Database db;
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


    }

    public void setData() {
        tblKorisnici.setItems(setTableData(null));
    }

    private ObservableList setTableData(String search) {
        String query = "SELECT * FROM korisnici";
        ArrayList<Users> usersArrayList = new ArrayList<Users>();
        try {
            PreparedStatement ps = db.connection.prepareStatement(query);
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
                    usersArrayList.add(user);
                }
            }
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
        setData();

    }

    public void editKorisnik(ActionEvent actionEvent) {
    }

    public void obrisiKorinisk(ActionEvent actionEvent) {
    }

    public void traziKorisnika(ActionEvent actionEvent) {
    }
}
