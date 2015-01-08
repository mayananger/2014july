package com.example.locationlogging;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.database.Cursor;
import android.os.IBinder;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;


public class MainActivity extends ActionBarActivity {

    MyService myService;
    boolean bound;

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
    protected void onStart() {
        super.onStart();
        Intent intent = new Intent(this, MyService.class);
        bindService(intent, connection, Context.BIND_AUTO_CREATE);
    }

    @Override
    protected void onStop() {
        super.onStop();
        if(bound){
            unbindService(connection);
            bound = false;
        }

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
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
        if(bound){
            unbindService(connection);
            bound = false;
        }
        Intent intent = new Intent(this, MyService.class);
        stopService(intent);
    }

    private ServiceConnection connection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            MyService.LocalBinder binder = (MyService.LocalBinder)service;
            myService = binder.getService();
            bound = true;
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            bound = false;
        }
    };

    public void btnShowX(View view) {
        if(bound){
            int x = myService.getX();
            Toast.makeText(this, "value of x: " + x, Toast.LENGTH_LONG).show();

        }
    }
}
