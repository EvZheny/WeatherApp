package com.example.weatherapp;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "WeatherApp";
    ApiClient apiClient = new ApiClient();

    @RequiresApi(api = Build.VERSION_CODES.R)
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        apiClient.getGoogleApiClient(this);
        Button button = this.findViewById(R.id.button);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentWeatherData = new Intent(MainActivity.this, WeatherData.class);
                startActivity(intentWeatherData);
            }
        });
    }

    @Override
    public void onStart() {
        Log.i(TAG, "onStart:");
        super.onStart();
        apiClient.googleApiClient.connect();
    }

    @Override
    public void onStop() {
        Log.i(TAG, "onStop:");
        super.onStop();
        if (apiClient.googleApiClient.isConnected()) apiClient.googleApiClient.disconnect();
    }
}