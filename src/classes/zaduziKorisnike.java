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
            zaduziSingleUserSaobracaj(user);
            zaduziSingleUserPaket(user);
        }


    }

    private void zaduziSingleUserPaket(Users user) {
        PreparedStatement ps;
        ResultSet rs;
        String query;
        String mesecKreiranja = LocalDate.parse(user.getDatumPrikljucka(), dateDateTimeFormater).format(monthDateTimeFormater);


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
        if (mesecZaduzenja.equals(mesecKreiranja)) {
            LocalDate datum = LocalDate.parse(mesecZaduzenja, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
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
            ps.setString(3, String.valueOf(LocalDate.parse(mesecZaduzenja, dateDateTimeFormater).format(monthDateTimeFormater)));
            ps.setDouble(4, zoneCene.getPretplata());
            ps.setString(5, "Paket");
            ps.executeUpdate();
            ps.close();
        } catch (SQLException e) {
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
        csvUserDataArrayList = FIXX.getCSVDataDescriptionDistinct(user.getId(), user.getBrojTelefona(), start, stop, db);

        for (CSVData csvData : csvUserDataArrayList) {
            destination des = new destination();
            des.setOpisDestinacije(csvData.getDescription());
            des.setMinutaZaNaplatu(fixx.getCSVDataChargedTimeS_SUM(user.getBrojTelefona(), csvData.getDescription(),
                    start, stop, db));
            //TODO  set gratis
            System.out.println(des.getOpisDestinacije());
            if (des.getOpisDestinacije().equals("Srbija Fiksna")) {
                if (des.getMinutaZaNaplatu() <= 60) {
                    des.setMinutaZaNaplatu(0);
                } else {
                    des.setMinutaZaNaplatu(des.getMinutaZaNaplatu() - 60);
                }
            }
            destinationsArrayList.add(des);
        }

        //izbrisati CSV i zaduzenje od accounta za taj mesec u slucaju dupliciranja importovanog fajla
        query = "DELETE FROM zaduzenja where zaMesec=? and userID=? AND uplaceno=0";
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
            Zone zoneData = fixx.getZoneData(destination.getOpisDestinacije());
            ZoneCene zoneCene = fixx.getZoneCeneData(zoneData.getZonaID());
            try {
                ps = db.connection.prepareStatement(query);
                ps.setString(1, LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")).toString());
                ps.setInt(2, user.getId());
                ps.setString(3, datumStart.format(DateTimeFormatter.ofPattern("yyyy-MM")));
                ps.setDouble(4, destination.getMinutaZaNaplatu() * zoneCene.getCena());
                ps.setString(5, "Saobracaj");
                ps.setString(6, destination.getOpisDestinacije());
                ps.setInt(7, zoneData.getId());
                ps.setInt(8, zoneData.getZonaID());
                ps.setInt(9, destination.getMinutaZaNaplatu());
                if (destination.getMinutaZaNaplatu() != 0)
                    ps.executeUpdate();
                ps.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }


    }




}
