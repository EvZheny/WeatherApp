package com.example.weatherapp;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

public class WeatherData extends AppCompatActivity {

    public String textWeatherDataKey = "textWeatherData";
    public String imageWeatherKey = "imageWeather";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather_data);

        final WeatherData weatherData = this;
        final EditText fieldCityName = this.findViewById(R.id.editTextCityName);
        Button buttonFindOutTheWeather = this.findViewById(R.id.findOutTheWeather);

        buttonFindOutTheWeather.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new WeatherInfo().networkService(fieldCityName, weatherData);
            }
        });
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);

        TextView textWeatherData = this.findViewById(R.id.textWeatherData);
        outState.putString(textWeatherDataKey, textWeatherData.getText().toString());

        ImageView imageWeather = this.findViewById(R.id.imageWeather);
        BitmapDrawable drawable = (BitmapDrawable) imageWeather.getDrawable();
        Bitmap bitmap = drawable.getBitmap();
        outState.putParcelable(imageWeatherKey, bitmap);
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        String textWeather = savedInstanceState.getString(textWeatherDataKey);
        TextView textWeatherData = this.findViewById(R.id.textWeatherData);
        textWeatherData.setText(textWeather);

        ImageView imageWeather = this.findViewById(R.id.imageWeather);
        Bitmap bitmap = savedInstanceState.getParcelable(imageWeatherKey);
        imageWeather.setImageBitmap(bitmap);
    }
}