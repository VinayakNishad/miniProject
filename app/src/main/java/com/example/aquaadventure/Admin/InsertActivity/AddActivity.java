package com.example.aquaadventure.Admin.InsertActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.aquaadventure.Home;
import com.example.aquaadventure.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import java.util.UUID;

public class AddActivity extends AppCompatActivity {

    private static final int PICK_IMAGE_REQUEST = 1;

    private EditText titleEditText, descriptionEditText, locationEditText, priceEditText, dateEditText, startTimeEditText, endTimeEditText, durationEditText;
    private ImageView imageView;
    private ProgressBar progressBar;

    private Uri imageUri;
    private StorageReference storageReference;
    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insert_image);
        titleEditText = findViewById(R.id.titleEditText);
        descriptionEditText = findViewById(R.id.descriptionEditText);
        locationEditText = findViewById(R.id.locationEditText);
        priceEditText = findViewById(R.id.priceEditText);
        dateEditText = findViewById(R.id.dateEditText);
        startTimeEditText = findViewById(R.id.startTimeEditText);
        endTimeEditText = findViewById(R.id.endTimeEditText);
        durationEditText = findViewById(R.id.durationEditText);

        imageView = findViewById(R.id.imageView);
        Button chooseImageButton = findViewById(R.id.chooseImageButton);
        Button uploadButton = findViewById(R.id.uploadButton);
        progressBar = findViewById(R.id.progressBar);

        FirebaseStorage storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();
        databaseReference = FirebaseDatabase.getInstance().getReference("Activity");

        // Choose image button action
        chooseImageButton.setOnClickListener(v -> openFileChooser());

        // Upload button action
        uploadButton.setOnClickListener(v -> uploadImage());
    }

    // Open image chooser
    private void openFileChooser() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, PICK_IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            imageUri = data.getData();
            imageView.setImageURI(imageUri);
        }
    }


    // Upload Image to Firebase
    private void uploadImage() {
        if (imageUri != null && !titleEditText.getText().toString().trim().isEmpty()) {
            progressBar.setVisibility(View.VISIBLE);

            final String title = titleEditText.getText().toString().trim();
            final String description = descriptionEditText.getText().toString().trim();
            final String location = locationEditText.getText().toString().trim();
            final String startTime = startTimeEditText.getText().toString().trim();
            final String endTime = endTimeEditText.getText().toString().trim();
            final String duration = durationEditText.getText().toString().trim();
            final String date = dateEditText.getText().toString().trim();
            final String price = priceEditText.getText().toString().trim();
            final String imageId = UUID.randomUUID().toString();

            StorageReference fileReference = storageReference.child("activityImage/" + imageId + ".jpg");

            // Uploading the image
            fileReference.putFile(imageUri).addOnSuccessListener(taskSnapshot -> {
                fileReference.getDownloadUrl().addOnSuccessListener(uri -> {
                    String imageUrl = uri.toString();

                    // Create ActivityItem object
                    ActivityItem activityItem = new ActivityItem(title, imageUrl, description, location, price, date, startTime, endTime, duration);

                    // Store data in Realtime Database
                    databaseReference.child(imageId).setValue(activityItem).addOnCompleteListener(task -> {
                        progressBar.setVisibility(View.GONE); // <-- Move here for success
                        if (task.isSuccessful()) {
                            Toast.makeText(AddActivity.this, "Upload successful", Toast.LENGTH_SHORT).show();
                            clearFields();
                            Intent i = new Intent(getApplicationContext(), Home.class);
                            startActivity(i);
                            finish();
                        } else {
                            Toast.makeText(AddActivity.this, "Failed to upload data", Toast.LENGTH_SHORT).show();
                        }
                    });

                }).addOnFailureListener(e -> {
                    progressBar.setVisibility(View.GONE); // <-- Ensure it's set back
                    Toast.makeText(AddActivity.this, "Failed to get image URL", Toast.LENGTH_SHORT).show();
                });
            }).addOnFailureListener(e -> {
                progressBar.setVisibility(View.GONE); // <-- Ensure it's set back
                Toast.makeText(AddActivity.this, "Failed to upload image", Toast.LENGTH_SHORT).show();
            });

        } else {
            Toast.makeText(this, "No file selected or title is empty", Toast.LENGTH_SHORT).show();
        }
    }


    // Clear input fields
    private void clearFields() {
        titleEditText.setText("");
        descriptionEditText.setText("");
        locationEditText.setText("");
        priceEditText.setText("");
        dateEditText.setText("");
        startTimeEditText.setText("");
        endTimeEditText.setText("");
        durationEditText.setText("");
        imageView.setImageResource(0);
    }
}
