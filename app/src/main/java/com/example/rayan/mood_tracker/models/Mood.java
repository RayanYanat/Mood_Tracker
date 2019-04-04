package com.example.rayan.mood_tracker.models;

import android.graphics.Color;
import android.support.annotation.ColorInt;

import com.example.rayan.mood_tracker.R;

public enum Mood {
    SAD(R.drawable.smiley_sad, Color.RED, R.raw.sound1),
    DISAPPOINTED(R.drawable.smiley_disappointed, Color.MAGENTA, R.raw.sound2),
    NORMAL(R.drawable.smiley_normal, Color.YELLOW, R.raw.sound3),
    HAPPY(R.drawable.smiley_happy, Color.GREEN, R.raw.sound4),
    SUPER_HAPPY(R.drawable.smiley_super_happy, Color.CYAN, R.raw.sound5);
    private int drawableRes;
    private int color;
    private int sound;

    @ColorInt
    public int getColor() {
        return color;
    }

    Mood(int drawableRes, int color, int sound) {
        this.drawableRes = drawableRes;
        this.color = color;
        this.sound = sound;


    }

    public int getDrawableRes() {
        return drawableRes;
    }

    public int getSound() {
        return sound;
    }
}
