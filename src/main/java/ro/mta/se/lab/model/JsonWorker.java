package ro.mta.se.lab.model;

import com.eclipsesource.json.Json;
import com.eclipsesource.json.JsonArray;
import com.eclipsesource.json.JsonValue;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

public class JsonWorker {

    private JsonValue json;


    public String requestJson(String city, String key) throws IOException {

        String urlString = "http://api.openweathermap.org/data/2.5/weather?q=" + city + "&units=metric" + "&appid=" + key;
        StringBuilder res = new StringBuilder();
        URL url;

        url = new URL(urlString);
        URLConnection conn = url.openConnection();
        BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        String line;
        while ((line = rd.readLine()) != null)
            res.append(line);
        rd.close();

        return String.valueOf(res);
    }

    public String extractMax(String json) {
        JsonValue mainTemp = Json.parse(json).asObject().get("main").asObject();
        String retValue = mainTemp.asObject().get("temp_max").toString();

        return getRoundedValue(retValue);
    }

    public String extractMin(String json) {
        JsonValue mainTemp = Json.parse(json).asObject().get("main").asObject();
        String retValue = mainTemp.asObject().get("temp_min").toString();

        return getRoundedValue(retValue);
    }

    public ArrayList<String> extractTempWeatherIcon(String json) {

        ArrayList<String> params = new ArrayList<String>();
        JsonArray w = Json.parse(json).asObject().get("weather").asArray();

        for (JsonValue obj : w) {
            params.add(obj.asObject().getString("main", "Weather"));
            params.add(obj.asObject().getString("description", "Weather"));
            params.add(obj.asObject().getString("icon", "icon"));
        }

        return params;

    }

    public String extractWind(String json) {
        JsonValue windObj = Json.parse(json).asObject().get("wind").asObject();
        return windObj.asObject().get("speed").toString();
    }

    public String extractPres(String json) {
        JsonValue mainTemp = Json.parse(json).asObject().get("main").asObject();
        return mainTemp.asObject().get("pressure").toString();
    }

    public String extractHum(String json) {
        JsonValue mainTemp = Json.parse(json).asObject().get("main").asObject();
        return mainTemp.asObject().get("humidity").toString();
    }

    public String extractFeelsLike(String json) {
        JsonValue mainTemp = Json.parse(json).asObject().get("main").asObject();
        String retValue = mainTemp.asObject().get("feels_like").toString();

        return getRoundedValue(retValue);
    }

    public String extractTemp(String json) {
        JsonValue mainTemp = Json.parse(json).asObject().get("main").asObject();
        String retValue = mainTemp.asObject().get("temp").toString();

        return getRoundedValue(retValue);
    }

    public String getRoundedValue(String value) {
        return String.valueOf(Math.round(Float.parseFloat(value)));
    }

    //region getters and setters
    public void setJson(JsonValue json) {
        this.json = json;
    }

    //endregion

}
