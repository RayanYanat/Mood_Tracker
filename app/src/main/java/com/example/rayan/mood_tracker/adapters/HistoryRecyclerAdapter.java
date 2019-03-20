package com.example.rayan.mood_tracker.adapters;


import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.TextView;


import com.example.rayan.mood_tracker.R;
import com.example.rayan.mood_tracker.RecyclerViewClickListener;
import com.example.rayan.mood_tracker.models.MoodStorage;


import java.util.Collections;
import java.util.List;

public class HistoryRecyclerAdapter extends RecyclerView.Adapter<HistoryRecyclerAdapter.StoredMoodViewHolder> implements RecyclerViewClickListener {

    private static final int HISTORY_ITEM = 7;

    private List<MoodStorage> mMoodStorage;

    private  RecyclerViewClickListener mItemListener;

    public String date;

    public HistoryRecyclerAdapter(List<MoodStorage> moodStorage, RecyclerViewClickListener itemListener) {
        mMoodStorage = moodStorage;
        mItemListener = itemListener;

    }


    @NonNull
    @Override
    public StoredMoodViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.history_item, parent, false);
        view.getLayoutParams().height = parent.getHeight() / 7;
        StoredMoodViewHolder storedMoodViewHolder = new StoredMoodViewHolder(view,this);
        return storedMoodViewHolder;
    }


    @Override
    public void onBindViewHolder(@NonNull StoredMoodViewHolder holder, int position) {


        final MoodStorage currentItem = mMoodStorage.get(position);

        holder.mFrameLayout.setBackgroundColor(currentItem.getMood().getColor());
        holder.mTextView.setText(currentItem.getDate((mMoodStorage.size() - 1) - position));
        holder.mImageButton.setImageResource(R.drawable.ic_comment_black_48px);
        if("".equals(currentItem.getComment())){
            holder.mImageButton.setVisibility(View.GONE);
        }
        else {
            holder.mImageButton.setVisibility(View.VISIBLE);
        }






    }

    @Override
    public int getItemCount() {
        if (mMoodStorage.size() > HISTORY_ITEM)
            mMoodStorage.remove(0);

        return mMoodStorage.size();

    }

    public void swapHistoryItem(){
        Collections.swap(mMoodStorage,6,5);
        Collections.swap(mMoodStorage,5,4);
        Collections.swap(mMoodStorage,4,3);
        Collections.swap(mMoodStorage,3,2);
        Collections.swap(mMoodStorage,2,1);
        Collections.swap(mMoodStorage,1,0);
    }

    @Override
    public void onClick(View v, int position) {
        mItemListener.onClick(v,position);
    }

    public static class StoredMoodViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private FrameLayout mFrameLayout;
        private TextView mTextView;
        private ImageButton mImageButton;
        private RecyclerViewClickListener mListener;


        private StoredMoodViewHolder(View itemView,RecyclerViewClickListener listener) {
            super(itemView);
            mFrameLayout = itemView.findViewById(R.id.history_item_layout);
            mTextView = itemView.findViewById(R.id.history_textView);
            mImageButton = itemView.findViewById(R.id.history_commentBtn);
            mListener = listener;
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            mListener.onClick(v, this.getAdapterPosition());

        }


    }
    }





