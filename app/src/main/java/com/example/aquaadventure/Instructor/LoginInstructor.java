package com.example.aquaadventure.Instructor;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import com.example.aquaadventure.Admin.AddInstructor;
import com.example.aquaadventure.CustomerBook;
import com.example.aquaadventure.R;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import java.util.Objects;

public class LoginInstructor extends AppCompatActivity {

    private TextInputEditText emailEditText, phoneEditText, passwordEditText;
    private Button loginButton;
    private ProgressBar progressBar;
    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_instructor);

        // Initialize Firebase Database reference
        databaseReference = FirebaseDatabase.getInstance().getReference("Instructor");

        // Initialize UI elements
        emailEditText = findViewById(R.id.email);
        phoneEditText = findViewById(R.id.phone);
        passwordEditText = findViewById(R.id.password);
        loginButton = findViewById(R.id.login);
        progressBar = findViewById(R.id.pgbar);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginInstructor();
            }
        });
    }

    private void loginInstructor() {
        // Get values from EditTexts
        String email = Objects.requireNonNull(emailEditText.getText()).toString();
        String phone = Objects.requireNonNull(phoneEditText.getText()).toString();
        String password = Objects.requireNonNull(passwordEditText.getText()).toString();

        // Validate inputs
        if (TextUtils.isEmpty(email) || TextUtils.isEmpty(phone) || TextUtils.isEmpty(password)) {
            Toast.makeText(LoginInstructor.this, "Please fill in all the fields", Toast.LENGTH_SHORT).show();
            return;
        }

        // Show progress bar
        progressBar.setVisibility(View.VISIBLE);

        // Query Firebase Realtime Database to authenticate the instructor
        databaseReference.orderByChild("email").equalTo(email)
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        if (dataSnapshot.exists()) {
                            for (DataSnapshot instructorSnapshot : dataSnapshot.getChildren()) {
                                String dbPhone = Objects.requireNonNull(instructorSnapshot.child("phone").getValue()).toString();
                                String dbPassword = Objects.requireNonNull(instructorSnapshot.child("password").getValue()).toString();

                                // Check if the phone and password match
                                if (dbPhone.equals(phone) && dbPassword.equals(password)) {
                                    Toast.makeText(LoginInstructor.this, "Login Successful", Toast.LENGTH_SHORT).show();
                                    Intent i = new Intent(LoginInstructor.this, CustomerBook.class);
                                    startActivity(i);
                                } else {
                                    Toast.makeText(LoginInstructor.this, "Incorrect phone number or password", Toast.LENGTH_SHORT).show();
                                }
                            }
                        } else {
                            Toast.makeText(LoginInstructor.this, "No instructor found with this email", Toast.LENGTH_SHORT).show();
                        }

                        // Hide progress bar
                        progressBar.setVisibility(View.GONE);
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        // Handle database error
                        Toast.makeText(LoginInstructor.this, "Failed to login: " + databaseError.getMessage(), Toast.LENGTH_SHORT).show();
                        progressBar.setVisibility(View.GONE);
                    }
                });
    }
}
