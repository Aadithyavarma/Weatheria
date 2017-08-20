package com.example.aadithyavarma.weatheria;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by Aadithya Varma on 8/3/2017.
 */

public class DBHelper {
    SQLiteDatabase myDb;

    /**
     * DBHelper created;
     * @param context
     */
    public DBHelper(Context context){
        myDb=context.openOrCreateDatabase("Places",MODE_PRIVATE,null);
        myDb.execSQL("CREATE TABLE IF NOT EXISTS MyUsers (id INTEGER PRIMARY KEY, location VARCHAR, notes VARCHAR)");
        Cursor c=getAllItems();
        if (c.getCount()==0){
            addItem("Paris","The city of passion and love");
        }

    }

    /**
     * Add item to database;
     * @param loc
     * @param note
     */
    public void addItem(String loc, String note){
        if(note.equals(null)){
            myDb.execSQL("INSERT INTO MyUsers (location,notes) VALUES ('"+loc+"','Enter your experience in this location')");
        }else{
            myDb.execSQL("INSERT INTO MyUsers (location,notes) VALUES ('"+loc+"','"+note+"')");
        }
    }

    /**
     * Update item in the database;
     * @param oldValue
     * @param newValue
     */
    public void updateItem(String oldValue,String newValue){
        myDb.execSQL("UPDATE MyUsers SET value = '"+newValue+"' WHERE value = '"+oldValue+"'");
    }

    /**
     * Get all elements in the database;
     * @return
     */
    public Cursor getAllItems() {
        Cursor res = myDb.rawQuery( "SELECT * FROM MyUsers", null );
        return res;
    }


}
