package net.yuvideo.voipRacuni.classes;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

/**
 * Created by PsyhoZOOM@gmail.com on 5/9/17.
 */
public class userRacun {

  private final String rokPlacanja;
  private final String zaMesec;
  private final LocalDate zaMesecOd;
  private final LocalDate zaMesecDo;
  private Users user;
  private Database db;
  private Double pretplata = 0.00;
  private Double pretplataPDV = 0.00;
  private Double potrosnja = 0.00;
  private Double zaUplatu = 0.00;
  private Double prethodniDug = 0.00;
  private ArrayList<destination> destinacija = new ArrayList<>();
  private FIXX fixx;


  public userRacun(Database db, String zaMesec, String rokPlacanja, Users user) {
    this.db = db;
    this.fixx = new FIXX(this.db);
    this.user = user;
    this.zaMesec = zaMesec;
    this.rokPlacanja = LocalDate.parse(rokPlacanja)
        .format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
    LocalDate date = LocalDate
        .parse(LocalDate.parse(zaMesec + "-01").format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
    zaMesecOd = LocalDate.of(date.getYear(), date.getMonthValue(), 1);
    zaMesecDo = LocalDate.of(date.getYear(), date.getMonthValue(), date.lengthOfMonth());

    this.pretplataPDV = fixx.getPaketData(user.getNazivPaketaID()).getPDV();
    setPretplata();
    setPrethodniDug();
    setDestination();
    setPotrosnja();
    setZaUplatu();


  }


  private void setPotrosnja() {
    for (destination dest : destinacija) {
      this.potrosnja += dest.getUkupno();
      System.out.println("potrosnja: " + this.potrosnja);
    }
  }

  private void setDestination() {
    PreparedStatement ps;
    ResultSet rs;
    Zone zone;
    ZoneCene zoneCene;
    String query = "SELECT * FROM zaduzenja WHERE userID=? AND zaMesec=? AND komentar = 'Saobracaj' AND uplaceno=0";

    try {
      ps = db.connection.prepareStatement(query);
      ps.setInt(1, user.getId());
      ps.setString(2, zaMesec);
      rs = ps.executeQuery();
      if (rs.isBeforeFirst()) {
        while (rs.next()) {
          destination destination = new destination();
          destination.setUserid(user.getId());
          destination.setUkupno(rs.getDouble("zaUplatu"));
          destination.setId(rs.getInt("id"));
          destinacija.add(destination);
          System.out.println(destinacija);
        }
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }

  }

  private void setPrethodniDug() {
    PreparedStatement ps;
    ResultSet rs;
    String query =
        "SELECT SUM(zaUplatu) AS ukupnoPrethodniDug FROM zaduzenja WHERE userID=? AND zaMesec <? AND "
            +
            "uplaceno=0";
    try {
      ps = db.connection.prepareStatement(query);
      ps.setInt(1, user.getId());
      ps.setString(2, zaMesec);
      rs = ps.executeQuery();
      if (rs.isBeforeFirst()) {
        rs.next();
        double prDug = rs.getDouble("ukupnoPrethodniDug");
        this.prethodniDug = prDug + valueToPercent.getValue(prDug, this.getPDV());
      }
      ps.close();
      rs.close();
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }


  private void setZaUplatu() {

    this.zaUplatu = this.getPretplata() + this.getPotrosnja() + this.prethodniDug +
        valueToPercent.getValue((this.getPretplata() + this.getPotrosnja()), this.getPDV());
  }


  private void setPretplata() {
    ResultSet rs;
    PreparedStatement ps;
    String query = "SELECT * FROM zaduzenja WHERE userID=? AND zaMesec=? AND komentar = 'Paket' AND uplaceno=0";
    try {
      ps = db.connection.prepareStatement(query);
      ps.setInt(1, user.getId());
      ps.setString(2, zaMesec);
      rs = ps.executeQuery();
      if (rs.isBeforeFirst()) {
        rs.next();
        this.pretplata = rs.getDouble("zaUplatu");
      }
      ps.close();
      rs.close();

      //// TO-DO brisanje
            /*
            LocalDate datumPr = LocalDate.parse(user.getDatumPrikljucka(), DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            LocalDate datumZaMesec = LocalDate.parse(this.zaMesec + "-01");



            //ako je korisnik prikljucen u toku meseca onda moramo izracunati koliko dana ce mu se naplatiti pretplata
            //a ne ceo mesec


            if (datumPr.getYear() == datumZaMesec.getYear() && datumPr.getMonthValue() == datumZaMesec.getMonthValue()) {
                int danaUMesecu = 0;
                int danPriljucka = 0;
                int danaZaNaplatu = 0;
                double cenaDana = 0.00;
                danaUMesecu = datumPr.lengthOfMonth();
                danPriljucka = datumPr.getDayOfMonth();
                danaZaNaplatu = danaUMesecu - danPriljucka;
                cenaDana = this.pretplata / danaUMesecu;
                this.pretplata = cenaDana * danaZaNaplatu;
            }


            */

    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

  public Users getUser() {
    return this.user;
  }

  public void setUser(Users user) {
    this.user = user;
  }

  public String getPeriodDo() {
    return zaMesecDo.format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
  }

  public String getPeriodOd() {
    return zaMesecOd.format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
  }

  public String getRokPlacanja() {
    return rokPlacanja;
  }

  public Double getPretplata() {
    return pretplata;
  }

  public void setPretplata(Double pretplata) {
    this.pretplata = pretplata;
  }

  public double getPotrosnja() {
    return potrosnja;
  }

  public void setPotrosnja(Double potrosnja) {
    this.potrosnja = potrosnja;
  }

  public double getZaUplatu() {
    return zaUplatu;
  }

  public void setZaUplatu(Double zaUplatu) {
    this.zaUplatu = zaUplatu;
  }

  public double getPDV() {
    return this.pretplataPDV;
  }

  public double getPrethodniDug() {
    return prethodniDug;
  }

  public void setPrethodniDug(Double prethodniDug) {
    this.prethodniDug = prethodniDug;
  }

  public ArrayList<destination> getDestinacija() {
    return destinacija;
  }

  public void setDestinacija(ArrayList<destination> destinacija) {
    this.destinacija = destinacija;
  }

  public void setDb(Database db) {
    this.db = db;
  }

  public void setPretplataPDV(Double pretplataPDV) {
    this.pretplataPDV = pretplataPDV;
  }

  public void setFixx(FIXX fixx) {
    this.fixx = fixx;
  }
}

