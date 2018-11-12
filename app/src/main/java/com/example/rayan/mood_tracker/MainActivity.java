package com.example.rayan.mood_tracker;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Button;

import com.example.rayan.mood_tracker.adapters.RecyclerAdapter;


public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private int[] images = {R.drawable.smiley_sad,R.drawable.smiley_disappointed,
            R.drawable.smiley_normal,R.drawable.smiley_happy,R.drawable.smiley_super_happy};

    private int[] colors ={R.color.banana_yellow, R.color.light_sage, R.color.warm_grey, R.color.cornflower_blue_65
            ,R.color.faded_red};

    private RecyclerAdapter adapter;
    private RecyclerView.LayoutManager layoutManager;

    private Button historyButton;
    private Button commentButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView = findViewById(R.id.MyRecyclerView);
        historyButton = findViewById(R.id.activity_main_history_btn);
        commentButton = findViewById(R.id.activity_main_comment_btn);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new RecyclerAdapter(images,colors);
        recyclerView.setAdapter(adapter);
    }
}
