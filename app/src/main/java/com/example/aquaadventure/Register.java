package com.example.aquaadventure;

import static androidx.core.content.ContextCompat.startActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.Firebase;
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
    public void onStart() {
        super.onStart();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser != null) {
            Intent i = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(i);
            finish();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference("Users");

        et_fname = findViewById(R.id.fullname);
        et_address = findViewById(R.id.address);
        et_phone = findViewById(R.id.phone);
        et_email = findViewById(R.id.email);
        et_password = findViewById(R.id.password);
        et_cpassword = findViewById(R.id.cpassword);
        btn_reg = findViewById(R.id.register);
        pgbar = findViewById(R.id.pgbar);
        tv_navLogin = findViewById(R.id.navLogin);

        tv_navLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), Login.class);
                startActivity(i);
                finish();
            }
        });

        btn_reg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email, password, cpassword, fullName, phone, address;
                fullName = String.valueOf(et_fname.getText());
                phone = String.valueOf(et_phone.getText());
                address = String.valueOf(et_address.getText());
                email = String.valueOf(et_email.getText());
                password = String.valueOf(et_password.getText());
                cpassword = String.valueOf(et_cpassword.getText());

                if (TextUtils.isEmpty(email)) {
                    Toast.makeText(Register.this, "Please Enter Email", Toast.LENGTH_LONG).show();
                    return;
                }
                if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                    Toast.makeText(Register.this, "Please Enter a Valid Email", Toast.LENGTH_LONG).show();
                    return;
                }
                if (TextUtils.isEmpty(password)) {
                    Toast.makeText(Register.this, "Please Enter Password", Toast.LENGTH_LONG).show();
                    return;
                }
                if (!password.equals(cpassword)) {
                    Toast.makeText(Register.this, "Password and Confirm Password should be same", Toast.LENGTH_LONG).show();
                    return;
                }

                pgbar.setVisibility(View.VISIBLE);

                mAuth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                pgbar.setVisibility(View.GONE);
                                if (task.isSuccessful()) {
                                    // Get user ID and save additional details to database
                                    FirebaseUser user = mAuth.getCurrentUser();
                                    String userId = user.getUid();

                                    // Create user object to store in the database
                                    User userDetails = new User(fullName, email, phone, address,password);

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

                                } else {
                                    Toast.makeText(Register.this, "Authentication failed.", Toast.LENGTH_SHORT).show();
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
        public String password;

        public User(String fullName, String email, String phone, String address,String password) {
            this.fullName = fullName;
            this.email = email;
            this.phone = phone;
            this.address = address;
            this.password=password;
        }
    }
}
