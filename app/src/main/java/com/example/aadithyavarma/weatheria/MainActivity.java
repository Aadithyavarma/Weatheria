package com.example.aadithyavarma.weatheria;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    /**
     * initialize the variables;
     */
    EditText place;
    public static Spinner spinner;
    public static TextView weatherReport;
    public static ImageView image;
    public static ArrayList<String> locations;
    public static ArrayAdapter<String> adapter;
    Uri picture;
    String weather;

    /**
     * Local Broadcast Receiver to get data back from the Broadcast Receiver;
     */
    BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            weather = intent.getStringExtra("weatherDetails");
            picture = intent.getData();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        place = (EditText)findViewById(R.id.place);
        spinner = (Spinner)findViewById(R.id.savedLocations);
        weatherReport = (TextView)findViewById(R.id.weatherReport);
        image =(ImageView)findViewById(R.id.image);

        locations = new ArrayList<>();

        /**
         * Register the local Broadcast Receiver;
         */
        registerReceiver(broadcastReceiver,new IntentFilter("broadcast"));

        fillDataForSpinner();
    }

    /**
     * Insert the saved elements from Database to spinner;
     */
    private void fillDataForSpinner() {
        DBHelper db = new DBHelper(MainActivity.this);
        Cursor cursor = db.getAllItems();
        int loc = cursor.getColumnIndex("location");
        cursor.moveToFirst();
        do {
            locations.add(cursor.getString(loc));
        }while (cursor.moveToNext() && cursor.getCount() > 0);
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, locations);
        spinner.setAdapter(adapter);
    }

    /**
     * Button to search the weather details of the place
     * given in the EditText;
     * @param view
     */
    public void searchPlace(View view){

        InputMethodManager inputManager = (InputMethodManager)
                getSystemService(Context.INPUT_METHOD_SERVICE);

        inputManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),
                InputMethodManager.HIDE_NOT_ALWAYS);

        image.setVisibility(View.VISIBLE);
        Intent i = new Intent();
        i.putExtra("input",place.getText().toString());
        i.setAction("MyReceiver");
        sendBroadcast(i);
    }

    /**
     * Button to search the weather details of the place
     * selected in the spinner;
     * @param view
     */
    public void viewPlace(View view){

        InputMethodManager inputManager = (InputMethodManager)
                getSystemService(Context.INPUT_METHOD_SERVICE);

        inputManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),
                InputMethodManager.HIDE_NOT_ALWAYS);

        image.setVisibility(View.VISIBLE);

        int pos = spinner.getSelectedItemPosition()+1;
        String placeName = spinner.getSelectedItem().toString();

        Intent i = new Intent();
        i.putExtra("input",placeName);
        i.putExtra("note","sent");
        i.setAction("MyReceiver");
        sendBroadcast(i);
    }


    /**
     * Intent to open StoreToDatabase class;
     * @param view
     */
    public void addPlace(View view){
        Intent i = new Intent(MainActivity.this,StoreToDatabase.class);
        i.putExtra("place",place.getText().toString());
        startActivity(i);
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
