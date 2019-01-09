package net.yuvideo.voipRacuni.Controllers;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.DatePicker;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import net.yuvideo.voipRacuni.classes.Database;
import net.yuvideo.voipRacuni.classes.Users;
import net.yuvideo.voipRacuni.classes.userRacun;
import net.yuvideo.voipRacuni.classes.valueToPercent;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;


public class ExcelExportController implements Initializable {

  public DatePicker dtpDatum;
  private Database db;
  private DecimalFormat df = new DecimalFormat("###,###,##0.00");

  @Override
  public void initialize(URL location, ResourceBundle resources) {

  }

  public void exportCSV(ActionEvent actionEvent) {

    ArrayList<Users> usersArrayList = getAllUsers();
    ArrayList<userRacun> userRacunArrayList = new ArrayList<>();

    String date = dtpDatum.getValue().format(DateTimeFormatter.ofPattern("yyyy-MM"));
    String rok = dtpDatum.getValue().plusMonths(1)
        .format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
    for (Users user : usersArrayList) {
      userRacun userRacun = new userRacun(db, date, rok, user);
      userRacun.setZaUplatu(getZaduzenje(user.getId(), date));
      userRacunArrayList.add(userRacun);
    }

    setXLSX(userRacunArrayList);

  }

  private Double getZaduzenje(int id, String date) {
    double zaUplatu = 0.00;
    PreparedStatement ps;
    ResultSet rs;
    String query = "SELECT SUM(zaUplatu) AS zaUplatu from  zaduzenja WHERE userID=? AND zaMesec=? ";
    try {
      ps = db.connection.prepareStatement(query);
      ps.setInt(1, id);
      ps.setString(2, date);
      rs = ps.executeQuery();
      if (rs.isBeforeFirst()) {
        while (rs.next()) {
          Double upl = valueToPercent.getValue(rs.getDouble("zaUplatu"), 20);
          zaUplatu = upl + rs.getDouble("zaUplatu");
        }
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return zaUplatu;
  }

  private void setXLSX(ArrayList<userRacun> userRacunArrayList) {
    String[] columns = {"BROJ", "NAZIV", "ADRESA", "DUGOVANJE"};
    Workbook workbook = new XSSFWorkbook();
    CreationHelper creationHelper = workbook.getCreationHelper();
    Sheet sheet = workbook.createSheet(String
        .format("TELEFONIJA %s ", LocalDate.now().format(DateTimeFormatter.ofPattern("MM-yyyy"))));
    Font headerFont = workbook.createFont();
    headerFont.setBold(true);
    headerFont.setFontHeightInPoints((short) 14);
    headerFont.setColor(IndexedColors.BLACK.getIndex());
    CellStyle headerStyle = workbook.createCellStyle();
    headerStyle.setFont(headerFont);

    Row headerRow = sheet.createRow(0);
    for (int i = 0; i < columns.length; i++) {
      Cell cell = headerRow.createCell(i);
      cell.setCellValue(columns[i]);
      cell.setCellStyle(headerStyle);
    }

    int rowNum = 1;
    for (userRacun ur : userRacunArrayList) {
      Row row = sheet.createRow(rowNum++);
      row.createCell(0).setCellValue(ur.getUser().getBrojTelefona());
      if (ur.getUser().isFirma()) {
        row.createCell(1).setCellValue(ur.getUser().getNazivFirme());
      } else {
        row.createCell(1).setCellValue(ur.getUser().getIme());
      }
      String adresa = String.format("%s %s", ur.getUser().getAdresa(), ur.getUser().getMesto());
      row.createCell(2).setCellValue(adresa);
      String dug = df.format(ur.getPotrosnja());
      row.createCell(3).setCellValue(df.format(ur.getZaUplatu()));
    }

    for (int i = 0; i < columns.length; i++) {
      sheet.autoSizeColumn(i);
    }

    ExtensionFilter extensionFilter = new ExtensionFilter("xlsx", ".xlsx");
    FileChooser fileChooser = new FileChooser();
    fileChooser.setTitle("EXPORT xlsx");
    fileChooser.setSelectedExtensionFilter(extensionFilter);
    File file = fileChooser.showSaveDialog(dtpDatum.getScene().getWindow());
    try {
      FileOutputStream fileOutputStream = new FileOutputStream(file);
      workbook.write(fileOutputStream);
      fileOutputStream.close();
      workbook.close();
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    }


  }

  private ArrayList<Users> getAllUsers() {
    ArrayList<Users> usersArrayList = new ArrayList<>();
    PreparedStatement ps;
    ResultSet rs;
    String query = "SELECT * FROM korisnici";

    try {
      ps = db.connection.prepareStatement(query);
      rs = ps.executeQuery();
      if (rs.isBeforeFirst()) {
        stampaRacuna stampaRacuna = new stampaRacuna();
        stampaRacuna.db = this.db;
        while (rs.next()) {
          Users users = new Users();
          users.setId(rs.getInt("id"));
          users.setIme(rs.getString("imePrezime"));
          users.setAdresa(rs.getString("adresa"));
          users.setMesto(rs.getString("mesto"));
          users.setPostBr(rs.getString("postBr"));
          users.setBrUgovora(rs.getString("brUgovora"));
          users.setCustomerId(rs.getString("customerID"));
          users.setPozivNaBroj(rs.getString("pozivNaBroj"));
          users.setNazivPaketaID(stampaRacuna.getUserPaket(rs.getInt("paketID")).getId());
          users.setNazivUsluge(stampaRacuna.getPaketName(rs.getInt("paketID")));
          users.setStampa(rs.getBoolean("stampa"));
          users.setBrojTelefona(rs.getString("brojTelefona"));
          users.setFirma(rs.getBoolean("firma"));
          users.setNazivFirme(rs.getString("nazivFirme"));
          users.setPib(rs.getString("pib"));
          users.setMbr(rs.getString("mbr"));
          users.setDatumPrikljucka(rs.getString("datumPrikljucka"));

          usersArrayList.add(users);
        }
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return usersArrayList;
  }

  public void setDb(Database db) {
    this.db = db;
  }
}
