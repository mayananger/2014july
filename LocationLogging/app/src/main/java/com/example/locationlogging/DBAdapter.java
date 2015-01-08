package com.example.locationlogging;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


/**
 * Created by eladlavi on 1/5/15.
 * http://www.sqlite.org/lang.html
 * http://sqlitebrowser.org/
 */
public class DBAdapter {
    static final String KEY_DATE = "SampleTime";
    static final String KEY_LAT = "Lat";
    static final String KEY_LNG = "Lng";
    static final String DATABASE_NAME = "MyDB";
    static final String DATABASE_TABLE = "Locations";
    static final int DATABASE_VERSION = 1;

    final Context context;
    SQLiteDatabase db;
    DatabaseHelper dbHelper;

    public DBAdapter(Context ctx){
        this.context = ctx;
        dbHelper = new DatabaseHelper(ctx);
    }

    private static class DatabaseHelper extends SQLiteOpenHelper{

        public DatabaseHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL("CREATE TABLE " + DATABASE_TABLE + "("+KEY_DATE+" INTEGER, "+KEY_LAT+" REAL, "+KEY_LNG+" REAL);");
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL("DROP TABLE IF EXISTS " + DATABASE_TABLE);
            onCreate(db);
        }
    }

    public void open() {
        db = dbHelper.getWritableDatabase();
    }

    public void close(){
        db.close();
    }

    //this method will add a new record to the Locations table.
    public long insertLocation(long sampleTime, double lat, double lng){
        ContentValues values = new ContentValues();
        values.put(KEY_DATE, sampleTime);
        values.put(KEY_LAT, lat);
        values.put(KEY_LNG, lng);
        return db.insert(DATABASE_TABLE, null, values);
    }

    public Cursor getAllLocations(){
        return db.query(DATABASE_TABLE,new String[]{KEY_DATE, KEY_LAT,
                KEY_LNG}, null, null, null, null, null);

    }


}
