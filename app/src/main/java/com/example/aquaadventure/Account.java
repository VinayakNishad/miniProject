package com.example.aquaadventure;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

public class Account extends AppCompatActivity {
    private static final int REQUEST_CODE_GALLERY = 1001;
    FirebaseAuth auth;
    EditText fullname, address, phone;
    TextView email;
    FirebaseUser fuser;
    DatabaseReference mDatabase;
    StorageReference storageReference;
    ImageView avatarImageView;
    Uri avatarUri;
    Button btn_update;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);

        // Initialize Firebase Auth, Database, and Storage
        auth = FirebaseAuth.getInstance();
        fuser = auth.getCurrentUser();
        storageReference = FirebaseStorage.getInstance().getReference("Avatars").child(fuser.getUid());

        // Initialize UI elements
        fullname = findViewById(R.id.fullname);
        email = findViewById(R.id.email_display);
        phone = findViewById(R.id.phone);
        address = findViewById(R.id.address);
        avatarImageView = findViewById(R.id.avatarImageView);
        btn_update = findViewById(R.id.update);

        // Initialize Firebase Database reference
        mDatabase = FirebaseDatabase.getInstance().getReference("Users").child(fuser.getUid());

        // Load user data
        loadUserData();

        // Set click listener for avatar to open gallery
        avatarImageView.setOnClickListener(v -> openGallery());

        // Update button listener to save new user details
        btn_update.setOnClickListener(v -> updateUserDetails());
    }

    private void loadUserData() {
        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    User user = dataSnapshot.getValue(User.class);
                    if (user != null) {
                        fullname.setText(user.fullName);
                        phone.setText(user.phone);
                        address.setText(user.address);
                        email.setText(user.email);
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(Account.this, "Failed to load user data", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void openGallery() {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent, REQUEST_CODE_GALLERY);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_GALLERY && resultCode == Activity.RESULT_OK && data != null) {
            avatarUri = data.getData();
            avatarImageView.setImageURI(avatarUri);
            uploadAvatar();
        }
    }

    private void uploadAvatar() {
        if (avatarUri != null) {
            storageReference.putFile(avatarUri).addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    Toast.makeText(this, "Avatar uploaded successfully", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(this, "Avatar upload failed", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    private void updateUserDetails() {
        String newFullName = fullname.getText().toString();
        String newPhone = phone.getText().toString();
        String newAddress = address.getText().toString();

        if (!newFullName.isEmpty() && !newPhone.isEmpty() && !newAddress.isEmpty()) {
            mDatabase.child("fullName").setValue(newFullName);
            mDatabase.child("phone").setValue(newPhone);
            mDatabase.child("address").setValue(newAddress);
            Toast.makeText(Account.this, "Details updated successfully", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(Account.this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
        }
    }

    public static class User {
        public String fullName, email, phone, address;

        public User() { /* Default constructor for Firebase */ }

        public User(String fullName, String email, String phone, String address) {
            this.fullName = fullName;
            this.email = email;
            this.phone = phone;
            this.address = address;
        }
    }
}
