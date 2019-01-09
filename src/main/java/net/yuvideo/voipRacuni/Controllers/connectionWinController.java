package net.yuvideo.voipRacuni.Controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import net.yuvideo.voipRacuni.classes.Database;

/**
 * Created by PsyhoZOOM@gmail.com on 6/1/17.
 */
public class connectionWinController implements Initializable {

  public TextField tHost;
  public TextField tUser;
  public TextField tPass;
  public Label lMessage;
  public Stage primaryStage;
  Parent root = null;
  FXMLLoader fxmlLoader;
  private URL location;
  private ResourceBundle resources;
  private Database db;

  @Override
  public void initialize(URL location, ResourceBundle resources) {
    this.location = location;
    this.resources = resources;
  }

  public void connectToHost(ActionEvent actionEvent) {
    String hostName = tHost.getText();
    String user = tUser.getText();
    String password = tPass.getText();

    db = new Database(hostName, user, password);

    if (db.isConnected) {
      lMessage.setText(String.valueOf(db.isConnected));
      showMainWin();
    } else {
      lMessage.setText("Pogrešno korisničko ime/šifra/host");
    }

  }


  private void showMainWin() {
    fxmlLoader = new FXMLLoader(getClass().getResource("/FXML/MainWin.fxml"), resources);

    MainWin mainWin;// = fxmlLoader.getController();

    primaryStage = (Stage) tPass.getScene().getWindow();
    primaryStage.setTitle("Fiksna Telefonija Stampa Racuna " + tHost.getText());
    primaryStage.getIcons()
        .add(new Image(getClass().getResourceAsStream("/Assets/YuVideoLogo.png")));
    try {
      root = fxmlLoader.load();
      mainWin = fxmlLoader.getController();
      mainWin.setDB(db);
    } catch (IOException e) {
      e.printStackTrace();
    }

    primaryStage.setScene(new Scene(root, 900, 600));

    primaryStage.setMaximized(true);
    primaryStage.show();
  }
}
