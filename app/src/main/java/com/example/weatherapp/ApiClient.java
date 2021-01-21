package com.example.weatherapp;

import android.location.Location;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;

public class ApiClient implements
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,
        com.google.android.gms.location.LocationListener {

    private static final String TAG = "WeatherApp";
    public GoogleApiClient googleApiClient;
    LocationService locationService = new LocationService();
    MainActivity mainActivity;

    public void getGoogleApiClient(MainActivity main) {
        googleApiClient = new GoogleApiClient.Builder(main)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
        mainActivity = main;
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        Log.i(TAG, "Connection established");
        locationService.checkPermission(mainActivity);
        Location location = LocationServices.FusedLocationApi.getLastLocation(googleApiClient);
        locationService.requestWeatherByLocation(location, googleApiClient, mainActivity);
    }

    //Reconnects if it was suspended
    @Override
    public void onConnectionSuspended(int i) {
        Log.i(TAG, "Connection Suspended");
        googleApiClient.connect();
    }

    //Outputs the error result to the tag
    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Log.i(TAG, "Connection failed. Error: " + connectionResult.getErrorCode());
    }

    //Requests weather if location was changed
    @Override
    public void onLocationChanged(Location location) {
        Log.i(TAG, "Location changed");
        locationService.requestWeatherByLocation(location, googleApiClient, mainActivity);
    }
}