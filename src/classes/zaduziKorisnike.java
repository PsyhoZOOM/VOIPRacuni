package classes;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

/**
 * Created by PsyhoZOOM on 6/8/17.
 */
public class zaduziKorisnike {
    private final Database db;
    public boolean done = false;
    FIXX fixx;

    public zaduziKorisnike(ArrayList<Users> usersArrayList, String datumZaduzenja, Database db) {
        this.db = db;
        fixx = new FIXX(this.db);
        for (Users user : usersArrayList) {
            zaduziSingleUserSaobracaj(user, datumZaduzenja);
            zaduziSingleUserPaket(user, datumZaduzenja);
        }


    }

    private void zaduziSingleUserPaket(Users user, String datumZaduzenja) {
        PreparedStatement ps;
        ResultSet rs;
        String query;
        datumZaduzenja = LocalDate.parse(datumZaduzenja, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")).format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        String mesecZaduzenja = LocalDate.parse(datumZaduzenja, DateTimeFormatter.ofPattern("yyyy-MM-dd")).format(DateTimeFormatter.ofPattern("yyyy-MM"));
        String mesecKreiranje = LocalDate.parse(user.getDatumPrikljucka(), DateTimeFormatter.ofPattern("yyyy-MM-dd")).format(DateTimeFormatter.ofPattern("yyyy-MM"));


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
        if (mesecZaduzenja.equals(mesecKreiranje)) {
            LocalDate datum = LocalDate.parse(user.getDatumPrikljucka(), DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            int daysInMonth = datum.lengthOfMonth();
            int currentDay = datum.getDayOfMonth();
            double oneDayPrice = zoneCene.getPretplata() / daysInMonth;
            zoneCene.setPretplata((daysInMonth - currentDay) * oneDayPrice);
        }

        query = "INSERT INTO zaduzenja (datumZaduzenja, userID, zaMesec, zaUplatu, komentar) VALUES (?,?,?,?,?)";
        try {
            ps = db.connection.prepareStatement(query);
            ps.setString(1, LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
            ps.setInt(2, user.getId());
            ps.setString(3, mesecZaduzenja);
            ps.setDouble(4, zoneCene.getPretplata());
            ps.setString(5, "Paket");
            ps.executeUpdate();
            ps.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }


    }

    private void zaduziSingleUserSaobracaj(Users user, String datumZaduznja) {
        LocalDate datumStart = LocalDate.parse(datumZaduznja, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        String start = datumStart.withDayOfMonth(1).toString();
        String stop = datumStart.withDayOfMonth(datumStart.lengthOfMonth()).toString();
        PreparedStatement ps;
        ResultSet rs;
        String query;

        ArrayList<CSVData> csvUserDataArrayList;

        ArrayList<destination> destinationsArrayList = new ArrayList<>();

        getCSVUserData getCsvUserData = new getCSVUserData();

        csvUserDataArrayList = getCSVUserData.getData(user.getId(), user.getBrojTelefona(), start, stop, db);

        query = "SELECT opis  FROM zone";
        try {
            ps = db.connection.prepareStatement(query);
            rs = ps.executeQuery();
            //uzeti svaki opis iz zone table
            //svaki opis uporediti sa korisnikovom destinacijom i ako postoji ubaciti je u korisnik zaduzenja ++
            //na kraju insertovati u zaduzenja korisnika sa cenom
            if (rs.isBeforeFirst()) {
                Double zaduzenje = 0.00;
                int minutaZaNaplatu = 0;

                destination dest;
                //za svaki destination iz zone uporediti opis sa CSV tablom i ako jeste
                //spojiti svaki destination iz CSV table u jedan destination i po zavrsetku ubaciti u arraylistu
                //koja ce ubaciti u zaduzenja tablu
                while (rs.next()) {
                    String opis = rs.getString("opis");
                    dest = new destination();
                    for (CSVData csvUserData : csvUserDataArrayList) {
                        if (csvUserData.getDescription().equals(opis)) {
                            dest.setAccount(csvUserData.getAccount());
                            dest.setMinutaZaNaplatu(dest.getMinutaZaNaplatu() + (csvUserData.getChargedTimeSec() / 60));
                            dest.setCenaPoMinutu(getPricePerMinute(csvUserData.getDescription()));
                            dest.setId(csvUserData.getId());
                            dest.setOpisDestinacije(csvUserData.getDescription());
                            dest.setNazivDestinacije(fixx.getZoneData(csvUserData.getDescription()).getNaziv());
                            dest.setCenaPoMinutu(
                                    fixx.getZoneCeneData(
                                            fixx.getZoneData(
                                                    csvUserData.getDescription()
                                            ).getZonaID()
                                    ).getCena()
                            );
                            dest.setUkupno(dest.getUkupno() + (dest.getCenaPoMinutu() * dest.getMinutaZaNaplatu()));
                            dest.setUserid(user.getId());
                            dest.setUtrosenoMinuta(dest.getUtrosenoMinuta() + (csvUserData.getChargedTimeSec() / 60));
                        }
                    }
                    if (dest.getAccount() != null)
                        destinationsArrayList.add(dest);
                }
            }
            ps.close();
            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }


        //izbrisati CSV i zaduzenje od accounta za taj mesec u slucaju dupliciranja importovanog fajla
        query = "DELETE FROM zaduzenja where zaMesec=? and userID=?";
        try {
            ps = db.connection.prepareStatement(query);
            ps.setString(1, datumStart.format(DateTimeFormatter.ofPattern("yyyy-MM")));
            ps.setInt(2, user.getId());
            ps.executeUpdate();
            ps.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }


        for (destination destination : destinationsArrayList) {


            //fill the data :))
            query = "INSERT INTO zaduzenja (datumZaduzenja, userID, zaMesec, zaUplatu, komentar, destination, zoneID," +
                    "zoneCeneID, minutaZaNaplatu) VALUES (?,?,?,?,?,?,?,?,?)";
            try {
                ps = db.connection.prepareStatement(query);
                ps.setString(1, LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")).toString());
                ps.setInt(2, destination.getUserid());
                ps.setString(3, datumStart.format(DateTimeFormatter.ofPattern("yyyy-MM")));
                ps.setDouble(4, destination.getUkupno());
                ps.setString(5, "Saobracaj");
                ps.setString(6, destination.getOpisDestinacije());
                ps.setInt(7, fixx.getZoneData(destination.getOpisDestinacije()).getId());
                ps.setInt(8, fixx.getZoneData(destination.getOpisDestinacije()).getZonaID());
                ps.setInt(9, destination.getMinutaZaNaplatu());
                ps.executeUpdate();
                ps.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }


    }


    private double getPricePerMinute(String description) {
        PreparedStatement ps;
        ResultSet rs;
        String query = "SELECT * FROM zone WHERE opis=?";
        int idZoneCene = 0;
        double pricePerMinute = 0.00;
        try {
            ps = db.connection.prepareStatement(query);
            ps.setString(1, description);
            rs = ps.executeQuery();
            if (rs.isBeforeFirst()) {
                rs.next();
                idZoneCene = rs.getInt("zonaID");
            }
            ps.close();
            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        query = "SELECT * FROM zoneCene WHERE id=?";
        try {
            ps = db.connection.prepareStatement(query);
            ps.setInt(1, idZoneCene);
            rs = ps.executeQuery();
            if (rs.isBeforeFirst()) {
                rs.next();
                pricePerMinute = rs.getDouble("cena");
            }
            ps.close();
            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return pricePerMinute;
    }


}
