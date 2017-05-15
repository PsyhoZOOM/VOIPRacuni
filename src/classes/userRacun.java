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
    private final String startDate;
    private final String stopDate;
    private Users user;
    private ArrayList<Paketi> paketiArrayList = new ArrayList<>();
    private Racun racun;
    private ArrayList<Zone> zoneArr = new ArrayList<>();
    private Database db;
    private ArrayList<CSVData> csvDataArr = new ArrayList<>();
    private ArrayList<ZoneCene> zoneCenesArr = new ArrayList<>();
    private ArrayList<destination> userDestinationData = new ArrayList<>();
    private String startD;
    private String stopD;


    public userRacun(Database db, String startD, String stopD, String rokPlacanja, Users user) {

        this.db = db;
        this.startDate = LocalDate.parse(startD).format(DateTimeFormatter.ofPattern("dd.MM.yyyy")).toString();
        this.stopDate = LocalDate.parse(stopD).format(DateTimeFormatter.ofPattern("dd.MM.yyyy")).toString();
        this.startD = startD + " 00:00:00";
        this.stopD = stopD + " 59:59:59";
        this.user = user;
        this.rokPlacanja = LocalDate.parse(rokPlacanja).format(DateTimeFormatter.ofPattern("dd.MM.yyyy")).toString();

        setZoneCene();
        setZoneData();
        setPaketData();
        setChargedTimeMS();
        saberiSve();
    }


    private void setZoneData() {
        PreparedStatement ps;
        ResultSet rs;
        String query = "SELECT * FROM zone";

        try {
            ps = db.connection.prepareStatement(query);
            rs = ps.executeQuery();
            if (rs.isBeforeFirst()) {
                while (rs.next()) {
                    Zone zona = new Zone();
                    zona.setId(rs.getInt("id"));
                    zona.setNaziv(rs.getString("naziv"));
                    zona.setOpis(rs.getString("opis"));
                    zona.setZona(rs.getString("zona"));
                    zona.setZonaID(rs.getInt("zonaID"));
                    zoneArr.add(zona);
                }
            }
            ps.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void setZoneCene() {
        PreparedStatement ps;
        ResultSet rs;
        String query = "SELECT * FROM zoneCene";

        try {
            ps = db.connection.prepareStatement(query);
            rs = ps.executeQuery();
            if (rs.isBeforeFirst()) {
                while (rs.next()) {
                    ZoneCene zoneCene = new ZoneCene();
                    zoneCene.setId(rs.getInt("id"));
                    zoneCene.setVrstaUsluge(rs.getString("vrstaUsluge"));
                    zoneCene.setProviderCena(rs.getDouble("providerCena"));
                    zoneCene.setProviderPDV(rs.getDouble("providerPDV"));
                    zoneCene.setCena(rs.getDouble("cena"));
                    zoneCene.setPDV(rs.getDouble("PDV"));
                    zoneCene.setCenaPDV(rs.getDouble("cenaPDV"));
                    zoneCene.setCompetitionCena(rs.getDouble("otherCena"));
                    zoneCenesArr.add(zoneCene);
                }
                ps.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Racun getRacun() {
        return this.racun;
    }

    private void setRacun(ArrayList<destination> userDestinationData) {
        racun = new Racun();
        racun.setDestinacija(userDestinationData);
        racun.setBroj(user.getBrojTelefona());
        racun.setUgovorBr(user.getBrUgovora());
        racun.setDatumIzdavanja(LocalDate.now().format(DateTimeFormatter.ofPattern("dd.MM.yyyy")));
        racun.setPdv(getPaketData(user.getNazivPaketaID()).getPDV());
        racun.setPeriodOd(startDate);
        racun.setPeriodDo(stopDate);
        racun.setPrethodniDug(getDugKorisnika(user.getId()));
        racun.setRokPlacanja(rokPlacanja);
        racun.setPozivNaBroj(user.getPozivNaBroj());
        racun.setPretplata(getPaketData(user.getNazivPaketaID()).getPretplata());
        user.setPozivNaBroj(user.pozivNaBroj);
        Double potrosnja = 0.00;
        for (int i = 0; i < userDestinationData.size(); i++) {
            potrosnja += userDestinationData.get(i).getUkupno();
        }
        racun.setPotrosnja(potrosnja);
    }

    private Double getDugKorisnika(int userID) {
        PreparedStatement ps;
        ResultSet rs;
        String query = "SELECT SUM(zaUplatu) AS zaUplatu FROM uplate WHERE userID=? AND uplaceno=0";
        Double dug = 0.00;

        try {
            ps = db.connection.prepareStatement(query);
            ps.setInt(1, userID);
            rs = ps.executeQuery();
            if (rs.isBeforeFirst()) {
                rs.next();
                dug = rs.getDouble("zaUplatu");
            }


        } catch (SQLException e) {
            e.printStackTrace();
        }
        return dug;
    }

    private void saberiSve() {
        double prethodni_dug = 0.00;
        double pretplata = 0.00;
        double potrosnja = 0.00;
        double pdv = 0.00;
        double ukupnoZaUplatu = 0.00;

        prethodni_dug = racun.getPrethodniDug();
        pretplata = getPaketData(user.getNazivPaketaID()).getPretplata();
        potrosnja = racun.getPotrosnja();
        pdv = getPaketData(user.getNazivPaketaID()).getPDV();

        ukupnoZaUplatu = pretplata + potrosnja;
        ukupnoZaUplatu = ukupnoZaUplatu + valueToPercent.getValue(ukupnoZaUplatu, pdv);
        ukupnoZaUplatu = ukupnoZaUplatu + prethodni_dug;

        racun.setZaUplatu(ukupnoZaUplatu);
    }

    private void setPaketData() {
        PreparedStatement ps;
        ResultSet rs;
        Paketi paket;

        try {
            ps = db.connection.prepareStatement("SELECT * FROM paketi");
            rs = ps.executeQuery();
            if (rs.isBeforeFirst()) {
                while (rs.next()) {
                    paket = new Paketi();
                    paket.setId(rs.getInt("id"));
                    paket.setPretplata(rs.getInt("pretplata"));
                    paket.setPDV(rs.getDouble("PDV"));
                    paket.setBesplatniMinutiFiksna(rs.getInt("besplatniMinutiFiksna"));
                    paketiArrayList.add(paket);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void setChargedTimeMS() {
        PreparedStatement ps;
        ResultSet rs;
        String query;
        ArrayList<destination> dstArr = new ArrayList<>();

        for (int i = 0; i < zoneArr.size(); i++) {
            query = "SELECT account, description, SUM(chargedTimeS) AS chargedTimeMS FROM csv  WHERE account=? " +
                    "AND connectTime >=? AND connectTime <=? AND description=?";
            try {
                ps = db.connection.prepareStatement(query);
                ps.setString(1, user.getBrojTelefona());
                ps.setString(2, startD);
                ps.setString(3, stopD);
                ps.setString(4, zoneArr.get(i).getOpis());
                rs = ps.executeQuery();
                if (rs.isBeforeFirst()) {
                    rs.next();
                    if (rs.getString("account") == null)
                        continue;
                    destination dst = new destination();
                    dst.setUserid(user.getId());
                    dst.setAccount(rs.getString("account"));
                    dst.setNazivDestinacije(rs.getString("description"));
                    dst.setUtrosenoMinuta(rs.getInt("chargedTimeMS") / 60);
                    dst.setCenaPoMinutu(getZone(rs.getString("description")).getCena());
                    dstArr.add(dst);
                }
                ps.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        userDestinationData = setGratis(dstArr);
        userDestinationData = setMinutaZaNaplatu(userDestinationData);
        setRacun(userDestinationData);
    }

    private ArrayList<destination> setMinutaZaNaplatu(ArrayList<destination> userDestinationData) {

        for (int i = 0; i < userDestinationData.size(); i++) {
            int utrosenoMinuta = 0;
            int gratisMinuta = 0;
            int minutaZaNaplatu = 0;
            Double cenaPoMinutu = 0.00;
            Double ukupno = 0.00;

            cenaPoMinutu = getZone(userDestinationData.get(i).getNazivDestinacije()).getCena();
            userDestinationData.get(i).setCenaPoMinutu(cenaPoMinutu);
            minutaZaNaplatu = userDestinationData.get(i).getUtrosenoMinuta() - userDestinationData.get(i).getGratisMinuta();
            if (minutaZaNaplatu > 0) {
                userDestinationData.get(i).setMinutaZaNaplatu(minutaZaNaplatu);
                userDestinationData.get(i).setUkupno(minutaZaNaplatu * cenaPoMinutu);
            } else {
                userDestinationData.get(i).setMinutaZaNaplatu(0);
                userDestinationData.get(i).setUkupno(0.00);
            }
        }
        return userDestinationData;
    }

    private ArrayList<destination> setGratis(ArrayList<destination> userDestinationData) {
        final String gratisFiksnaSrbija = "Srbija Fiksna";
        final String gratisMobilaSrbija = "Srbija Mobilna";
        int gratisMinuta = 0;

        for (int i = 0; i < userDestinationData.size(); i++) {
            //FIKSNA TELEFONIJA SRBIJA GRATIS
            if (userDestinationData.get(i).getNazivDestinacije().equals(gratisFiksnaSrbija)) {
                gratisMinuta = getPaketData(user.getNazivPaketaID()).besplatniMinutiFiksna;
                userDestinationData.get(i).setGratisMinuta(gratisMinuta);
            } else {
                if (userDestinationData.get(i).getUtrosenoMinuta() > 0) {
                    userDestinationData.get(i).setGratisMinuta(0);
                }
            }
        }
        return userDestinationData;
    }

    private Double getCenaZone(int zoneID) {
        return null;
    }

    private Paketi getPaketData(int paketID) {
        Paketi paketi = null;
        for (int i = 0; i < paketiArrayList.size(); i++) {
            if (paketiArrayList.get(i).getId() == paketID) {
                paketi = paketiArrayList.get(i);
            }

        }
        return paketi;
    }

    private ZoneCene getZone(String opis) {
        ZoneCene zoneCene = null;


        for (int i = 0; i < zoneArr.size(); i++) {
            if (zoneArr.get(i).getOpis().equals(opis)) {
                zoneCene = getZoneCeneData(zoneArr.get(i).getZonaID());
            }

        }
        return zoneCene;
    }

    private ZoneCene getZoneCeneData(int zoneID) {
        ZoneCene zoneCene = null;
        for (int i = 0; i < zoneCenesArr.size(); i++) {
            if (zoneCenesArr.get(i).getId() == zoneID)
                zoneCene = zoneCenesArr.get(i);
        }
        return zoneCene;
    }

    public Users getUser() {
        return user;
    }

    public String getPeriodOd() {
        return startDate;
    }

    public String getPeriodDo() {
        return stopDate;
    }

    public String getRokPlacanja() {
        return rokPlacanja;
    }

    public double getPretplata() {
        return racun.getPretplata();
    }

    public ArrayList<destination> getDestinacija() {
        return userDestinationData;
    }

    public double getZaUplatu() {
        return racun.zaUplatu;
    }

    public double getPDV() {
        return racun.getPdv();
    }

    public double getPrethodniDug() {
        return racun.getPrethodniDug();
    }

    public double getPotrosnja() {
        return racun.getPotrosnja();
    }
}
