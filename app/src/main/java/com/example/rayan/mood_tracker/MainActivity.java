package com.example.rayan.mood_tracker;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.LinearSnapHelper;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SnapHelper;
import android.widget.Button;

import com.example.rayan.mood_tracker.adapters.RecyclerAdapter;


public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    public int[] images = {R.drawable.smiley_sad,R.drawable.smiley_disappointed,
            R.drawable.smiley_normal,R.drawable.smiley_happy,R.drawable.smiley_super_happy};

    public int[] colors ={Color.RED,Color.MAGENTA,Color.YELLOW,Color.GREEN,Color.CYAN};

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
        SnapHelper snapHelper = new LinearSnapHelper();
        snapHelper.attachToRecyclerView(recyclerView);
        adapter = new RecyclerAdapter(images,colors);
        recyclerView.setAdapter(adapter);
    }
}
