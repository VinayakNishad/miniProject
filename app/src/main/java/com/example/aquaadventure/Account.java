package com.example.aquaadventure;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Account extends AppCompatActivity {
    FirebaseAuth auth;
    EditText fullname, address, phone, email;
    FirebaseUser fuser;
    DatabaseReference mDatabase;
    TextView tv_account;
    Button btn_update;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);

        // Initialize Firebase Auth
        auth = FirebaseAuth.getInstance();
        FirebaseUser user = auth.getCurrentUser();

        // Initialize UI elements
        fullname = findViewById(R.id.fullname);
        email = findViewById(R.id.email);
        phone = findViewById(R.id.phone);
        address = findViewById(R.id.address);
        tv_account = findViewById(R.id.tv_account);
        btn_update = findViewById(R.id.update);

        // Get current user from authentication
        fuser = auth.getCurrentUser();
        if (fuser == null) {
            finish(); // Go back to login if no user is logged in
        } else {
            // Get user ID and retrieve user details from Realtime Database
            String userId = fuser.getUid();
            mDatabase = FirebaseDatabase.getInstance().getReference("Users").child(userId);

            // Add a listener to retrieve data from the database
            mDatabase.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    if (dataSnapshot.exists()) {
                        // Retrieve user data using the User class
                        User user = dataSnapshot.getValue(User.class);

                        // Display the fetched data
                        if (user != null) {
                            fullname.setText(user.fullName);
                            phone.setText(user.phone);
                            address.setText(user.address);
                            email.setText(user.email);  // Email is not editable
                        }
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    // Handle possible errors
                    tv_account.setText("Failed to load user data: " + databaseError.getMessage());
                }
            });
        }

        // Update button listener
        btn_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String newFullName = fullname.getText().toString();
                String newPhone = phone.getText().toString();
                String newAddress = address.getText().toString();

                if (!newFullName.isEmpty() && !newPhone.isEmpty() && !newAddress.isEmpty()) {
                    // Update the Firebase Realtime Database
                    mDatabase.child("fullName").setValue(newFullName);
                    mDatabase.child("phone").setValue(newPhone);
                    mDatabase.child("address").setValue(newAddress);

                    Toast.makeText(Account.this, "Details updated successfully", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(Account.this, "Please fill in all the fields", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    // Define a User class to map to the database
    public static class User {
        public String fullName;
        public String email;
        public String phone;
        public String address;

        public User() {
            // Default constructor required for calls to DataSnapshot.getValue(User.class)
        }

        public User(String fullName, String email, String phone, String address) {
            this.fullName = fullName;
            this.email = email;
            this.phone = phone;
            this.address = address;
        }
    }
}
