package Controllers;

import classes.CSVData;
import classes.Database;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.NodeOrientation;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.util.Callback;

import java.awt.event.ActionEvent;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;

/**
 * Created by zoom on 4/25/17.
 */
public class CSVEdit implements Initializable {
    public Database db;
    ButtonType yes = new ButtonType("Da", ButtonBar.ButtonData.OK_DONE);
    ButtonType no = new ButtonType("Ne", ButtonBar.ButtonData.NO);
    private URL location;
    private ResourceBundle resources;
    private TableView<CSVData> tblCSVDAta;
    private TableColumn cAccount;
    private TableColumn cFrom;
    private TableColumn cTo;
    private TableColumn cConnecTime;
    private TableColumn cChargedAmountRSD;
    private TableColumn cDescription;
    private TableColumn cChargedTimeMS;
    private TableColumn cCustomerID;
    private TableColumn cCountry;
    private Button bIzbrisi = new Button("Izbrisi");
    private Button bOsvezi = new Button("Osvezi");

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.location = location;
        this.resources = resources;
        tblCSVDAta = new TableView<CSVData>();
        tblCSVDAta.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        tblCSVDAta.setTableMenuButtonVisible(true);
        cAccount = new TableColumn("Account");
        cChargedAmountRSD = new TableColumn("Naplaćeno");
        cFrom = new TableColumn("od broja");
        cTo = new TableColumn("za broj");
        cConnecTime = new TableColumn("Vreme konekcije");
        cDescription = new TableColumn("Opis");
        cChargedTimeMS = new TableColumn("Naplaceno vreme Min.Sek.");
        cCustomerID = new TableColumn("Customer ID");
        cCountry = new TableColumn("Zemlja");


        cAccount.setCellFactory(new Callback<TableColumn, TableCell>() {
            @Override
            public TableCell call(TableColumn param) {
                TableCell cell = new TableCell<CSVData, String>() {
                    @Override
                    public void updateItem(String item, boolean empty) {
                        super.updateItem(item, empty);
                        setText(item);
                    }

                    private String getString() {
                        return getItem() == null ? "" : getId().toString();
                    }
                };
                cell.setStyle("-fx-alignment: CENTER");
                return cell;
            }
        });

        cAccount.setCellValueFactory(new PropertyValueFactory<CSVData, String>("account"));
        cFrom.setCellValueFactory(new PropertyValueFactory<CSVData, String>("from"));
        cTo.setCellValueFactory(new PropertyValueFactory<CSVData, String>("to"));
        cConnecTime.setCellValueFactory(new PropertyValueFactory<CSVData, String>("connectTime"));
        cChargedAmountRSD.setCellValueFactory(new PropertyValueFactory<CSVData, Double>("chargedAmountRSD"));
        cDescription.setCellValueFactory(new PropertyValueFactory<CSVData, String>("description"));
        cChargedTimeMS.setCellValueFactory(new PropertyValueFactory<CSVData, String>("chargedTimeMinSec"));
        cCustomerID.setCellValueFactory(new PropertyValueFactory<CSVData, String>("customerID"));
        cCountry.setCellValueFactory(new PropertyValueFactory<CSVData, String>("country"));


