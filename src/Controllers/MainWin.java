package Controllers;

import classes.CSVData;
import classes.Database;
import classes.FIXX;
import com.csvreader.CsvReader;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.BorderPane;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.apache.commons.io.FilenameUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

/**
 * Created by PsyhoZOOM@gmail.com on 4/20/17.
 */
public class MainWin implements Initializable {
    public MenuItem menuOpenCSV;
    public MenuItem meniExit;
    public MenuItem menuAbout;
    public BorderPane bPane;
    public MenuItem menuPrikaziUsluge;
    public MenuItem menuShowCSV;
    public Label lMessage;
    public ProgressBar progBarImport;
    public MenuItem menuPrikaziZone;
    public MenuItem menuPaketi;
    public MenuItem menuStampa;
    public MenuItem menuMesecniObracun;
    public MenuItem menuIzvestaji;
    public MenuItem menuStatistika;
    URL location;
    ResourceBundle resources;
    FXMLLoader fxmlLoader;
    korisniciWin korisniciWinController;
    FIXX fixx;
    private Database db;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.location = location;
        this.resources = resources;
        fxmlLoader = new FXMLLoader(getClass().getResource("/FXML/korisniciWin.fxml"), resources);


    }

    public void setDB(Database db) {
        this.db = db;
        try {
            bPane.setCenter(fxmlLoader.load());
            korisniciWinController = fxmlLoader.getController();
            korisniciWinController.db = db;
            korisniciWinController.setData(null);
            fixx = new FIXX(db);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void showUsluge(ActionEvent actionEvent) {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/FXML/zoneCene.fxml"), resources);

        try {
            Scene scene = new Scene(fxmlLoader.load());
            zoneCene zoneCeneController = fxmlLoader.getController();
            zoneCeneController.db = db;
            zoneCeneController.showData("");
            Stage stage = new Stage();
            stage.initOwner(bPane.getScene().getWindow());
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setTitle("Izmena Usluge-Zona");
            stage.setScene(scene);
            stage.showAndWait();
            zoneCeneController.showData("");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void openCSV(ActionEvent actionEvent) {
        //OPEN CSV file and set extensions filter
        FileChooser fileChooser = new FileChooser();
        FileChooser.ExtensionFilter extFilter =
                new FileChooser.ExtensionFilter("Mesečni obračun CSV Fajl", "*.csv");
        fileChooser.getExtensionFilters().add(extFilter);
        fileChooser.setInitialDirectory(new File(System.getProperty("user.home")));
        fileChooser.setTitle("Import CSV fajl");

        final List<File> lf = fileChooser.showOpenMultipleDialog(bPane.getScene().getWindow());
        final List<File> csvFiles = new ArrayList<>();


                String message;
                for (int i = 0; i < lf.size(); i++) {
                        csvFiles.add(lf.get(i));
                    System.out.println("Fajl: " + lf.get(i).getAbsoluteFile() + " je importovan");
                }
                exportCSVtoDatabase(csvFiles);

        Alert alert = new Alert(Alert.AlertType.INFORMATION, "Import CSV Fajla i obracun zavrsen");
                alert.showAndWait();





    }


    private void exportCSVtoDatabase(List<File> csvFiles) {

        PreparedStatement ps;
        CsvReader csvReader;
        CSVData csvData;
        String fileName;
        String customerID;
        String datumZaduzenja = null;

        final ArrayList<CSVData> csvDataArrayList = new ArrayList<CSVData>();
        for (int i = 0; i < csvFiles.size(); i++) {
            fileName = FilenameUtils.getBaseName(csvFiles.get(i).getName());
            customerID = fileName.substring(fileName.lastIndexOf("-"));
            customerID = customerID.replace("-customer", "");


            try {

                csvReader = new CsvReader(new FileReader(csvFiles.get(i)));
                csvReader.setDelimiter(',');
                csvReader.readHeaders();
                while (csvReader.readRecord()) {
                    if (csvReader.get("Account").equals("SUBTOTAL") || csvReader.get("Account").isEmpty())
                        break;
                    csvData = new CSVData();
                    csvData.setAccount(csvReader.get("Account"));
                    csvData.setFrom(csvReader.get("From"));
                    csvData.setTo(csvReader.get("To"));
                    csvData.setCountry(csvReader.get("Country"));
                    csvData.setDescription(csvReader.get("Description"));
                    csvData.setConnectTime(csvReader.get("Connect Time"));
                    csvData.setChargedTimeMinSec(csvReader.get("Charged Time, min:sec"));
                    csvData.setChargedTimeSec(Integer.parseInt(csvReader.get("Charged Time, sec.")));
                    csvData.setChargedAmountRSD(Double.parseDouble(csvReader.get("Charged Amount, RSD")));
                    csvData.setServiceName(csvReader.get("Service Name"));
                    csvData.setChargedQuantity(Integer.parseInt(csvReader.get("Charged quantity")));
                    csvData.setServiceUnit(csvReader.get("Service unit"));
                    csvData.setCustomerID(customerID);
                    csvData.setFileName(fileName);
                    csvDataArrayList.add(csvData);
                }
            } catch (FileNotFoundException e) {
                Alert alert = new Alert(Alert.AlertType.ERROR, e.getMessage());
                alert.showAndWait();
                e.printStackTrace();
            } catch (IOException e) {
                Alert alert = new Alert(Alert.AlertType.ERROR, e.getMessage());
                e.printStackTrace();
            }


        }

        for (int i = 0; i < csvDataArrayList.size(); i++) {
            CSVData csvDataSQL = csvDataArrayList.get(i);

            String query = "INSERT INTO csv (account, `from`, `to`, country, description, connectTime, chargedTimeMS," +
                    " chargedTimeS, chargedAmountRSD, serviceName, chargedQuantity, serviceUnit, customerID, fileName) " +
                    "VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
            try {
                ps = db.connection.prepareStatement(query);
                ps.setString(1, csvDataSQL.getAccount());
                ps.setString(2, csvDataSQL.getFrom());
                ps.setString(3, csvDataSQL.getTo());
                ps.setString(4, csvDataSQL.getCountry());
                ps.setString(5, csvDataSQL.getDescription());
                ps.setString(6, csvDataSQL.getConnectTime());
                ps.setString(7, csvDataSQL.getChargedTimeMinSec());
                ps.setInt(8, csvDataSQL.getChargedTimeSec());
                ps.setDouble(9, csvDataSQL.getChargedAmountRSD());
                ps.setString(10, csvDataSQL.getServiceName());
                ps.setInt(11, csvDataSQL.getChargedQuantity());
                ps.setString(12, csvDataSQL.getServiceUnit());
                ps.setString(13, csvDataSQL.getCustomerID());
                ps.setString(14, csvDataSQL.getFileName());
                datumZaduzenja = String.valueOf(LocalDate.parse(csvDataSQL.getConnectTime(), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
                ps.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            }

        }



    }



    public void showCSV(ActionEvent actionEvent) {
        CSVEdit csvEdit = new CSVEdit();
        csvEdit.db = db;
        csvEdit.initialize(location, resources);
    }

    public void showZone(ActionEvent actionEvent) {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/FXML/editZone.fxml"), resources);

        try {
            Scene scene = new Scene(fxmlLoader.load());
            editZone editZonecontroller = fxmlLoader.getController();
            editZonecontroller.db = db;
            editZonecontroller.showData("");
            Stage stage = new Stage();
            stage.initOwner(bPane.getScene().getWindow());
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setTitle("Izmene Zona");
            stage.setScene(scene);
            stage.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void showPaketi(ActionEvent actionEvent) {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/FXML/editPaketi.fxml"), resources);

        try {
            Scene scene = new Scene(fxmlLoader.load());
            editPaket editPaketController = fxmlLoader.getController();
            editPaketController.db = db;
            editPaketController.showData("");
            Stage stage = new Stage();
            stage.initOwner(bPane.getScene().getWindow());
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setTitle("Izmena Paketa");
            stage.setScene(scene);
            stage.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void showStampa(ActionEvent actionEvent) {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/FXML/stampaRacuna.fxml"), resources);


        try {
            Scene scene = new Scene(fxmlLoader.load());
            stampaRacuna stampaRacunaController = fxmlLoader.getController();
            stampaRacunaController.db = db;
            Stage stage = new Stage();
            stage.initOwner(bPane.getScene().getWindow());
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setTitle("Štampa računa");
            stage.setScene(scene);
            stage.showAndWait();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void showMesecniObracune(ActionEvent actionEvent) {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/FXML/mesecniObracuni.fxml"), resources);

        try {
            Scene scene = new Scene(fxmlLoader.load());
            MesecniObracuni mesecniObracunController = fxmlLoader.getController();
            mesecniObracunController.db = db;
            mesecniObracunController.check_if_obracun_exists(LocalDate.now().minusMonths(1));


            Stage stage = new Stage();
            stage.initOwner(bPane.getScene().getWindow());
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setTitle("Mesečni obračuni");
            stage.setScene(scene);
            stage.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void showIzvestaje(ActionEvent actionEvent) {
    }

    public void showStatistika(ActionEvent actionEvent) {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/FXML/statistika.fxml"), resources);

        try {
            Scene scene = new Scene(fxmlLoader.load());
            statistika statistikaController = fxmlLoader.getController();
            statistikaController.db = db;

            Stage stage = new Stage();
            stage.initOwner(bPane.getScene().getWindow());
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setTitle("Statistika");
            stage.setScene(scene);
            stage.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
