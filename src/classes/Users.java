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
}
