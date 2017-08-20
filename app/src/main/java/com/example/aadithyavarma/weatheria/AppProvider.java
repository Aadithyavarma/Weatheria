package com.example.aadithyavarma.weatheria;

import android.content.ContentProvider;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;

import java.util.Arrays;
import java.util.HashSet;

/**
 * Created by Aadithya Varma on 8/3/2017.
 */

public class AppProvider extends ContentProvider {

    private AppDatabase mHelper;

    // Used for the UriMatcher;
    private static final int LOCS = 10;
    private static final int LOC_ID = 20;

    static final String CONTENT_AUTHORITY = "com.example.aadithyavarma.weatheria.provider";
    public static final String BASE_PATH = "weatheria";
    public static final Uri CONTENT_URI = Uri.parse("content://"+ CONTENT_AUTHORITY + "/" + BASE_PATH);
    public static final String CONTENT_TYPE = ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + BASE_PATH;
    public static final String CONTENT_ITEM_TYPE = ContentResolver.CURSOR_ITEM_BASE_TYPE  + "/" + ContentProviderHelper.TABLE_NAME;

    private static final UriMatcher sURIMatcher = new UriMatcher(
            UriMatcher.NO_MATCH);
    static {
        sURIMatcher.addURI(CONTENT_AUTHORITY, BASE_PATH, LOCS);
        sURIMatcher.addURI(CONTENT_AUTHORITY, BASE_PATH + "/#", LOC_ID);
    }

    /**
     * Database creation;
     * @return
     */
    @Override
    public boolean onCreate() {
        mHelper = AppDatabase.getInstance(getContext());
        return false;
    }

    /**
     * Cursor to access the tables;
     * @param uri
     * @param projection
     * @param selection
     * @param selectionArgs
     * @param sortOrder
     * @return
     */
    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {

        SQLiteQueryBuilder queryBuilder = new SQLiteQueryBuilder();
        checkColumns(projection);

        queryBuilder.setTables(ContentProviderHelper.TABLE_NAME);
        int uriType = sURIMatcher.match(uri);
        switch (uriType){
            case LOCS:
                break;
            case LOC_ID:
                queryBuilder.appendWhere(ContentProviderHelper.Columns._ID + "=" + uri.getLastPathSegment());
                break;
            default:
                throw new IllegalArgumentException("Unknown URI: " + uri);
        }

        SQLiteDatabase db = mHelper.getWritableDatabase();
        Cursor cursor = queryBuilder.query(db, projection, selection, selectionArgs, null, null, sortOrder);
        cursor.setNotificationUri(getContext().getContentResolver(), uri);

        return cursor;
    }


    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        return null;
    }

    /**
     * Insert the element in the Database;
     * @param uri
     * @param values
     * @return
     */
    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {
        int uriType = sURIMatcher.match(uri);
        SQLiteDatabase sqlDB = mHelper.getWritableDatabase();
        long id = 0;
        switch (uriType) {
            case LOCS:
                id = sqlDB.insert(ContentProviderHelper.TABLE_NAME, null, values);
                break;
            default:
                throw new IllegalArgumentException("Unknown URI: " + uri);
        }
        getContext().getContentResolver().notifyChange(uri, null);
        return Uri.parse(BASE_PATH + "/" + id);
    }

    /**
     * Delete the element from the database;
     * @param uri
     * @param selection
     * @param selectionArgs
     * @return
     */
    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        int uriType = sURIMatcher.match(uri);
        SQLiteDatabase sqlDB = mHelper.getWritableDatabase();
        int rowsDeleted = 0;
        switch (uriType) {
            case LOCS:
                rowsDeleted = sqlDB.delete(ContentProviderHelper.TABLE_NAME, selection,
                        selectionArgs);
                break;
            case LOC_ID:
                String id = uri.getLastPathSegment();
                if (TextUtils.isEmpty(selection)) {
                    rowsDeleted = sqlDB.delete(
                            ContentProviderHelper.TABLE_NAME,
                            ContentProviderHelper.Columns._ID + "=" + id,
                            null);
                } else {
                    rowsDeleted = sqlDB.delete(
                            ContentProviderHelper.TABLE_NAME,
                            ContentProviderHelper.Columns._ID + "=" + id
                                    + " and " + selection,
                            selectionArgs);
                }
                break;
            default:
                throw new IllegalArgumentException("Unknown URI: " + uri);
        }
        getContext().getContentResolver().notifyChange(uri, null);
        return rowsDeleted;
    }

    /**
     * Update the element in the database;
     * @param uri
     * @param values
     * @param selection
     * @param selectionArgs
     * @return
     */
    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs) {
        int uriType = sURIMatcher.match(uri);
        SQLiteDatabase sqlDB = mHelper.getWritableDatabase();
        int rowsUpdated = 0;
        switch (uriType) {
            case LOCS:
                rowsUpdated = sqlDB.update(ContentProviderHelper.TABLE_NAME,
                        values,
                        selection,
                        selectionArgs);
                break;
            case LOC_ID:
                String id = uri.getLastPathSegment();
                if (TextUtils.isEmpty(selection)) {
                    rowsUpdated = sqlDB.update(ContentProviderHelper.TABLE_NAME,
                            values,
                            ContentProviderHelper.Columns._ID + "=" + id,
                            null);
                } else {
                    rowsUpdated = sqlDB.update(ContentProviderHelper.TABLE_NAME,
                            values,
                            ContentProviderHelper.Columns._ID + "=" + id
                                    + " and "
                                    + selection,
                            selectionArgs);
                }
                break;
            default:
                throw new IllegalArgumentException("Unknown URI: " + uri);
        }
        getContext().getContentResolver().notifyChange(uri, null);
        return rowsUpdated;
    }

    /**
     * Check the columns in the database with the input
     * to check if there is a match;
     * @param projection
     */
    private void checkColumns(String[] projection) {
        String[] available = { ContentProviderHelper.Columns._ID, ContentProviderHelper.Columns.location};
        if (projection != null) {
            HashSet<String> requestedColumns = new HashSet<String>(
                    Arrays.asList(projection));
            HashSet<String> availableColumns = new HashSet<String>(
                    Arrays.asList(available));
            // check if all columns which are requested are available
            if (!availableColumns.containsAll(requestedColumns)) {
                throw new IllegalArgumentException(
                        "Unknown columns in projection");
            }
        }
    }
}
