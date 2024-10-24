package com.example.aquaadventure.Admin;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.aquaadventure.Admin.InsertActivity.AddActivity;
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
                    Intent i = new Intent(getApplicationContext(), AddActivity.class);
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