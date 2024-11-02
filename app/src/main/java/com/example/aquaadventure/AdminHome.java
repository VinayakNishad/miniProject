package com.example.aquaadventure;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.PopupMenu;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.aquaadventure.Admin.AddInstructor;
import com.example.aquaadventure.Admin.InsertActivity.AddActivity;

public class AdminHome extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_home);

        // Set up the toolbar
        Toolbar toolbar = findViewById(R.id.toolBar);
        setSupportActionBar(toolbar);

        // Enable the home button as a "Hamburger" button
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setHomeAsUpIndicator(R.drawable.baseline_menu_24); // Custom icon
        }

        // Setting up edge-to-edge display
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        // Handle the hamburger button click
        if (id == android.R.id.home) {
            showPopupMenu();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void showPopupMenu() {
        // Create a popup menu and set its items
        PopupMenu popupMenu = new PopupMenu(this, findViewById(R.id.toolBar));
        popupMenu.getMenuInflater().inflate(R.menu.menu_admin_home, popupMenu.getMenu());

        // Handle menu item clicks
        popupMenu.setOnMenuItemClickListener(item -> {
            int itemId = item.getItemId();
            if (itemId == R.id.menu_add_instructor) {
                startActivity(new Intent(this, AddInstructor.class));
            } else if (itemId == R.id.menu_view_customer_booking) {
                startActivity(new Intent(this, CustomerBook.class));
            } else if (itemId == R.id.menu_add_activity) {
                startActivity(new Intent(this, AddActivity.class));
            }
            return true;
        });

        popupMenu.show();
    }
}
