/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.yuvideo.voipRacuni.Controllers;

import com.itextpdf.text.DocumentException;
import com.itextpdf.text.pdf.PdfWriter;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.FileChooser;
import javafx.util.StringConverter;
import net.yuvideo.voipRacuni.classes.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

import static java.time.temporal.TemporalAdjusters.lastDayOfMonth;

/**
 * FXML Controller class
 *
 * @author PsyhoZOOM
 */
public class IzvestajiController implements Initializable {

	static Database db;

	@FXML
	private ComboBox<Users> cmbKorisnik;
	@FXML
	private Button bShow;
	@FXML
	private TableView<izvestajPotrosnje> tblPozivani;
	@FXML
	private TableColumn<izvestajPotrosnje, String> cPozivaniBr;
	@FXML
	private TableColumn<izvestajPotrosnje, String> cPozivaniBrojTel;
	@FXML
	private TableColumn<izvestajPotrosnje, String> cPozDest;
	@FXML
	private TableColumn<izvestajPotrosnje, String> cPozPocetak;
	@FXML
	private TableColumn<izvestajPotrosnje, Integer> cPozTrajanje;
	@FXML
	private Label lblPozivaniMin;
	@FXML
	private Button bStampa;

	private URL url;
	private ResourceBundle resources;
	@FXML
	private DatePicker dtpPeriodOd;
	@FXML
	private DatePicker dtpPeriodDo;

	private DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
	private DateTimeFormatter dtfM = DateTimeFormatter.ofPattern("yyyy-MM");

	/**
	 * Initializes the controller class.
	 */
	@Override
	public void initialize(URL url, ResourceBundle rb) {
		// TODO
		this.url = url;
		this.resources = rb;

		cPozDest.setCellValueFactory(new PropertyValueFactory<>("destination"));
		cPozPocetak.setCellValueFactory(new PropertyValueFactory<>("datumPoziva"));
		cPozTrajanje.setCellValueFactory(new PropertyValueFactory<>("trajanje"));
		cPozivaniBr.setCellValueFactory(new PropertyValueFactory<>("br"));
		cPozivaniBrojTel.setCellValueFactory(new PropertyValueFactory<>("source"));

		cmbKorisnik.setConverter(new StringConverter<Users>() {
			Users user;

			@Override
			public String toString(Users object) {
				String cmbString = null;
				if (object != null) {
					this.user = object;
					cmbString = String.format("%s, %s, %s", object.getPozivNaBroj(), object.getBrojTelefona(), object.getIme());
				}
					return cmbString;
			}

			@Override
			public Users fromString(String string) {
				return user;
			}

		});

		dtpPeriodDo.setConverter(new StringConverter<LocalDate>() {
			@Override
			public String toString(LocalDate object) {
				return object.format(dtf);
			}

			@Override
			public LocalDate fromString(String string) {
				LocalDate date = LocalDate.parse(string, dtf);
				return date;
			}
		});

		dtpPeriodOd.setValue(LocalDate.now().minusMonths(1).withDayOfMonth(1));
		dtpPeriodDo.setValue(LocalDate.now().minusMonths(1).with(lastDayOfMonth()));

		dtpPeriodOd.setConverter(new StringConverter<LocalDate>() {
			@Override
			public String toString(LocalDate object) {
				return object.format(dtf);
			}

			@Override
			public LocalDate fromString(String string) {
				LocalDate date = LocalDate.parse(string, dtf);
				return date;
			}
		});

	}

