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

    @Override
    public ImageViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //inflate le fichier xml
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.smiley_item, parent, false);
        //creer une nouvelle classe qui prend en parametre view
        ImageViewHolder imageViewHolder = new ImageViewHolder(view);

        return imageViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ImageViewHolder holder, int position) {

        Mood moodItem = mData.get(position);
        moodItem.name();

        //affiche le Mood correspondant en fonction de la position
        holder.mSmiley.setImageResource(moodItem.getDrawableRes());
        holder.itemView.setBackgroundColor(moodItem.getColor());

    }
    //le nombre d'item a afficher correspond aux nombres d'item dans notre liste de mood
    @Override
    public int getItemCount() {
        return mData.size();
    }

    // permet de definir l imageview a recycler quand l'utilisateur scroll
    public static class ImageViewHolder extends RecyclerView.ViewHolder {

        ImageView mSmiley;

        public ImageViewHolder(View itemView) {
            super(itemView);
            mSmiley = itemView.findViewById(R.id.imageView_smiley);

        }
    }


}
