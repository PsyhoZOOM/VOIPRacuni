package Controllers;

import classes.Database;
import classes.Users;
import classes.uplate;
import classes.valueToPercent;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Callback;

import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.ResourceBundle;

/**
 * Created by PsyhoZOOM@gmail.com on 5/11/17.
 */
public class korisnikUplate implements Initializable {
    public TableView<uplate> tblUplate;
    public TableColumn cIme;
    public TableColumn cModel;
    public TableColumn cBrTel;
    public TableColumn cZaMesec;
    public TableColumn cZaUplatu;
    public TableColumn cUplaceno;
    public TableColumn cDatumUplate;
    public Button bUplati;
    public Label lDug;
    public TableColumn cDug;
    public TableColumn cPDV;
    Database db;
    Users user;
    private URL location;
    private ResourceBundle resources;
    private DecimalFormat df = new DecimalFormat("#,###,##0.00");

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.location = location;
        this.resources = resources;

        cIme.setCellValueFactory(new PropertyValueFactory<uplate, String>("ime"));
        cModel.setCellValueFactory(new PropertyValueFactory<uplate, String>("modelPoziv"));
        cBrTel.setCellValueFactory(new PropertyValueFactory<uplate, String>("brojTel"));
        cZaMesec.setCellValueFactory(new PropertyValueFactory<uplate, String>("zaMesec"));
        cZaUplatu.setCellValueFactory(new PropertyValueFactory<uplate, Double>("zaUplatu"));
        cUplaceno.setCellValueFactory(new PropertyValueFactory<uplate, Double>("uplaceno"));
        cDatumUplate.setCellValueFactory(new PropertyValueFactory<uplate, String>("datumUplate"));
        //       cDug.setCellValueFactory(new PropertyValueFactory<uplate, Double>("dug"));
        //       cPDV.setCellValueFactory(new PropertyValueFactory<uplate, Double>("PDV"));

        cZaUplatu.setCellFactory(new Callback<TableColumn, TableCell>() {
            @Override
            public TableCell call(TableColumn param) {
                TableCell<uplate, Double> cell = new TableCell<uplate, Double>() {
                    @Override
                    public void updateItem(Double uplata, boolean bool) {
                        super.updateItem(uplata, bool);
                        if (bool) {
                            setText(null);
                        } else {
                            setText(df.format(uplata));
                        }
                    }
                };
                return cell;
            }
        });

        cUplaceno.setCellFactory(new Callback<TableColumn, TableCell>() {
            @Override
            public TableCell call(TableColumn param) {
                TableCell<uplate, Double> cell = new TableCell<uplate, Double>() {
                    @Override
                    public void updateItem(Double uplaceno, boolean bool) {
                        super.updateItem(uplaceno, bool);
                        if (bool) {
                            setText(null);
                        } else {
                            setText(df.format(uplaceno));
                        }
                    }
                };
                return cell;
            }
        });
/*
        cPDV.setCellFactory(new Callback<TableColumn, TableCell>() {
            @Override
            public TableCell<uplate, Double> call(TableColumn param) {
                TableCell<uplate, Double> cell = new TableCell<uplate, Double>(){
                    @Override
                    protected void updateItem(Double item, boolean empty) {
                        super.updateItem(item, empty);
                        if(empty){
                            setText(null);
                        }else {
                            setText(df.format(item));
                        }
                    }
                };
                return  cell;
            }
        });

        cDug.setCellFactory(new Callback<TableColumn, TableCell>() {
            @Override
            public TableCell call(TableColumn param) {
                TableCell<uplate, Double> cell = new TableCell<uplate, Double>(){

                    @Override
                    protected void updateItem(Double item, boolean empty) {
                        super.updateItem(item, empty);
                        if(empty){
                            setText(null);
                        }else{
                            setText(df.format(item));
                        }
                    }
                };
                return cell;
            }
        });


        */

        tblUplate.selectionModelProperty().addListener(new ChangeListener<TableView.TableViewSelectionModel<uplate>>() {
            @Override
            public void changed(ObservableValue<? extends TableView.TableViewSelectionModel<uplate>> observable, TableView.TableViewSelectionModel<uplate> oldValue, TableView.TableViewSelectionModel<uplate> newValue) {
                if (newValue.getSelectedItem().getUplaceno() > 0) {
                    bUplati.setDisable(true);
                } else {
                    bUplati.setDisable(false);
                }
            }
        });
    }

    public void setData() {
        ObservableList data = FXCollections.observableArrayList(getUplate(user.getId()));
        tblUplate.setItems(data);


    }

    private Double getPDV(int userPaketID){
        PreparedStatement ps;
        ResultSet rs;
        String query = "SELECT PDV from paketi WHERE id=?";
        Double pdv = 0.00;
        try {
            ps = db.connection.prepareStatement(query);
            ps.setInt(1, userPaketID);
            rs = ps.executeQuery();
            if(rs.isBeforeFirst()){
                rs.next();
                pdv = rs.getDouble("PDV");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return pdv;
    }

    private ArrayList<uplate> getUplate(int userId) {
        PreparedStatement ps;
        ResultSet rs;
        String query = "SELECT * FROM uplate WHERE userID=?";
        ArrayList<uplate> uplateArrayList = new ArrayList<>();
        uplate uplata;
        double ukupno = 0.00;

        try {
            ps = db.connection.prepareStatement(query);
            ps.setInt(1, userId);
            rs = ps.executeQuery();

            if (rs.isBeforeFirst()) {
                while (rs.next()) {
                    uplata = new uplate();
                    uplata.setId(rs.getInt("id"));
                    uplata.setIme(rs.getString("ime"));
                    uplata.setBrojTel(rs.getString("brojTel"));
                    uplata.setDug(rs.getDouble("zaUplatu"));
                    uplata.setUplaceno(rs.getDouble("uplaceno"));
                    uplata.setZaMesec(LocalDate.parse(rs.getString("datumZaduzenja"),DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm:ss")).format(DateTimeFormatter.ofPattern("MM-yyyy")));
                    uplata.setUserID(rs.getInt("userID"));
                    uplata.setDatumUplate(rs.getString("datumUplate"));
                    uplata.setPDV(getPDV(user.getNazivPaketaID()));
                    uplata.setZaUplatu(uplata.getDug() + valueToPercent.getValue(uplata.getDug(), uplata.getPDV()));
                    ukupno += uplata.getZaUplatu();
                    ukupno -= rs.getDouble("uplaceno");
                    uplateArrayList.add(uplata);
                    lDug.setText(String.valueOf(ukupno));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return uplateArrayList;
    }

    public void uplati(ActionEvent actionEvent) {
        if (tblUplate.getSelectionModel().getSelectedIndex() == -1)
            return;

        String date = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));

        uplate uplata = tblUplate.getSelectionModel().getSelectedItem();
        PreparedStatement ps;
        String query = "UPDATE uplate SET uplaceno=?, datumUplate=?, datumZaduzenja=? WHERE id=?";

        try {
            ps = db.connection.prepareStatement(query);
            ps.setDouble(1, uplata.getZaUplatu());
            ps.setString(2, date);
            ps.setString(3, date);
            ps.setInt(4, tblUplate.getSelectionModel().getSelectedItem().getId());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        setData();
    }
}
