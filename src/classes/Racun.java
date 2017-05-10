package classes;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by PsyhoZOOM@gmail.com on 5/9/17.
 */
public class Racun implements Serializable {
    int id;
    Double pretplata;
    Double potrosnja;
    Double pdv;
    Double prethodniDug;
    Double zaUplatu;
    String ugovorBr;
    String datumIzdavanja;
    String periodOd;
    String periodDo;
    String rokPlacanja;
    String broj;
    ArrayList<destination> destinacija;
    String pozivNaBroj;

    public String getPozivNaBroj() {
        return pozivNaBroj;
    }

    public void setPozivNaBroj(String pozivNaBroj) {
        this.pozivNaBroj = pozivNaBroj;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Double getPretplata() {
        return pretplata;
    }

    public void setPretplata(Double pretplata) {
        this.pretplata = pretplata;
    }

    public Double getPotrosnja() {
        return potrosnja;
    }

    public void setPotrosnja(Double potrosnja) {
        this.potrosnja = potrosnja;
    }

    public Double getPdv() {
        return pdv;
    }

    public void setPdv(Double pdv) {
        this.pdv = pdv;
    }

    public Double getPrethodniDug() {
        return prethodniDug;
    }

    public void setPrethodniDug(Double prethodniDug) {
        this.prethodniDug = prethodniDug;
    }

    public Double getZaUplatu() {
        return zaUplatu;
    }

    public void setZaUplatu(Double zaUplatu) {
        this.zaUplatu = zaUplatu;
    }

    public String getUgovorBr() {
        return ugovorBr;
    }

    public void setUgovorBr(String ugovorBr) {
        this.ugovorBr = ugovorBr;
    }

    public String getDatumIzdavanja() {
        return datumIzdavanja;
    }

    public void setDatumIzdavanja(String datumIzdavanja) {
        this.datumIzdavanja = datumIzdavanja;
    }

    public String getPeriodOd() {
        return periodOd;
    }

    public void setPeriodOd(String periodOd) {
        this.periodOd = periodOd;
    }

    public String getPeriodDo() {
        return periodDo;
    }

    public void setPeriodDo(String periodDo) {
        this.periodDo = periodDo;
    }

    public String getRokPlacanja() {
        return rokPlacanja;
    }

    public void setRokPlacanja(String rokPlacanja) {
        this.rokPlacanja = rokPlacanja;
    }

    public String getBroj() {
        return broj;
    }

    public void setBroj(String broj) {
        this.broj = broj;
    }

    public ArrayList<destination> getDestinacija() {
        return destinacija;
    }

    public void setDestinacija(ArrayList<destination> destinacija) {
        this.destinacija = destinacija;
    }

}
