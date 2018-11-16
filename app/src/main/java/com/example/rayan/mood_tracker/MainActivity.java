package com.example.rayan.mood_tracker;

import android.graphics.Color;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.LinearSnapHelper;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SnapHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

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
    private EditText commentPopup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView = findViewById(R.id.MyRecyclerView);
        historyButton = findViewById(R.id.activity_main_history_btn);
        commentButton = findViewById(R.id.activity_main_comment_btn);
        commentPopup = findViewById(R.id.comment_Popup);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(layoutManager);
        SnapHelper snapHelper = new LinearSnapHelper();
        snapHelper.attachToRecyclerView(recyclerView);
        adapter = new RecyclerAdapter(images,colors);
        recyclerView.setAdapter(adapter);

        commentButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder= new AlertDialog.Builder(MainActivity.this);
                builder.setMessage("commentary");
                        View mView = LayoutInflater.from(MainActivity.this).inflate(R.layout.comment_popup,null);
                builder.setView(mView);
                        commentPopup = (EditText)mView.findViewById(R.id.comment_Popup);
                        commentPopup.getText().toString();
                builder.show();

            }
            });
    }
}
