/*
 * Weather
 *
 * Version 1.0
 *
 * All rights reserved.
 */

package ro.mta.se.lab.model;

import java.io.FileNotFoundException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Model class for managing weather information
 * @author Corina Tanase
 */
public class Weather {

    /** members for actual weather parameters */
    private String currentCity;
    private String desc;
    private String pression;
    private String humidity;
    private String feelsLike;
    private String currentDate;
    private String temp;
    private String maxTemp;
    private String minTemp;
    private String wind;
    private String iconString;


    /**
     *    Weather constructor
     * Sets class members based on JSON value and city name
     * @param jsonString JSON value for current weather
     * @param city current city
     */
    public Weather(String jsonString, String city) {

        setCurrentCity(city);

        JsonWorker jw=new JsonWorker();

        ArrayList<String> params=jw.extractTempWeatherIcon(jsonString);
        setDesc(params.get(1));
        setIconString(params.get(2));

        DateFormat dateFormat = new SimpleDateFormat("EEEE, h:mm a");
        Date date = new Date(System.currentTimeMillis());
        setCurrentDate(dateFormat.format(date));

        setFeelsLike(jw.extractFeelsLike(jsonString));
        setTemp(jw.extractTemp(jsonString));
        setMaxTemp(jw.extractMax(jsonString));
        setMinTemp(jw.extractMin(jsonString));
        setPression(jw.extractPres(jsonString));
        setHumidity(jw.extractHum(jsonString));
        setWind(String.valueOf(Math.round((Float.parseFloat(jw.extractWind(jsonString)))*1.85)));

    }

    //region getters and setters

    public String getIconString() {
        return iconString;
    }

    public void setIconString(String iconString) {
        this.iconString = iconString;
    }

    public String getWind() {
        return wind;
    }

    public void setWind(String wind) {
        this.wind = wind;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getCurrentCity() {
        return currentCity;
    }

    public void setCurrentCity(String currentCity) {
        this.currentCity = currentCity;
    }

    public String getCurrentDate() {
        return currentDate;
    }

    public void setCurrentDate(String currentDate) {
        this.currentDate = currentDate;
    }

    public String getTemp() {
        return temp;
    }

    public void setTemp(String temp) {
        this.temp = temp;
    }

    public String getFeelsLike() {
        return feelsLike;
    }

    public void setFeelsLike(String feels_like) {
        this.feelsLike = feels_like;
    }

    public String getMaxTemp() {
        return maxTemp;
    }

    public void setMaxTemp(String maxTemp) {
        this.maxTemp = maxTemp;
    }

    public String getMinTemp() {
        return minTemp;
    }

    public void setMinTemp(String minTemp) {
        this.minTemp = minTemp;
    }

    public String getPression() {
        return pression;
    }

    public void setPression(String pression) {
        this.pression = pression;
    }

    public String getHumidity() {
        return humidity;
    }

    public void setHumidity(String humidity) {
        this.humidity = humidity;
    }

    //endregion

}