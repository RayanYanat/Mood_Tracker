package com.example.rayan.mood_tracker.models;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.example.rayan.mood_tracker.controllers.DatabaseManager;


import org.joda.time.LocalDate;

public class MyAlarm extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        DatabaseManager databaseManager;
        databaseManager = new DatabaseManager(context);
        MoodStorage lastMood = databaseManager.readLast();
        if (lastMood != null && lastMood.getEpoch() != LocalDate.now()) {
            databaseManager.insertMood(Mood.HAPPY, LocalDate.now());
            databaseManager.updateComment("default mood ", LocalDate.now());
        }
    }
}

