package com.example.aadithyavarma.weatheria;

import android.app.Service;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.IBinder;
import android.support.annotation.IntDef;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

import java.util.ArrayList;

public class MyService extends Service {

    public static ArrayList<String> locations;
    private SimpleCursorAdapter adapter;
    public static String note;

    AppDatabase appDatabase;

    public MyService() {
    }

    /**
     * Database initializing;
     * @param intent
     * @return
     */
    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        appDatabase = AppDatabase.getInstance(this);
        final SQLiteDatabase db = appDatabase.getReadableDatabase();
        return null;
    }

    /**
     * Service started and getting data from database;
     * @param intent
     * @param flags
     * @param startId
     * @return
     */
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        //Toast.makeText(getApplicationContext(),"hai",Toast.LENGTH_LONG).show();
        int pos = MainActivity.spinner.getSelectedItemPosition()+1;
        DBHelper db = new DBHelper(getApplicationContext());
        Cursor cursor = db.getAllItems();
        int loc = cursor.getColumnIndex("notes");
        cursor.move(pos);
        String res = cursor.getString(loc);

        String output = intent.getStringExtra("input") + "\nNote:\n" + res;
        MainActivity.weatherReport.setText(output);

        return START_NOT_STICKY;
    }
}
