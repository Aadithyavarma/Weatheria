package com.example.aadithyavarma.weatheria;

import android.provider.BaseColumns;

/**
 * Created by Aadithya Varma on 8/3/2017.
 */

public class ContentProviderHelper {
    /**
     * Define the table name;
     */
    static final String TABLE_NAME = "locationStorage";

    /**
     * Create the columns of the database;
     */
    public static class Columns{
        public static final String _ID = BaseColumns._ID;
        public static final String location = "location";
        public static final String note = "note";

        private Columns(){

        }
    }
}
