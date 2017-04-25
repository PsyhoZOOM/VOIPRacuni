package Controllers;

import classes.CSVData;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.NodeOrientation;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by zoom on 4/25/17.
 */
public class CSVEdit implements Initializable {
    private URL location;
    private ResourceBundle resources;
    private TableView<CSVData> tblCSVDAta;
    private TableColumn cAccount;
    private TableColumn cFrom;
    private TableColumn cTo;
    private TableColumn cConnecTime;
    private TableColumn cChargedAmountRSD;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.location = location;
        this.resources = resources;
        tblCSVDAta = new TableView<CSVData>();
        tblCSVDAta.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        tblCSVDAta.setTableMenuButtonVisible(true);
        cAccount = new TableColumn("Account");
        cChargedAmountRSD = new TableColumn("Charged Amoaunt");
        cFrom = new TableColumn("od broja");
        cTo = new TableColumn("za broj");
        cConnecTime = new TableColumn("Vreme konekcija");

        cAccount.setCellValueFactory(new PropertyValueFactory<CSVData, String>("account"));
        cFrom.setCellValueFactory(new PropertyValueFactory<CSVData, String>("from"));
        cTo.setCellValueFactory(new PropertyValueFactory<CSVData, String>("to"));
        cConnecTime.setCellValueFactory(new PropertyValueFactory<CSVData, String>("connectTime"));
        cChargedAmountRSD.setCellValueFactory(new PropertyValueFactory<CSVData, Double>("chargedAmountRSD"));


        initInterface();


    }

    private void initInterface() {
        AnchorPane anchorPane = new AnchorPane();
        Parent root = anchorPane;
        Scene scene = new Scene(root);
        scene.getStylesheets().add(getClass().getResource("/css/Main.css").toExternalForm());
        Stage stage = new Stage();
        stage.setTitle("EDIT CSV");
        stage.setScene(scene);
        tblCSVDAta.getColumns().addAll(cAccount, cFrom, cTo, cConnecTime, cChargedAmountRSD);
        tblCSVDAta.setMaxHeight(Double.MAX_VALUE);
        tblCSVDAta.setMaxWidth(Double.MAX_VALUE);
        GridPane gridPane = new GridPane();
        Button bIzbrisi = new Button("Izbrisi");
        Button bOsvezi = new Button("Osvezi");
        HBox bBox = new HBox();
        bBox.setSpacing(5);
        bBox.setPadding(new Insets(5.00, 5.00, 5.00, 5.00));

        gridPane.setHgap(5);
        gridPane.setVgap(5);
        gridPane.autosize();
        anchorPane.autosize();
        gridPane.setAlignment(Pos.CENTER);
        bBox.getChildren().add(bIzbrisi);
        bBox.getChildren().add(bOsvezi);
        bBox.setNodeOrientation(NodeOrientation.RIGHT_TO_LEFT);
        gridPane.add(bBox, 1, 0);
        gridPane.add(tblCSVDAta, 0, 1);

        anchorPane.getChildren().add(gridPane);

        stage.showAndWait();


    }
}
