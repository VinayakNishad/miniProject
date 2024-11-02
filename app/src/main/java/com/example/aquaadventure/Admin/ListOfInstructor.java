package com.example.aquaadventure.Admin;

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

public class ListOfInstructor extends AppCompatActivity {

    private ListView listViewInstructors;
    private ArrayList<String> instructorList;
    private ArrayAdapter<String> adapter;
    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_of_instructor);

        // Initialize ListView and list
        listViewInstructors = findViewById(R.id.listView_instructors);
        instructorList = new ArrayList<>();
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, instructorList);
        listViewInstructors.setAdapter(adapter);

        // Reference to "Instructor" node in Firebase
        databaseReference = FirebaseDatabase.getInstance().getReference("Instructor");

        // Fetch data from Firebase
        fetchInstructorData();
    }

    private void fetchInstructorData() {
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                instructorList.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    String name = snapshot.child("fullName").getValue(String.class);
                    String location = snapshot.child("location").getValue(String.class);

                    if (name != null && location != null) {
                        instructorList.add("Name: " + name + "\nLocation: " + location);
                    }
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(ListOfInstructor.this, "Failed to load data", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
