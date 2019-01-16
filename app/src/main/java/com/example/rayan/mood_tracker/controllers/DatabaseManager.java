package com.example.rayan.mood_tracker.controllers;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


import com.example.rayan.mood_tracker.models.Mood;
import com.example.rayan.mood_tracker.models.MoodStorage;

import org.joda.time.DateTime;
import org.joda.time.LocalDate;


import java.util.ArrayList;
import java.util.List;

public class DatabaseManager extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "History.db";
    private static final int DATABASE_VERSION = 1;

    private static final String COLUMN_EPOCH = "epoch";
    private static final  String COLUMN_COMMENT = "commentary";

    DatabaseManager(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String strSql = "CREATE TABLE T_Mood ("
                + "mood_enum text ,"
                + "commentary text ,"
                + "epoch text NOT NULL"
                + ")";

        db.execSQL(strSql);
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public void insertMood(Mood mood,DateTime epoch) {

        String strSql = " INSERT INTO T_Mood (mood_enum ,epoch) VALUES ("
                + mood.name() + ","  + new DateTime() + ")";
        this.getWritableDatabase().execSQL(strSql);
    }

    public void insertComment(String comment){
        String strSql1 = "UPDATE T_Mood SET commentary = '"+comment+"' WHERE epoch =" + new LocalDate();
        this.getWritableDatabase().execSQL(strSql1);

    }


    public List<MoodStorage> readLast7() {
        ArrayList<MoodStorage> moods = new ArrayList<>();
        String strSql = "SELECT * FROM T_Mood ORDER BY epoch DESC LIMIT 7";
        Cursor cursor = this.getReadableDatabase().rawQuery(strSql, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            String moodEnumName = cursor.getString(cursor.getColumnIndex("mood_enum"));

            MoodStorage moodStorage = new MoodStorage(Mood.valueOf(moodEnumName),
                    new DateTime(cursor.getString(cursor.getColumnIndex(COLUMN_EPOCH))),
                    cursor.getString(cursor.getColumnIndex(COLUMN_COMMENT)));

            moods.add(moodStorage);
            cursor.moveToNext();
        }
        cursor.close();

        return moods;

    }
}
