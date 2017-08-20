package com.example.aadithyavarma.weatheria;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by Aadithya Varma on 8/3/2017.
 */

class AppDatabase extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "Sample.db";
    public static final int DATABASE_VERSION = 1;

    private static AppDatabase instance = null;

    private AppDatabase(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    static AppDatabase getInstance(Context context){
        if (instance == null){
            instance = new AppDatabase(context);
        }
        return instance;
    }

    /**
     * Creating the database;
     * @param db
     */
    @Override
    public void onCreate(SQLiteDatabase db) {
        String val;
        val = "CREATE TABLE"+ ContentProviderHelper.TABLE_NAME + " ("
                + ContentProviderHelper.Columns._ID + " INTEGER NOT NULL PRIMARY KEY, "
                + ContentProviderHelper.Columns.location + " TEXT NOT NULL" + ")";
        Log.i("SQL",val);
        db.execSQL(val);
    }

    /**
     * If upgrading is required;
     * @param db
     * @param oldVersion
     * @param newVersion
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
