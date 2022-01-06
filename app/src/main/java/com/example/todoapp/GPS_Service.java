package com.example.todoapp;

import android.annotation.SuppressLint;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.IBinder;
import android.provider.Settings;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;


import java.text.DecimalFormat;

public class GPS_Service extends Service {

    LocationListener listener;
    LocationManager locationManager;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @SuppressLint("MissingPermission")
    @Override
    public void onCreate() {
        listener = new LocationListener() {
            @Override
            public void onLocationChanged(@NonNull Location location) {
                DecimalFormat df=new DecimalFormat("#.##");
                Intent i = new Intent("location_update");
                i.putExtra("Coordinates",getResources().getString(R.string.longitude) + ": " + df.format( location.getLongitude()) +"  " + getResources().getString(R.string.latitude) +": " + df.format( location.getLatitude()) + "\n");
                sendBroadcast(i);
            }

            @Override
            public void onProviderDisabled(@NonNull String provider) {
                Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        };
        locationManager = (LocationManager) getApplicationContext().getSystemService(Context.LOCATION_SERVICE);
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,20000,5,listener);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (locationManager != null) {
            locationManager.removeUpdates(listener);
        }
    }
}
