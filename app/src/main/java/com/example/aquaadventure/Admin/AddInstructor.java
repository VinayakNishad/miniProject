package com.example.aquaadventure.Admin;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import com.example.aquaadventure.Instructor.LoginInstructor;
import com.example.aquaadventure.R;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import java.util.HashMap;

public class AddInstructor extends AppCompatActivity {

    private TextInputEditText fullNameEditText, phoneEditText, emailEditText, addressEditText, passwordEditText, cPasswordEditText, locationEditText, specializationEditText;
    private Button registerButton;
    private ProgressBar progressBar;
    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_instructor);

        // Initialize Firebase Database reference
        databaseReference = FirebaseDatabase.getInstance().getReference("Instructor");

        // Initialize UI elements
        fullNameEditText = findViewById(R.id.fullname);
        phoneEditText = findViewById(R.id.phone);
        emailEditText = findViewById(R.id.email);
        addressEditText = findViewById(R.id.address);
        passwordEditText = findViewById(R.id.password);
        cPasswordEditText = findViewById(R.id.cpassword);
        locationEditText = findViewById(R.id.location_allocated);
        specializationEditText = findViewById(R.id.specialization);
        registerButton = findViewById(R.id.register);
        progressBar = findViewById(R.id.pgbar);

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerInstructor();
            }
        });
    }

    private void registerInstructor() {
        // Get values from EditTexts
        String fullName = fullNameEditText.getText().toString();
        String phone = phoneEditText.getText().toString();
        String email = emailEditText.getText().toString();
        String address = addressEditText.getText().toString();
        String password = passwordEditText.getText().toString();
        String cPassword = cPasswordEditText.getText().toString();
        String location = locationEditText.getText().toString();
        String specialization = specializationEditText.getText().toString();

        // Validate inputs
        if (TextUtils.isEmpty(fullName) || TextUtils.isEmpty(phone) || TextUtils.isEmpty(email) || TextUtils.isEmpty(password) || TextUtils.isEmpty(cPassword)) {
            Toast.makeText(AddInstructor.this, "Please fill in all the fields", Toast.LENGTH_SHORT).show();
            return;
        }

        if (!password.equals(cPassword)) {
            Toast.makeText(AddInstructor.this, "Passwords do not match", Toast.LENGTH_SHORT).show();
            return;
        }

        // Show progress bar
        progressBar.setVisibility(View.VISIBLE);

        // Create instructor data map
        HashMap<String, Object> instructorData = new HashMap<>();
        instructorData.put("fullName", fullName);
        instructorData.put("phone", phone);
        instructorData.put("email", email);
        instructorData.put("address", address);
        instructorData.put("location", location);
        instructorData.put("password",password);
        instructorData.put("specialization", specialization);

        // Generate unique ID for the instructor and store data
        String instructorId = databaseReference.push().getKey();

        if (instructorId != null) {
            databaseReference.child(instructorId).setValue(instructorData)
                    .addOnCompleteListener(task -> {
                        // Hide progress bar
                        progressBar.setVisibility(View.GONE);

                        // After successful registration
                        if (task.isSuccessful()) {
                            Toast.makeText(AddInstructor.this, "Instructor Registered Successfully", Toast.LENGTH_SHORT).show();
                            clearFields();
                            Intent intent = new Intent(AddInstructor.this, ListOfInstructor.class);
                            startActivity(intent);
                        } else {
                            Toast.makeText(AddInstructor.this, "Failed to register instructor", Toast.LENGTH_SHORT).show();
                        }
                    });
        } else {
            progressBar.setVisibility(View.GONE);
            Toast.makeText(AddInstructor.this, "Failed to generate unique instructor ID", Toast.LENGTH_SHORT).show();
        }
    }

    // Clear form fields after successful registration
    private void clearFields() {
        fullNameEditText.setText("");
        phoneEditText.setText("");
        emailEditText.setText("");
        addressEditText.setText("");
        passwordEditText.setText("");
        cPasswordEditText.setText("");
        locationEditText.setText("");
        specializationEditText.setText("");
    }
}
