package com.example.aquaadventure.Admin;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.aquaadventure.Login;
import com.example.aquaadventure.MainActivity;
import com.example.aquaadventure.R;

public class AdminLogin extends AppCompatActivity {
    EditText uniqueId;
    Button btn_login;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adminlogin);
        uniqueId = findViewById(R.id.password);
        btn_login = findViewById(R.id.adminlogin);
        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String check = "vn5543@#";
                String login = uniqueId.getText().toString();
                if(check.equals(login)){
                    Intent i = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(i);
                    finish();
                }
                else {
                    Toast.makeText(AdminLogin.this,"Incorrect UniqueId",Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}