	@FXML
	private void showData(ActionEvent event) {
		if(cmbKorisnik.getValue() == null){
			Alert alert = new Alert(Alert.AlertType.ERROR, "Nije izabranKorisnik", ButtonType.CLOSE);
			alert.showAndWait();
		}
		PreparedStatement ps;
		ResultSet rs;
		String start = dtpPeriodOd.getValue().format(dtf);
		String end = dtpPeriodDo.getValue().format(dtf);
		String query = "SELECT * FROM csv WHERE `from`=? or `to`=? ";
		ArrayList<izvestajPotrosnje> dataArr = new ArrayList<>();
		izvestajPotrosnje data;

		try {
			ps = db.connection.prepareStatement(query);
			ps.setString(1, cmbKorisnik.getValue().getBrojTelefona());
			ps.setString(2, cmbKorisnik.getValue().getBrojTelefona());
			System.out.println("QUERY:"+ps.toString());
			rs = ps.executeQuery();
			if (rs.isBeforeFirst()) {
				int i = 1;
				int sekunde_ukupno=0;
				while (rs.next()) {
					if(rs.getString("from").isEmpty() || rs.getString("to").isEmpty())
						continue;

					LocalDateTime st = LocalDateTime.parse(rs.getString("connectTime"), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
					if(!st.isAfter(dtpPeriodOd.getValue().atTime(0, 0, 0)))
						continue;
					
					if(!st.isBefore(dtpPeriodDo.getValue().atTime(23, 59, 59)))
						continue;
					
					data = new izvestajPotrosnje();

					data.setBr(i);
					data.setDatumPoziva(rs.getString("connectTime"));
					data.setSource(rs.getString("from"));
					data.setDestination(rs.getString("to"));
					LocalTime plusSeconds = LocalTime.MIN.plusSeconds(rs.getInt("chargedTimeS"));
					sekunde_ukupno += rs.getInt("chargedTimeS");
					data.setUkupno_poziv(sekunde_ukupno);
					data.setTrajanje(plusSeconds.toString());

					dataArr.add(data);
					i++;
				}

				
				lblPozivaniMin.setText(String.valueOf(LocalTime.MIN.plusSeconds(sekunde_ukupno).toString()));
			}
		} catch (SQLException ex) {
			Logger.getLogger(IzvestajiController.class.getName()).log(Level.SEVERE, null, ex);
		}

		ObservableList<izvestajPotrosnje> dataTbl = FXCollections.observableArrayList(dataArr);
		tblPozivani.setItems(dataTbl);

	}

	@FXML
	private void stampaj(ActionEvent event) {

		ObservableList<izvestajPotrosnje> items = tblPozivani.getItems();
		if(items.size() < 1){
			Alert alert = new Alert(Alert.AlertType.ERROR, "Nema podataka za stampu", ButtonType.CLOSE);
			alert.showAndWait();
			return;
		}

		Users user = cmbKorisnik.getValue();
		String period = String.format("Period od %s do %s", 
				dtpPeriodOd.getValue().toString(),
				dtpPeriodDo.getValue().toString());

		PrintPageListing printPageListin = new PrintPageListing(items, user, period);
		
		String path = System.getProperty("user.home");
		
		FileChooser fc = new FileChooser();
		fc.setInitialDirectory(new File(path));
		FileChooser.ExtensionFilter ef = new FileChooser.ExtensionFilter("PDF", "*.pdf");
		String name;
		if(user.isFirma()){
			name = user.getNazivFirme();
		}else{
			name = user.getIme();
		}
		fc.setTitle(String.format("Listing poziva za %s", name));
		fc.setInitialFileName(String.format("Listing_Poziva_%s", name));
		fc.getExtensionFilters().addAll(ef);
		File file = fc.showSaveDialog(this.bStampa.getScene().getWindow());
		try {
			PdfWriter pdfWriter = PdfWriter.getInstance(printPageListin.getDocument(), new FileOutputStream(file.getAbsolutePath() + ".pdf"));
			printPageListin.createListing();
		} catch (FileNotFoundException ex) {
			Logger.getLogger(IzvestajiController.class.getName()).log(Level.SEVERE, null, ex);
		} catch (DocumentException ex) {
			Logger.getLogger(IzvestajiController.class.getName()).log(Level.SEVERE, null, ex);
		}
	}

	public void setData() {
		ObservableList<Users> users = FXCollections.observableArrayList(getUsers());
		cmbKorisnik.setItems(users);
	}

	private ArrayList<CSVData> getData() {
		PreparedStatement ps;
		ResultSet rs;
		String query = "SELECT * FROM csv WHERE from=? OR to=?";
		ArrayList<CSVData> dataArr = new ArrayList<>();
		CSVData data;

		try {
			ps = db.connection.prepareStatement(query);
			ps.setString(1, cmbKorisnik.getValue().getBrojTelefona());
			ps.setString(2, cmbKorisnik.getValue().getBrojTelefona());
			rs = ps.executeQuery();
			if (rs.isBeforeFirst()) {
				while (rs.next()) {
					data = new CSVData();
					data.setAccount(rs.getString("account"));
					data.setFrom(rs.getString("from"));
					data.setTo(rs.getString("to"));
					data.setCountry(rs.getString("country"));
					data.setDescription(rs.getString("description"));
					data.setConnectTime(rs.getString("connectTime"));
					data.setChargedTimeSec(rs.getInt("chargedTimeS"));
					data.setChargedAmountRSD(rs.getDouble("chargedAmmountRSD"));
					data.setServiceName(rs.getString("serviceName"));
					data.setChargedQuantity(rs.getInt("chargedQuantity"));
					data.setServiceUnit(rs.getString("serviceUnit"));
					data.setCustomerID(rs.getString("customerID"));
					dataArr.add(data);
				}
			}
			ps.close();
			rs.close();
		} catch (SQLException ex) {
			Logger.getLogger(IzvestajiController.class.getName()).log(Level.SEVERE, null, ex);
		}

		return dataArr;

	}

	private ArrayList<Users> getUsers() {
		PreparedStatement ps;
		ResultSet rs;
		String query = "SELECT * FROM korisnici";

		ArrayList<Users> usersArr = new ArrayList<>();
		Users user;

		try {
			ps = db.connection.prepareStatement(query);
			rs = ps.executeQuery();
			if (rs.isBeforeFirst()) {
				while (rs.next()) {
					user = new Users();
					user.setId(rs.getInt("id"));
					user.setIme(rs.getString("imePrezime"));
					user.setAdresa(rs.getString("adresa"));
					user.setMesto(rs.getString("mesto"));
					user.setPostBr(rs.getString("postbr"));
					user.setBrUgovora(rs.getString("brUgovora"));
					user.setCustomerId(rs.getString("customerID"));
					user.setPozivNaBroj(rs.getString("pozivNaBroj"));
					user.setNazivPaketaID(rs.getInt("paketID"));
					user.setBrojTelefona(rs.getString("brojTelefona"));
					user.setStampa(rs.getBoolean("stampa"));
					user.setMbr(rs.getString("mbr"));
					user.setPib(rs.getString("pib"));
					user.setFirma(rs.getBoolean("firma"));
					user.setNazivFirme(rs.getString("nazivFirme"));
					user.setDatumPrikljucka(rs.getString("datumPrikljucka"));
					usersArr.add(user);
				}
			}
		} catch (SQLException ex) {
			Logger.getLogger(IzvestajiController.class.getName()).log(Level.SEVERE, null, ex);
		}

		return usersArr;
	}

}
