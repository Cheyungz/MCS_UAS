package com.example.uas_mcs.helper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {

    public DBHelper(Context context) {
        super(context, "notification.db", null, 2);
    }

    @Override
    public void onCreate(SQLiteDatabase dbOpenHelper) {
        dbOpenHelper.execSQL("create table notification (title TEXT, " +
                "body TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase dbOpenHelper, int oldVersion, int newVersion) {
        if (oldVersion < newVersion) {
            dbOpenHelper.execSQL("DROP TABLE IF EXISTS notification");
            onCreate(dbOpenHelper);
        }
    }

    public Boolean insertNotificationData(String title, String body){
        SQLiteDatabase dbOpenHelper = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("title", title);
        contentValues.put("body", body);
        long check = dbOpenHelper.insert("notification", null, contentValues);
        if (check == -1){
            return false;
        }
        else{
            return true;
        }
    }

    public Cursor getData(){
        SQLiteDatabase dbOpenHelper = this.getReadableDatabase();
        Cursor cursor =dbOpenHelper.rawQuery("SELECT * FROM notification",null);
        return cursor;
    }

    public void clearData(){
        SQLiteDatabase dbOpenHelper = this.getReadableDatabase();
        dbOpenHelper.execSQL("drop table if exists notification");
    }
}
