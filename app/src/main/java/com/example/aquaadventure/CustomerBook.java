package com.example.aquaadventure;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.aquaadventure.Instructor.BookingModel;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class CustomerBook extends AppCompatActivity {

    private RecyclerView recyclerView;
    private DatabaseReference bookingRef;

    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_book);

        // Reference to your Firebase Realtime Database "Booking" table
        bookingRef = FirebaseDatabase.getInstance().getReference().child("Booking");

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        fetchBookingData();
        toolbar=findViewById(R.id.toolBar_back);
        setSupportActionBar(toolbar);
        if(getSupportActionBar()!=null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle("Map");
        }
    }


    private void fetchBookingData() {
        FirebaseRecyclerOptions<BookingModel> options =
                new FirebaseRecyclerOptions.Builder<BookingModel>()
                        .setQuery(bookingRef, BookingModel.class)
                        .build();

        FirebaseRecyclerAdapter<BookingModel, BookingViewHolder> adapter =
                new FirebaseRecyclerAdapter<BookingModel, BookingViewHolder>(options) {
                    @Override
                    protected void onBindViewHolder(@NonNull BookingViewHolder holder, int position, @NonNull BookingModel model) {
                        holder.setBookingDetails(model);
                    }

                    @Override
                    public BookingViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_booking, parent, false);
                        return new BookingViewHolder(view);
                    }
                };

        recyclerView.setAdapter(adapter);
        adapter.startListening();
    }

    public static class BookingViewHolder extends RecyclerView.ViewHolder {

        View mView;

        public BookingViewHolder(View itemView) {
            super(itemView);
            mView = itemView;
        }

        public void setBookingDetails(BookingModel booking) {
            TextView activityName = mView.findViewById(R.id.activity_name);
            TextView userName = mView.findViewById(R.id.user_name);
            TextView userPhone = mView.findViewById(R.id.user_phone);
            TextView location = mView.findViewById(R.id.activity_location);
            TextView date = mView.findViewById(R.id.activity_date);
            TextView startTime = mView.findViewById(R.id.activity_start_time);
            TextView endTime = mView.findViewById(R.id.activity_end_time);

            activityName.setText(booking.getActivityName());
            userName.setText(booking.getUserName());
            userPhone.setText(booking.getUserPhone());
            location.setText(booking.getActivityLocation());
            date.setText(booking.getActivityDate());
            startTime.setText(booking.getStartTime());
            endTime.setText(booking.getEndTime());
        }
    }
}