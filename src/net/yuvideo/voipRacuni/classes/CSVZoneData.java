package net.yuvideo.voipRacuni.classes;

import java.io.Serializable;

/**
 * Created by PsyhoZOOM@gmail.com on 5/11/17.
 */
public class CSVZoneData implements Serializable {
    int id;
    String country;
    String description;
    String zona;
    int CenaZoneID;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getZona() {
        return zona;
    }

    public void setZona(String zona) {
        this.zona = zona;
    }

    public int getCenaZoneID() {
        return CenaZoneID;
    }

    public void setCenaZoneID(int cenaZoneID) {
        CenaZoneID = cenaZoneID;
    }
}
