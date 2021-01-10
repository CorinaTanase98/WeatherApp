package ro.mta.se.lab.model;


import com.eclipsesource.json.Json;
import com.eclipsesource.json.JsonArray;
import com.eclipsesource.json.JsonValue;


import javax.lang.model.element.NestingKind;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;


public class Weather {

    public void setCurrentCity(String currentCity) {
        this.currentCity = currentCity;
    }

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


    private String getRoundedValue(String value)
    {
        return String.valueOf(Math.round(Float.parseFloat(value)));
    }

    public Weather(String jsonString, String city) {

        //JsonArray jsonArray = Json.parse(jsonString).(JSONArray) jsonString.get("contact");

        setCurrentCity(city);
        JsonArray w = Json.parse(jsonString).asObject().get("weather").asArray();

        String mainWeather=null;
        String descWeather=null;
        String icon=null;
        for( JsonValue obj : w )
        {
            mainWeather= obj.asObject().getString("main","Weather");
            descWeather= obj.asObject().getString("description","Weather");
            icon= obj.asObject().getString("icon","icon");
        }

        JsonValue mainTemp = Json.parse(jsonString).asObject().get("main").asObject();
        String exactTemp= mainTemp.asObject().get("temp").toString();
        String feelsLikeTemp= mainTemp.asObject().get("feels_like").toString();
        String minTemp= mainTemp.asObject().get("temp_min").toString();
        String maxTemp= mainTemp.asObject().get("temp_max").toString();
        String pressure= mainTemp.asObject().get("pressure").toString();
        String humidity= mainTemp.asObject().get("humidity").toString();

        JsonValue windObj = Json.parse(jsonString).asObject().get("wind").asObject();
        String windSpeed= windObj.asObject().get("speed").toString();
        String degSpeed= windObj.asObject().get("deg").toString();

        DateFormat dateFormat = new SimpleDateFormat("EEEE, h:mm a");
        Date date = new Date(System.currentTimeMillis());
        //System.out.println(dateFormat.format(date));

        setCurrentDate(dateFormat.format(date));
        setFeelsLike(getRoundedValue(feelsLikeTemp));
        setTemp(getRoundedValue(exactTemp));
        setMaxTemp(getRoundedValue(maxTemp));
        setMinTemp(getRoundedValue(minTemp));
        setDesc(descWeather);
        setPression(pressure);
        setHumidity(humidity);
        setWind(String.valueOf(Math.round((Float.parseFloat(windSpeed))*1.85)));
        setIconString(icon);

        //System.out.println(mainWeather+ " press "+ pressure + "wind sp "+ windSpeed + "   "+ descWeather);
       /* System.out.println(mainWeather);
        System.out.println(descWeather);
        System.out.println(pressure);System.out.println(humidity);
        System.out.println(exactTemp);
        System.out.println(feelsLikeTemp);*/


    }

}