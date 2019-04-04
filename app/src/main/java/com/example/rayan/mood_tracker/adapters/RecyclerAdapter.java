package com.example.rayan.mood_tracker.adapters;


import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.rayan.mood_tracker.R;
import com.example.rayan.mood_tracker.models.Mood;

import java.util.List;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ImageViewHolder> {

    private List<Mood> mData;


    public RecyclerAdapter(List<Mood> data) {

        mData = data;
    }


    @NonNull
    @Override
    public ImageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.smiley_item, parent, false);
        return new ImageViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ImageViewHolder holder, int position) {

        Mood moodItem = mData.get(position);
        moodItem.name();

        holder.mSmiley.setImageResource(moodItem.getDrawableRes());
        holder.itemView.setBackgroundColor(moodItem.getColor());

    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    static class ImageViewHolder extends RecyclerView.ViewHolder {

        ImageView mSmiley;

        ImageViewHolder(View itemView) {
            super(itemView);
            mSmiley = itemView.findViewById(R.id.imageView_smiley);

        }
    }


}
