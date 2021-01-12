package ro.mta.se.lab.model;

import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.theories.suppliers.TestedOn;
import org.mockito.Mockito;
import ro.mta.se.lab.controller.WeatherController;

import java.io.File;
import java.io.IOException;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.Mockito;
import ro.mta.se.lab.model.City;
import ro.mta.se.lab.model.JsonWorker;
import ro.mta.se.lab.model.Weather;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class WeatherTest {

    City mockCity;
    Weather instance;
    JsonWorker mockJW;
    String key;

    @Before
    public void setUp() throws IOException {
        mockCity=mock(City.class);
        when(mockCity.getCountryCode()).thenReturn("FR");
        when(mockCity.getName()).thenReturn("Paris");

        key="968ed3b71d5f8d8d811e068b7ba3da27";

        mockJW=mock(JsonWorker.class);
        when(mockJW.requestJson(mockCity.getName(),key)).thenReturn("{\"coord\":{\"lon\":2.3488,\"lat\":48.8534}," +
                "\"weather\":[{\"id\":804,\"main\":\"Clouds\",\"description\":\"overcast clouds\"," +
                "\"icon\":\"04d\"}],\"base\":\"stations\",\"main\":{\"temp\":6.49,\"feels_like\":1.14," +
                "\"temp_min\":6,\"temp_max\":7.22,\"pressure\":1015,\"humidity\":93},\"visibility\":10000," +
                "\"wind\":{\"speed\":6.17,\"deg\":250},\"clouds\":{\"all\":90},\"dt\":1610440595,\"sys\":{\"type" +
                "\":1,\"id\":6550,\"country\":\"FR\",\"sunrise\":1610437231,\"sunset\":1610468217},\"timezone" +
                "\":3600,\"id\":2988507,\"name\":\"Paris\",\"cod\":200}");
    }

    @Test
    public void test() throws IOException {

        String returnedJson= mockJW.requestJson(mockCity.getName(),key);

        instance=new Weather(returnedJson,mockCity);

        assertEquals("Paris",instance.getCurrentCity());
        assertEquals("6",instance.getTemp());
        assertEquals("93",instance.getHumidity());
        assertEquals("7",instance.getMaxTemp());
        assertEquals("6",instance.getMinTemp());
        assertEquals("11",instance.getWind());
    }

}

