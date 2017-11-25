package net.yuvideo.voipRacuni.Controllers;

import net.yuvideo.voipRacuni.classes.CSVData;
import net.yuvideo.voipRacuni.classes.Database;
import net.yuvideo.voipRacuni.classes.FIXX;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.Initializable;
import javafx.geometry.Side;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.util.StringConverter;

import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.ResourceBundle;

/**
 * Created by PsyhoZOOM@gmail.com on 7/11/17.
 */
public class statistika implements Initializable {
    public DatePicker dtpDoDatuma;
    public DatePicker dtpOdDatuma;
    public PieChart pieDoZemlje;
    public PieChart pieOdBroja;
    public PieChart pieKaBroju;
    public TextField tUserSearch;
    public Button bSearch;
    public PieChart pieOdKorisnikaKa;
    public PieChart pieKaKorisnikuOd;
    public Database db;
    DateTimeFormatter formatterYMD = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    private URL location;
    private ResourceBundle resources;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.location = location;
        this.resources = resources;


        dtpOdDatuma.setValue(LocalDate.parse(LocalDate.now().format(formatterYMD)));
        dtpDoDatuma.setValue(LocalDate.parse(LocalDate.now().format(formatterYMD)));

        dtpDoDatuma.setConverter(new StringConverter<LocalDate>() {
            @Override
            public String toString(LocalDate object) {
                return object.format(formatterYMD);
            }

            @Override
            public LocalDate fromString(String string) {
                return LocalDate.parse(dtpDoDatuma.getValue().toString());
            }
        });

        dtpOdDatuma.setConverter(new StringConverter<LocalDate>() {
            @Override
            public String toString(LocalDate object) {
                return object.format(formatterYMD);
            }

            @Override
            public LocalDate fromString(String string) {
                return LocalDate.parse(dtpOdDatuma.getValue().toString());
            }
        });


        pieDoZemlje.setTitle("Pozivi po zemljama u minutima");
        pieDoZemlje.setLabelLineLength(10);
        pieDoZemlje.setLegendVisible(true);
        pieDoZemlje.setLegendSide(Side.BOTTOM);


    }


    public void showStatistika(ActionEvent actionEvent) {
        pieDoZemlje.getData().clear();
        ArrayList<CSVData> allCSVData = FIXX.getSumCountryChargetTimeS(db, dtpOdDatuma.getValue().toString(), dtpDoDatuma.getValue().toString());
        for (CSVData data : allCSVData) {
            pieDoZemlje.getData().add(new PieChart.Data(data.getCountry() + " " + data.getChargedTimeSec(), data.getChargedTimeSec()));
        }

        final Label caption = new Label("");
        caption.setTextFill(Color.DARKORANGE);
        caption.setStyle("-fx-font: 24 arial;");

        for (final PieChart.Data data : pieDoZemlje.getData()) {
            data.getNode().addEventHandler(MouseEvent.MOUSE_PRESSED,
                    new EventHandler<MouseEvent>() {
                        @Override
                        public void handle(MouseEvent e) {
                            caption.setTranslateX(e.getSceneX());
                            caption.setTranslateY(e.getSceneY());
                            caption.setText(String.valueOf(data.getPieValue()) + "min.");
                        }
                    });
        }

    }
}
