package com.example.weatherapp;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface WeatherByLocation {

    @GET("weather?")
    Call<Weather> weather(
            @Query("appid") String token,
            @Query("units") String units,
            @Query("lat") double lat,
            @Query("lon") double lon);

}
