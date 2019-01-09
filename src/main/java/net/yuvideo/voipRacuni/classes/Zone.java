package net.yuvideo.voipRacuni.classes;

import java.io.Serializable;

/**
 * Created by PsyhoZOOM@gmail.com on 5/1/17.
 */
public class Zone implements Serializable {

  int id;
  String naziv;
  String opis;
  String zona;
  int zonaID;

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public String getNaziv() {
    return naziv;
  }

  public void setNaziv(String naziv) {
    this.naziv = naziv;
  }

  public String getOpis() {
    return opis;
  }

  public void setOpis(String opis) {
    this.opis = opis;
  }


  public int getZonaID() {
    return zonaID;
  }

  public void setZonaID(int zonaID) {
    this.zonaID = zonaID;
  }

  public String getZona() {
    return zona;
  }

  public void setZona(String zona) {
    this.zona = zona;
  }
}
