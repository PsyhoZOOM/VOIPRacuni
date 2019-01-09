/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.yuvideo.voipRacuni.classes;

import java.io.Serializable;

/**
 * @author PsyhoZOOM
 */
public class izvestajPotrosnje implements Serializable {

  String source;
  String destination;
  String pocetak;
  String kraj;
  String datumPoziva;
  String trajanje;
  int ukupno_poziv;
  int ukupno_primljeno;
  int br;
  int id;

  public String getDatumPoziva() {
    return datumPoziva;
  }

  public void setDatumPoziva(String datumPoziva) {
    this.datumPoziva = datumPoziva;
  }


  public String getSource() {
    return source;
  }

  public void setSource(String source) {
    this.source = source;
  }

  public String getDestination() {
    return destination;
  }

  public void setDestination(String destination) {
    this.destination = destination;
  }

  public String getPocetak() {
    return pocetak;
  }

  public void setPocetak(String pocetak) {
    this.pocetak = pocetak;
  }

  public String getKraj() {
    return kraj;
  }

  public void setKraj(String kraj) {
    this.kraj = kraj;
  }

  public String getTrajanje() {
    return trajanje;
  }

  public void setTrajanje(String trajanje) {
    this.trajanje = trajanje;
  }

  public int getUkupno_poziv() {
    return ukupno_poziv;
  }

  public void setUkupno_poziv(int ukupno_poziv) {
    this.ukupno_poziv = ukupno_poziv;
  }

  public int getUkupno_primljeno() {
    return ukupno_primljeno;
  }

  public void setUkupno_primljeno(int ukupno_primljeno) {
    this.ukupno_primljeno = ukupno_primljeno;
  }

  public int getBr() {
    return br;
  }

  public void setBr(int br) {
    this.br = br;
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }


}
