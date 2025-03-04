<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Weather Application</title>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0-beta3/css/all.min.css">
    <style>
        body {
            font-family: 'Arial', sans-serif;
            margin: 0;
            padding: 0;
            background: linear-gradient(to right, #6dd5ed, #2193b0);
            color: #fff;
            text-align: center;
            min-height: 100vh;
            display: flex;
            flex-direction: column;
            align-items: center;
            justify-content: center;
        }

        .container {
            background: rgba(255, 255, 255, 0.1);
            border-radius: 10px;
            padding: 30px;
            width: 90%;
            max-width: 500px;
            box-shadow: 0 4px 6px rgba(0, 0, 0, 0.2);
        }

        h1 {
            margin-bottom: 20px;
            font-size: 2rem;
            font-weight: bold;
        }

        form {
            margin-bottom: 20px;
        }

        input[type="text"] {
            width: 80%;
            padding: 10px;
            font-size: 1rem;
            border: none;
            border-radius: 5px;
            margin-bottom: 10px;
        }

        button {
            background-color: #4caf50;
            color: #fff;
            border: none;
            padding: 10px 20px;
            font-size: 1rem;
            cursor: pointer;
            border-radius: 5px;
            transition: all 0.3s;
        }

        button:hover {
            background-color: #45a049;
            transform: scale(1.1);
        }

        .weather-data {
            margin-top: 20px;
            text-align: left;
            background: rgba(255, 255, 255, 0.9);
            color: #000;
            padding: 15px;
            border-radius: 8px;
            box-shadow: 0 4px 6px rgba(0, 0, 0, 0.2);
        }

        .weather-data h3 {
            text-align: center;
            margin-bottom: 15px;
            color: #333;
        }

        .loading-spinner {
            display: none;
            font-size: 2rem;
        }

        .hidden {
            display: none;
        }
    </style>
    <script>
        function toggleSpinner(show) {
            const spinner = document.getElementById("spinner");
            if (show) {
                spinner.classList.remove("hidden");
            } else {
                spinner.classList.add("hidden");
            }
        }

        function validateForm(event) {
            const cityInput = document.getElementById("city");
            if (!cityInput.value.trim()) {
                alert("Please enter a city name.");
                event.preventDefault();
                return false;
            }
            toggleSpinner(true);
        }
    </script>
</head>
<body>
    <div class="container">
        <h1>🌤 Weather Application</h1>
        <form action="weather" method="get" onsubmit="validateForm(event)">
            <input type="text" id="city" name="city" placeholder="Enter city name..." required>
            <br>
            <button type="submit">Get Weather</button>
        </form>

        <div id="spinner" class="loading-spinner">
            <i class="fas fa-spinner fa-spin"></i>
        </div>

        <div class="weather-data">
            <h3>Weather Details</h3>
            <p><%= request.getAttribute("weatherData") != null ? request.getAttribute("weatherData") : "No data available." %></p>
        </div>
    </div>
</body>
</html>
