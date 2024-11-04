package com.example.aquaadventure;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class BookByUser extends AppCompatActivity {

    private static final String TAG = "BookByUser";
    private FirebaseDatabase database;
    private DatabaseReference bookingsRef;
    private FirebaseAuth auth;
    private RecyclerView bookingRecyclerView;
    private TextView noBookingsMessage;
    private BookingAdapter bookingAdapter;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_book_by_user);

        bookingRecyclerView = findViewById(R.id.bookingRecyclerView);
        noBookingsMessage = findViewById(R.id.noBookingsMessage);

        database = FirebaseDatabase.getInstance();
        auth = FirebaseAuth.getInstance();

        // Adjust layout padding for system bars
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Load bookings for the current user if authenticated
        FirebaseUser currentUser = auth.getCurrentUser();
        if (currentUser != null) {
            String userEmail = currentUser.getEmail();
            Log.d(TAG, "Current user email: " + userEmail); // Debugging current user email
            loadUserBookings(userEmail);
        } else {
            // Handle the case where the user is not logged in
            noBookingsMessage.setText("Please log in to view bookings.");
            noBookingsMessage.setVisibility(View.VISIBLE);
            bookingRecyclerView.setVisibility(View.GONE);
        }

        // Set up Toolbar
        toolbar = findViewById(R.id.toolBar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(false);
            getSupportActionBar().setTitle("Book");
        }

        // Set up BottomNavigationView and highlight the Book tab
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setSelectedItemId(R.id.nav_book); // Highlight the Book tab

        bottomNavigationView.setOnItemSelectedListener(item -> {
            if (item.getItemId() == R.id.nav_book) {
                Toast.makeText(BookByUser.this, "Book", Toast.LENGTH_SHORT).show();
                return true;
            } else if (item.getItemId() == R.id.nav_home) {
                Toast.makeText(BookByUser.this, "Home", Toast.LENGTH_SHORT).show();
                Intent homeIntent = new Intent(BookByUser.this, Home.class);
                startActivity(homeIntent);
                return true;
            } else if (item.getItemId() == R.id.nav_account) {
                Toast.makeText(BookByUser.this, "Account", Toast.LENGTH_SHORT).show();
                Intent accountIntent = new Intent(BookByUser.this, Account.class);
                startActivity(accountIntent);
                return true;
            } else {
                return false;
            }
        });
    }

    private void loadUserBookings(String userEmail) {
        bookingsRef = database.getReference("Booking"); // Root of Booking node
        bookingsRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                List<BookingModel> bookings = new ArrayList<>();

                for (DataSnapshot bookingSnapshot : dataSnapshot.getChildren()) {
                    BookingModel booking = bookingSnapshot.getValue(BookingModel.class);

                    if (booking != null) {
                        String bookingEmail = booking.getEmail();
                        Log.d(TAG, "Checking booking with email: " + bookingEmail);

                        // Check if the booking's email matches the current user's email
                        if (userEmail.equals(bookingEmail)) {
                            bookings.add(booking);
                            Log.d(TAG, "Booking matched for user email: " + userEmail);
                        }
                    } else {
                        Log.w(TAG, "Booking model is null or not properly formatted for snapshot: " + bookingSnapshot.getKey());
                    }
                }

                displayBookings(bookings);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.e(TAG, "Database error: " + databaseError.getMessage());
            }
        });
    }

    private void displayBookings(List<BookingModel> bookings) {
        if (bookings.isEmpty()) {
            noBookingsMessage.setVisibility(View.VISIBLE);
            bookingRecyclerView.setVisibility(View.GONE);
            Log.d(TAG, "No bookings found for the user.");
        } else {
            noBookingsMessage.setVisibility(View.GONE);
            bookingRecyclerView.setVisibility(View.VISIBLE);

            bookingAdapter = new BookingAdapter(bookings);
            bookingRecyclerView.setAdapter(bookingAdapter);
            Log.d(TAG, "Bookings found and displayed.");
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        new MenuInflater(this).inflate(R.menu.appbar, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int itemId = item.getItemId();

        if (itemId == R.id.location) {
            Toast.makeText(this, "Location", Toast.LENGTH_SHORT).show();
            Intent i = new Intent(getApplicationContext(), Map.class);
            startActivity(i);
            finish();
        } else if (itemId == R.id.user_logout) {
            FirebaseAuth.getInstance().signOut();
            Intent i = new Intent(getApplicationContext(), Login.class);
            startActivity(i);
            finish();
            Toast.makeText(this, "Logged out successfully", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Invalid", Toast.LENGTH_SHORT).show();
        }
        return super.onOptionsItemSelected(item);
    }
}
