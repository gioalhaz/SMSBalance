package com.smsbalance.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by gioal on 02.07.2017.
 */

public class SMSDataSource {
    // Database fields
    private SQLiteDatabase database;
    private SMSRecordsDBHelper dbHelper;
//    private String[] allColumns = { MySQLiteHelper.COLUMN_ID,
//            MySQLiteHelper.COLUMN_COMMENT };

    public SMSDataSource(Context context) {
        dbHelper = new SMSRecordsDBHelper(context);
    }

    public void open() throws SQLException {
        database = dbHelper.getWritableDatabase();
    }

    public void close() {
        dbHelper.close();
    }

    public void insertSMSRecord(SMSRecord record) {

        ContentValues values = new ContentValues();

//        values.put(SMSRecordsDBHelper.COLUMN_COMMENT, comment);

        long insertId = database.insert(SMSRecordsDBHelper.TABLE_RECORDS, null,
                values);
    }

//    public void deleteSMSRecord(Comment comment) {
//        long id = comment.getId();
//        System.out.println("Comment deleted with id: " + id);
//        database.delete(MySQLiteHelper.TABLE_COMMENTS, MySQLiteHelper.COLUMN_ID
//                + " = " + id, null);
//    }

//    public List<SMSRecord> getAllSMSRecords() {
//        List<SMSRecord> list = new ArrayList<SMSRecord>();
//
//        Cursor cursor = database.query(SMSRecordsDBHelper.TABLE_COMMENTS,
//                allColumns, null, null, null, null, null);
//
//        cursor.moveToFirst();
//        while (!cursor.isAfterLast()) {
//            Comment comment = cursorToComment(cursor);
//            comments.add(comment);
//            cursor.moveToNext();
//        }
//        // make sure to close the cursor
//        cursor.close();
//        return comments;
//    }

    /*
    private Comment cursorToComment(Cursor cursor) {
        Comment comment = new Comment();
        comment.setId(cursor.getLong(0));
        comment.setComment(cursor.getString(1));
        return comment;
    }*/
}
