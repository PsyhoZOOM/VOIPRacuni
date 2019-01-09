package net.yuvideo.voipRacuni.Controllers;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfWriter;
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
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.FileChooser;
import javafx.util.Callback;
import javafx.util.StringConverter;
import net.yuvideo.voipRacuni.classes.Database;
import net.yuvideo.voipRacuni.classes.Paketi;
import net.yuvideo.voipRacuni.classes.PrintPage;
import net.yuvideo.voipRacuni.classes.Users;
import net.yuvideo.voipRacuni.classes.userRacun;

/**
 * Created by PsyhoZOOM@gmail.com on 4/21/17.
 */
public class stampaRacuna implements Initializable {

  public Button bPrikazi;
  public Button bPrint;
  public TableView<Users> tblData;
  public TableColumn cIme;
  public TableColumn cNazivUsluge;
  public DatePicker dtpZaMesec;
  public TableColumn cStampaChkBox;
  public Database db;
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

    cStampaChkBox.setCellValueFactory(
        new Callback<TableColumn.CellDataFeatures<Users, CheckBox>, ObservableValue<CheckBox>>() {
          @Override
          public ObservableValue<CheckBox> call(
              TableColumn.CellDataFeatures<Users, CheckBox> param) {
            Users user = param.getValue();
            CheckBox checkBox = new CheckBox();
            checkBox.setSelected(user.isStampa());
            checkBox.selectedProperty().addListener(new ChangeListener<Boolean>() {
              @Override
              public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue,
                  Boolean newValue) {
                user.setStampa(newValue);
              }
            });
            return new SimpleObjectProperty<CheckBox>(checkBox);
          }
        });

    dtpZaMesec.setConverter(new StringConverter<LocalDate>() {
      private DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");

      @Override
      public String toString(LocalDate object) {
        if (object == null) {
          return null;
        }
        return dateTimeFormatter.format(object);
      }

      @Override
      public LocalDate fromString(String string) {
        if (string == null || string.trim().isEmpty()) {
          return null;
        } else {
          return LocalDate.parse(string, dateTimeFormatter);
        }
      }
    });

    dtpRokPlacanja.setConverter(new StringConverter<LocalDate>() {
      private DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");

      @Override
      public String toString(LocalDate object) {
        if (object == null) {
          return "";
        }
        return object.format(dateTimeFormatter);
      }

      @Override
      public LocalDate fromString(String string) {
        if (string == null || string.trim().isEmpty()) {
          return null;
        }
        return LocalDate.parse(string, dateTimeFormatter);
      }
    });

    dtpZaMesec.setValue(LocalDate.now());
    dtpRokPlacanja
        .setValue(LocalDate.of(LocalDate.now().getYear(), LocalDate.now().getMonth(), 28));
  }


  private void setData() {
    PreparedStatement ps;
    ResultSet rs;
    LocalDate zaM = dtpZaMesec.getValue();
    String query = "SELECT * FROM korisnici WHERE datumPrikljucka <= ?";
    Users users;
    ArrayList<Users> usresArrayList = new ArrayList<Users>();

    try {
      ps = db.connection.prepareStatement(query);
      ps.setString(1, zaM.format(DateTimeFormatter.ofPattern("yyyy-MM")) + "-31");
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
          users.setDatumPrikljucka(rs.getString("datumPrikljucka"));

          usresArrayList.add(users);
        }
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }

    data = FXCollections.observableArrayList(usresArrayList);
    tblData.setItems(data);
  }


  public Paketi getUserPaket(int idPaket) {
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


  public String getPaketName(int idPaket) {
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
    setData();
  }

  public void printData(ActionEvent actionEvent) {
    String userDIR = System.getProperty("user.home");
    FileChooser fileChooser = new FileChooser();
    fileChooser.setInitialDirectory(new File(userDIR));
    FileChooser.ExtensionFilter extensionFilter = new FileChooser.ExtensionFilter(
        "PDF Files (*.pdf)", "*.pdf");
    fileChooser.getExtensionFilters().addAll(extensionFilter);
    fileChooser.setInitialFileName(
        "RacuniFiksnaTelefonija-" + LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM"))
            + ".pdf");

    File file = fileChooser.showSaveDialog(bPrint.getScene().getWindow());

    ObservableList<Users> usersArrayList = tblData.getItems();
    if (usersArrayList.size() < 1) {
      return;
    }

    Document doc = new Document(new Rectangle(PageSize.A4), 14, 14, 60, 14);
    PdfWriter pdfWriter = null;
    try {
      //USER CANCELED
      if (file.getAbsolutePath() == null) {
        return;
      }

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

    Alert alert = new Alert(Alert.AlertType.INFORMATION, "Štampa završena!");


  }

  public void stampajSingle(ActionEvent actionEvent) {
    if (tblData.getSelectionModel().getSelectedIndex() == -1) {
      return;
    }
    String userDIR = System.getProperty("user.home");
    FileChooser fileChooser = new FileChooser();
    fileChooser.setInitialDirectory(new File(userDIR));
    FileChooser.ExtensionFilter extensionFilter = new FileChooser.ExtensionFilter(
        "PDF Files (*.pdf)", "*.pdf");
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
    if (user.isStampa() == false) {
      return;
    }

    doc.open();

    PrintRacune(user, doc, pdfWriter);
    doc.close();

  }


  private void PrintRacune(Users user, Document doc, PdfWriter pdfWriter) {
    DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    String rokPlacanja = dtpRokPlacanja.getValue().format(timeFormatter);
    String zaMesec = dtpZaMesec.getValue().format(DateTimeFormatter.ofPattern("yyyy-MM"));

    userRacun ur = new userRacun(db, zaMesec, rokPlacanja, user);

    PrintPage printPage = new PrintPage(ur, doc, pdfWriter);


  }


}
