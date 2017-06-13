package classes;

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
        this.rokPlacanja = LocalDate.parse(rokPlacanja).format(DateTimeFormatter.ofPattern("dd.MM.yyyy")).toString();
        LocalDate date = LocalDate.parse(LocalDate.parse(zaMesec + "-01").format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
        zaMesecOd = LocalDate.of(date.getYear(), date.getMonthValue(), 1);
        zaMesecDo = LocalDate.of(date.getYear(), date.getMonthValue(), date.lengthOfMonth());

        setPretplata();
        setPrethodniDug();
        setDestination();
        setPotrosnja();
        setZaUplatu();

        // TODO: 6/9/17 izbrisati klasu kada nam natprovajder automatski skida besplatneminute



    }

    private void setPotrosnja() {
        for (destination dest : destinacija) {
            this.potrosnja += dest.getUkupno();
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
                    zone = fixx.getZoneData(rs.getString("destination"));
                    zoneCene = fixx.getZoneCeneData(rs.getInt("zoneCeneID"));
                    destination destination = new destination();
                    destination.setUserid(user.getId());
                    destination.setUkupno(rs.getDouble("zaUplatu"));
                    destination.setId(rs.getInt("id"));
                    destination.setUtrosenoMinuta(rs.getInt("minutaPotrosnja"));
                    destination.setNazivDestinacijeZone(
                            zoneCene.getVrstaUsluge()
                    );
                    destination.setMinutaZaNaplatu(rs.getInt("minutaZaNaplatu"));
                    destination.setCenaPoMinutu(zoneCene.getCena());
                    if (destination.getNazivDestinacijeZone().equals("Srbija fiksni")) {
                        destination.setGratisMinuta(60);
                    }
                    //this.potrosnja = + rs.getDouble("zaUplatu");
                    destinacija.add(destination);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    private void setPrethodniDug() {
        PreparedStatement ps;
        ResultSet rs;
        String query = "SELECT SUM(zaUplatu) AS ukupnoPrethodniDug FROM zaduzenja WHERE userID=? AND zaMesec <? AND " +
                "uplaceno=0";
        try {
            ps = db.connection.prepareStatement(query);
            ps.setInt(1, user.getId());
            ps.setString(2, zaMesec);
            rs = ps.executeQuery();
            if (rs.isBeforeFirst()) {
                rs.next();
                //TODO potrosnja saobracaj porez pretplata bez poreza
                double prDug = rs.getDouble("ukupnoPrethodniDug");
                this.prethodniDug = prDug;
                //this.prethodniDug = prDug + valueToPercent.getValue(prDug, this.getPDV());
            }
            ps.close();
            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    private void setZaUplatu() {

        this.zaUplatu = this.getPretplata() + this.getPotrosnja() +
                valueToPercent.getValue((this.getPretplata() + this.getPotrosnja()), this.getPDV()) + prethodniDug;
    }


    private void setPretplata() {
        ResultSet rs;
        PreparedStatement ps;
        String query = "SELECT *  FROM paketi WHERE id=?";
        try {
            ps = db.connection.prepareStatement(query);
            ps.setInt(1, user.getNazivPaketaID());
            rs = ps.executeQuery();
            if (rs.isBeforeFirst()) {
                rs.next();
                this.pretplata = rs.getDouble("pretplata");
                this.pretplataPDV = rs.getDouble("PDV");
            }
            ps.close();
            rs.close();

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


        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Users getUser() {
        return this.user;
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

    public double getPotrosnja() {
        return potrosnja;
    }

    public double getZaUplatu() {
        return zaUplatu;
    }

    public double getPDV() {
        return this.pretplataPDV;
    }

    public double getPrethodniDug() {
        return prethodniDug;
    }

    public ArrayList<destination> getDestinacija() {
        return destinacija;
    }

}

