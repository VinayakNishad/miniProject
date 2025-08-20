package com.example.aquaadventure.Instructor;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.aquaadventure.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ListOfActivityAccepted extends AppCompatActivity {

    private ListView listViewAccepted;
    private DatabaseReference bookingRef;
    private ArrayList<BookingModel> acceptedBookings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_of_accepted);

        listViewAccepted = findViewById(R.id.listViewAccepted);
        bookingRef = FirebaseDatabase.getInstance().getReference().child("Booking");
        acceptedBookings = new ArrayList<>();

        fetchAcceptedBookings();

        // Set item click listener to open DetailBooking with all booking details
        listViewAccepted.setOnItemClickListener((parent, view, position, id) -> {
            BookingModel selectedBooking = acceptedBookings.get(position);
            Intent intent = new Intent(ListOfActivityAccepted.this, DetailBooking.class);

            // Pass all booking details to DetailBooking
            intent.putExtra("bookingId", selectedBooking.getBookingId());
            intent.putExtra("customerName", selectedBooking.getUserName());
            intent.putExtra("activityName", selectedBooking.getActivityName());
            intent.putExtra("date", selectedBooking.getDate());
            intent.putExtra("startTime", selectedBooking.getStartTime());
            intent.putExtra("endTime", selectedBooking.getEndTime());
            intent.putExtra("duration", selectedBooking.getDuration());

            intent.putExtra("location", selectedBooking.getLocation());
            intent.putExtra("userPhone", selectedBooking.getUserPhone());
            intent.putExtra("userAddress", selectedBooking.getUserAddress());
            intent.putExtra("userEmail", selectedBooking.getUserEmail());
            intent.putExtra("bookingDateTime", selectedBooking.getBookingDateTime());

            startActivity(intent);
        });
    }

    private void fetchAcceptedBookings() {
        bookingRef.orderByChild("status").equalTo(1).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                acceptedBookings.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    BookingModel booking = snapshot.getValue(BookingModel.class);
                    if (booking != null) {
                        acceptedBookings.add(booking);
                    }
                }
                ArrayAdapter<BookingModel> adapter = new ArrayAdapter<>(ListOfActivityAccepted.this,
                        android.R.layout.simple_list_item_1, acceptedBookings);
                listViewAccepted.setAdapter(adapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(ListOfActivityAccepted.this, "Error fetching data.", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
