package com.example.aquaadventure.getActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.aquaadventure.Booking;
import com.example.aquaadventure.Home;
import com.example.aquaadventure.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class ActivityDetail extends AppCompatActivity {

    private ImageView cardImageView;
    FloatingActionButton btn_back;
    private TextView titleTextView, descriptionTextView, locationTextView, priceTextView, dateTextView, startTimeTextView, endTimeTextView, durationTextView;
    private Button bookButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_card_detail);
        btn_back=findViewById(R.id.backButton);
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(ActivityDetail.this, Home.class);
                startActivity(i);
            }
        });

        // Initialize views
        cardImageView = findViewById(R.id.card_image);
        titleTextView = findViewById(R.id.card_title);
        descriptionTextView = findViewById(R.id.card_description);
        locationTextView = findViewById(R.id.card_location);
        priceTextView = findViewById(R.id.card_price);
        dateTextView = findViewById(R.id.card_date);
        startTimeTextView = findViewById(R.id.card_start_time);
        endTimeTextView = findViewById(R.id.card_end_time);
        durationTextView = findViewById(R.id.card_duration);
        bookButton = findViewById(R.id.book_btn);

        // Get data from intent
        String title = getIntent().getStringExtra("title");
        String description = getIntent().getStringExtra("description");
        String location = getIntent().getStringExtra("location");
        String price = getIntent().getStringExtra("price");
        String date = getIntent().getStringExtra("date");
        String startTime = getIntent().getStringExtra("startTime");
        String endTime = getIntent().getStringExtra("endTime");
        String duration = getIntent().getStringExtra("duration");
        String imageUrl = getIntent().getStringExtra("imageUrl");

        // Set the data to views
        titleTextView.setText(title);
        descriptionTextView.setText(description);
        locationTextView.setText("Location: " + location);
        priceTextView.setText("Price: " + price);
        dateTextView.setText("Date: " + date);
        startTimeTextView.setText("Start Time: " + startTime);
        endTimeTextView.setText("End Time: " + endTime);
        durationTextView.setText("Duration: " + duration);

        // Load the image
        Glide.with(this)
                .load(imageUrl)
                .centerCrop()
                .into(cardImageView);

        // Set click listener on the book button
        bookButton.setOnClickListener(v -> {
            // Get current date and time
            String currentDateTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()).format(new Date());

            // Navigate to booking page
            Intent bookingIntent = new Intent(ActivityDetail.this, Booking.class);
            bookingIntent.putExtra("activityName", title);
            bookingIntent.putExtra("bookingDateTime", currentDateTime);
            bookingIntent.putExtra("description",description);
            bookingIntent.putExtra("duration",duration);
            bookingIntent.putExtra("start_time",startTime);
            bookingIntent.putExtra("end_time",endTime);
            bookingIntent.putExtra("price",price);
            bookingIntent.putExtra("location",location);
            bookingIntent.putExtra("date",date);

            startActivity(bookingIntent);
        });
    }
}
