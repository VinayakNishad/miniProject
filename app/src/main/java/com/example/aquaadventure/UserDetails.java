package com.example.aquaadventure;

import android.os.Bundle;
import android.util.Log;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Firebase;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.messaging.FirebaseMessaging;

public class UserDetails extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_user_details);
//        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("copywrite");
//        databaseReference.setValue("Vinayak Codings");
        DatabaseReference user = FirebaseDatabase.getInstance().getReference("user");
        String userId = user.push().getKey();
        UserModel userModel = new UserModel("Vinayak Nishad","Zariwada Podocem","7558397994","vn07244@gmail.com","password");
        user.child(userId).setValue(userModel);

    }
}