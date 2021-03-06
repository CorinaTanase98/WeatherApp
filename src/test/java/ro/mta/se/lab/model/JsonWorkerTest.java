/*
 * JsonWeatherTest
 *
 * Version 1.0
 *
 * All rights reserved.
 */

package ro.mta.se.lab.model;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

import static org.junit.Assert.*;

/**
 * Unit testing class for JsonWorker class
 *
 * @author Corina Tanase
 */

public class JsonWorkerTest {

    private JsonWorker jw=new JsonWorker();
    static String json;

    @BeforeClass
    static public void _init() throws IOException {

        //initialize default values
        String urlString = "http://api.openweathermap.org/data/2.5/weather?q=Bucharest&units=metric&appid=968ed3b71d5f8d8d811e068b7ba3da27";
        StringBuilder res = new StringBuilder();
        URL url;

        {
            try {
                url = new URL(urlString);
                URLConnection conn = url.openConnection();
                BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                String line;
                while ((line = rd.readLine()) != null)
                    res.append(line);
                rd.close();

            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        json=String.valueOf(res);
    }

    @Rule
    public ExpectedException exception;

    //tests using real time weather data, need actualized expected values for running corectly
    @Test(expected = FileNotFoundException.class)
    public void testRequestJson() throws IOException {
        //wrong city name
        assertEquals(jw.requestJson("Bucrest", "968ed3b71d5f8d8d811e068b7ba3da27"), json);
    }

    @Test
    public void extractWindTest() {
        assertEquals(new String("1.54"),jw.extractWind(json));
    }

    @Test
    public void extractPresTest() {
        assertEquals(new String("1017"),jw.extractPres(json));
    }

    @Test
    public void extractMaxTest() {
        assertEquals(new String("-4"),jw.extractMax(json));
    }

    @Test
    public void extractMinTest() {
        assertEquals(new String("-6"),jw.extractMin(json));
    }

    @Test
    public void extractTempTest() {
        assertEquals(new String("-5"),jw.extractTemp(json));
    }

    @Test
    public void extractFeelslikeTest() {
        assertEquals(new String("-9"),jw.extractFeelsLike(json));
    }

    @Test
    public void extractHumTest() {
        assertEquals(new String("-9"),jw.extractFeelsLike(json));
    }

    @Test
    public void getRoundedValueTest() {
        assertEquals(new String("1"),jw.getRoundedValue("1.23456"));
    }

    @Test
    public void extractTempWeatherIconTest() {
        ArrayList<String> res= jw.extractTempWeatherIcon(json);
        assertEquals(new String("Clouds"),res.get(0));
        assertEquals(new String("few clouds"),res.get(1));
        assertEquals(new String("02d"),res.get(2));
    }
}