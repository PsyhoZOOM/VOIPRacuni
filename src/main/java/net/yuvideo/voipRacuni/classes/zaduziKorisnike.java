package net.yuvideo.voipRacuni.classes;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import javafx.scene.control.Alert;

/**
 * Created by PsyhoZOOM on 6/8/17.
 */
public class zaduziKorisnike {

  private final Database db;
  private final String mesecZaduzenja;
  public boolean done = false;
  FIXX fixx;
  DateTimeFormatter fullDateTimeFormater = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
  DateTimeFormatter dateDateTimeFormater = DateTimeFormatter.ofPattern("yyyy-MM-dd");
  DateTimeFormatter monthDateTimeFormater = DateTimeFormatter.ofPattern("yyyy-MM");


  public zaduziKorisnike(ArrayList<Users> usersArrayList, String mesecZaduzenja, Database db) {
    this.db = db;
    fixx = new FIXX(this.db);
    this.mesecZaduzenja = mesecZaduzenja;
    for (Users user : usersArrayList) {
      LocalDate userPrikljucak = LocalDate.parse(user.getDatumPrikljucka(), dateDateTimeFormater);
      LocalDate dateZad = LocalDate.parse(mesecZaduzenja, dateDateTimeFormater);

      ////TO-DO
      //postaviti zadnji dan u mesecu zbog zaduzenja korisnika
      if (userPrikljucak.isAfter(dateZad)) {
        continue;
      }
      //zaduziSingleUserSaobracaj(user);
      zaduziKorisnikaSaobracaj(user);
      zaduziSingleUserPaket(user);
    }


  }

