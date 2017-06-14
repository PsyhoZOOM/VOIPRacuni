package classes;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Created by PsyhoZOOM on 6/8/17.
 */
public class FIXX {

    private final Database db;

    public FIXX(Database db) {
        this.db = db;
    }

    public static ArrayList<CSVData> getCSVDataDescriptionDistinct(int userID, String accountPhone, String startDate, String endDate, Database db) {
        ArrayList<CSVData> csvDataArrayList = new ArrayList<>();
        PreparedStatement ps;
        ResultSet rs;
        //chargedTimeS > 0 zbog nekih nebuloza u csv fajlu
        String query = "SELECT distinct(description) FROM csv WHERE account=? AND connectTime >= ? AND connectTime <= ? " +
                "and chargedTimeS != 0  ";
        try {
            ps = db.connection.prepareStatement(query);
            ps.setString(1, accountPhone);
            ps.setString(2, startDate + " 00:00:00");
            ps.setString(3, endDate + " 23:59:59");
            rs = ps.executeQuery();
            if (rs.isBeforeFirst()) {
                while (rs.next()) {
                    CSVData csvData = new CSVData();
                    /*
                    csvData.setId(rs.getInt("id"));
                    csvData.setAccount(rs.getString("account"));
                    csvData.setFrom(rs.getString("from"));
                    csvData.setTo(rs.getString("to"));
                    csvData.setCountry(rs.getString("country"));
                    csvData.setDescription(rs.getString("description"));
                    csvData.setConnectTime(rs.getString("connectTime"));
                    csvData.setChargedTimeMinSec(rs.getString("chargedTimeMS"));
                    csvData.setChargedTimeSec(rs.getInt("chargedTimeS"));
                    csvData.setChargedAmountRSD(rs.getDouble("chargedAmountRSD"));
                    csvData.setServiceName(rs.getString("serviceName"));
                    csvData.setChargedQuantity(rs.getInt("chargedQuantity"));
                    csvData.setServiceUnit(rs.getString("serviceUnit"));
                    csvData.setCustomerID(rs.getString("customerID"));
                    csvData.setFileName(rs.getString("fileName"));
                    */
                    csvData.setDescription(rs.getString("description"));
                    csvDataArrayList.add(csvData);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return csvDataArrayList;
    }

    /**
     * vraca ZoneCene data od id-a iz table->zone column->zonaID
     *
     * @param id int   id from table zoneCene.
     * @return ZoneCene all cell from table zoneCene.
     */
    public ZoneCene getZoneCeneData(int id) {
        ZoneCene zoneCene = null;

        PreparedStatement ps;
        ResultSet rs;
        String query = "SELECT * from zoneCene WHERE id=?";

        try {
            ps = db.connection.prepareStatement(query);
            ps.setInt(1, id);
            rs = ps.executeQuery();

            if (rs.isBeforeFirst()) {
                //fill the data :)
                rs.next();
                zoneCene = new ZoneCene();
                zoneCene.setId(rs.getInt("id"));
                zoneCene.setVrstaUsluge(rs.getString("vrstaUsluge"));
                zoneCene.setProviderCena(rs.getDouble("providerCena"));
                zoneCene.setPDV(rs.getDouble("providerPDV"));
                zoneCene.setCena(rs.getDouble("cena"));
                zoneCene.setPDV(rs.getDouble("PDV"));
                zoneCene.setOtherCena(rs.getDouble("otherCena"));
            }
            ps.close();
            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return zoneCene;
    }

    /**
     * vraca Zone data  iz table zone od prosledjenog opisa(Description)
     *
     * @param zoneDescription String naziv opisa(Description) za tablu zone.
     * @return Zone classu sa data iz zone table.
     */
    public Zone getZoneData(String zoneDescription) {
        PreparedStatement ps;
        ResultSet rs;
        Zone zoneData = null;
        String query = "SELECT * FROM zone where opis=?";

        try {
            ps = db.connection.prepareStatement(query);
            ps.setString(1, zoneDescription);
            rs = ps.executeQuery();
            if (rs.isBeforeFirst()) {
                rs.next();
                zoneData = new Zone();
                //fill the data :)
                zoneData.setId(rs.getInt("id"));
                zoneData.setNaziv(rs.getString("naziv"));
                zoneData.setOpis(rs.getString("opis"));
                zoneData.setZona(rs.getString("zona"));
                zoneData.setZonaID(rs.getInt("zonaID"));
            }
            ps.close();
            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return zoneData;
    }

    public Paketi getPaketData(int paketID) {
        PreparedStatement ps;
        ResultSet rs;
        String query = "SELECT * FROM paketi WHERE id=?";
        Paketi paket = null;

        try {
            ps = db.connection.prepareStatement(query);
            ps.setInt(1, paketID);
            rs = ps.executeQuery();
            if (rs.isBeforeFirst()) {
                rs.next();
                paket = new Paketi();
                paket.setId(rs.getInt("id"));
                paket.setNaziv(rs.getString("naziv"));
                paket.setPretplata(rs.getDouble("pretplata"));
                paket.setPDV(rs.getDouble("PDV"));
                paket.setBesplatniMinutiFiksna(rs.getInt("besplatniMinutiFiksna"));
            }
            ps.close();
            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return paket;
    }

    public int getCSVDataChargedTimeS_SUM(String account, String description, String startDate, String stopDate, Database db) {
        PreparedStatement ps;
        ResultSet rs;
        // chargedTimeS > 0 FIX zbog nekih nebuloza u CSV fajlu
        String query = "SELECT sum(chargedTimeS) AS seconds FROM csv WHERE account=? and description=? and " +
                "connectTime >= ? and connectTime <= ?";
        int minuta = 0;
        try {
            ps = db.connection.prepareStatement(query);
            ps.setString(1, account);
            ps.setString(2, description);
            ps.setString(3, startDate + " 00:00:00");
            ps.setString(4, stopDate + " 23:59:59");
            rs = ps.executeQuery();
            if (rs.isBeforeFirst()) {
                while (rs.next()) {
                    minuta = minuta + rs.getInt("seconds") / 60;
                }
            }
            ps.close();
            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }


        return minuta;
    }

    public ArrayList<Users> getUsers() {

        ArrayList<Users> usersArrayList = new ArrayList<>();
        Users user;

        PreparedStatement ps;
        ResultSet rs;

        //String query = "SELECT * FROM korisnici WHERE datumPrikljucka >=?";
        String query = "SELECT * FROM korisnici";

        try {
            ps = db.connection.prepareStatement(query);
            // ps.setString(1, datumZaduzenja);
            rs = ps.executeQuery();
            if (rs.isBeforeFirst()) {
                while (rs.next()) {
                    user = new Users();
                    user.setId(rs.getInt("id"));
                    user.setDatumPrikljucka(rs.getString("datumPrikljucka"));
                    user.setIme(rs.getString("imePrezime"));
                    user.setAdresa(rs.getString("adresa"));
                    user.setMesto(rs.getString("mesto"));
                    user.setPostBr(rs.getString("postBr"));
                    user.setBrUgovora(rs.getString("brUgovora"));
                    user.setCustomerId(rs.getString("customerID"));
                    user.setPozivNaBroj(rs.getString("pozivNaBroj"));
                    user.setBrojTelefona(rs.getString("brojTelefona"));
                    user.setNazivPaketaID(rs.getInt("paketID"));
                    user.setStampa(rs.getBoolean("stampa"));
                    user.setFirma(rs.getBoolean("firma"));
                    user.setMbr(rs.getString("mbr"));
                    user.setPib(rs.getString("pib"));
                    user.setNazivFirme(rs.getString("nazivFirme"));
                    usersArrayList.add(user);

                }
            }
            ps.close();
            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return usersArrayList;
    }

    public Users getUserData(int userID) {
        String ImePrezime = null;
        PreparedStatement ps;
        ResultSet rs;
        Users user = null;
        String query = "SELECT * FROM korisnici WHERE id=?";
        try {
            ps = db.connection.prepareStatement(query);
            ps.setInt(1, userID);
            rs = ps.executeQuery();
            if (rs.isBeforeFirst()) {
                rs.next();
                user = new Users();
                user.setId(rs.getInt("id"));
                user.setIme(rs.getString("ImePrezime"));
                user.setAdresa(rs.getString("adresa"));
                user.setMesec(rs.getString("mesto"));
                user.setPostBr(rs.getString("postBr"));
                user.setBrUgovora(rs.getString("brUgovora"));
                user.setCustomerId(rs.getString("customerID"));
                user.setPozivNaBroj(rs.getString("pozivNaBroj"));
                user.setBrojTelefona(rs.getString("brojTelefona"));
                user.setNazivPaketaID(rs.getInt("paketID"));
                user.setStampa(rs.getBoolean("stampa"));
                user.setFirma(rs.getBoolean("firma"));
                user.setMbr(rs.getString("mbr"));
                user.setPib(rs.getString("pib"));
                user.setNazivFirme(rs.getString("nazivFirme"));
            }
            ps.close();
            rs.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return user;
    }



}
