/*
 * JsonWorker
 *
 * Version 1.0
 *
 * All rights reserved.
 */

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

/**
 * Class implementing work with JSON weather value
 * @author Corina Tanase
 */
public class JsonWorker {

    /**
     * Function responsible for challenge API server to return JSON weather value for a specific city
     * @param city city parameter for requesting weather data
     * @param key client API key value
     * @throws  java.net.MalformedURLException for inadequate url string
     * @throws java.io.IOException  for reading response exception
     * @return value of JSON request response
     *
     */
    public String requestJson(String city, String key) throws IOException {

        String urlString = "http://api.openweathermap.org/data/2.5/weather?q=" + city + "&units=metric"+ "&appid=" + key;
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

    /**
     * Function responsible for selecting maximum temperature from JSON
     * @param json  JSON value to parse
     * @return value of maximum temperature
     *
     */
    public String extractMax(String json) {
        JsonValue mainTemp = Json.parse(json).asObject().get("main").asObject();
        String retValue = mainTemp.asObject().get("temp_max").toString();

        return getRoundedValue(retValue);
    }


    /**
     * Function responsible for selecting minimum temperature from JSON
     * @param json  JSON value to parse
     * @return value of minimum temperature
     *
     */
    public String extractMin(String json) {
        JsonValue mainTemp = Json.parse(json).asObject().get("main").asObject();
        String retValue= mainTemp.asObject().get("temp_min").toString();

        return getRoundedValue(retValue);
    }


    /**
     * Function responsible for parsing weather identifier, description and icon code from JSON
     * @param json  JSON value to parse
     * @return ArrayList with weather value, description value and icon code
     *
     */
    public ArrayList<String> extractTempWeatherIcon(String json) {

        ArrayList<String> params= new ArrayList<String>();
        JsonArray w = Json.parse(json).asObject().get("weather").asArray();

        for( JsonValue obj : w )
        {
            params.add(obj.asObject().getString("main","Weather"));
            params.add(obj.asObject().getString("description","Weather"));
            params.add(obj.asObject().getString("icon","icon"));
        }

        return params;

    }

    /**
     * Function responsible for extracting wind speed value from JSON
     * @param json  JSON value to parse
     * @return wind speed value
     *
     */
    public String extractWind(String json) {
        JsonValue windObj = Json.parse(json).asObject().get("wind").asObject();
        return windObj.asObject().get("speed").toString();
    }

    /**
     * Function responsible for extracting pression value from JSON
     * @param json  JSON value to parse
     * @return pression value
     *
     */
    public String extractPres(String json) {
        JsonValue mainTemp = Json.parse(json).asObject().get("main").asObject();
        return mainTemp.asObject().get("pressure").toString();
    }

    /**
     * Function responsible for extracting humidity value from JSON
     * @param json  JSON value to parse
     * @return humidity value
     *
     */
    public String extractHum(String json) {
        JsonValue mainTemp = Json.parse(json).asObject().get("main").asObject();
        return mainTemp.asObject().get("humidity").toString();
    }

    /**
     * Function responsible for extracting "feels like" temperature form JSON
     * @param json  JSON value to parse
     * @return temperature value
     *
     */
    public String extractFeelsLike(String json) {
        JsonValue mainTemp = Json.parse(json).asObject().get("main").asObject();
        String retValue = mainTemp.asObject().get("feels_like").toString();

        return getRoundedValue(retValue);
    }


    /**
     * Function responsible for extracting current temperature form JSON
     * @param json  JSON value to parse
     * @return temperature value
     *
     */
    public String extractTemp(String json) {
        JsonValue mainTemp = Json.parse(json).asObject().get("main").asObject();
        String retValue= mainTemp.asObject().get("temp").toString();

        return getRoundedValue(retValue);
    }


    /**
     * Function for computing round value of float value
     * @param value float values to transform
     * @return rounded value
     *
     */
    public String getRoundedValue(String value) {
        return String.valueOf(Math.round(Float.parseFloat(value)));
    }


}
