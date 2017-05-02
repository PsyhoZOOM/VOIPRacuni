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
                "CREATE TABLE IF NOT EXISTS`korisnici` (\n" +
                "\t`id`\tINTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,\n" +
                "\t`imePrezime`\tTEXT,\n" +
                "\t`adresa`\tTEXT,\n" +
                "\t`mesto`\tTEXT,\n" +
                "\t`postbr`\tTEXT,\n" +
                "\t`brUgovora`\tTEXT,\n" +
                "\t`customerID`\tTEXT\n" +
                ");";

        String queryBrojevi = "" +
                "CREATE TABLE IF NOT EXISTS  `brojevi` (\n" +
                "        `id`    INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,\n" +
                "        `userID`        INTEGER,\n" +
                "        `brTel` TEXT\n" +
                ");\n";

        String queryZoneCene = "" +
                "CREATE TABLE IF NOT EXISTS \"zoneCene\" (\n" +
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
                "CREATE TABLE IF NOT EXISTS  `csv` (\n" +
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
                "        `serviceUnit`   TEXT,\n" +
                "         `customerID`	TEXT,\n" +
                "         `fileName` TEXT\n);";

        String queryZone = "" +
                "CREATE TABLE `zone` (\n" +
                "\t`id`\tINTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,\n" +
                "\t`naziv`\tTEXT,\n" +
                "\t`opis`\tTEXT,\n" +
                "\t`zona`\tTEXT,\n" +
                "\t`uslugaID`\tINTEGER\n" +
                ");";



        try {
            Statement st = connection.createStatement();
            st.executeUpdate(queryBrojevi);
            st = connection.createStatement();
            st.executeUpdate(queryCSV);
            st = connection.createStatement();
            st.executeUpdate(queryKorisnici);
            st = connection.createStatement();
            st.executeUpdate(queryZoneCene);
            st = connection.createStatement();
            st.executeUpdate(queryZone);

            st.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


}
