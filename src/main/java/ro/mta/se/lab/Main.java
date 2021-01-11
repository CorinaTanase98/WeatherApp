package ro.mta.se.lab;

import javafx.collections.FXCollections;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import ro.mta.se.lab.controller.WeatherController;

import java.io.*;
import java.util.*;

import javafx.application.Application;

import java.io.IOException;


public class Main extends Application {

    private List<String> citiesList = FXCollections.observableArrayList();

    public static void main(String[] args) {
        launch(args);
    }

    public void start(Stage primaryStage) throws FileNotFoundException {

        FXMLLoader loader = new FXMLLoader();
        try {
            loader.setLocation(this.getClass().getResource("/view/WeatherView2.fxml"));
            loader.setController(new WeatherController("src/main/resources/infile.txt"));
            primaryStage.setTitle("BestWeatherApp");
            primaryStage.getIcons().add(new Image("https://cdn2.iconfinder.com/data/icons/weather-flat-14/64/weather02-512.png"));
            primaryStage.setScene(new Scene(loader.load()));
            primaryStage.setResizable(false);
            primaryStage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
