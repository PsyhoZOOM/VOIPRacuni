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
import java.util.Optional;
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
    public DatePicker dtpOd;
    public DatePicker dtpDo;
    public TableColumn cStampaChkBox;
    public Database db;
    public CheckBox stampaAll;
    public MenuItem menuStampajSingle;
    public DatePicker dtpRokPlacanja;
    public TableColumn cBrojTelefona;
    ObservableList<Users> data;
    private URL location;
    private ResourceBundle resources;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.location = location;
        this.resources = resources;


        cIme.setCellValueFactory(new PropertyValueFactory<Users, String>("ime"));
        cNazivUsluge.setCellValueFactory(new PropertyValueFactory<Users, String>("nazivUsluge"));
        cBrojTelefona.setCellValueFactory(new PropertyValueFactory<Users, String>("brojTelefona"));


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
                    users.setFirma(rs.getBoolean("firma"));
                    users.setNazivFirme(rs.getString("nazivFirme"));
                    users.setPib(rs.getString("pib"));
                    users.setMbr(rs.getString("mbr"));

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
        if(checkIfRacunExists(false)){
            return;
        }
        String userDIR = System.getProperty("user.home");
        FileChooser fileChooser = new FileChooser();
        fileChooser.setInitialDirectory(new File(userDIR));
        FileChooser.ExtensionFilter extensionFilter = new FileChooser.ExtensionFilter("PDF Files (*.pdf)", "*.pdf");
        fileChooser.getExtensionFilters().addAll(extensionFilter);

        File file = fileChooser.showSaveDialog(bPrint.getScene().getWindow());

        ObservableList<Users> usersArrayList = tblData.getItems();
        if (usersArrayList.size() < 1)
            return;

        Document doc = new Document(new Rectangle(PageSize.A4), 14, 14, 60, 14);
        PdfWriter pdfWriter = null;
        try {
            //USER CANCELED
            if(file.getAbsolutePath() == null)
                return;

            pdfWriter = PdfWriter.getInstance(doc, new FileOutputStream(file.getAbsolutePath()));
        } catch (DocumentException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }


        doc.open();
        for (int i = 0; i < usersArrayList.size(); i++) {
            if (usersArrayList.get(i).isStampa()) {
                System.out.println("FIRMA: "+usersArrayList.get(i).isFirma());
                PrintRacune(usersArrayList.get(i), doc, pdfWriter);


            }
        }

        doc.close();





    }

    public void stampajSingle(ActionEvent actionEvent) {
        if (tblData.getSelectionModel().getSelectedIndex() == -1)
            return;
        if(checkIfRacunExists(true)){
            return;
        }
        String userDIR = System.getProperty("user.home");
        FileChooser fileChooser = new FileChooser();
        fileChooser.setInitialDirectory(new File(userDIR));
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

    private boolean checkIfRacunExists(boolean singlePrint) {
        String datumZaduzenja = String.valueOf(dtpOd.getValue() + " 00:00:00");
        boolean notExist=false;

        PreparedStatement ps;
        ResultSet rs;
        String query;
        if(singlePrint) {
            query = "SELECT datumZaduzenja from uplate WHERE datumZaduzenja=? and userID=?";
        }else{
            query = "SELECT datumZaduzenja FROM uplate WHERE datumZaduzenja=?";
        }
        try {
            ps = db.connection.prepareCall(query);
            ps.setString(1, datumZaduzenja);
            if(singlePrint)
                ps.setInt(2, tblData.getSelectionModel().getSelectedItem().getId());
            rs = ps.executeQuery();
            if(rs.isBeforeFirst()) {
                ButtonType yes = new ButtonType("Da", ButtonBar.ButtonData.YES);
                ButtonType no = new ButtonType("Ne", ButtonBar.ButtonData.NO);
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION,
                        String.format("Računi za %d-%d postoje. " +
                                "Da li želite da izbrišete i ponovo generišete račune?",
                                dtpOd.getValue().getMonthValue(), dtpOd.getValue().getYear()),yes , no );
                alert.setTitle("Duplikovani racuni");
                alert.setHeaderText("Upozorenje");
                Optional<ButtonType> buttonType = alert.showAndWait();
                System.out.println(buttonType);
                if(buttonType.get() == yes ){
                    deleteUplate(dtpOd.getValue() + " 00:00:00",singlePrint);
                    notExist = false;
                }else {
                    notExist = true;
                }

            }
            ps.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return  notExist;
    }

    private void deleteUplate(String datumZaduzenja, boolean singlePrint) {
        PreparedStatement ps;
        String query;
        if(singlePrint) {
            query = "DELETE FROM uplate WHERE datumZaduzenja=? AND userID=?";
        }else{
            query = "DELETE FROM uplate WHERE datumZaduzenja=?";
        }
        try {
            ps = db.connection.prepareCall(query);
            ps.setString(1, datumZaduzenja);
            if(singlePrint)
                ps.setInt(2, tblData.getSelectionModel().getSelectedItem().getId());
            ps.executeUpdate();
            ps.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
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
        String query = "INSERT INTO uplate (ime, brojTel, zaUplatu, uplaceno, zaMesec, datumZaduzenja, userID, " +
                "modelPoziv)" +
                "VALUES  (?,?,?,?,?,?,?,?)";

        try {
            ps = db.connection.prepareStatement(query);
            ps.setString(1, ur.getUser().getIme());
            ps.setString(2, ur.getUser().getBrojTelefona());
            ps.setDouble(3, ur.getPretplata() + ur.getPotrosnja());
            ps.setDouble(4, 0.00);
            ps.setString(5, String.valueOf(LocalDate.parse(ur.getPeriodDo(),
                    DateTimeFormatter.ofPattern("dd.MM.yyyy")).getMonthValue()));
            ps.setString(6, String.valueOf(LocalDate.parse(ur.getPeriodOd(),
                    DateTimeFormatter.ofPattern("dd.MM.yyyy"))
                    .format(DateTimeFormatter.ofPattern("yyyy-MM-dd")))+" 00:00:00");
            ps.setInt(7, ur.getUser().getId());
            ps.setString(8, ur.getUser().getPozivNaBroj());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


}
