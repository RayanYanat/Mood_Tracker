package com.example.rayan.mood_tracker.controllers;


import android.os.Bundle;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;


import android.view.View;

import com.example.rayan.mood_tracker.R;
import com.example.rayan.mood_tracker.RecyclerViewClickListener;
import com.example.rayan.mood_tracker.adapters.HistoryRecyclerAdapter;
import com.example.rayan.mood_tracker.database.DatabaseManager;
import com.example.rayan.mood_tracker.models.MoodStorage;


import java.util.List;

public class HistoryActivity extends AppCompatActivity implements RecyclerViewClickListener {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.history_activity);
        RecyclerView historyRecyclerView = findViewById(R.id.history_recyclerview);
        List<MoodStorage> moodStorages = new DatabaseManager(this).readLast7();

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        historyRecyclerView.setHasFixedSize(true);
        historyRecyclerView.setLayoutManager(layoutManager);
        HistoryRecyclerAdapter adapter = new HistoryRecyclerAdapter(moodStorages, this);
        historyRecyclerView.setAdapter(adapter);


    }

    @Override
    public void onClick(View v, int position) {

    }
}




