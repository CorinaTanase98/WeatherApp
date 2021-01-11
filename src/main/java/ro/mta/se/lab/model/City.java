package ro.mta.se.lab.model;

import javafx.beans.property.*;

public class City {

    StringProperty name;
    StringProperty countryCode;
    IntegerProperty ID;
    StringProperty lat;
    StringProperty lon;

    public City(String name, String countryCode, int ID, String lon, String lat) {
        this.name = new SimpleStringProperty(name);
        this.countryCode = new SimpleStringProperty(countryCode);
        this.lon = new SimpleStringProperty(lon);
        this.ID = new SimpleIntegerProperty(ID);
        this.lat = new SimpleStringProperty(lat);
    }

    //region for getters and setters

    public String getName() {
        return name.get();
    }

    public StringProperty nameProperty() {
        return name;
    }

    public void setName(String name) {
        this.name.set(name);
    }

    public String getCountryCode() {
        return countryCode.get();
    }

    public StringProperty countryCodeProperty() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode.set(countryCode);
    }

    public int getID() {
        return ID.get();
    }

    public IntegerProperty IDProperty() {
        return ID;
    }

    //endregion

}
