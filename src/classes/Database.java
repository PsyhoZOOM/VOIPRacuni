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
        } catch (SQLException e) {
            e.printStackTrace();
        }


    }

    //INIT DATABASE IF NOT EXISTS
    private void createTables() {
        String queryKorisnici = "" +
                "CREATE TABLE IF NOT EXISTS `korisnici` (\n" +
                "\t`id`\tINTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,\n" +
                "\t`imePrezime`\tTEXT,\n" +
                "\t`adresa`\tTEXT,\n" +
                "\t`mesto`\tTEXT,\n" +
                "\t`postbr`\tTEXT,\n" +
                "\t`brUgovora`\tTEXT\n" +
                ");";

        String queryBrojevi = "" +
                "CREATE TABLE IF NOT EXISTS `brojevi` (\n" +
                "\t`id`\tINTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,\n" +
                "\t`userID`\tINTEGER,\n" +
                "\t`brTel`\tTEXT\n" +
                ");";

        String queryZoneCene = "CREATE TABLE IF NOT EXISTS `zoneCene` (\n" +
                "\t`id`\tINTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,\n" +
                "\t`vrstaUsluge`\tTEXT,\n" +
                "\t`providerCena`\tREAL,\n" +
                "\t`providerCenaPDV`\tREAL,\n" +
                "\t`cena`\tREAL,\n" +
                "\t`PDV`\tREAL,\n" +
                "\t`cenaPDV`\tREAL,\n" +
                "\t`otherCena`\tREAL,\n" +
                "\t`razlika`\tREAL\n" +
                ");" +
                "";

        String queryCSV = "" +
                "CREATE TABLE IF NOT EXISTS `csv` (\n" +
                "\t`id`\tINTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,\n" +
                "\t`account`\tTEXT,\n" +
                "\t`from`\tTEXT,\n" +
                "\t`to`\tTEXT,\n" +
                "\t`country`\tTEXT,\n" +
                "\t`description`\tTEXT,\n" +
                "\t`connectTime`\tTEXT,\n" +
                "\t`chargedTimeMS`\tTEXT,\n" +
                "\t`chargedTimeS`\tINTEGER,\n" +
                "\t`chargedAmountRSD`\tREAL,\n" +
                "\t`serviceName`\tTEXT,\n" +
                "\t`chargedQuantity`\tINTEGER,\n" +
                "\t`serviceUnit`\tTEXT\n" +
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
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


}
