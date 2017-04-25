package classes;

import java.sql.*;

/**
 * Created by PsyhoZOOM@gmail.com on 4/21/17.
 */
public class Database {

    public Connection connection;


    public Database() {
        try {
            Class.forName("org.sqlite.JDBC");
            connection = DriverManager.getConnection("jdbc:sqlite:data.db");

            create_new_database();


        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }


    }

    private void create_new_database() {
        try {
            DatabaseMetaData dbmtd = connection.getMetaData();
            ResultSet rs = dbmtd.getTables(null, null, "%", null);
            if (!rs.isBeforeFirst()) {
                createTables();
            }
            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }


    }

    //INIT DATABASE IF NOT EXISTS
    private void createTables() {
        String queryKorisnici = "" +
                "CREATE TABLE `korisnici` (\n" +
                "        `id`    INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,\n" +
                "        `imePrezime`    TEXT,\n" +
                "        `adresa`        TEXT,\n" +
                "        `mesto` TEXT,\n" +
                "        `postbr`        TEXT,\n" +
                "        `brUgovora`     TEXT\n" +
                ");\n";

        String queryBrojevi = "" +
                "CREATE TABLE `brojevi` (\n" +
                "        `id`    INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,\n" +
                "        `userID`        INTEGER,\n" +
                "        `brTel` TEXT\n" +
                ");\n";

        String queryZoneCene = "" +
                "CREATE TABLE \"zoneCene\" (\n" +
                "        `id`    INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,\n" +
                "        `vrstaUsluge`   TEXT,\n" +
                "        `providerCena`  REAL,\n" +
                "        `providerPDV`   REAL,\n" +
                "        `cena`  REAL,\n" +
                "        `PDV`   REAL,\n" +
                "        `cenaPDV`       REAL,\n" +
                "        `otherCena`     REAL\n" +
                ");\n";

        String queryCSV = "" +
                "CREATE TABLE `csv` (\n" +
                "        `id`    INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,\n" +
                "        `account`       TEXT,\n" +
                "        `from`  TEXT,\n" +
                "        `to`    TEXT,\n" +
                "        `country`       TEXT,\n" +
                "        `description`   TEXT,\n" +
                "        `connectTime`   TEXT,\n" +
                "        `chargedTimeMS` TEXT,\n" +
                "        `chargedTimeS`  INTEGER,\n" +
                "        `chargedAmountRSD`      REAL,\n" +
                "        `serviceName`   TEXT,\n" +
                "        `chargedQuantity`       INTEGER,\n" +
                "        `serviceUnit`   TEXT\n" +
                ");\n";


        try {
            Statement st = connection.createStatement();
            st.executeUpdate(queryBrojevi);
            st = connection.createStatement();
            st.executeUpdate(queryCSV);
            st = connection.createStatement();
            st.executeUpdate(queryKorisnici);
            st = connection.createStatement();
            st.executeUpdate(queryZoneCene);
            st.close();
            connection.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


}
