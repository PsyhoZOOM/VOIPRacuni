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
        setPotrosnja();
        setPrethodniDug();
        setDestination();
        setGratisFiksna();
        setZaUplatu();

        // TODO: 6/9/17 izbrisati klasu kada nam natprovajder automatski skida besplatneminute



    }

    private void setGratisFiksna() {
        int besplatniMinut = 60;
        for (destination dst : destinacija) {
            System.out.println(dst.getNazivDestinacijeZone());
            if (dst.getNazivDestinacijeZone().equals("Srbija fiksni")) {
                dst.setGratisMinuta(60);
                if (dst.getMinutaZaNaplatu() < 60) {
                    dst.setMinutaZaNaplatu(0);
                    dst.ukupno = 0.00;
                } else {
                    double minutazaNaplatu = (dst.getMinutaZaNaplatu() - dst.getGratisMinuta()) * dst.getCenaPoMinutu();
                    dst.setMinutaZaNaplatu(dst.getMinutaZaNaplatu() - dst.getGratisMinuta());
                    dst.setUkupno(minutazaNaplatu);
                }
            }
        }
    }

    private void setDestination() {
        PreparedStatement ps;
        ResultSet rs;
        String query = "SELECT * FROM zaduzenja WHERE userID=? AND zaMesec=? AND komentar = 'Saobracaj' AND uplaceno=0";

        try {
            ps = db.connection.prepareStatement(query);
            ps.setInt(1, user.getId());
            ps.setString(2, zaMesec);
            rs = ps.executeQuery();
            if (rs.isBeforeFirst()) {
                destination destination = new destination();
                while (rs.next()) {
                    destination.setUserid(user.getId());
                    destination.setUkupno(rs.getDouble("zaUplatu"));
                    destination.setId(rs.getInt("id"));
                    destination.setUtrosenoMinuta(rs.getInt("minutaZaNaplatu"));
                    destination.setNazivDestinacijeZone(fixx.getZoneCeneData(user.getNazivPaketaID()).getVrstaUsluge());
                    destination.setCenaPoMinutu(fixx.getZoneCeneData(user.getNazivPaketaID()).getCena());
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
                this.prethodniDug = rs.getDouble("ukupnoPrethodniDug");
                this.prethodniDug = this.prethodniDug + valueToPercent.getValue(this.prethodniDug, pretplataPDV);
            }
            ps.close();
            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    private void setZaUplatu() {
        this.zaUplatu = this.getPotrosnja() + valueToPercent.getValue(this.getPotrosnja(), this.getPDV())
                + this.getPrethodniDug() + valueToPercent.getValue(getPrethodniDug(), this.getPDV());
    }

    private void setPotrosnja() {
        PreparedStatement ps;
        ResultSet rs;
        String query = "SELECT SUM(zaUplatu) AS ukupnoZaUplatu FROM zaduzenja WHERE userID=? AND zaMesec=? AND uplaceno=?" +
                " AND komentar=?";
        try {
            ps = db.connection.prepareStatement(query);
            ps.setInt(1, user.getId());
            ps.setString(2, zaMesec);
            ps.setDouble(3, 0.00);
            ps.setString(4, "Saobracaj");
            rs = ps.executeQuery();
            if (rs.isBeforeFirst()) {
                rs.next();
                this.potrosnja = rs.getDouble(1);
            }
            ps.close();
            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
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

