package com.example.aquaadventure;

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

public class Register extends AppCompatActivity {
    TextInputEditText et_email,et_password;
    Button btn_reg;
    FirebaseAuth mAuth;
    ProgressBar pgbar;
    TextView tv_navLogin;
    @Override
    public void onStart() {
        super.onStart();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser != null){
            Intent i = new Intent(getApplicationContext(),MainActivity.class);
            startActivity(i);
            finish();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        mAuth = FirebaseAuth.getInstance();
        et_email  =  findViewById(R.id.email);
        et_password = findViewById(R.id.password);
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
                pgbar.setVisibility(View.VISIBLE);

                String email,password;
                email = String.valueOf(et_email.getText());
                password = String.valueOf(et_password.getText());
                if(TextUtils.isEmpty(email)){
                    Toast.makeText(Register.this,"Please Enter Email",Toast.LENGTH_LONG).show();
                    return;
                }
                if(TextUtils.isEmpty(password)){
                    Toast.makeText(Register.this,"Please Enter Password",Toast.LENGTH_LONG).show();
                    return;
                }
                mAuth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                pgbar.setVisibility(View.GONE);
                                if (task.isSuccessful()) {

                                    Toast.makeText(Register.this,"Register Successful",Toast.LENGTH_LONG).show();

                                } else {
                                    Toast.makeText(Register.this, "Authentication failed.",
                                            Toast.LENGTH_SHORT).show();
                                }
                            }
                        });

            }
        });



    }
}