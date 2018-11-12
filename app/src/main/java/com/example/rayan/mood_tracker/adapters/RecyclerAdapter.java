package com.example.rayan.mood_tracker.adapters;


import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.rayan.mood_tracker.R;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ImageViewHolder> {

    private int[] images;
    private int[] colors;

    public RecyclerAdapter (int [] images,int[] colors) {

        this.images=images;
        this.colors=colors;
    }

    @Override
    public ImageViewHolder onCreateViewHolder( ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.smiley_item,parent,false);
        ImageViewHolder imageViewHolder = new ImageViewHolder(view);

        return imageViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ImageViewHolder holder, int position) {

        int image_id = images [position];
        int colors_id = colors [position];
        holder.Smiley.setImageResource(image_id);
        holder.itemView.setBackgroundColor(colors_id);

    }

    @Override
    public int getItemCount() {
        return colors.length;
    }

    public static class ImageViewHolder extends RecyclerView.ViewHolder {

        ImageView Smiley;

        public ImageViewHolder( View itemView) {
            super(itemView);
            Smiley = itemView.findViewById(R.id.imageView_smiley);

        }
    }
}
