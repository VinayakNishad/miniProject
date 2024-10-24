package com.example.aquaadventure.getActivity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.aquaadventure.Admin.InsertActivity.ActivityItem;
import com.example.aquaadventure.R;

import java.util.List;

public class Activity extends RecyclerView.Adapter<Activity.CardViewHolder> {

    private Context context;
    private List<ActivityItem> activityItemList;

    // Constructor to initialize context and activityItemList
    public Activity(Context context, List<ActivityItem> activityItemList) {
        this.context = context;
        this.activityItemList = activityItemList;
    }

    @NonNull
    @Override
    public CardViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Inflate the card layout
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_item, parent, false);
        return new CardViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CardViewHolder holder, int position) {
        // Get the current card item
        ActivityItem currentItem = activityItemList.get(position);

        // Bind data to the ViewHolder
        holder.titleTextView.setText(currentItem.getTitle());
        holder.titleTextView.setTextSize(20); // Larger font size for the title
        holder.titleTextView.setTypeface(null, Typeface.BOLD);
        holder.titleTextView.setTextColor(ContextCompat.getColor(context, R.color.black));

        // Load the image using Glide or any other image loading library
        Glide.with(context)
                .load(currentItem.getImageUrl()) // Load image from the URL
                .centerCrop()
                .into(holder.cardImageView); // Set the image into the ImageView
        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(context, ActivityDetail.class);
            intent.putExtra("title", currentItem.getTitle());
            intent.putExtra("description", currentItem.getDescription());
            intent.putExtra("location", currentItem.getLocation());
            intent.putExtra("price", currentItem.getPrice());
            intent.putExtra("date", currentItem.getDate());
            intent.putExtra("startTime", currentItem.getStartTime());
            intent.putExtra("endTime", currentItem.getEndTime());
            intent.putExtra("duration", currentItem.getDuration());
            intent.putExtra("imageUrl", currentItem.getImageUrl());

            context.startActivity(intent);
        });
    }


    @Override
    public int getItemCount() {
        return activityItemList.size(); // Return the size of the list
    }

    // ViewHolder class to hold references to the views inside the card
    public static class CardViewHolder extends RecyclerView.ViewHolder {

        // UI components in the card layout
        public ImageView cardImageView;
        public TextView titleTextView;

        public CardViewHolder(@NonNull View itemView) {
            super(itemView);
            // Initialize the views
            cardImageView = itemView.findViewById(R.id.card_image);
            titleTextView = itemView.findViewById(R.id.card_title);
        }
    }
}
