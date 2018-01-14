package com.smsbalance.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DBHelper extends SQLiteOpenHelper {

    public static class Columns {
        public static String ID = "_id";
        public static String SMS_ID = "sms_id";
        public static String PAY_DATE = "pay_date";
        public static String SOURCE = "source";
        public static String PAY_POINT = "pay_point";
        public static String CARD = "card";
        public static String CURRENCY = "currency";
        public static String AMOUNT = "amount";
        public static String BALANCE = "balance";
    }

    private static final String DB_FILE = "smsrecords.db";
    private static final int DB_VERSION = 1;

    public static final String TABLE_RECORDS_NAME = "records";

    private static final String SQL_DROP_TABLE = "DROP TABLE IF EXISTS records";
    private static final String SQL_CREATE_TABLE =
"CREATE TABLE operations ("
        + Columns.ID + " INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,"
        + Columns.SMS_ID +" INTEGER NOT NULL UNIQUE,"
        + Columns.PAY_DATE + " TEXT NOT NULL,"
        + Columns.SOURCE + " TEXT NOT NULL,"
        + Columns.PAY_POINT + " TEXT NOT NULL,"
        + Columns.CARD + "INTEGER NOT NULL,"
        + Columns.CURRENCY + "INTEGER NOT NULL,"
        + Columns.AMOUNT + " INTEGER NOT NULL,"
        + Columns.BALANCE + " INTEGER"
        +");";


    public DBHelper(Context context) {
        super(context, DB_FILE, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase database) {
        database.execSQL(SQL_CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.w(DBHelper.class.getName(),
                "Upgrading database from version " + oldVersion + " to "
                        + newVersion + ", which will destroy all old data");
        db.execSQL(SQL_DROP_TABLE);
        onCreate(db);
    }

}
