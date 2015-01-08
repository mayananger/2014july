package com.example.locationlogging;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

/**
 * Created by eladlavi on 1/8/15.
 */
public class MyService extends Service {
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    int x = 0;

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        //Toast.makeText(this, "Service Start " + x++, Toast.LENGTH_LONG).show();
        Log.d("Elad", "Service Start " + x++);
        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        //Toast.makeText(this,"Service Destroyed", Toast.LENGTH_LONG).show();
        Log.d("Elad", "Service Stop");
    }
}
