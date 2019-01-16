package com.example.rayan.mood_tracker.models;


import android.support.annotation.IntRange;


import org.joda.time.DateTime;


public class MoodStorage {

    private  Mood mMood;
    private DateTime epoch;
    private  String mDate;
    private String comment;




    public MoodStorage(Mood mood, DateTime epoch, String comment) {

        this.setMood(mood);
        this.setEpoch(epoch);
        this.setComment(comment);


    }

    public  Mood getMood() {
        return mMood;
    }

    private void setMood(Mood mood) {
        mMood = mood;
    }

    public String getComment() {
        return comment;
    }

    private void setComment(String comment){
        this.comment = comment;
    }

    private void setEpoch(DateTime epoch) {
        this.epoch = epoch;
    }

    public String getDate(@IntRange (from = 0,to = 6 )int position) {
        String dayOfTheWeek[] = {"Hier", "Avant-hier",
                "trois jours", "quatre jours", "cinq jours", "six jours", "une semaine"};
        mDate = dayOfTheWeek[position];
        if (position > 1)
            return "il y a" + mDate;
        return mDate;
    }




    }


