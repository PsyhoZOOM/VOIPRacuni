package classes;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by PsyhoZOOM on 6/8/17.
 */
public class FIXX {

    private final Database db;

    public FIXX(Database db) {
        this.db = db;
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

}
