package com.example.aquaadventure;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.aquaadventure.Instructor.BookingModel;
import com.example.aquaadventure.getActivity.ActivityDetail;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Random;

public class Booking extends AppCompatActivity {

    private TextView activityNameTextView, bookingDateTimeTextView;
    private TextView booking_ID, descriptionTextView, locationTextView, priceTextView, dateTextView, startTimeTextView, endTimeTextView, durationTextView;
    private TextView userNameTextView, userPhoneTextView, userAddressTextView, userEmailTextView;
    private Button payment;
    FloatingActionButton btn_back;

    // Firebase database reference
    private DatabaseReference userRef;
    private FirebaseAuth firebaseAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking);

        // Initialize Firebase Auth
        firebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = firebaseAuth.getCurrentUser();
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Booking.this, ActivityDetail.class);
                startActivity(i);
            }
        });

        // Initialize views
        activityNameTextView = findViewById(R.id.activity_name);
        bookingDateTimeTextView = findViewById(R.id.booking_date_time);
        booking_ID = findViewById(R.id.booking_id);
        descriptionTextView = findViewById(R.id.descriptionEditText);
        locationTextView = findViewById(R.id.activity_location);
        priceTextView = findViewById(R.id.activity_price);
        dateTextView = findViewById(R.id.activity_date);
        startTimeTextView = findViewById(R.id.activity_start_time);
        endTimeTextView = findViewById(R.id.activity_end_time);
        durationTextView = findViewById(R.id.activity_duration);

        userNameTextView = findViewById(R.id.user_name);
        userPhoneTextView = findViewById(R.id.user_phone);
        userAddressTextView = findViewById(R.id.user_address);
        userEmailTextView = findViewById(R.id.user_email);
        payment = findViewById(R.id.payment_button);

        payment.setOnClickListener(v -> {
            // Save the booking details to Firebase Realtime Database
            saveBookingDataToFirebase();
        });


        // Generate a 4-digit random booking ID
        int bookingId = new Random().nextInt(9000) + 1000;
        booking_ID.setText("Booking ID: " + bookingId);

        // Get data from intent
        String activityName = getIntent().getStringExtra("activityName");
        String bookingDateTime = getIntent().getStringExtra("bookingDateTime");
        String description = getIntent().getStringExtra("description");
        String location = getIntent().getStringExtra("location");
        String startTime = getIntent().getStringExtra("start_time");
        String endTime = getIntent().getStringExtra("end_time");
        String duration = getIntent().getStringExtra("duration");
        String price = getIntent().getStringExtra("price");
        String date = getIntent().getStringExtra("date");

        // Set the activity-related data to views
        activityNameTextView.setText(activityName);
        bookingDateTimeTextView.setText("Booking Date & Time: " + bookingDateTime);
        dateTextView.setText("Activity date : " + date);
        descriptionTextView.setText(description);
        locationTextView.setText("Location : " + location);
        startTimeTextView.setText("Start time: " + startTime);
        endTimeTextView.setText("Ending time: " + endTime);
        priceTextView.setText("Price per person: " + price + " Rupees");
        durationTextView.setText("Probably of duration " + duration + " hours.");

        // Check if the user is logged in
        if (currentUser != null) {
            String userId = currentUser.getUid();

            // Initialize Firebase database reference
            userRef = FirebaseDatabase.getInstance().getReference("Users").child(userId);

            // Fetch user details from Firebase
            userRef.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if (snapshot.exists()) {
                        String fullName = snapshot.child("fullName").getValue(String.class);
                        String phone = snapshot.child("phone").getValue(String.class);
                        String address = snapshot.child("address").getValue(String.class);
                        String email = snapshot.child("email").getValue(String.class);

                        // Set the user-related data to views
                        userNameTextView.setText("Full Name: " + fullName);
                        userPhoneTextView.setText("Phone: " + phone);
                        userAddressTextView.setText("Address: " + address);
                        userEmailTextView.setText("Email: " + email);
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    // Handle possible errors
                }
            });
        } else {
            // Handle case where user is not logged in
            userNameTextView.setText("User not logged in");
        }
    }
    private void saveBookingDataToFirebase() {
        FirebaseUser currentUser = firebaseAuth.getCurrentUser();

        if (currentUser != null) {
            String userId = currentUser.getUid();

            // Generate a 4-digit random booking ID as an int
            int bookingId = new Random().nextInt(9000) + 1000;

            // Collect booking data
            String activityName = activityNameTextView.getText().toString();
            String description = descriptionTextView.getText().toString();
            String location = locationTextView.getText().toString();
            String price = priceTextView.getText().toString();
            String date = dateTextView.getText().toString();
            String startTime = startTimeTextView.getText().toString();
            String endTime = endTimeTextView.getText().toString();
            String duration = durationTextView.getText().toString();
            String bookingDateTime = bookingDateTimeTextView.getText().toString();

            String userName = userNameTextView.getText().toString();
            String userPhone = userPhoneTextView.getText().toString();
            String userAddress = userAddressTextView.getText().toString();
            String userEmail = userEmailTextView.getText().toString();

            // Create a new booking object with bookingId as int
            BookingModel booking = new BookingModel(
                    bookingId,
                    activityName,
                    description,
                    location,
                    price,
                    date,
                    startTime,
                    endTime,
                    duration,
                    userName,
                    userPhone,
                    userAddress,
                    userEmail,
                    bookingDateTime
            );

            // Save the booking data to Firebase Realtime Database under "Booking" node
            DatabaseReference bookingRef = FirebaseDatabase.getInstance().getReference().child("Booking");
            bookingRef.child(String.valueOf(bookingId)).setValue(booking)  // Set booking with ID as child key
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            Toast.makeText(Booking.this, "Booking saved successfully!", Toast.LENGTH_SHORT).show();
                            Intent i = new Intent(getApplicationContext(), CustomerBook.class);
                            startActivity(i);
                        } else {
                            Toast.makeText(Booking.this, "Failed to save booking. Please try again.", Toast.LENGTH_SHORT).show();
                        }
                    });
        } else {
            Toast.makeText(this, "User not logged in", Toast.LENGTH_SHORT).show();
        }
    }

}
