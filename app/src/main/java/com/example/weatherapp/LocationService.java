package com.example.weatherapp;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.util.Log;

import androidx.core.app.ActivityCompat;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;

public class LocationService {

    private static final int MY_PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION = 1;
    private static final long UPDATE_INTERVAL = 1000 * 10;
    private static final long FASTEST_INTERVAL = 1000 * 5;

    private static final String TAG = "WeatherApp";

    //Requests weather by current location
    public void requestWeatherByLocation(
            Location location,
            GoogleApiClient googleApiClient,
            MainActivity mainActivity) {
        if(location == null) {
            startLocationUpdates(googleApiClient, mainActivity);
            Log.i(TAG, "333333");
        }
        else {
            new WeatherInfo().networkService(location, mainActivity);
        }

    }

    // Requests location updates
    protected void startLocationUpdates(
            GoogleApiClient googleApiClient,
            MainActivity mainActivity) {
        Log.i(TAG, "222222");
        // Create the location request
        LocationRequest locationRequest = LocationRequest.create()
                .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
                .setInterval(UPDATE_INTERVAL)
                .setFastestInterval(FASTEST_INTERVAL);

        // Request location updates
        checkPermission(mainActivity);
        LocationServices.FusedLocationApi.requestLocationUpdates(
                googleApiClient,
                locationRequest,
                (LocationListener) this);
    }

    //Checks if App have permission to geolocation
    public void checkPermission(MainActivity mainActivity) {
        if (ActivityCompat.checkSelfPermission(
                mainActivity,
                Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(
                    mainActivity,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    MY_PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION);
        }
    }
}