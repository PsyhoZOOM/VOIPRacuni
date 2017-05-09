package Controllers;

import classes.*;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
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
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.ResourceBundle;

/**
 * Created by PsyhoZOOM@gmail.com on 4/21/17.
 */
public class stampaRacuna implements Initializable {
    public Button bPrikazi;
    public Button bPrint;
    public TableView<Users> tblData;
    public TableColumn cIme;
    public TableColumn cNazivUsluge;
    public TableColumn cDatum;
    public DatePicker dtpOd;
    public DatePicker dtpDo;
    public TableColumn cStampaChkBox;
    public Database db;
    public CheckBox stampaAll;
    public MenuItem menuStampajSingle;
    ObservableList<Users> data;
    private URL location;
    private ResourceBundle resources;
    private Calendar calStart = Calendar.getInstance();
    private Calendar calEnd = Calendar.getInstance();
    private LocalDate localDateStart;
    private LocalDate localDateStop;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.location = location;
        this.resources = resources;


        cIme.setCellValueFactory(new PropertyValueFactory<Users, String>("ime"));
        cNazivUsluge.setCellValueFactory(new PropertyValueFactory<Users, String>("nazivUsluge"));
        cDatum.setCellValueFactory(new PropertyValueFactory<Users, String>("mesec"));


        cStampaChkBox.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Users, CheckBox>, ObservableValue<CheckBox>>() {
            @Override
            public ObservableValue<CheckBox> call(TableColumn.CellDataFeatures<Users, CheckBox> param) {
                Users user = param.getValue();
                CheckBox checkBox = new CheckBox();
                checkBox.setSelected(user.isStampa());
                checkBox.selectedProperty().addListener(new ChangeListener<Boolean>() {
                    @Override
                    public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                        user.setStampa(newValue);
                    }
                });
                return new SimpleObjectProperty<CheckBox>(checkBox);
            }
        });


        dtpOd.setValue(LocalDate.now());
        dtpDo.setValue(LocalDate.now().plusMonths(1));


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
                    users.setNazivPaketaID(getUserPaket(rs.getInt("paketID")).getId());
                    users.setNazivUsluge(getPaketName(rs.getInt("paketID")));
                    users.setStampa(rs.getBoolean("stampa"));
                    users.setBrojTelefona(rs.getString("brojTelefona"));
                    usresArrayList.add(users);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        data = FXCollections.observableArrayList(usresArrayList);
        tblData.setItems(data);
    }


    private Paketi getUserPaket(int idPaket) {
        PreparedStatement ps;
        ResultSet rs;
        String query = "SELECT * FROM paketi WHERE id=?";
        Paketi paket = null;

        try {
            ps = db.connection.prepareStatement(query);
            ps.setInt(1, idPaket);
            rs = ps.executeQuery();
            if (rs.isBeforeFirst()) {
                paket = new Paketi();
                rs.next();
                paket.setId(rs.getInt("id"));
                paket.setNaziv(rs.getString("naziv"));
                paket.setPretplata(rs.getDouble("pretplata"));
                paket.setPDV(rs.getDouble("PDV"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return paket;
    }


    private String getPaketName(int idPaket) {
        PreparedStatement ps;
        ResultSet rs;
        String query = "SELECT * FROM paketi WHERE id=?";
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
        LocalDate start = dtpOd.getValue();
        LocalDate stop = dtpDo.getValue();


        setData();


    }

    public void printData(ActionEvent actionEvent) {
        ObservableList<Users> usersArrayList = tblData.getItems();
        for (int i = 0; i < usersArrayList.size(); i++) {
            System.out.println(usersArrayList.get(i).isStampa());
        }
    }

    public void stampajSingle(ActionEvent actionEvent) {
        if (tblData.getSelectionModel().getSelectedIndex() == -1)
            return;

        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        Users user = tblData.getSelectionModel().getSelectedItem();
        String starDate = dtpOd.getValue().format(timeFormatter);
        String stopDate = dtpDo.getValue().format(timeFormatter);


        ArrayList<CSVData> csvDataArrayList = getCSVUserData.getData(user.getId(), user.getBrojTelefona(), starDate, stopDate, db);
        PrintPage printPage = new PrintPage(user, csvDataArrayList, starDate, stopDate, getUserPaket(user.getNazivPaketaID()));


    }


}
