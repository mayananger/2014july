package com.example.proximityalert;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.location.LocationManager;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;


public class MainActivity extends ActionBarActivity {


    LocationManager lm;
    PendingIntent pendingIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


    }

    @Override
    protected void onResume() {
        super.onResume();
        lm = (LocationManager)getSystemService(Context.LOCATION_SERVICE);
        Intent intent = new Intent(this, SecondActivity.class);
        intent.putExtra("myKey","myValye");
        pendingIntent = PendingIntent.getActivity(this, 0, intent,0);
        lm.addProximityAlert(37.1234, 40.1234, 50, -1, pendingIntent);

    }

    @Override
    protected void onPause() {
        super.onPause();

        lm.removeProximityAlert(pendingIntent);
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
