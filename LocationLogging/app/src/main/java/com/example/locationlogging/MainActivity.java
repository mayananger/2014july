package com.example.locationlogging;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;


public class MainActivity extends ActionBarActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /*
        DBAdapter db = new DBAdapter(this);
        db.open();
        db.insertLocation(System.currentTimeMillis(), 123.123453f, 456.4345556f);
        db.insertLocation(System.currentTimeMillis(), 123.113453f, 456.4341556f);

        Cursor c = db.getAllLocations();
        while(c.moveToNext()){
            long sampleTime = c.getLong(c.getColumnIndex(DBAdapter.KEY_DATE));
            //or simply: c.getLong(0); if we already know the column index.
            float lat = c.getFloat(c.getColumnIndex(DBAdapter.KEY_LAT));
            float lng = c.getFloat(c.getColumnIndex(DBAdapter.KEY_LNG));
            Log.d("Elad","record: time: " + sampleTime + ", lat: " + lat + ", lng: " + lng);


        }
        c.close();
        db.close();
        */
        /*
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                for(int i=0;i<30;i++){
                    Log.d("Elad", "value " + i);
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        t.start();
        */
    }


    @Override
    protected void onStop() {
        super.onStop();
        Log.d("Elad", "in onStop");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d("Elad", "in onDestroy");
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

    public void btnStart(View view) {
        Intent intent = new Intent(this, MyService.class);
        startService(intent);
    }

    public void btnStop(View view) {
        Intent intent = new Intent(this, MyService.class);
        stopService(intent);
    }
}
