package Controllers;

import classes.Database;
import classes.Paketi;
import classes.Users;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.util.Callback;
import javafx.util.StringConverter;

import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
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
    public TextField tCustomerID;
    public TextField tPozivNaBroj;
    public TextField tBrojTelefona;
    public ComboBox<Paketi> cmbPaket;
    public CheckBox chkStampa;
    public CheckBox cmbKoristiFirmu;
    public TextField tNazivFirme;
    public TextField tPIB;
    public TextField tMBR;
    public DatePicker dtpDatumPrikljucka;
    public Database db;
    public boolean editUser = false;
    public Users user;
    private URL location;
    private ResourceBundle resources;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.location = location;
        this.resources = resources;

        dtpDatumPrikljucka.setConverter(new StringConverter<LocalDate>() {
            @Override
            public String toString(LocalDate object) {
                return object.format(DateTimeFormatter.ofPattern("dd-MM-yyyy"));
            }

            @Override
            public LocalDate fromString(String string) {
                String date = LocalDate.parse(string).format(DateTimeFormatter.ofPattern("dd-MM-yyyy"));

                return LocalDate.parse(date);
            }
        });

        bClose.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Stage stage = (Stage) bClose.getScene().getWindow();
                stage.close();
            }
        });


        cmbPaket.setCellFactory(new Callback<ListView<Paketi>, ListCell<Paketi>>() {
            @Override
            public ListCell<Paketi> call(ListView<Paketi> param) {
                ListCell<Paketi> cell = new ListCell<Paketi>() {
                    @Override
                    protected void updateItem(Paketi item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            setText(null);
                        } else {
                            setText(item.getNaziv());

                        }
                    }
                };
                return cell;
            }
        });

        cmbPaket.setConverter(new StringConverter<Paketi>() {
            @Override
            public String toString(Paketi object) {
                return object.getNaziv();
            }

            @Override
            public Paketi fromString(String string) {
                return null;
            }
        });
    }

    public void snimiKorisnika(ActionEvent actionEvent) {
        if (editUser) {
            updateUser();
        } else {
            saveUser();
        }

        Stage window = (Stage) bClose.getScene().getWindow();
        window.close();

    }

    private void saveUser() {

        PreparedStatement ps;
        String query = "INSERT INTO korisnici (imePrezime, adresa, mesto, postbr, brUgovora, customerID, pozivNaBroj, " +
                "brojTelefona, paketID, stampa, firma, mbr, pib, nazivFirme, datumPrikljucka) " +
                "VALUES " +
                "(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
        try {
            ps = db.connection.prepareStatement(query);
            ps.setString(1, tImePrezime.getText());
            ps.setString(2, tAdresa.getText());
            ps.setString(3, tMesto.getText());
            ps.setString(4, tPostBr.getText());
            ps.setString(5, tbrUgovora.getText());
            ps.setString(6, tCustomerID.getText());
            ps.setString(7, tPozivNaBroj.getText());
            ps.setString(8, tBrojTelefona.getText());
            ps.setInt(9, cmbPaket.getValue().getId());
            ps.setBoolean(10, chkStampa.isSelected());
            ps.setBoolean(11, cmbKoristiFirmu.isSelected());
            ps.setString(12, tMBR.getText());
            ps.setString(13, tPIB.getText());
            ps.setString(14, tNazivFirme.getText());
            ps.setString(15, dtpDatumPrikljucka.getValue().toString());
            ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void updateUser() {

        PreparedStatement ps;
        String query = "UPDATE korisnici SET imePrezime=?, adresa=?, mesto=?, postbr=?, brUgovora=?, customerID=?, " +
                "pozivNaBroj=?, brojTelefona=?, paketID=?, stampa=?, firma=?, mbr=?,  pib=?, nazivFirme=?, datumPrikljucka=? WHERE id=? ";
        try {
            ps = db.connection.prepareStatement(query);
            ps.setString(1, tImePrezime.getText());
            ps.setString(2, tAdresa.getText());
            ps.setString(3, tMesto.getText());
            ps.setString(4, tPostBr.getText());
            ps.setString(5, tbrUgovora.getText());
            ps.setString(6, tCustomerID.getText());
            ps.setString(7, tPozivNaBroj.getText());
            ps.setString(8, tBrojTelefona.getText());
            ps.setInt(9, cmbPaket.getValue().getId());
            ps.setBoolean(10, chkStampa.isSelected());
            ps.setBoolean(11, cmbKoristiFirmu.isSelected());
            ps.setString(12, tMBR.getText());
            ps.setString(13, tPIB.getText());
            ps.setString(14, tNazivFirme.getText());
            ps.setString(15, dtpDatumPrikljucka.getValue().toString());
            ps.setInt(16, user.getId());
            ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void setData() {
        if(!editUser){
            setPaketiData();
            return;
        }

        bSnimi.setText("Izmeni");
        tImePrezime.setText(user.getIme());
        tAdresa.setText(user.getAdresa());
        tMesto.setText(user.getMesto());
        tPostBr.setText(user.getPostBr());
        tbrUgovora.setText(user.getBrUgovora());
        tCustomerID.setText(user.getCustomerId());
        tPozivNaBroj.setText(user.getPozivNaBroj());
        tBrojTelefona.setText(user.getBrojTelefona());
        chkStampa.setSelected(user.isStampa());
        cmbKoristiFirmu.setSelected(user.isFirma());
        tMBR.setText(user.getMbr());
        tPIB.setText(user.getPib());
        tNazivFirme.setText(user.getNazivFirme());
        dtpDatumPrikljucka.setValue(LocalDate.parse(user.getDatumPrikljucka(), DateTimeFormatter.ofPattern("yyyy-MM-dd")));

        setPaketiData();
    }

    private void setPaketiData() {
        PreparedStatement ps;
        ResultSet rs;
        String query = "SELECT * FROM paketi";
        Paketi paketi;
        ArrayList<Paketi> paketiArrayList = new ArrayList<Paketi>();

        try {
            ps = db.connection.prepareStatement(query);
            rs = ps.executeQuery();
            if (rs.isBeforeFirst()) {
                while (rs.next()) {
                    paketi = new Paketi();
                    paketi.setId(rs.getInt("id"));
                    paketi.setPretplata(rs.getDouble("pretplata"));
                    paketi.setNaziv(rs.getString("naziv"));
                    paketi.setPDV(rs.getDouble("PDV"));
                    paketiArrayList.add(paketi);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        ObservableList data = FXCollections.observableArrayList(paketiArrayList);
        cmbPaket.setItems(data);

        if (editUser) {
            cmbPaket.getSelectionModel().selectFirst();
            ObservableList<Paketi> items = cmbPaket.getItems();
            for (int i = 0; i < items.size(); i++) {
                if (items.get(i).getId() == user.getNazivPaketaID()) {
                    cmbPaket.setValue(items.get(i));
                }
            }
        }
    }
}
