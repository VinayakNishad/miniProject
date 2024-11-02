package com.example.aquaadventure.Instructor;

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
    }

    private void fetchAcceptedBookings() {
        bookingRef.orderByChild("status").equalTo(1).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                acceptedBookings.clear(); // Clear the list before adding new data
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    BookingModel booking = snapshot.getValue(BookingModel.class);
                    if (booking != null) {
                        acceptedBookings.add(booking); // Add the accepted booking to the list
                    }
                }
                // Update the ListView with the fetched data
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
