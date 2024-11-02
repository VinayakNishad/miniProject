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

import com.example.aquaadventure.Admin.InsertActivity.AddActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Login extends AppCompatActivity {
    TextInputEditText et_email, et_password;
    Button btn_log;
    FirebaseAuth mAuth;
    ProgressBar pgbar;
    TextView tv_navReg;
    private FloatingActionButton btn_back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();
        btn_back = findViewById(R.id.backButton);


        // Link XML views
        et_email = findViewById(R.id.email);
        et_password = findViewById(R.id.password);
        btn_log = findViewById(R.id.login);
        pgbar = findViewById(R.id.pgbar);
        tv_navReg = findViewById(R.id.navRegister);
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i= new Intent(Login.this, CommonInterface.class);
                startActivity(i);
            }
        });

        // Navigation to Register Activity
        tv_navReg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), Register.class);
                startActivity(i);
                finish();
            }
        });

        // Login button click event
        btn_log.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = et_email.getText().toString().trim();
                String password = et_password.getText().toString().trim();

                // Input validation
                if (TextUtils.isEmpty(email)) {
                    et_email.setError("Please enter your email.");
                    return;
                }

                if (TextUtils.isEmpty(password)) {
                    et_password.setError("Please enter your password.");
                    return;
                }

                // Show progress bar
                pgbar.setVisibility(View.VISIBLE);

                // Firebase login
                mAuth.signInWithEmailAndPassword(email, password)
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                // Hide progress bar after task completes
                                pgbar.setVisibility(View.GONE);

                                if (task.isSuccessful()) {
                                    Toast.makeText(getApplicationContext(), "Login Successful", Toast.LENGTH_SHORT).show();

                                    // Check for admin credentials
                                    if (email.equals("vn07244@gmail.com") && password.equals("vn2468@#")) {
                                        // Navigate to Admin screen
                                        Intent i = new Intent(getApplicationContext(), AdminHome.class);
                                        startActivity(i);
                                        finish();
                                    } else {
                                        // Navigate to Home screen for regular users
                                        Intent i = new Intent(getApplicationContext(), Home.class);
                                        startActivity(i);
                                    }
                                } else {
                                    // Display detailed error message
                                    String errorMessage = task.getException() != null ? task.getException().getMessage() : "Authentication failed.";
                                    Toast.makeText(Login.this, errorMessage, Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            }
        });
    }
}
