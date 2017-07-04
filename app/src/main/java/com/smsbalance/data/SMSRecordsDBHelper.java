package com.smsbalance.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by gioal on 02.07.2017.
 */

public class SMSRecordsDBHelper extends SQLiteOpenHelper {

    public static final String TABLE_RECORDS = "records";

    private static final int DB_VERSION = 1;
    private static final String DB_FILE = "smsrecords.db";


    // Database creation sql statement
    private static final String DATABASE_CREATE =
            "CREATE TABLE records ("
            + "id INTEGER PRIMARY KEY AUTOINCREMENT,"
            + "id INTEGER NOT NULL,"
            + "threadId INTEGER NOT NULL,"
            + "address TEXT NOT NULL,"
            + "date INTEGER NOT NULL,"
            + "body TEXT NOT NULL,"
            + "deleted INTEGER NOT NULL,"
            + "type INTEGER NOT NULL,"
            +");";


    public SMSRecordsDBHelper(Context context) {
        super(context, DB_FILE, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase database) {
        database.execSQL(DATABASE_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.w(SMSRecordsDBHelper.class.getName(),
                "Upgrading database from version " + oldVersion + " to "
                        + newVersion + ", which will destroy all old data");
        db.execSQL("DROP TABLE IF EXISTS records");
        onCreate(db);
    }
}
