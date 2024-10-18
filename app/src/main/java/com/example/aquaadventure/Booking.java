package com.example.aquaadventure;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.aquaadventure.R;

public class Booking extends AppCompatActivity {

    private TextView activityNameTextView, bookingDateTimeTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking);

        // Initialize views
        activityNameTextView = findViewById(R.id.activity_name);
        bookingDateTimeTextView = findViewById(R.id.booking_date_time);

        // Get data from intent
        String activityName = getIntent().getStringExtra("activityName");
        String bookingDateTime = getIntent().getStringExtra("bookingDateTime");

        // Set the data to views
        activityNameTextView.setText("Activity Name: " + activityName);
        bookingDateTimeTextView.setText("Booking Date & Time: " + bookingDateTime);
    }
}
