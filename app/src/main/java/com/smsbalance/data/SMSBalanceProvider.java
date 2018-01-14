package com.smsbalance.data;

import android.content.ContentProvider;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;

import android.database.Cursor;
import android.database.SQLException;

import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;

import android.net.Uri;
import android.text.TextUtils;

public class SMSBalanceProvider extends ContentProvider {

    static final String PROVIDER_NAME = "com.smsbalance.SMSBalanceProvider";
    static final String SMS_RECORDS_PATH = "smsrecords";
//    static final String URL = "content://" + PROVIDER_NAME + "/";
    static final String URL = ContentResolver.SCHEME_CONTENT + PROVIDER_NAME + "/" + SMS_RECORDS_PATH;
    static final Uri CONTENT_URI = Uri.parse(URL);

//    private static HashMap<String, String> STUDENTS_PROJECTION_MAP;

    static final int SMS_RECORDS = 1;
    static final int SMS_RECORD_ID = 2;

    static final UriMatcher uriMatcher;
    static{
        uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        uriMatcher.addURI(PROVIDER_NAME, SMS_RECORDS_PATH, SMS_RECORDS);
        uriMatcher.addURI(PROVIDER_NAME, SMS_RECORDS_PATH + "/#", SMS_RECORD_ID);
    }

    /**
     * Database specific constant declarations
     */

    private SQLiteDatabase db;

    @Override
    public boolean onCreate() {
        Context context = getContext();
        DBHelper dbHelper = new DBHelper(context);

        /**
         * Create a write able database which will trigger its
         * creation if it doesn't already exist.
         */

        db = dbHelper.getWritableDatabase();
        db.close();
        return db != null;
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        /**
         * Add a new student record
         */
        long rowID = db.insert(DBHelper.TABLE_RECORDS_NAME, "", values);

        /**
         * If record is added successfully
         */
        if (rowID > 0) {
            Uri newUri = ContentUris.withAppendedId(CONTENT_URI, rowID);
            getContext().getContentResolver().notifyChange(newUri, null);
            return newUri;
        }

        throw new SQLException("Failed to add a record into " + uri);
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection,String[] selectionArgs, String sortOrder) {
        SQLiteQueryBuilder qb = new SQLiteQueryBuilder();
        qb.setTables(DBHelper.TABLE_RECORDS_NAME);

//        switch (uriMatcher.match(uri)) {
//            case SMS_RECORDS:
//                qb.setProjectionMap(STUDENTS_PROJECTION_MAP);
//                break;
//
//            case STUDENT_ID:
//                qb.appendWhere( _ID + "=" + uri.getPathSegments().get(1));
//                break;
//
//            default:
//        }

        if (sortOrder == null || sortOrder.equals("")){

            sortOrder = DBHelper.Columns.SMS_ID; // ??? DBHelper.Columns.PAY_DATE
        }

        Cursor c = qb.query(db,	projection,	selection,
                selectionArgs,null, null, sortOrder);
        /**
         * register to watch a content URI for changes
         */
        c.setNotificationUri(getContext().getContentResolver(), uri);
        return c;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        int count;// = 0;
        switch (uriMatcher.match(uri)){
            case SMS_RECORDS:
                count = db.delete(DBHelper.TABLE_RECORDS_NAME, selection, selectionArgs);
                break;

            case SMS_RECORD_ID:
                String id = uri.getPathSegments().get(1);
                count = db.delete( DBHelper.TABLE_RECORDS_NAME, DBHelper.Columns.ID +  " = " + id +
                                (!TextUtils.isEmpty(selection) ? " AND (" + selection + ')' : ""), selectionArgs);
                break;
            default:
                throw new IllegalArgumentException("Unknown URI " + uri);
        }

        getContext().getContentResolver().notifyChange(uri, null);
        return count;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        int count;// = 0;
        switch (uriMatcher.match(uri)) {
            case SMS_RECORDS:
                count = db.update(DBHelper.TABLE_RECORDS_NAME, values, selection, selectionArgs);
                break;

            case SMS_RECORD_ID:
                count = db.update(DBHelper.TABLE_RECORDS_NAME, values,
                        DBHelper.Columns.ID + " = " + uri.getPathSegments().get(1) +
                                (!TextUtils.isEmpty(selection) ? " AND (" +selection + ')' : ""), selectionArgs);
                break;
            default:
                throw new IllegalArgumentException("Unknown URI " + uri );
        }

        getContext().getContentResolver().notifyChange(uri, null);
        return count;
    }

    @Override
    public String getType(Uri uri) {
        switch (uriMatcher.match(uri)){
            /**
             * Get all student records
             */
            case SMS_RECORDS:
                return "vnd.android.cursor.dir/vnd.example.students";
            /**
             * Get a particular student
             */
            case SMS_RECORD_ID:
                return "vnd.android.cursor.item/vnd.example.students";
            default:
                throw new IllegalArgumentException("Unsupported URI: " + uri);
        }
    }
}