  private void zaduziSingleUserPaket(Users user) {
    PreparedStatement ps;
    ResultSet rs;
    String query;
    String mesecKreiranja = LocalDate.parse(user.getDatumPrikljucka(),
        dateDateTimeFormater).format(dateDateTimeFormater);

    query = "SELECT paketID from korisnici WHERE id=?";
    int paketID = 0;

    try {
      ps = db.connection.prepareStatement(query);
      ps.setInt(1, user.getId());
      rs = ps.executeQuery();
      if (rs.isBeforeFirst()) {
        rs.next();
        paketID = rs.getInt("paketID");
      }
      ps.close();
      rs.close();
    } catch (SQLException e) {
      e.printStackTrace();
    }

    Paketi zoneCene = fixx.getPaketData(paketID);
    LocalDate datumPr = LocalDate.parse(mesecKreiranja, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
    LocalDate datumZaMesec = LocalDate
        .parse(mesecZaduzenja, DateTimeFormatter.ofPattern("yyyy-MM-dd"));

    if (datumPr.getYear() == datumZaMesec.getYear() && datumPr.getMonthValue() == datumZaMesec
        .getMonthValue()) {
      int danaUMesecu = 0;
      int danPriljucka = 0;
      int danaZaNaplatu = 0;
      double cenaDana = 0.00;
      danaUMesecu = datumPr.lengthOfMonth();
      danPriljucka = datumPr.getDayOfMonth();
      danaZaNaplatu = danaUMesecu - danPriljucka;
      cenaDana = zoneCene.getPretplata() / danaUMesecu;
      zoneCene.setPretplata(cenaDana * danaZaNaplatu);
    }

    query = "INSERT INTO zaduzenja (datumZaduzenja, userID, zaMesec, zaUplatu, komentar) VALUES (?,?,?,?,?)";
    try {
      ps = db.connection.prepareStatement(query);
      ps.setString(1, LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
      ps.setInt(2, user.getId());
      ps.setString(3,
          LocalDate.parse(mesecZaduzenja, dateDateTimeFormater).format(monthDateTimeFormater));
      ps.setDouble(4, zoneCene.getPretplata());
      ps.setString(5, "Paket");
      ps.executeUpdate();
      ps.close();
    } catch (SQLException e) {
      e.printStackTrace();
    }


  }

  private void zaduziKorisnikaSaobracaj(Users user) {
    PreparedStatement ps;
    ResultSet rs;

    FIXX fixx = new FIXX(db);

    String query;
    LocalDate datumStart = LocalDate.parse(mesecZaduzenja);
    String start = datumStart.withDayOfMonth(1).toString();
    String stop = datumStart.withDayOfMonth(datumStart.lengthOfMonth()).toString();

    double debt = FIXX
        .getAccountDebt(user.getBrojTelefona(), datumStart.format(monthDateTimeFormater), db);

    query = "INSERT INTO zaduzenja (datumZaduzenja, userID, zaMesec, zaUplatu, komentar) VALUES (?,?,?,?,?)";

    try {
      ps = db.connection.prepareStatement(query);
      ps.setString(1, LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
      ps.setInt(2, user.getId());
      ps.setString(3, datumStart.format(DateTimeFormatter.ofPattern("yyyy-MM")));
      ps.setDouble(4, debt);
      ps.setString(5, "Saobracaj");
      ps.executeUpdate();
      ps.close();
    } catch (SQLException e) {
      Alert alert = new Alert(Alert.AlertType.ERROR, e.getMessage());
      alert.showAndWait();
      e.printStackTrace();
    }


  }

  private void zaduziSingleUserSaobracaj(Users user) {
    LocalDate datumStart = LocalDate.parse(mesecZaduzenja);
    String start = datumStart.withDayOfMonth(1).toString();
    String stop = datumStart.withDayOfMonth(datumStart.lengthOfMonth()).toString();
    PreparedStatement ps;
    ResultSet rs;
    String query;

    ArrayList<CSVData> csvUserDataArrayList;
    ArrayList<destination> destinationsArrayList = new ArrayList<>();
    csvUserDataArrayList = FIXX
        .getCSVDataDescriptionDistinct(user.getId(), user.getBrojTelefona(), start, stop, db);

    for (CSVData csvData : csvUserDataArrayList) {
      destination des = new destination();
      des.setOpisDestinacije(csvData.getDescription());
      des.setMinutaZaNaplatu(
          fixx.getCSVDataChargedTimeS_SUM(user.getBrojTelefona(), csvData.getDescription(),
              start, stop, db));
      des.setUtrosenoMinuta(
          fixx.getCSVDataChargedTimeS_SUM(user.getBrojTelefona(), csvData.getDescription(),
              start, stop, db));
      //TODO  set gratis
      if (des.getOpisDestinacije().equals("Srbija Fiksna")) {
        int minZaNaplatu = des.getMinutaZaNaplatu();
        if (minZaNaplatu <= 60) {
          minZaNaplatu = 0;
        } else {
          minZaNaplatu = minZaNaplatu - 60;
        }
        des.setMinutaZaNaplatu(minZaNaplatu);
      }
      System.out.println("za naplatu:" + des.getMinutaZaNaplatu() + " " + des.getOpisDestinacije());
      destinationsArrayList.add(des);
    }

    for (destination destination : destinationsArrayList) {
      //fill the data :))
      query =
          "INSERT INTO zaduzenja (datumZaduzenja, userID, zaMesec, zaUplatu, komentar, destination, zoneID,"
              +
              "zoneCeneID, minutaZaNaplatu, minutaPotrosnja) VALUES (?,?,?,?,?,?,?,?,?,?)";
      Zone zoneData = fixx.getZoneData(destination.getOpisDestinacije());
      ZoneCene zoneCene = fixx.getZoneCeneData(zoneData.getZonaID());
      try {
        ps = db.connection.prepareStatement(query);
        ps.setString(1, LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
        ps.setInt(2, user.getId());
        ps.setString(3, datumStart.format(DateTimeFormatter.ofPattern("yyyy-MM")));
        ps.setDouble(4, destination.getMinutaZaNaplatu() * zoneCene.getCena());
        ps.setString(5, "Saobracaj");
        ps.setString(6, destination.getOpisDestinacije());
        ps.setInt(7, zoneData.getId());
        ps.setInt(8, zoneData.getZonaID());
        ps.setInt(9, destination.getMinutaZaNaplatu());
        ps.setInt(10, destination.getUtrosenoMinuta());
        if (destination.getMinutaZaNaplatu() != 0) {
          ps.executeUpdate();
        }
        ps.close();
      } catch (SQLException e) {
        e.printStackTrace();
      }
    }
  }


}
