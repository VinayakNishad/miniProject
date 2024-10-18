package com.example.aquaadventure;

import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.aquaadventure.getActivity.Activity;
import com.example.aquaadventure.Admin.InsertActivity.CardItem;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class Home extends AppCompatActivity {
    Toolbar toolbar;

    private RecyclerView recyclerView;
    private Activity adapter;
    private List<CardItem> cardItemList;
    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        cardItemList = new ArrayList<>();
        adapter = new Activity(this, cardItemList);
        recyclerView.setAdapter(adapter);


        Toolbar
        toolbar=findViewById(R.id.toolBar);
        setSupportActionBar(toolbar);

        // Firebase reference
        databaseReference = FirebaseDatabase.getInstance().getReference("Activity");

        // Fetch data from Firebase
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                cardItemList.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    CardItem cardItem = dataSnapshot.getValue(CardItem.class);
                    if (cardItem != null) {
                        cardItemList.add(cardItem);
                        Log.d("FirebaseData", "Item: " + cardItem.getTitle()); // Debugging log
                    }
                }
                adapter.notifyDataSetChanged();
            }
            @Override
            public void onCancelled(DatabaseError error) {
                // Handle possible errors.
            }
        });
    }
}