        initInterface();

    }

    private void initInterface() {
        AnchorPane anchorPane = new AnchorPane();
        Parent root = anchorPane;
        Scene scene = new Scene(root);
        ColumnConstraints cc = new ColumnConstraints();
        RowConstraints rc = new RowConstraints();
        Stage stage = new Stage();

        scene.getStylesheets().add(getClass().getResource("/css/Main.css").toExternalForm());
        stage.setTitle("EDIT CSV");

        tblCSVDAta.getColumns().addAll(cCustomerID, cAccount, cFrom, cTo, cCountry, cDescription, cConnecTime, cChargedAmountRSD, cChargedTimeMS);
        tblCSVDAta.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

        GridPane gridPane = new GridPane();


        HBox bBox = new HBox();

        bBox.setSpacing(5);
        bBox.setPadding(new Insets(5.00, 5.00, 5.00, 5.00));

        gridPane.setHgap(5);
        gridPane.setVgap(5);
        gridPane.setAlignment(Pos.CENTER);

        bBox.getChildren().add(bIzbrisi);
        bBox.getChildren().add(bOsvezi);
        bBox.setNodeOrientation(NodeOrientation.RIGHT_TO_LEFT);

        gridPane.add(bBox, 1, 0);

        gridPane.getColumnConstraints().add(cc);
        gridPane.getRowConstraints().add(new RowConstraints());
        gridPane.add(tblCSVDAta, 0, 1, 2, 1);
        cc.setHgrow(Priority.ALWAYS);
        rc.setVgrow(Priority.ALWAYS);
        gridPane.getRowConstraints().add(rc);

        gridPane.paddingProperty().setValue(new Insets(5));

        anchorPane.setMinHeight(600.00);
        anchorPane.setMinWidth(900.00);
        AnchorPane.setTopAnchor(gridPane, 10.0);
        AnchorPane.setLeftAnchor(gridPane, 10.0);
        AnchorPane.setRightAnchor(gridPane, 10.0);
        AnchorPane.setBottomAnchor(gridPane, 10.0);
        anchorPane.getChildren().add(gridPane);

        setListeners();


        stage.setScene(scene);
        stage.showAndWait();


    }

    private void setListeners() {

        bOsvezi.setOnAction(new EventHandler<javafx.event.ActionEvent>() {
            @Override
            public void handle(javafx.event.ActionEvent event) {
                showTable();
            }
        });


        bIzbrisi.setOnAction(new EventHandler<javafx.event.ActionEvent>() {
            @Override
            public void handle(javafx.event.ActionEvent event) {
                if (tblCSVDAta.getSelectionModel().getSelectedIndex() == -1)
                    return;

                Alert aler = new Alert(Alert.AlertType.CONFIRMATION, "Da li ste sigurni da želite da izbrišete?", yes, no);
                aler.setTitle("Brisanje CSV-a");
                aler.showAndWait();
                if (aler.getResult() == no)
                    return;
                ObservableList<CSVData> csvDataArrayList = tblCSVDAta.getSelectionModel().getSelectedItems();
                PreparedStatement ps = null;
                String query = "DELETE FROM csv WHERE id=?";
                try {
                    ps = db.connection.prepareStatement(query);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                for (int i = 0; i < csvDataArrayList.size(); i++) {
                    try {
                        ps.setInt(1, csvDataArrayList.get(i).getId());
                        ps.executeUpdate();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }


                }
            }
        });
        showTable();

    }

    private void showTable() {
        PreparedStatement ps;
        ResultSet rs;
        String query = "SELECT * FROM csv";
        ArrayList<CSVData> csvDataArrayList = new ArrayList<CSVData>();
        CSVData csvData;

        try {
            ps = db.connection.prepareStatement(query);
            rs = ps.executeQuery();
            if (rs.isBeforeFirst()) {
                while (rs.next()) {
                    csvData = new CSVData();
                    csvData.setId(rs.getInt("id"));
                    csvData.setAccount(rs.getString("account"));
                    csvData.setFrom(rs.getString("from"));
                    csvData.setTo(rs.getString("to"));
                    csvData.setCountry(rs.getString("country"));
                    csvData.setDescription(rs.getString("description"));
                    csvData.setConnectTime(rs.getString("connectTime"));
                    csvData.setChargedTimeMinSec(rs.getString("chargedTimeMS"));
                    csvData.setChargedTimeSec(rs.getInt("chargedTimeS"));
                    csvData.setChargedAmountRSD(rs.getDouble("chargedAmountRSD"));
                    csvData.setServiceName(rs.getString("serviceName"));
                    csvData.setChargedQuantity(rs.getInt("chargedQuantity"));
                    csvData.setServiceUnit(rs.getString("serviceUnit"));
                    csvData.setCustomerID(rs.getString("customerID"));
                    csvData.setFileName(rs.getString("fileName"));
                    csvDataArrayList.add(csvData);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        ObservableList data = FXCollections.observableArrayList(csvDataArrayList);
        tblCSVDAta.setItems(data);


    }

    private void setbOsvezi(ActionEvent event) {
        System.out.println(event.getActionCommand());
    }



}
