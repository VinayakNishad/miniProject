package com.example.aquaadventure;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.firebase.Firebase;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {
    FirebaseAuth auth;
    Button btn_logout;
    TextView tv_udetail;
    FirebaseUser fuser;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        auth = FirebaseAuth.getInstance();
        btn_logout = findViewById(R.id.logout);
        tv_udetail =findViewById(R.id.userDetail);
        fuser = auth.getCurrentUser();
        if(fuser==null){
            Intent i = new Intent(getApplicationContext(), Login.class);
            startActivity(i);
            finish();
        }
        else{
            tv_udetail.setText(fuser.getEmail());
        }
        btn_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                Intent i = new Intent(getApplicationContext(), Login.class);
                startActivity(i);
                finish();
            }
        });
    }
}