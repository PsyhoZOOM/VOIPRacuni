package Controllers;

import classes.*;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfWriter;
import javafx.beans.property.SimpleObjectProperty;
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

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
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
    public DatePicker dtpRokPlacanja;
    ObservableList<Users> data;
    private URL location;
    private ResourceBundle resources;


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


        dtpOd.setValue(LocalDate.of(LocalDate.now().getYear(), LocalDate.now().getMonth().minus(1), 1));
        dtpDo.setValue(LocalDate.of(LocalDate.now().getYear(), LocalDate.now().getMonth().minus(1), 25));
        dtpRokPlacanja.setValue(LocalDate.of(LocalDate.now().getYear(), LocalDate.now().getMonth(), 28));


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
        FileChooser fileChooser = new FileChooser();
        FileChooser.ExtensionFilter extensionFilter = new FileChooser.ExtensionFilter("PDF Files (*.pdf)", "*.pdf");
        fileChooser.getExtensionFilters().addAll(extensionFilter);

        File file = fileChooser.showSaveDialog(bPrint.getScene().getWindow());

        ObservableList<Users> usersArrayList = tblData.getItems();
        if (usersArrayList.size() < 1)
            return;

        Document doc = new Document(new Rectangle(PageSize.A4), 14, 14, 60, 14);
        PdfWriter pdfWriter = null;
        try {
            pdfWriter = PdfWriter.getInstance(doc, new FileOutputStream(file.getAbsolutePath()));
        } catch (DocumentException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }


        doc.open();
        for (int i = 0; i < usersArrayList.size(); i++) {
            if (usersArrayList.get(i).isStampa()) {
                PrintRacune(usersArrayList.get(i), doc, pdfWriter);


            }
        }

        doc.close();





    }

    public void stampajSingle(ActionEvent actionEvent) {
        if (tblData.getSelectionModel().getSelectedIndex() == -1)
            return;
        FileChooser fileChooser = new FileChooser();
        FileChooser.ExtensionFilter extensionFilter = new FileChooser.ExtensionFilter("PDF Files (*.pdf)", "*.pdf");
        fileChooser.getExtensionFilters().addAll(extensionFilter);

        File file = fileChooser.showSaveDialog(bPrint.getScene().getWindow());

        Document doc = new Document(new Rectangle(PageSize.A4), 14, 14, 60, 14);
        PdfWriter pdfWriter = null;
        try {
            pdfWriter = PdfWriter.getInstance(doc, new FileOutputStream(file.getAbsolutePath()));
        } catch (DocumentException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        Users user = tblData.getSelectionModel().getSelectedItem();
        if (user.isStampa() == false)
            return;

        doc.open();

        PrintRacune(user, doc, pdfWriter);
        doc.close();

    }


    private void PrintRacune(Users user, Document doc, PdfWriter pdfWriter) {

        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");


        String starDate = dtpOd.getValue().format(timeFormatter);
        String stopDate = dtpDo.getValue().format(timeFormatter);
        String rokPlacanja = dtpRokPlacanja.getValue().format(timeFormatter);


        userRacun ur = new userRacun(db, starDate, stopDate, rokPlacanja, user);
        PrintPage printPage = new PrintPage(ur, doc, pdfWriter);

        zaduziKorisnika(ur);


    }

    private void zaduziKorisnika(userRacun ur) {
        PreparedStatement ps;
        String query = "INSERT INTO uplate (ime, brojTel, zaUplatu, uplaceno, zaMesec, userID, modelPoziv, datumUplate)" +
                "VALUES  (?,?,?,?,?,?,?,?)";

        try {
            ps = db.connection.prepareStatement(query);
            ps.setString(1, ur.getUser().getIme());
            ps.setString(2, ur.getUser().getBrojTelefona());
            ps.setDouble(3, ur.getPretplata() + ur.getPotrosnja());
            ps.setDouble(4, 0.00);
            ps.setString(5, String.valueOf(LocalDate.parse(ur.getPeriodDo(), DateTimeFormatter.ofPattern("dd.MM.yyyy")).getMonthValue()));
            ps.setInt(6, ur.getUser().getId());
            ps.setString(7, ur.getUser().getPozivNaBroj());
            ps.setString(8, LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


}
