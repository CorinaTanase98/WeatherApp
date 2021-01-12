package ro.mta.se.lab.controller;

import com.eclipsesource.json.Json;
import com.eclipsesource.json.JsonArray;
import com.eclipsesource.json.JsonValue;
import javafx.beans.Observable;
import javafx.beans.value.ChangeListener;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.stage.Window;
import ro.mta.se.lab.model.City;
import ro.mta.se.lab.model.FileWorker;
import ro.mta.se.lab.model.JsonWorker;
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
    private String logFilename;
    private Weather currentWeather;

    //region FXML elements
    @FXML
    private ChoiceBox<String> countryChoiceBox = new ChoiceBox<String>();
    @FXML
    private ChoiceBox<String> cityChoiceBox=new ChoiceBox<String>();
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

    @FXML
    Label fixWindLabel;
    @FXML
    Label fixHumLabel;
    @FXML
    Label fixPresLabel;
    @FXML
    Label fixFeelslikeLabel;


    @FXML
    Label welcomeLabel;
    @FXML
    Label welcome2Label;

    @FXML
    Menu HelpClick;

    //endregion


    public WeatherController(String file) throws FileNotFoundException {
        setFilename(file);
        setLogFilename("src/main/resources/logfile.txt");
    }

    @FXML
    private void initialize() throws Exception {

        //initialize view
        boolean initResult=initializeCountries();

        countryChoiceBox.setValue("Choose country");
        cityChoiceBox.setValue("Choose city");
        countryChoiceBox.setItems(FXCollections.observableList(new ArrayList<String>(countriesMap.keySet())));

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
                    updateCurrentWeather(new City((String) cityChoiceBox.getValue()));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

        HelpClick.setOnAction(event -> {

            Alert a = new Alert(Alert.AlertType.NONE);
            Window window = a.getDialogPane().getScene().getWindow();
            a.setTitle("About");
            window.setOnCloseRequest(e -> a.hide());
            a.setContentText(" \t\t\t\tBestWeatherApp \n\t\t\t\t\tVersion 1 \n\n\n\nPowered by CATSoftware \nCopyright © 2021 | ATMF'1' ");
            a.show();
        });

    }

    private boolean initializeCountries() throws Exception {

        FileWorker fw= new FileWorker(this.getFilename());
        ArrayList<String> lines=fw.readFromFile();

        String[] wordsArray = new String[6];
        for( String item: lines) {
            wordsArray = item.split("[\t ]+");
            String id = wordsArray[0];
            String name = wordsArray[1];
            String lat = wordsArray[2];
            String lon = wordsArray[3];
            String country = wordsArray[4];
            City city = new City(name, country, Integer.parseInt(id), lat, lon);

            if (!countriesMap.containsKey(city.getCountryCode())) {
                countriesMap.put(city.getCountryCode(), new ArrayList<City>());
            }
            countriesMap.get(city.getCountryCode()).add(city);
        }

        return true;

    }

    private void updateCities(String country) {

        ArrayList<String> c = new ArrayList<String>();
        for (City i : countriesMap.get(country)) {
            c.add(i.getName());
        }
        cityChoiceBox.setItems(FXCollections.observableList(c));
    }

    public void updateCurrentWeather(City currentCity) throws IOException {

        JsonWorker jw= new JsonWorker();
        String json= jw.requestJson(currentCity.getName(),"968ed3b71d5f8d8d811e068b7ba3da27" );
        setCurrentWeather(new Weather(json,currentCity));
        updateInfo();
    }

    private void updateInfo() throws IOException {

        switchLabelsVisibility();

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

        FileWorker fw=new FileWorker(getLogFilename());
        fw.writeToFile(currentWeather);
    }

    private void switchLabelsVisibility() {

        welcome2Label.setVisible(false);
        welcomeLabel.setVisible(false);
        currentCityLabel.setVisible(true);
        currentTempLabel.setVisible(true);
        minLabel.setVisible(true);
        maxLabel.setVisible(true);
        pressureLabel.setVisible(true);
        windLabel.setVisible(true);
        humLabel.setVisible(true);
        weatherLabel.setVisible(true);
        fixHumLabel.setVisible(true);
        fixPresLabel.setVisible(true);
        fixWindLabel.setVisible(true);
        fixFeelslikeLabel.setVisible(true);
        feelslikeLabel.setVisible(true);

    }

    //region getters and setters

    public Weather getCurrentWeather() {
        return currentWeather;
    }

    public void setCurrentWeather(Weather currentWeather) throws IOException {
        this.currentWeather = currentWeather;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public String getLogFilename() {
        return logFilename;
    }

    public void setLogFilename(String logFilename) {
        this.logFilename = logFilename;
    }

    //endregion

}
