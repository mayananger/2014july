package com.example.location;

import android.content.Context;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.location.LocationProvider;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import java.util.List;


public class MainActivity extends ActionBarActivity {

    LocationManager lm;
    LocationListener locationListener;

    private class MyLocationListener implements LocationListener {

        @Override
        public void onLocationChanged(Location location) {
            if (location != null) {
                String loc = "Location changed: Lat: " + location.getLatitude() +
                        " Lng: " + location.getLongitude();
                Toast.makeText(getBaseContext(), loc, Toast.LENGTH_SHORT).show();
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
            Toast.makeText(getBaseContext(), provider + " " + statusString,
                    Toast.LENGTH_LONG).show();
        }

        @Override
        public void onProviderEnabled(String provider) {
            Toast.makeText(getBaseContext(), "Provider: " + provider + " enabled.",
                    Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onProviderDisabled(String provider) {
            Toast.makeText(getBaseContext(), "Provider: " + provider + " disabled.",
                    Toast.LENGTH_SHORT).show();
        }
    }




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (savedInstanceState == null) {
            lm = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

            locationListener = new MyLocationListener();

            List<String> locationProviders = lm.getAllProviders();
            for (String provider : locationProviders) {
                Log.d("Elad", provider);
            }

            Criteria c = new Criteria();
            c.setAccuracy(Criteria.ACCURACY_FINE);
            c.setAltitudeRequired(true);
            c.setBearingRequired(false);
            c.setCostAllowed(true);
            c.setPowerRequirement(Criteria.POWER_HIGH);

            String bestProvider = lm.getBestProvider(c, true);
            Log.d("Elad", "best provider: " + bestProvider);


            Location location = lm.getLastKnownLocation(bestProvider);


        }


    }






    @Override
    protected void onResume() {
        super.onResume();
        lm.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000 * 60 * 60, 0, locationListener);


    }

    @Override
    protected void onPause() {
        super.onPause();
        lm.removeUpdates(locationListener);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
