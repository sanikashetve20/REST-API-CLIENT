package com.weather;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/weather")
public class WeatherServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String city = request.getParameter("city");
        try {
            String jsonResponse = WeatherBackend.getWeatherData(city);
            String formattedData = WeatherBackend.parseWeatherData(jsonResponse);
            request.setAttribute("weatherData", formattedData);
        } catch (Exception e) {
            request.setAttribute("weatherData", "Error fetching weather data: " + e.getMessage());
        }
        request.getRequestDispatcher("/index.jsp").forward(request, response);
    }
}
