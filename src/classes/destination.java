package classes;

/**
 * Created by PsyhoZOOM@gmail.com on 5/9/17.
 */
public class destination {
    int id;
    int userid;
    String nazivDestinacije;
    int utrosenoMinuta;
    int gratisMinuta;
    int minutaZaNaplatu;
    double cenaPoMinutu;
    double ukupno;
    String account;
    String nazivDestinacijeZone;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNazivDestinacijeZone() {
        return nazivDestinacijeZone;
    }

    public void setNazivDestinacijeZone(String nazivDestinacijeZone) {
        this.nazivDestinacijeZone = nazivDestinacijeZone;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public int getUserid() {
        return userid;
    }

    public void setUserid(int userid) {
        this.userid = userid;
    }

    public String getNazivDestinacije() {
        return nazivDestinacije;
    }

    public void setNazivDestinacije(String nazivDestinacije) {
        this.nazivDestinacije = nazivDestinacije;
    }

    public int getUtrosenoMinuta() {
        return utrosenoMinuta;
    }

    public void setUtrosenoMinuta(int utrosenoMinuta) {
        this.utrosenoMinuta = utrosenoMinuta;
    }

    public int getGratisMinuta() {
        return gratisMinuta;
    }

    public void setGratisMinuta(int gratisMinuta) {
        this.gratisMinuta = gratisMinuta;
    }

    public int getMinutaZaNaplatu() {
        return minutaZaNaplatu;
    }

    public void setMinutaZaNaplatu(int minutaZaNaplatu) {
        this.minutaZaNaplatu = minutaZaNaplatu;
    }

    public double getCenaPoMinutu() {
        return cenaPoMinutu;
    }

    public void setCenaPoMinutu(double cenaPoMinutu) {
        this.cenaPoMinutu = cenaPoMinutu;
    }

    public double getUkupno() {
        return ukupno;
    }

    public void setUkupno(double ukupno) {
        this.ukupno = ukupno;
    }
}
