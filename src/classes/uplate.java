package classes;

import java.io.Serializable;

/**
 * Created by PsyhoZOOM@gmail.com on 5/11/17.
 */
public class uplate implements Serializable {
    int id;
    String ime;
    String brojTel;
    Double dug;
    Double zaUplatu;
    String uplaceno;
    String zaMesec;
    String uplatio;
    int userID;
    String datumUplate;
    Double PDV;
    String komentar;

    public String getUplaceno() {
        return uplaceno;
    }

    public void setUplaceno(String uplaceno) {
        this.uplaceno = uplaceno;
    }

    public String getKomentar() {
        return komentar;
    }

    public void setKomentar(String komentar) {
        this.komentar = komentar;
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

    public String getBrojTel() {
        return brojTel;
    }

    public void setBrojTel(String brojTel) {
        this.brojTel = brojTel;
    }

    public Double getDug() {
        return dug;
    }

    public void setDug(Double dug) {
        this.dug = dug;
    }

    public Double getZaUplatu() {
        return zaUplatu;
    }

    public void setZaUplatu(Double zaUplatu) {
        this.zaUplatu = zaUplatu;
    }

    public String getZaMesec() {
        return zaMesec;
    }

    public void setZaMesec(String zaMesec) {
        this.zaMesec = zaMesec;
    }

    public String getUplatio() {
        return uplatio;
    }

    public void setUplatio(String uplatio) {
        this.uplatio = uplatio;
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public String getDatumUplate() {
        return datumUplate;
    }

    public void setDatumUplate(String datumUplate) {
        this.datumUplate = datumUplate;
    }

    public Double getPDV() {
        return PDV;
    }

    public void setPDV(Double PDV) {
        this.PDV = PDV;
    }
}
