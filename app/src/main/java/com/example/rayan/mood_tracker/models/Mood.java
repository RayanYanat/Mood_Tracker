package com.example.rayan.mood_tracker.models;

import android.graphics.Color;

import com.example.rayan.mood_tracker.R;

public enum Mood {
    SAD(R.drawable.smiley_sad, Color.RED), DISAPPOINTED(R.drawable.smiley_disappointed,Color.MAGENTA),
    NORMAL(R.drawable.smiley_normal,Color.YELLOW),HAPPY(R.drawable.smiley_happy,Color.GREEN)
    ,SUPER_HAPPY(R.drawable.smiley_super_happy,Color.CYAN);
    private int drawableRes;
    private int color;

    public int getColor() {
        return color;
    }

    Mood(int drawableRes, int color) {
        this.drawableRes = drawableRes;
        this.color = color;


    }

    public int getDrawableRes() {
        return drawableRes;
    }
}
