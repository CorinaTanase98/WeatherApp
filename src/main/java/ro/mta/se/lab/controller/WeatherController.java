package ro.mta.se.lab.controller;

import com.eclipsesource.json.Json;
import com.eclipsesource.json.JsonArray;
import com.eclipsesource.json.JsonValue;
import javafx.beans.Observable;
import javafx.beans.value.ChangeListener;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import ro.mta.se.lab.model.City;
import ro.mta.se.lab.model.Weather;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class WeatherController {

    private HashMap<String, ArrayList<City>> countriesMap = new HashMap<String, ArrayList<City>>();
    private String filename;

    public Weather getCurrentWeather() {
        return currentWeather;
    }

    public void setCurrentWeather(Weather currentWeather) throws IOException {
        this.currentWeather = currentWeather;
    }

    private void updateInfo() throws IOException {
        currentCityLabel.setText(currentWeather.getCurrentCity());
        currentTempLabel.setText(currentWeather.getTemp()+"°C");
        minLabel.setText("L: "+currentWeather.getMinTemp()+"°C");
        maxLabel.setText("H: "+currentWeather.getMaxTemp()+"°C");
        weatherLabel.setText(currentWeather.getDesc());
        feelslikeLabel.setText(currentWeather.getFeelsLike()+"°C");
        humLabel.setText(currentWeather.getHumidity() +"%");
        pressureLabel.setText(currentWeather.getPression()+" hPa");
        windLabel.setText(currentWeather.getWind()+" km/hr");
        BufferedImage buffImg =null;
        URL imgURL = new URL("http://openweathermap.org/img/wn/"+currentWeather.getIconString()+".png");
        buffImg = ImageIO.read(imgURL);
        iconImageView.setImage(new Image(String.valueOf(imgURL)));
    }

    private Weather currentWeather;


    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    @FXML
    private ChoiceBox<String> countryChoiceBox = new ChoiceBox();
    @FXML
    private ChoiceBox cityChoiceBox;
    @FXML
    private Label currentCityLabel;
    @FXML
    private Label currentTempLabel;
    @FXML
    private Label minLabel;
    @FXML
    private Label maxLabel;
    @FXML
    private Label weatherLabel;
    @FXML
    private Label pressureLabel;
    @FXML
    private Label humLabel;
    @FXML
    private Label feelslikeLabel;
    @FXML
    private Label windLabel;
    @FXML
    private ImageView iconImageView;



    public WeatherController(String file) throws FileNotFoundException {
        setFilename(file);
        initializeCountries();
    }

    private void initializeCountries() throws FileNotFoundException {

        try {
            BufferedReader buf = new BufferedReader(new FileReader(this.getFilename()));
            String[] wordsArray = new String[6];

            String strRead;
            strRead = buf.readLine();
            while ((strRead = buf.readLine()) != null) {
                wordsArray = strRead.split("[\t ]+");
                String id = wordsArray[0];
                String name = wordsArray[1];
                String lat = wordsArray[2];
                String lon = wordsArray[3];
                String country = wordsArray[4];
                City city = new City(name, country, Integer.parseInt(id), lat, lon);
                // System.out.println(city.getName() + " " + city.getCountryCode());

                if (!countriesMap.containsKey(city.getCountryCode())) {
                    countriesMap.put(city.getCountryCode(), new ArrayList<City>());
                }
                countriesMap.get(city.getCountryCode()).add(city);
            }
            buf.close();
        } catch (IOException e) {
            e.printStackTrace();
           /* for (String i : countriesMap.keySet()) {
                System.out.println(i);
            }*/

        } catch (Exception e) {
            e.printStackTrace();
        }
       // System.out.println(getFilename());
       /* for (Map.Entry<String, ArrayList<City>>entry : countriesMap.entrySet()) {
            System.out.println(entry.getKey()+" " );
            for( City c : entry.getValue())
                System.out.println(c.getName());
            System.out.println("\n");
        }*/

    }

    @FXML
    private void initialize() {
        // Initialize  view

        countryChoiceBox.setValue("Choose country");
        cityChoiceBox.setValue("Choose city");
        countryChoiceBox.setItems(FXCollections.observableList(new ArrayList<String>(countriesMap.keySet())));

        //countryChoiceBox.getSelectionModel().selectedItemProperty().addListener(newValue -> updateCities(newValue.toString()));

        countryChoiceBox.setOnAction(event -> {
            cityChoiceBox.getItems().clear();
            cityChoiceBox.setValue("Choose city");
            //The above line is important otherwise everytime there is an action it will just keep adding more
            if (countryChoiceBox.getValue() != null) {//This cannot be null but I added because idk what yours will look like
                updateCities(countryChoiceBox.getValue());
            }
        });

        cityChoiceBox.setOnAction(event -> {
            if(cityChoiceBox.getValue()!="Choose city" && cityChoiceBox.getValue()!=null) {
                try {
                    updateCurrentWeather((String) cityChoiceBox.getValue());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

    }

    private void updateCurrentWeather(String currentCity) throws IOException {

        //System.out.println(currentCity);
        String apiKey = "968ed3b71d5f8d8d811e068b7ba3da27";
        String urlString = "http://api.openweathermap.org/data/2.5/weather?q=" + currentCity + "&units=metric"+ "&appid=" + apiKey;

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
                //System.out.println(res);
                //citiesList=cities;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        setCurrentWeather(new Weather(String.valueOf(res),currentCity));
        updateInfo();

    }
    private void updateCities(String newValue) {

        ArrayList<String> c = new ArrayList<String>();
        for (City i : countriesMap.get(newValue)) {
            c.add(i.getName());
        }
        cityChoiceBox.setItems(FXCollections.observableList(c));
    }


}
