package Controllers;

import classes.CSVData;
import classes.Database;
import com.csvreader.CsvReader;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.BorderPane;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.*;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

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
    URL location;
    ResourceBundle resources;
    FXMLLoader fxmlLoader;
    korisniciWin korisniciWinController;
    Database db = new Database();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.location = location;
        this.resources = resources;
        fxmlLoader = new FXMLLoader(getClass().getResource("/FXML/korisniciWin.fxml"), resources);
        try {
            bPane.setCenter((Node) fxmlLoader.load());
            korisniciWinController = fxmlLoader.getController();
            korisniciWinController.db = db;
            korisniciWinController.setData(null);
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    public void showUsluge(ActionEvent actionEvent) {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/FXML/zoneCene.fxml"), resources);

        try {
            Scene scene = new Scene((Parent) fxmlLoader.load());
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
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("Mesečni obračun CSV Fajl", "csv");
        FileChooser.ExtensionFilter extFilterAll = new FileChooser.ExtensionFilter("Svi fajlovi", "*.zip", "*.csv", "*.*");
        FileChooser.ExtensionFilter extensionFilter = new FileChooser.ExtensionFilter("Mesecčni obračun Zipovani-CSV Fajl", "*.zip");
        fileChooser.getExtensionFilters().add(extensionFilter);
        fileChooser.getExtensionFilters().add(extFilter);
        fileChooser.getExtensionFilters().add(extFilterAll);
        fileChooser.setInitialDirectory(new File(System.getProperty("user.home")));
        fileChooser.setTitle("Import Zipovani-CSV fajl");

        List<File> lf = fileChooser.showOpenMultipleDialog(bPane.getScene().getWindow());
        List<File> csvFiles = new ArrayList<File>();


        for (int i = 0; i < lf.size(); i++) {
            if (lf.get(i).getName().toLowerCase().endsWith(".zip")) {
                System.out.println("Unzipping file: " + lf.get(i));
                List<File> lsFileTmp = unZipFile(lf.get(i));
                for (int a = 0; a < lsFileTmp.size(); a++) {
                    System.out.println("UNPACKED: " + lsFileTmp.get(a).getName());
                    csvFiles.add(lsFileTmp.get(a));
                }

            } else {
                System.out.println("No need for unzip of file " + lf.get(i).getName());
                csvFiles.add(lf.get(i));
            }
        }

        exportCSVtoDatabase(csvFiles);
    }

    private void exportCSVtoDatabase(List<File> csvFiles) {
        PreparedStatement ps = null;
        CsvReader csvReader;
        CSVData csvData;
        ArrayList<CSVData> csvDataArrayList = new ArrayList<CSVData>();
        for (int i = 0; i < csvFiles.size(); i++) {
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
                    csvData.setDescrition(csvReader.get("Description"));
                    csvData.setConnectTime(csvReader.get("Connect Time"));
                    csvData.setChargedTimeMinSec(csvReader.get("Charged Time, min:sec"));
                    csvData.setChargedTimeSec(Integer.parseInt(csvReader.get("Charged Time, sec.")));
                    csvData.setChargetAmmountRSD(Double.parseDouble(csvReader.get("Charged Amount, RSD")));
                    csvData.setServiceName(csvReader.get("Service Name"));
                    csvData.setChargedQuantity(Integer.parseInt(csvReader.get("Charged quantity")));
                    csvData.setServiceUnit(csvReader.get("Service unit"));
                    csvDataArrayList.add(csvData);
                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }

        for (int i = 0; i < csvDataArrayList.size(); i++) {
            CSVData csvDataSQL = csvDataArrayList.get(i);
            String query = "INSERT INTO csv (account, `from`, `to`, country, description, connectTime, chargedTimeMS, chargedTimeS, chargedAmountRSD, serviceName, chargedQuantity, serviceUnit) VALUES (?,?,?,?,?,?,?,?,?,?,?,?)";
            try {
                ps = db.connection.prepareStatement(query);
                ps.setString(1, csvDataSQL.getAccount());
                ps.setString(2, csvDataSQL.getFrom());
                ps.setString(3, csvDataSQL.getTo());
                ps.setString(4, csvDataSQL.getCountry());
                ps.setString(5, csvDataSQL.getDescrition());
                ps.setString(6, csvDataSQL.getConnectTime());
                ps.setString(7, csvDataSQL.getChargedTimeMinSec());
                ps.setInt(8, csvDataSQL.getChargedTimeSec());
                ps.setDouble(9, csvDataSQL.getChargetAmmountRSD());
                ps.setString(10, csvDataSQL.getServiceName());
                ps.setInt(11, csvDataSQL.getChargedQuantity());
                ps.setString(12, csvDataSQL.getServiceUnit());
                ps.executeUpdate();
            } catch (SQLException e) {
                System.out.println(e.getMessage());
                e.printStackTrace();
            }

        }
    }


    private List<File> unZipFile(File file) {
        List<File> csvFiles = new ArrayList<File>();
        ZipInputStream zipInputStream;
        ZipEntry zipEntry;
        FileInputStream fileInputStream;
        File newFile = null;
        byte[] buffer = new byte[1024];

        try {
            newFile = File.createTempFile(file.getName().toLowerCase().replace(".zip", ""), ".tmp");
            newFile.deleteOnExit();
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            fileInputStream = new FileInputStream(file);
            zipInputStream = new ZipInputStream(fileInputStream);

            zipEntry = zipInputStream.getNextEntry();

            while (zipEntry != null) {
                File unzipedFile = File.createTempFile(zipEntry.getName().toLowerCase().replace(".zip", ""), ".csv");
                unzipedFile.deleteOnExit();
                FileOutputStream fos = new FileOutputStream(unzipedFile);
                int len;
                while ((len = zipInputStream.read(buffer)) > 0) {
                    fos.write(buffer, 0, len);
                }
                if (zipEntry.getName().toLowerCase().endsWith(".csv")) {
                    csvFiles.add(unzipedFile);
                } else {
                    if (zipEntry.getName().toLowerCase().endsWith(".zip")) {
                        List<File> dblFile = new ArrayList<File>();
                        dblFile = unZipFile(unzipedFile);
                        for (int z = 0; z < dblFile.size(); z++) {
                            csvFiles.add(dblFile.get(z));
                        }
                    }
                }
                fos.close();
                zipInputStream.closeEntry();
                zipEntry = zipInputStream.getNextEntry();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return csvFiles;
    }

    public void showCSV(ActionEvent actionEvent) {
        CSVEdit csvEdit = new CSVEdit();
        csvEdit.initialize(location, resources);
    }
}
