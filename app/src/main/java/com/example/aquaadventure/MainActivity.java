package com.example.aquaadventure;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    public EditText uname;
    public EditText uemail, upassword, uphone, uaddress, urole, uprofile;
    public Button save;
    public DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        uname = findViewById(R.id.uname);
        uemail = findViewById(R.id.uemail);
        upassword = findViewById(R.id.upassword);
        uphone = findViewById(R.id.uphone);
        uaddress = findViewById(R.id.uaddress);
        urole = findViewById(R.id.urole);
        uprofile = findViewById(R.id.uprofile);
        save = findViewById(R.id.save);

        dbHelper = new DatabaseHelper(this);

        save.setOnClickListener(v -> {
            String u_name = uname.getText().toString().trim();
            String u_email = uemail.getText().toString().trim();
            String u_password = upassword.getText().toString().trim();
            String u_phone = uphone.getText().toString().trim();
            String u_role = urole.getText().toString().trim();
            String u_profile = uprofile.getText().toString().trim();
            String u_address = uaddress.getText().toString().trim();
            insertData(u_name, u_email, u_address, u_phone, u_password, u_role, u_profile);
        });
    }


    private void insertData(String u_name, String u_email, String u_address, String u_phone, String u_password, String u_role, String u_profile) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("UserName", u_name);
        values.put("UserEmail", u_email);
        values.put("UserAddress", u_address);
        values.put("UserPhone", u_phone);
        values.put("UserPassword", u_password);
        values.put("UserRole", u_role);
        values.put("UserProfile", u_profile);

        long result = db.insert("User", null, values);
        if (result == -1) {
            Toast.makeText(this, "Failed to insert data", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Data saved successfully", Toast.LENGTH_SHORT).show();
        }
        db.close();
    }
}
