package classes;

import java.io.Serializable;

/**
 * Created by PsyhoZOOM@gmail.com on 5/11/17.
 */
public class uplate implements Serializable {
    int id;
    String ime;
    String brojTel;
    Double zaUplatu;
    Double uplaceno;
    String zaMesec;
    String uplatio;
    int userID;

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

    public Double getZaUplatu() {
        return zaUplatu;
    }

    public void setZaUplatu(Double zaUplatu) {
        this.zaUplatu = zaUplatu;
    }

    public Double getUplaceno() {
        return uplaceno;
    }

    public void setUplaceno(Double uplaceno) {
        this.uplaceno = uplaceno;
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
}
