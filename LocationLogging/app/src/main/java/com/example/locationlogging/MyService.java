package com.example.locationlogging;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.location.LocationProvider;
import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

/**
 * Created by eladlavi on 1/8/15.
 */
public class MyService extends Service {

    LocationManager locationManager;
    LocationListener locationListener;

    @Override
    public IBinder onBind(Intent intent) {
        return binder;
    }
    private final IBinder binder = new LocalBinder();
    public class LocalBinder extends Binder{
        MyService getService(){
            return MyService.this;
        }
    }

    private int x = 0;
    public int getX(){
        return x;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        //Toast.makeText(this, "Service Start " + x++, Toast.LENGTH_LONG).show();
        Log.d("Elad", "Service Start " + x++);

        if(locationManager==null) {
            locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
            locationListener = new MyLocationListener();
            locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER,
                    1000, 0, locationListener);
        }

        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        //Toast.makeText(this,"Service Destroyed", Toast.LENGTH_LONG).show();
        Log.d("Elad", "Service Stop");
        locationManager.removeUpdates(locationListener);
    }

    private class MyLocationListener implements LocationListener {
        @Override
        public void onLocationChanged(Location location) {
            if (location != null) {
                String loc = "Location changed: Lat: " + location.getLatitude() +
                        " Lng: " + location.getLongitude();
                Log.d("Elad", loc);
            }
        }
        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {
            String statusString = "";
            switch (status) {
                case LocationProvider.AVAILABLE:
                    statusString = "available";
                    break;
                case LocationProvider.OUT_OF_SERVICE:
                    statusString = "out of service";
                    break;
                case LocationProvider.TEMPORARILY_UNAVAILABLE:
                    statusString = "temporarily unavailable";
                    break;
            }

        }
        @Override
        public void onProviderEnabled(String provider) {

        }
        @Override
        public void onProviderDisabled(String provider) {

        }
    }
}
