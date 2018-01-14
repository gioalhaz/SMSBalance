package com.smsbalance.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;


public class SMSDataSource {
    // Database fields
    private SQLiteDatabase database;
    private DBHelper dbHelper;
//    private String[] allColumns = { MySQLiteHelper.COLUMN_ID,
//            MySQLiteHelper.COLUMN_COMMENT };

    public SMSDataSource(Context context) {
        dbHelper = new DBHelper(context);
    }

    public void open() throws SQLException {
        database = dbHelper.getWritableDatabase();
    }

    public void close() {
        dbHelper.close();
    }

    public void insertSMSRecord(SMSRecord record) {

        ContentValues values = new ContentValues();

//        values.put(DBHelper.COLUMN_COMMENT, comment);

        long insertId = database.insert(DBHelper.TABLE_RECORDS_NAME, null, values);
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
//        Cursor cursor = database.query(DBHelper.TABLE_COMMENTS,
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
