package net.yuvideo.voipRacuni.Controllers;

import net.yuvideo.voipRacuni.classes.Database;
import net.yuvideo.voipRacuni.classes.FIXX;
import net.yuvideo.voipRacuni.classes.Users;
import net.yuvideo.voipRacuni.classes.zaduziKorisnike;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.util.StringConverter;

import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.ResourceBundle;

/**
 * Created by PsyhoZOOM on 7/4/17.
 */
public class MesecniObracuni implements Initializable {
    public Database db;
    public DatePicker dtpObracunZaMesec;
    public Button bObracunaj;
    public Label lMessage;
    DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("yyyy-MM");
    private URL localtion;
    private ResourceBundle resources;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.localtion = location;
        this.resources = resources;

        bObracunaj.setDisable(true);
        dtpObracunZaMesec.setEditable(false);
        dtpObracunZaMesec.setConverter(new StringConverter<LocalDate>() {
            @Override
            public String toString(LocalDate object) {
                return object.format(dateFormat);
            }

            @Override
            public LocalDate fromString(String string) {
                String date = LocalDate.now().format(dateFormat);
                LocalDate localDate = LocalDate.parse(date);
                return null;
            }
        });
        dtpObracunZaMesec.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                System.out.println(dtpObracunZaMesec.getValue());
                check_if_obracun_exists(dtpObracunZaMesec.getValue());
            }
        });
        dtpObracunZaMesec.setValue(LocalDate.now().minusMonths(1));
    }

    public void check_if_obracun_exists(LocalDate value) {
        PreparedStatement ps;
        ResultSet rs;
        String query = "SELECT  zaMesec FROM zaduzenja WHERE zaMesec=?";
        boolean exist = false;


        try {
            ps = db.connection.prepareStatement(query);
            ps.setString(1, dtpObracunZaMesec.getValue().format(dateFormat));
            rs = ps.executeQuery();
            if (rs.isBeforeFirst())
                exist = true;

            ps.close();
            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        if (exist) {
            bObracunaj.setDisable(true);
            lMessage.setText(String.format("Obracun za mesec %s postoji", dtpObracunZaMesec.getValue().format(dateFormat)));

        } else {
            bObracunaj.setDisable(false);
            lMessage.setText(null);
        }
    }

    public void obracunaj(ActionEvent actionEvent) {
        PreparedStatement ps;
        ResultSet rs;
        String query;

        FIXX fix = new FIXX(db);

        ArrayList<Users> users = fix.getUsers();
        LocalDate obracunZaMesec = LocalDate.parse(dtpObracunZaMesec.getValue().toString());
        obracunZaMesec = LocalDate.of(obracunZaMesec.getYear(), obracunZaMesec.getMonthValue(), obracunZaMesec.lengthOfMonth());


        zaduziKorisnike zaduziKorisnike = new zaduziKorisnike(users, obracunZaMesec.toString(), db);


        bObracunaj.setDisable(true);

        lMessage.setText(String.format("Obracun za mesec %s  je završen. Možete zatvoriti ovaj prozor.", dtpObracunZaMesec.getValue().format(dateFormat)));


    }

}
