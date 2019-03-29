package com.example.rayan.mood_tracker.controllers;

import android.annotation.SuppressLint;
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


import org.joda.time.LocalDate;


import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity {

    public static int BUNDLE_REQUEST_CODE = 36;

    private RecyclerView recyclerView;

    private List<Mood> collection = new ArrayList<>();

    private DatabaseManager mDatabaseManager;

    int moodPosition ;

    private String comment;

    private MediaPlayer [] mMediaPlayer = new MediaPlayer[Mood.values().length];




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        collection.add(Mood.SAD);
        collection.add(Mood.DISAPPOINTED);
        collection.add(Mood.NORMAL);
        collection.add(Mood.HAPPY);
        collection.add(Mood.SUPER_HAPPY);

        recyclerView = findViewById(R.id.MyRecyclerView);
        Button historyButton = findViewById(R.id.activity_main_history_btn);
        Button commentButton = findViewById(R.id.activity_main_comment_btn);


        //permet de choisir la position de depart de notre recyclerview
        recyclerView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                recyclerView.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                recyclerView.scrollToPosition((collection.size() + 1) / 2);
            }
        });

        final LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(layoutManager);
        SnapHelper snapHelper = new LinearSnapHelper();
        snapHelper.attachToRecyclerView(recyclerView);
        RecyclerAdapter adapter = new RecyclerAdapter(collection);
        recyclerView.setAdapter(adapter);

        mDatabaseManager = new DatabaseManager(this);

        //affiche une fenetre de dialogue lorsque l'utilisateur appuie sur le bouton comment
        commentButton.setOnClickListener(new View.OnClickListener() {

            @SuppressLint("InflateParams")
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setMessage("commentary");
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

        //Demarre l'history activity quand l'utilisateur appuie sur le bouton historique
        historyButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent HistoryActivity = new Intent(MainActivity.this, HistoryActivity.class);
                startActivityForResult(HistoryActivity, BUNDLE_REQUEST_CODE);

            }
        });

        //permet de recuperer le mood actuellement scrollé par l'utilisateur et de d' ajouer a notre
        //base de donnee les data du dernier mood scrollé lorsque la date change
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {



            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                mDatabaseManager.updateComment("",LocalDate.now());


            }


            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);

                MoodStorage lastMood = mDatabaseManager.readLast();
                Mood currentMood = collection.get(layoutManager.findFirstVisibleItemPosition());

                if (newState == RecyclerView.SCROLL_STATE_DRAGGING) {
                    resetComment();
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
            recyclerView.scrollToPosition(moodPosition);
        } else {
            mDatabaseManager.insertMood(Mood.values()[moodPosition], LocalDate.now());
        }


    }

    //reset la valeur du commentaire chaque fois que l'utilisateur scroll
    public void resetComment() {
        comment = "";
    }

    public void playSound(Mood mood){
        if(mMediaPlayer[mood.ordinal()] == null) {
            mMediaPlayer [mood.ordinal()] = MediaPlayer.create(this, mood.getSound());
        }
        mMediaPlayer[mood.ordinal()].start();
    }

}

