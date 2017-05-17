package classes;

import java.io.Serializable;

/**
 * Created by PsyhoZOOM@gmail.com on 4/21/17.
 */
public class Users implements Serializable {
    int id;
    String ime;
    String adresa;
    String mesto;
    String postBr;
    String brUgovora;
    String customerId;
    String pozivNaBroj;
    boolean stampa;
    String nazivUsluge;
    boolean aktivan;
    String mesec;
    int nazivPaketaID;
    String brojTelefona;
    boolean firma;
    String pib;
    String mbr;
    String nazivFirme;


    public String getNazivFirme() {
        return nazivFirme;
    }

    public void setNazivFirme(String nazivFirme) {
        this.nazivFirme = nazivFirme;
    }

    public boolean isFirma() {
        return firma;
    }

    public void setFirma(boolean firma) {
        this.firma = firma;
    }

    public String getPib() {
        return pib;
    }

    public void setPib(String pib) {
        this.pib = pib;
    }

    public String getMbr() {
        return mbr;
    }

    public void setMbr(String mbr) {
        this.mbr = mbr;
    }

    public int getNazivPaketaID() {
        return nazivPaketaID;
    }

    public void setNazivPaketaID(int nazivPaketaID) {
        this.nazivPaketaID = nazivPaketaID;
    }

    public String getBrojTelefona() {
        return brojTelefona;
    }

    public void setBrojTelefona(String brojTelefona) {
        this.brojTelefona = brojTelefona;
    }




    public String getMesec() {
        return mesec;
    }

    public void setMesec(String mesec) {
        this.mesec = mesec;
    }

    public String getNazivUsluge() {
        return nazivUsluge;
    }

    public void setNazivUsluge(String nazivUsluge) {
        this.nazivUsluge = nazivUsluge;
    }

    public boolean isAktivan() {
        return aktivan;
    }

    public void setAktivan(boolean aktivan) {
        this.aktivan = aktivan;
    }

    public boolean isStampa() {
        return stampa;
    }

    public void setStampa(boolean stampa) {
        this.stampa = stampa;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getIme() {
        return ime;
    }

    public void setIme(String ime) {
        this.ime = ime;
    }

    public String getAdresa() {
        return adresa;
    }

    public void setAdresa(String adresa) {
        this.adresa = adresa;
    }

    public String getMesto() {
        return mesto;
    }

    public void setMesto(String mesto) {
        this.mesto = mesto;
    }

    public String getPostBr() {
        return postBr;
    }

    public void setPostBr(String postBr) {
        this.postBr = postBr;
    }

    public String getBrUgovora() {
        return brUgovora;
    }

    public void setBrUgovora(String brUgovora) {
        this.brUgovora = brUgovora;
    }


    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getPozivNaBroj() {
        return pozivNaBroj;
    }

    public void setPozivNaBroj(String pozivNaBroj) {
        this.pozivNaBroj = pozivNaBroj;
    }
}
