/*
 * City
 *
 * Version 1.0
 *
 * All rights reserved.
 */

package ro.mta.se.lab.model;

import javafx.beans.property.*;

/**
 * Model class for managing city information
 * @author Corina Tanase
 */
public class City {

    /** members for city parameters */
    StringProperty name;
    StringProperty countryCode;
    IntegerProperty ID;
    StringProperty lat;
    StringProperty lon;

    /**
     *    City constructor
     * Sets class members based on JSON value and city name
     * @param name city name
     * @param countryCode code for country
     * @param ID city object identifier for weather API
     * @param lon city location longitude value
     * @param lat city location latitude value
     */
    public City(String name, String countryCode, int ID, String lon, String lat) {
        this.name = new SimpleStringProperty(name);
        this.countryCode = new SimpleStringProperty(countryCode);
        this.lon = new SimpleStringProperty(lon);
        this.ID = new SimpleIntegerProperty(ID);
        this.lat = new SimpleStringProperty(lat);
    }


    //region setters and getters
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

    public City( String s)
    {
        setName(s);
    }

}
