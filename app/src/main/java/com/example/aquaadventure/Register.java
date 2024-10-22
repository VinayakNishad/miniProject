package com.example.aquaadventure;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import android.util.Patterns;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Register extends AppCompatActivity {
    TextInputEditText et_email, et_password, et_cpassword, et_phone, et_address, et_fname;
    Button btn_reg;
    FirebaseAuth mAuth;
    ProgressBar pgbar;
    TextView tv_navLogin;
    DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        // Initialize FirebaseAuth and FirebaseDatabase reference
        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference("Users");

        // Link XML views
        et_fname = findViewById(R.id.fullname);
        et_address = findViewById(R.id.address);
        et_phone = findViewById(R.id.phone);
        et_email = findViewById(R.id.email);
        et_password = findViewById(R.id.password);
        et_cpassword = findViewById(R.id.cpassword);
        btn_reg = findViewById(R.id.register);
        pgbar = findViewById(R.id.pgbar);
        tv_navLogin = findViewById(R.id.navLogin);

        // Handle navigation to Login
        tv_navLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), Login.class);
                startActivity(i);
                finish();
            }
        });

        // Register button click event
        btn_reg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = et_email.getText().toString().trim();
                String password = et_password.getText().toString().trim();
                String cpassword = et_cpassword.getText().toString().trim();
                String fullName = et_fname.getText().toString().trim();
                String phone = et_phone.getText().toString().trim();
                String address = et_address.getText().toString().trim();

                // Input validation
                if (TextUtils.isEmpty(fullName)) {
                    et_fname.setError("Full Name is required.");
                    return;
                }

                if (TextUtils.isEmpty(phone)) {
                    et_phone.setError("Phone is required.");
                    return;
                }

                if (TextUtils.isEmpty(address)) {
                    et_address.setError("Address is required.");
                    return;
                }

                if (TextUtils.isEmpty(email) || !Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                    et_email.setError("Please enter a valid email.");
                    return;
                }

                if (TextUtils.isEmpty(password)) {
                    et_password.setError("Password is required.");
                    return;
                }

                if (password.length() < 6) {
                    et_password.setError("Password must be at least 6 characters.");
                    return;
                }

                if (!password.equals(cpassword)) {
                    et_cpassword.setError("Passwords do not match.");
                    return;
                }

                // Show progress bar
                pgbar.setVisibility(View.VISIBLE);

                // Create user in Firebase
                mAuth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                pgbar.setVisibility(View.GONE);
                                if (task.isSuccessful()) {
                                    // Get user ID and save additional details to database
                                    FirebaseUser user = mAuth.getCurrentUser();
                                    if (user != null) {
                                        String userId = user.getUid();

                                        // Create user object (without storing password)
                                        User userDetails = new User(fullName, email, phone, address);

                                        // Add user to the 'Users' node in Realtime Database
                                        mDatabase.child(userId).setValue(userDetails)
                                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                    @Override
                                                    public void onComplete(@NonNull Task<Void> task) {
                                                        if (task.isSuccessful()) {
                                                            Toast.makeText(Register.this, "Register Successful", Toast.LENGTH_LONG).show();
                                                            Intent i = new Intent(getApplicationContext(), Login.class);
                                                            startActivity(i);
                                                            finish();
                                                        } else {
                                                            Toast.makeText(Register.this, "Failed to add user details to database.", Toast.LENGTH_SHORT).show();
                                                        }
                                                    }
                                                });
                                    }
                                } else {
                                    // Provide detailed error messages
                                    Toast.makeText(Register.this, "Authentication failed: " + task.getException().getMessage(), Toast.LENGTH_LONG).show();
                                }
                            }
                        });
            }
        });
    }

    // Define a User class to store user information
    public static class User {
        public String fullName;
        public String email;
        public String phone;
        public String address;

        public User(String fullName, String email, String phone, String address) {
            this.fullName = fullName;
            this.email = email;
            this.phone = phone;
            this.address = address;
        }
    }
}
