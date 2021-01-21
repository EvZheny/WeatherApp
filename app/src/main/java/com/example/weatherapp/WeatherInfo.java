package com.example.weatherapp;

import android.location.Location;
import android.util.Log;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class WeatherInfo {

    private static final String TAG = "WeatherApp";
    private static final String TOKEN = "71ac2c39d12a429574284debb1216555";
    public String units = "metric";
    public String textWeather;

    //Finds weather by city name
    public void networkService(
            EditText fieldCityName,
            final WeatherData weatherData) {
        final TextView textWeatherData = weatherData.findViewById(R.id.textWeatherData);
        final ImageView imageWeather = weatherData.findViewById(R.id.imageWeather);

        NetworkService.getInstance()
                .getWeatherApi()
                .weather(TOKEN, units, fieldCityName.getText().toString())
                .enqueue(new Callback<Weather>() {

                    @Override
                    public void onResponse(Call<Weather> call, Response<Weather> response) {
                        requestWeather(response, textWeatherData);
                        findImage(response.body(), imageWeather);
                    }

                    @Override
                    public void onFailure(Call<Weather> call, Throwable t) {
                        Log.i(TAG,"failure " + t);
                    }
                });
    }

    //Finds weather by current location
    public void networkService(
            Location location,
            final MainActivity mainActivity) {
        final ImageView imageWeather = mainActivity.findViewById(R.id.imageCurrentWeather);
        final TextView textCurrentWeather = mainActivity.findViewById(R.id.textCurrentWeather);

        NetworkService.getInstance()
                .getWeatherByLocation()
                .weather(TOKEN, units, location.getLatitude(), location.getLongitude())
                .enqueue(new Callback<Weather>() {

                    @Override
                    public void onResponse(Call<Weather> call, Response<Weather> response) {
                        requestWeather(response, textCurrentWeather);
                        findImage(response.body(), imageWeather);
                    }

                    @Override
                    public void onFailure(Call<Weather> call, Throwable t) {
                        Log.i(TAG,"failure " + t);
                    }
                });
    }

    public String responseData (Weather weather) {
        return textWeather = "City: " + weather.getName() +
                "\nTemp: " + weather.getMainTemp().temp +
                "\nFeels like: " + weather.getMainTemp().getFeelsLike() +
                "\nMain: " + weather.getWeather().get(0).getMain() +
                "\nDescription: " + weather.getWeather().get(0).getDescription() +
                "\nHumidity: " + weather.getMainTemp().getHumidity() + "%" +
                "\nWind speed: " + weather.getWind().getSpeed() +
                "\nWind direction: " + new CalculateWindDirection().calcDirection(weather) +
                "\nCloudiness: " + weather.getClouds().getAll() + "%";
    }

    public void findImage (Weather weather, ImageView imageWeather) {
        String imageUrl = "http://openweathermap.org/img/wn/";
        imageUrl += weather.getWeather().get(0).getIcon() + "@2x.png";
        Picasso.get().load(imageUrl).into(imageWeather);
    }

    public void requestWeather(Response<Weather> response, TextView textWeatherData) {
        if (response.isSuccessful()) textWeatherData.setText(responseData(response.body()));
        else textWeatherData.setText(R.string.wrongCityName);
    }
}