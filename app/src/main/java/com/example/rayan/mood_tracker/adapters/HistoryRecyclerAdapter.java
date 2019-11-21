package com.example.rayan.mood_tracker.adapters;



import android.content.res.Resources;
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


import java.util.List;

public class HistoryRecyclerAdapter extends RecyclerView.Adapter<HistoryRecyclerAdapter.StoredMoodViewHolder> implements RecyclerViewClickListener {


    private List<MoodStorage> mMoodStorage;

    private RecyclerViewClickListener mItemListener;

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
        view.getLayoutParams().width = parent.getWidth();
        return new StoredMoodViewHolder(view, this);
    }


    @Override
    public void onBindViewHolder(@NonNull StoredMoodViewHolder holder, int position) {


        final MoodStorage currentItem = mMoodStorage.get(position);

        int width = Resources.getSystem().getDisplayMetrics().widthPixels;

        holder.mFrameLayout.setBackgroundColor(currentItem.getMood().getColor());
        holder.mTextView.setText(currentItem.getDate((mMoodStorage.size() - 1) - position));
        holder.mImageButton.setImageResource(R.drawable.ic_comment_black_48px);
        if ((currentItem.getComment()) == null || "".equals(currentItem.getComment())) {
            holder.mImageButton.setVisibility(View.GONE);
        } else {
            holder.mImageButton.setVisibility(View.VISIBLE);
        }
        switch (currentItem.getMood().getPosition()){
            case 0:
                holder.mFrameLayout.getLayoutParams().width = width*25/100;
                break;
            case 1:
                holder.mFrameLayout.getLayoutParams().width = width*40/100;
                break;
            case 2:
                holder.mFrameLayout.getLayoutParams().width = width*60/100;
                break;
            case 3:
                holder.mFrameLayout.getLayoutParams().width = width*80/100;
                break;
            case 4:
                holder.mFrameLayout.getLayoutParams().width = width;
                break;

        }
    }

    @Override
    public int getItemCount() {
        return mMoodStorage.size();
    }


    @Override
    public void onClick(View v, int position) {
        mItemListener.onClick(v, position);
    }

    public static class StoredMoodViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private FrameLayout mFrameLayout;
        private TextView mTextView;
        private ImageButton mImageButton;
        private RecyclerViewClickListener mListener;


        private StoredMoodViewHolder(View itemView, RecyclerViewClickListener listener) {
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





