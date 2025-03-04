package com.weather;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Properties;

import org.json.JSONObject;

public class WeatherBackend {
    private static final String API_URL = "https://api.openweathermap.org/data/2.5/weather";
    private static final String API_KEY = getApiKey();

    private static String getApiKey() {
        String apiKey = System.getenv("WEATHER_API_KEY");
        if (apiKey == null || apiKey.isEmpty()) {
            try {
                Properties properties = new Properties();
                properties.load(new FileInputStream("src/main/resources/config.properties"));
                apiKey = properties.getProperty("api_key");
            } catch (IOException e) {
                System.err.println("Error loading API key from config.properties");
            }
        }
        if (apiKey == null || apiKey.isEmpty()) {
            throw new IllegalStateException("API Key is missing! Set WEATHER_API_KEY as an environment variable or in config.properties.");
        }
        return apiKey;
    }

    public static String getWeatherData(String city) throws Exception {
        String urlString = API_URL + "?q=" + city + "&appid=" + API_KEY + "&units=metric";
        URL url = new URL(urlString);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");
        connection.setRequestProperty("Content-Type", "application/json");

        int responseCode = connection.getResponseCode();
        if (responseCode != HttpURLConnection.HTTP_OK) {
            throw new Exception("HTTP GET Request failed with code " + responseCode);
        }

        BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        StringBuilder response = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
            response.append(line);
        }
        reader.close();

        return response.toString();
    }

    public static String parseWeatherData(String jsonResponse) {
        JSONObject jsonObject = new JSONObject(jsonResponse);
        String city = jsonObject.getString("name");
        JSONObject main = jsonObject.getJSONObject("main");
        double temperature = main.getDouble("temp");
        double feelsLike = main.getDouble("feels_like");
        int humidity = main.getInt("humidity");
        String description = jsonObject.getJSONArray("weather").getJSONObject(0).getString("description");

        return String.format("City: %s\nTemperature: %.2f°C\nFeels Like: %.2f°C\nHumidity: %d%%\nDescription: %s",
                city, temperature, feelsLike, humidity, description);
    }
}
