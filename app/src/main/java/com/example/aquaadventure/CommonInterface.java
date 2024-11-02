package com.example.aquaadventure;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.aquaadventure.Instructor.LoginInstructor;

public class CommonInterface extends AppCompatActivity {
    private Spinner loginTypeSpinner;
    private Button loginButton;
    private String selectedLoginType;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_common_interface);

        loginTypeSpinner = findViewById(R.id.login_type_spinner);
        loginButton = findViewById(R.id.login_button);

        // Set listener for the Spinner
        loginTypeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedLoginType = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                selectedLoginType = null;
            }
        });

        // Set listener for the login button
        loginButton.setOnClickListener(v -> {
            if (selectedLoginType != null) {
                if (selectedLoginType.equals("User Login")) {
                    Intent intent = new Intent(CommonInterface.this, Login.class);
                    startActivity(intent);
                } else if (selectedLoginType.equals("Instructor Login")) {
                    Intent intent = new Intent(CommonInterface.this, LoginInstructor.class);
                    startActivity(intent);
                }
            } else {
                Toast.makeText(CommonInterface.this, "Please select a login type", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
