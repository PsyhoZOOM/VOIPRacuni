package Controllers;

import classes.*;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.TreeItemPropertyValueFactory;
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
    public Button bUplati;
    public Label lDug;
    public TableColumn cDug;
    public TableColumn cPDV;
    public TreeTableView<uplate> tblUplateTree;
    public TreeTableColumn<uplate, String> ctImePrezime;
    public TreeTableColumn<uplate, String> ctVrsta;
    public TreeTableColumn<uplate, String> ctBrTel;
    public TreeTableColumn<uplate, String> ctZaMesec;
    public TreeTableColumn<uplate, Double> ctZaUplatu;
    public TreeTableColumn<uplate, Double> ctUplaceno;
    public TreeTableColumn<uplate, String> ctDatumUplate;
    Database db;
    Users user;
    FIXX fixx;
    private URL location;
    private ResourceBundle resources;
    private DecimalFormat df = new DecimalFormat("#,###,##0.00");

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.location = location;
        this.resources = resources;


        ctImePrezime.setCellValueFactory(new TreeItemPropertyValueFactory<uplate, String>("ime"));
        ctBrTel.setCellValueFactory(new TreeItemPropertyValueFactory<uplate, String>("brojTel"));
        ctZaMesec.setCellValueFactory(new TreeItemPropertyValueFactory<uplate, String>("zaMesec"));
        ctZaUplatu.setCellValueFactory(new TreeItemPropertyValueFactory<uplate, Double>("zaUplatu"));
        ctUplaceno.setCellValueFactory(new TreeItemPropertyValueFactory<uplate, Double>("uplaceno"));
        ctDatumUplate.setCellValueFactory(new TreeItemPropertyValueFactory<uplate, String>("datumUplate"));
        ctVrsta.setCellValueFactory(new TreeItemPropertyValueFactory<uplate, String>("komentar"));


        ctImePrezime.setCellFactory(new Callback<TreeTableColumn<uplate, String>, TreeTableCell<uplate, String>>() {
            @Override
            public TreeTableCell<uplate, String> call(TreeTableColumn<uplate, String> param) {
                TreeTableCell<uplate, String> cell = new TreeTableCell<uplate, String>() {
                    @Override
                    protected void updateItem(String item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            setText(null);
                        } else {
                            setText(item);
                        }
                    }
                };
                return cell;
            }
        });

        ctZaUplatu.setCellFactory(new Callback<TreeTableColumn<uplate, Double>, TreeTableCell<uplate, Double>>() {
            @Override
            public TreeTableCell<uplate, Double> call(TreeTableColumn<uplate, Double> param) {
                TreeTableCell<uplate, Double> cell = new TreeTableCell<uplate, Double>() {
                    @Override
                    protected void updateItem(Double item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            setText(null);
                        } else {
                            if (item != null) {
                                setText(df.format(item));
                            } else {
                                setText(null);
                            }
                        }
                    }
                };
                return cell;
            }
        });

        ctUplaceno.setCellFactory(new Callback<TreeTableColumn<uplate, Double>, TreeTableCell<uplate, Double>>() {
            @Override
            public TreeTableCell<uplate, Double> call(TreeTableColumn<uplate, Double> param) {
                TreeTableCell<uplate, Double> cell = new TreeTableCell<uplate, Double>() {
                    @Override
                    protected void updateItem(Double item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            setText(null);
                        } else {
                            if (item != null) {
                                setText(df.format(item));
                            } else {
                                setText(null);
                            }
                        }
                    }
                };
                return cell;
            }
        });

        bUplati.setDisable(true);
        tblUplateTree.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<TreeItem<uplate>>() {
            @Override
            public void changed(ObservableValue<? extends TreeItem<uplate>> observable, TreeItem<uplate> oldValue, TreeItem<uplate> newValue) {
                if (newValue.getValue().getKomentar().equals("Saobracaj")) {
                    bUplati.setDisable(true);
                } else {
                    bUplati.setDisable(false);
                }
            }
        });
    }

    public void setData() {
        Double zaUplatuUkupno = 0.00;
        Double Uplaceno = 0.00;
        ObservableList<uplate> data = FXCollections.observableArrayList(getUplate(user.getId()));
        //tblUplate.setItems(data);


        TreeItem<uplate> root = new TreeItem<>();
        TreeItem<uplate> treeItemPaket;


        for (uplate uplata : data) {
                if (uplata.getKomentar().equals("Paket")) {
                    treeItemPaket = new TreeItem(uplata);
                    root.getChildren().add(treeItemPaket);
                }
        }

        for (TreeItem<uplate> treupl : root.getChildren()) {
            for (uplate uplata : data) {
                zaUplatuUkupno = uplata.getZaUplatu() - uplata.getUplaceno();
                if (treupl.getValue().getZaMesec().equals(uplata.getZaMesec())) {
                    if (uplata.getKomentar().equals("Saobracaj")) {
                        treupl.getChildren().add(new TreeItem<uplate>(uplata));
                        treupl.getValue().setZaUplatu(treupl.getValue().getZaUplatu() + uplata.getZaUplatu());
                    }
                }
            }
            treupl.getValue().setZaUplatu(treupl.getValue().getZaUplatu() +
                    valueToPercent.getValue(treupl.getValue().getZaUplatu(), treupl.getValue().getPDV()));
        }


        //TODO
        //za uplatu pdvset

        zaUplatuUkupno = zaUplatuUkupno + valueToPercent.getValue(zaUplatuUkupno, 20);

        lDug.setText(df.format(zaUplatuUkupno));

        tblUplateTree.setShowRoot(false);
        root.expandedProperty().setValue(true);
        tblUplateTree.setRoot(root);








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
        fixx = new FIXX(db);
        Users user;
        PreparedStatement ps;
        ResultSet rs;
        //String query = "SELECT sum(zaUplatu) as zaUplatu, id, uplaceno, userID, datumUplate, komentar, zaMesec FROM zaduzenja WHERE userID=? and uplaceno=0  ORDER by zaMesec";
        String query = "SELECT * FROM zaduzenja WHERE userID=? ORDER BY zaMesec DESC";
        ArrayList<uplate> uplateArrayList = new ArrayList<>();
        uplate uplata;

        try {
            ps = db.connection.prepareStatement(query);
            ps.setInt(1, userId);
            rs = ps.executeQuery();

            if (rs.isBeforeFirst()) {
                while (rs.next()) {
                    user = fixx.getUserData(rs.getInt("userID"));
                    uplata = new uplate();
                    uplata.setId(rs.getInt("id"));
                    uplata.setIme(user.getIme());
                    uplata.setBrojTel(user.getBrojTelefona());
                    uplata.setDug(rs.getDouble("zaUplatu"));
                    uplata.setUplaceno(rs.getDouble("uplaceno"));
                    uplata.setZaMesec(rs.getString("zaMesec"));
                    uplata.setUserID(rs.getInt("userID"));
                    uplata.setDatumUplate(rs.getString("datumUplate"));
                    uplata.setPDV(getPDV(user.getNazivPaketaID()));
                    uplata.setZaUplatu(uplata.getDug());
                    uplata.setKomentar(rs.getString("komentar"));
                    uplateArrayList.add(uplata);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return uplateArrayList;
    }

    public void uplati(ActionEvent actionEvent) {
        if (tblUplateTree.selectionModelProperty().getValue().getSelectedIndex() == -1)
            return;

        String date = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));

        //    uplate uplata = tblUplateTree.getSelectionModel().getSelectedItem().getChildren().;
        for (TreeItem<uplate> item : tblUplateTree.getRoot().getChildren()) {
            System.out.println("TREE ITEM: " + item.getValue().getKomentar());
            System.out.println("TREE ITEM PRICE: " + item.getValue().getZaUplatu());
        }

        TreeItem<uplate> uplataRoot = tblUplateTree.getSelectionModel().getSelectedItem();

        for (TreeItem<uplate> trIt : uplataRoot.getChildren()) {
            int id = trIt.getValue().getId();
            Double pduug = trIt.getValue().getZaUplatu();
            uplatiUplatu(id, pduug);
        }
        uplatiUplatu(uplataRoot.getValue().getId(), uplataRoot.getValue().getZaUplatu());



        setData();
    }


    private void uplatiUplatu(int id, double pdug) {
        PreparedStatement ps;
        String query = "UPDATE zaduzenja SET uplaceno=?, datumUplate=? WHERE id=?";
        String date = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        try {
            ps = db.connection.prepareStatement(query);
            ps.setDouble(1, pdug);
            ps.setString(2, date);
            ps.setInt(3, id);
            ps.executeUpdate();
            ps.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

}
