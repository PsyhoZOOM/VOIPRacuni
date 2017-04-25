package classes;

import java.io.Serializable;

/**
 * Created by PsyhoZOOM@gmail.com on 4/21/17.
 */
public class CSVData implements Serializable {
    int id;
    String account;
    String from;
    String to;
    String country;
    String descrition;
    String connectTime;
    String chargedTimeMinSec;
    int chargedTimeSec;
    double chargetAmmountRSD;
    String serviceName;
    int chargedQuantity;
    String serviceUnit;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getDescrition() {
        return descrition;
    }

    public void setDescrition(String descrition) {
        this.descrition = descrition;
    }

    public String getConnectTime() {
        return connectTime;
    }

    public void setConnectTime(String connectTime) {
        this.connectTime = connectTime;
    }

    public String getChargedTimeMinSec() {
        return chargedTimeMinSec;
    }

    public void setChargedTimeMinSec(String chargedTimeMinSec) {
        this.chargedTimeMinSec = chargedTimeMinSec;
    }

    public int getChargedTimeSec() {
        return chargedTimeSec;
    }

    public void setChargedTimeSec(int chargedTimeSec) {
        this.chargedTimeSec = chargedTimeSec;
    }

    public double getChargetAmmountRSD() {
        return chargetAmmountRSD;
    }

    public void setChargetAmmountRSD(double chargetAmmountRSD) {
        this.chargetAmmountRSD = chargetAmmountRSD;
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public int getChargedQuantity() {
        return chargedQuantity;
    }

    public void setChargedQuantity(int chargedQuantity) {
        this.chargedQuantity = chargedQuantity;
    }

    public String getServiceUnit() {
        return serviceUnit;
    }

    public void setServiceUnit(String serviceUnit) {
        this.serviceUnit = serviceUnit;
    }
}
