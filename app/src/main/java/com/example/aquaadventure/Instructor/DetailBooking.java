package com.example.aquaadventure.Instructor;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.aquaadventure.R;

public class DetailBooking extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_booking);

        // Retrieve data from intent
        int bookingId = getIntent().getIntExtra("bookingId", 0);
        String customerName = getIntent().getStringExtra("customerName");
        String activityName = getIntent().getStringExtra("activityName");
        String date = getIntent().getStringExtra("date");
        String startTime = getIntent().getStringExtra("startTime");
        String endTime = getIntent().getStringExtra("endTime");
        String duration = getIntent().getStringExtra("duration");
        String location = getIntent().getStringExtra("location");

        String userPhone = getIntent().getStringExtra("userPhone");
        String userAddress = getIntent().getStringExtra("userAddress");
        String userEmail = getIntent().getStringExtra("userEmail");
        String bookingDateTime = getIntent().getStringExtra("bookingDateTime");

        // Find TextViews and set their text
        ((TextView) findViewById(R.id.textBookingId)).setText("Booking ID: " + bookingId);
        ((TextView) findViewById(R.id.textCustomerName)).setText("Customer: " + customerName);
        ((TextView) findViewById(R.id.textActivityName)).setText("Activity: " + activityName);
        ((TextView) findViewById(R.id.textDate)).setText("Date: " + date);
        ((TextView) findViewById(R.id.textStartTime)).setText("Start Time: " + startTime);
        ((TextView) findViewById(R.id.textEndTime)).setText("End Time: " + endTime);
        ((TextView) findViewById(R.id.textDuration)).setText("Duration: " + duration);
        ((TextView) findViewById(R.id.textLocation)).setText("Location: " + location);
        ((TextView) findViewById(R.id.textUserPhone)).setText("Phone: " + userPhone);
        ((TextView) findViewById(R.id.textUserAddress)).setText("Address: " + userAddress);
        ((TextView) findViewById(R.id.textUserEmail)).setText("Email: " + userEmail);
        ((TextView) findViewById(R.id.textBookingDateTime)).setText("Booking Date/Time: " + bookingDateTime);
    }
}
