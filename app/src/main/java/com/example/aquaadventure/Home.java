package com.example.aquaadventure;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.aquaadventure.Admin.AdminLogin;
import com.example.aquaadventure.getActivity.Activity;
import com.example.aquaadventure.Admin.InsertActivity.CardItem;
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

        // Check if the user is already logged in
        FirebaseAuth auth = FirebaseAuth.getInstance();
        FirebaseUser user = auth.getCurrentUser();
        if (user == null) {
            // If not logged in, redirect to login page
            Intent i = new Intent(Home.this, Login.class);
            startActivity(i);
            finish();
            return;
        }

        // Initialize RecyclerView and Toolbar
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        cardItemList = new ArrayList<>();
        adapter = new Activity(this, cardItemList);
        recyclerView.setAdapter(adapter);

        toolbar = findViewById(R.id.toolBar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(false);
            getSupportActionBar().setTitle("AquaAdventure");
        }

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
                        Log.d("FirebaseData", "Item: " + cardItem.getTitle());
                    }
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Handle possible errors.
            }
        });

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnItemSelectedListener(new BottomNavigationView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if (item.getItemId() == R.id.nav_home) {
                    Toast.makeText(Home.this, "Home", Toast.LENGTH_SHORT).show();
                    return true;
                } else if (item.getItemId() == R.id.nav_book) {
                    Toast.makeText(Home.this, "Book", Toast.LENGTH_SHORT).show();
                    Intent bookIntent = new Intent(Home.this, Booking.class);
                    startActivity(bookIntent);
                    return true;
                } else if (item.getItemId() == R.id.nav_account) {
                    Toast.makeText(Home.this, "Account", Toast.LENGTH_SHORT).show();
                    Intent accountIntent = new Intent(Home.this, Account.class);
                    startActivity(accountIntent);
                    return true;
                } else {
                    return false;
                }
            }
        });

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
        } else if (itemId == R.id.adminlogin) {
            Toast.makeText(this, "Admin login", Toast.LENGTH_SHORT).show();
            Intent i = new Intent(getApplicationContext(), AdminLogin.class);
            startActivity(i);
            finish();
        } else if (itemId == R.id.user_logout) {
            // Logout the user
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
