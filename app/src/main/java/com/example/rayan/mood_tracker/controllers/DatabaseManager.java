package com.example.rayan.mood_tracker.controllers;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.Nullable;


import com.example.rayan.mood_tracker.models.Mood;
import com.example.rayan.mood_tracker.models.MoodStorage;


import org.joda.time.LocalDate;


import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class DatabaseManager extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "History.db";
    private static final int DATABASE_VERSION = 1;

    private static final String COLUMN_EPOCH = "epoch";
    private static final String COLUMN_COMMENT = "commentary";
    private static final String TABLE_TITTLE = "T_Mood";
    private static final String MOOD_ENUM = "mood_enum";

    public DatabaseManager(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String strSql = "CREATE TABLE " + TABLE_TITTLE + " ("
                + "mood_enum TEXT ,"
                + "commentary TEXT ,"
                + "epoch text NOT NULL"
                + ")";

        db.execSQL(strSql);
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public void insertMood(Mood mood, LocalDate epoch) {

        String strSql = " INSERT INTO " + TABLE_TITTLE + " (mood_enum ,epoch) VALUES ('"
                + mood.name() + "','" + epoch + "')";
        this.getWritableDatabase().execSQL(strSql);
    }

    public void updateMood(Mood mood, LocalDate epoch) {
        String strSql = "UPDATE " + TABLE_TITTLE + " SET mood_enum = '" + mood.name() + "' WHERE epoch ='" + epoch + "'";
        getWritableDatabase().execSQL(strSql);
    }

    public void updateComment(String comment, LocalDate epoch) {
        String strSql1 = "UPDATE " + TABLE_TITTLE + " SET commentary = '" + comment + "' WHERE epoch ='" + epoch + "'";
        this.getWritableDatabase().execSQL(strSql1);

    }

    @Nullable
    public MoodStorage readLast() {
        String strSql2 = "SELECT * FROM " + TABLE_TITTLE + " ORDER BY epoch DESC LIMIT 1";
        Cursor cursor2 = this.getReadableDatabase().rawQuery(strSql2, null);
        if (!cursor2.moveToLast()) {
            return null;
        }

        String moodEnumName = cursor2.getString(cursor2.getColumnIndex(MOOD_ENUM));

        MoodStorage lastMoodStored = new MoodStorage(Mood.valueOf(moodEnumName),
                new LocalDate(cursor2.getString(cursor2.getColumnIndex(COLUMN_EPOCH))),
                cursor2.getString(cursor2.getColumnIndex(COLUMN_COMMENT)));
        cursor2.close();

        return lastMoodStored;
    }

    public List<MoodStorage> readLast7() {
        ArrayList<MoodStorage> moods = new ArrayList<>();
        String strSql = "SELECT * FROM " + TABLE_TITTLE + " ORDER BY epoch DESC LIMIT 7";
        Cursor cursor = this.getReadableDatabase().rawQuery(strSql, null);
        cursor.moveToFirst();
        cursor.moveToNext();
        while (!cursor.isAfterLast()) {
            String moodEnumName = cursor.getString(cursor.getColumnIndex(MOOD_ENUM));

            MoodStorage moodStorage = new MoodStorage(Mood.valueOf(moodEnumName),
                    new LocalDate(cursor.getString(cursor.getColumnIndex(COLUMN_EPOCH))),
                    cursor.getString(cursor.getColumnIndex(COLUMN_COMMENT)));

            moods.add(moodStorage);
            cursor.moveToNext();
        }
        cursor.close();
        Collections.reverse(moods);

        return moods;

    }
}
