package classes;

import java.io.Serializable;

/**
 * Created by PsyhoZOOM@gmail.com on 5/1/17.
 */
public class Zone implements Serializable {
    int id;
    String naziv;
    String opis;
    String usluga;
    int uslugaID;

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

    public String getUsluga() {
        return usluga;
    }

    public void setUsluga(String usluga) {
        this.usluga = usluga;
    }

    public int getUslugaID() {
        return uslugaID;
    }

    public void setUslugaID(int uslugaID) {
        this.uslugaID = uslugaID;
    }
}
