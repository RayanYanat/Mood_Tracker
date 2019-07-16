package com.example.rayan.mood_tracker.controllers;

import android.annotation.SuppressLint;
import android.app.AlarmManager;
import android.app.PendingIntent;

import android.content.DialogInterface;
import android.content.Intent;
import android.media.MediaPlayer;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.LinearSnapHelper;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SnapHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.EditText;

import com.example.rayan.mood_tracker.R;
import com.example.rayan.mood_tracker.adapters.RecyclerAdapter;
import com.example.rayan.mood_tracker.models.Mood;
import com.example.rayan.mood_tracker.models.MoodStorage;
import com.example.rayan.mood_tracker.models.MyAlarm;


import org.joda.time.LocalDate;


import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;


public class MainActivity extends AppCompatActivity {

    public static int BUNDLE_REQUEST_CODE = 36;

    private RecyclerView recyclerView;

    private List<Mood> collection = new ArrayList<>();

    private DatabaseManager mDatabaseManager;

    int moodPosition;

    public String comment;

    private MediaPlayer[] mMediaPlayer = new MediaPlayer[Mood.values().length];

    private Button historyButton;
    private Button commentButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.MyRecyclerView);
        historyButton = findViewById(R.id.activity_main_history_btn);
        commentButton = findViewById(R.id.activity_main_comment_btn);


        final LinearLayoutManager layoutManager = new LinearLayoutManager(this);

        mDatabaseManager = new DatabaseManager(this);

        setUpCollection();
        setUpRecyclerView(layoutManager);
        setUpRecyclerViewScroll(layoutManager);
        setUpButton();
        setUpAlarm();

    }
    private void setUpCollection(){
        collection.add(Mood.SAD);
        collection.add(Mood.DISAPPOINTED);
        collection.add(Mood.NORMAL);
        collection.add(Mood.HAPPY);
        collection.add(Mood.SUPER_HAPPY);
    }

    private void setUpRecyclerView(LinearLayoutManager layoutManager){
        //allows to choose the starting position of our recyclerview
        recyclerView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                recyclerView.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                if (mDatabaseManager.readLast().getMood()!= null) {
                    recyclerView.scrollToPosition((mDatabaseManager.readLast().getMood().getPosition()));
                }
            }
        });

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(layoutManager);
        SnapHelper snapHelper = new LinearSnapHelper();
        snapHelper.attachToRecyclerView(recyclerView);
        RecyclerAdapter adapter = new RecyclerAdapter(collection);
        recyclerView.setAdapter(adapter);
    }

    private void setUpButton(){
        //displays a dialog window when the user presses the comment button
        commentButton.setOnClickListener(new View.OnClickListener() {

            @SuppressLint("InflateParams")
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setMessage("Commentary");
                final View mView;
                mView = LayoutInflater.from(MainActivity.this).inflate(R.layout.comment_popup, null);
                builder.setView(mView);

                builder.setPositiveButton("Validate", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        EditText commentPopUp = mView.findViewById(R.id.comment_popup);
                        comment = commentPopUp.getText().toString();
                        mDatabaseManager.updateComment(comment, LocalDate.now());
                        dialog.dismiss();
                    }
                });
                builder.setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                builder.create();
                builder.show();


            }
        });


        //Start the history activity when the user presses the history button

        historyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent HistoryActivity = new Intent(MainActivity.this, HistoryActivity.class);
                startActivityForResult(HistoryActivity, BUNDLE_REQUEST_CODE);

            }
        });
    }

    private void setUpAlarm(){
        // The alarm is set to be launch at midnight
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.add(Calendar.DATE, 1);

        // Recover a PendingIntent that will perform a broadcast
        Intent alarmIntent = new Intent(this, MyAlarm.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 0, alarmIntent, 0);
        //setting of our alarm
        AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
        if (alarmManager != null) {
            alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP,
                    calendar.getTimeInMillis(), AlarmManager.INTERVAL_DAY, pendingIntent);
        }
    }

    private void setUpRecyclerViewScroll(final LinearLayoutManager layoutManager){
        // allows to recover the mood currently scrolled by the user and to add to our database the data of the last scrolled mood when the date changes
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);


            }


            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);

                MoodStorage lastMood = mDatabaseManager.readLast();
                final Mood currentMood = collection.get(layoutManager.findFirstVisibleItemPosition());

                if (newState == RecyclerView.SCROLL_STATE_DRAGGING) {
                    mDatabaseManager.updateComment("", LocalDate.now());


                } else if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    layoutManager.findFirstVisibleItemPosition();
                    playSound(currentMood);

                    assert lastMood != null;
                    if (lastMood.getEpoch().equals(LocalDate.now())) {
                        mDatabaseManager.updateMood(currentMood, LocalDate.now());
                    } else {
                        mDatabaseManager.insertMood(currentMood, LocalDate.now());
                    }
                }


            }
        });
    }


    @Override
    public void onResume() {
        super.onResume();
        moodPosition = ((collection.size() + 1) / 2);
        MoodStorage lastMood = mDatabaseManager.readLast();
        if (lastMood != null && lastMood.getEpoch().equals(LocalDate.now())) {
            moodPosition = lastMood.getMood().ordinal();
        } else {
            mDatabaseManager.insertMood(Mood.values()[moodPosition], LocalDate.now());
        }
        recyclerView.scrollToPosition(moodPosition);


    }
    @Override
    public void onPause() {
        super.onPause();
        moodPosition = ((collection.size() + 1) / 2);
        MoodStorage lastMood = mDatabaseManager.readLast();
        if (lastMood != null && lastMood.getEpoch().equals(LocalDate.now())) {
            moodPosition = lastMood.getMood().ordinal();
        } else {
            mDatabaseManager.insertMood(Mood.values()[moodPosition], LocalDate.now());
        }
        recyclerView.scrollToPosition(moodPosition);

    }

    public void playSound(Mood mood) {
        if (mMediaPlayer[mood.ordinal()] == null) {
            mMediaPlayer[mood.ordinal()] = MediaPlayer.create(this, mood.getSound());
        }
        mMediaPlayer[mood.ordinal()].start();
    }

}

