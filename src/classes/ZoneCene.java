package classes;

import java.io.Serializable;

/**
 * Created by PsyhoZOOM@gmail.com on 4/21/17.
 */
public class ZoneCene implements Serializable {
    int id;
    String vrstaUsluge;
    double providerCena;
    double providerCenaPDV;
    double cena;
    double PDV;
    double cenaPDV;
    double competitionCena;
    double razlika;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getVrstaUsluge() {
        return vrstaUsluge;
    }

    public void setVrstaUsluge(String vrstaUsluge) {
        this.vrstaUsluge = vrstaUsluge;
    }

    public double getProviderCena() {
        return providerCena;
    }

    public void setProviderCena(double providerCena) {
        this.providerCena = providerCena;
    }

    public double getProviderCenaPDV() {
        return providerCenaPDV;
    }

    public void setProviderCenaPDV(double providerCenaPDV) {
        this.providerCenaPDV = providerCenaPDV;
    }

    public double getCena() {
        return cena;
    }

    public void setCena(double cena) {
        this.cena = cena;
    }

    public double getPDV() {
        return PDV;
    }

    public void setPDV(double PDV) {
        this.PDV = PDV;
    }

    public double getCenaPDV() {
        return cenaPDV;
    }

    public void setCenaPDV(double cenaPDV) {
        this.cenaPDV = cenaPDV;
    }

    public double getCompetitionCena() {
        return competitionCena;
    }

    public void setCompetitionCena(double competitionCena) {
        this.competitionCena = competitionCena;
    }

    public double getRazlika() {
        return razlika;
    }

    public void setRazlika(double razlika) {
        this.razlika = razlika;
    }
}
