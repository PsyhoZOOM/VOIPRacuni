package net.yuvideo.voipRacuni.classes;

import java.io.Serializable;

/**
 * Created by PsyhoZOOM@gmail.com on 4/21/17.
 */
public class Brojevi implements Serializable {
    int id;
    int userID;
    String brojTel;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public String getBrojTel() {
        return brojTel;
    }

    public void setBrojTel(String brojTel) {
        this.brojTel = brojTel;
    }
}
