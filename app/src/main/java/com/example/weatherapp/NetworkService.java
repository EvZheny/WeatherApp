package com.example.weatherapp;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class NetworkService {

    private static final String BASE_URL = "https://api.openweathermap.org/data/2.5/";
    private static NetworkService instance;

    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build();

    public static NetworkService getInstance() {
        if (instance == null) instance = new NetworkService();
        return instance;
    }

    public WeatherApi getWeatherApi() {
        return retrofit.create(WeatherApi.class);
    }

    public WeatherByLocation getWeatherByLocation() {
        return retrofit.create(WeatherByLocation.class);
    }
}