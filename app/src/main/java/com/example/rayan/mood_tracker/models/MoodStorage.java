package com.example.rayan.mood_tracker.models;


import android.support.annotation.IntRange;


import org.joda.time.LocalDate;


public class MoodStorage {

    private Mood mMood;
    private LocalDate epoch;
    private String comment;


    public MoodStorage(Mood mood, LocalDate epoch, String comment) {

        this.setMood(mood);
        this.setEpoch(epoch);
        this.setComment(comment);


    }

    public Mood getMood() {
        return mMood;
    }

    private void setMood(Mood mood) {
        mMood = mood;
    }

    public String getComment() {
        return comment;
    }

    private void setComment(String comment) {
        this.comment = comment;
    }

    public LocalDate getEpoch() {
        return epoch;
    }

    private void setEpoch(LocalDate epoch) {
        this.epoch = epoch;
    }

    public String getDate(@IntRange(from = 0, to = 6) int position) {
        String dayOfTheWeek[] = {"Aujourd'hui","Hier", "Avant-hier",
                "trois jours", "quatre jours", "cinq jours", "six jours"};
        String date = dayOfTheWeek[position];
        if (position > 2)
            return "il y a " + date;
        return date;
    }


}


