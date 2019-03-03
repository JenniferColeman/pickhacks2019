package com.pickhacks.pickhacks2019;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Base64;
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

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Context context = v.getContext();
                    Intent intent = new Intent(context, PlantDetailActivity.class);
                    intent.putExtra("plantName", mNameTextView.getText());
                    context.startActivity(intent);
                }
            });
        }
    }

    public SearchItemAdapter(ArrayList<SearchItem> searchItems) {
        mSearchItems = searchItems;
    }

    @NonNull
    @Override
    public SearchItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.search_item, parent, false);
        return new SearchItemViewHolder(v);
    }

    public void onBindViewHolder(@NonNull SearchItemViewHolder viewHolder, int position) {
        SearchItem currentItem = mSearchItems.get(position);

        loadImageFromBase64IntoView(viewHolder.mPhotoImageView, currentItem.getmPhoto());
        if (currentItem.ismStar()) {
            viewHolder.mStarImageView.setImageResource(R.drawable.filledstar);
        }
        else {
            viewHolder.mStarImageView.setImageResource(R.drawable.emptystar);
        }
        viewHolder.mBriefTextView.setText(currentItem.getmBrief());
        viewHolder.mNameTextView.setText(currentItem.getmName());
        viewHolder.mTimeTextView.setText(currentItem.getmTime());
    }

    @Override
    public int getItemCount() {
        return mSearchItems.size();
    }

    private void loadImageFromBase64IntoView(ImageView imageView, String encodedImage) {
        byte[] decodedString = Base64.decode(encodedImage, Base64.DEFAULT);
        System.out.println(encodedImage);
        Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
        imageView.setImageBitmap(decodedByte);
    }
}
