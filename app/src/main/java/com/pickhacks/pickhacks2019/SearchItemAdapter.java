package com.pickhacks.pickhacks2019;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class SearchItemAdapter extends RecyclerView.Adapter<SearchItemAdapter.SearchItemViewHolder> {

    private ArrayList<SearchItem> mSearchItems;

    public static class SearchItemViewHolder extends RecyclerView.ViewHolder {

        public ImageView mPhotoImageView;
        public ImageView mStarImageView;
        public TextView mNameTextView;
        public TextView mTimeTextView;
        public TextView mBriefTextView;

        public SearchItemViewHolder(View itemView) {
            super(itemView);

            this.mPhotoImageView = itemView.findViewById(R.id.photoImageView);
            this.mStarImageView = itemView.findViewById(R.id.starImageView);
            this.mNameTextView = itemView.findViewById(R.id.nameTextView);
            this.mTimeTextView = itemView.findViewById(R.id.timeTextView);
            this.mBriefTextView = itemView.findViewById(R.id.briefTextView);
        }
    }

    public SearchItemAdapter(ArrayList<SearchItem> searchItems) {
        mSearchItems = searchItems;
    }

    @NonNull
    @Override
    public SearchItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.search_item, parent, false);
        SearchItemViewHolder sivh = new SearchItemViewHolder(v);
        return sivh;
    }

    public void onBindViewHolder(SearchItemViewHolder viewHolder, int position) {
        SearchItem currentItem = mSearchItems.get(position);

        viewHolder.mPhotoImageView.setImageResource(currentItem.getmPhoto());
        //viewHolder.mStarImageView.setImageResource(currentItem.ismStar());
        viewHolder.mBriefTextView.setText(currentItem.getmBrief());
        viewHolder.mNameTextView.setText(currentItem.getmName());
        viewHolder.mTimeTextView.setText(currentItem.getmTime());
    }

    @Override
    public int getItemCount() {
        return mSearchItems.size();
    }
}
