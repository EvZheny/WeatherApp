package com.example.weatherapp

class CalculateWindDirection {

    //Calculates the direction of the wind in azimuth
    fun calcDirection(weather: Weather): String {
        return when (weather.wind.deg) {
            in 0..22 -> "N"
            in 23..67 -> "NE"
            in 68..112 -> "E"
            in 113..157 -> "SE"
            in 158..202 -> "S"
            in 203..247 -> "SW"
            in 248..292 -> "W"
            in 293..337 -> "NW"
            else -> "N"
        };
    }
}