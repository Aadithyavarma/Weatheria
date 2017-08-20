package com.example.aadithyavarma.weatheria;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class StoreToDatabase extends AppCompatActivity {

    /**
     * Initialize the variables;
     */
    EditText placeName,note;
    AppDatabase appDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_store_to_database);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        placeName = (EditText)findViewById(R.id.placeName);
        note = (EditText)findViewById(R.id.notes);

        Intent i = getIntent();
        placeName.setText(i.getStringExtra("place"));

        appDatabase = AppDatabase.getInstance(this);
    }

    /**
     * Save the element to database if the inputs are valid;
     * Shows a toast if the input is invalid;
     * @param view
     */
    public void saveToDB(View view){

        String place =  placeName.getText().toString();
        String exp = note.getText().toString();
        int flag =0;
        if (place.equals(null) || exp.equals(null)){
            Toast.makeText(StoreToDatabase.this,"Please enter the location name and a note..",Toast.LENGTH_SHORT).show();
        }
        else {
            DBHelper db = new DBHelper(StoreToDatabase.this);
            db.addItem(place,exp);
            MainActivity.locations.add(place);
            MainActivity.adapter.notifyDataSetChanged();
            finish();
        }
    }

}
