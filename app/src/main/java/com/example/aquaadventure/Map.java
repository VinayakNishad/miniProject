package com.example.aquaadventure;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.Marker;

public class Map extends AppCompatActivity implements OnMapReadyCallback {
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        assert mapFragment != null;
        mapFragment.getMapAsync(this);

        toolbar = findViewById(R.id.toolBar_back);
        setSupportActionBar(toolbar);
        if(getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle("Map");
        }
    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        LatLng calangute = new LatLng(15.5494, 73.7535);
        LatLng miramar = new LatLng(15.4827, 73.8074);
        googleMap.addMarker(new MarkerOptions().position(calangute).title("Calangute Beach").snippet("Beautiful beach in North Goa."));
        googleMap.addMarker(new MarkerOptions().position(miramar).title("Miramar Beach").snippet("Popular spot for a beach day."));

        googleMap.setInfoWindowAdapter(new CustomInfoWindowAdapter());

        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(calangute, 12));
    }

    class CustomInfoWindowAdapter implements GoogleMap.InfoWindowAdapter {
        private final View mWindow;

        CustomInfoWindowAdapter() {
            mWindow = LayoutInflater.from(Map.this).inflate(R.layout.custom_info_window, null);
        }

        private void renderWindowText(Marker marker, View view) {
            String title = marker.getTitle();
            TextView tvTitle = view.findViewById(R.id.info_title);
            tvTitle.setText(title);

            String snippet = marker.getSnippet();
            TextView tvSnippet = view.findViewById(R.id.info_description);
            tvSnippet.setText(snippet);

            ImageView imageView = view.findViewById(R.id.info_image);
            if (title.equals("Calangute Beach")) {
                imageView.setImageResource(R.drawable.t1); // Replace with actual image
            } else if (title.equals("Miramar Beach")) {
                imageView.setImageResource(R.drawable.t1); // Replace with actual image
            }
        }

        @Override
        public View getInfoWindow(Marker marker) {
            renderWindowText(marker, mWindow);
            return mWindow;
        }

        @Override
        public View getInfoContents(Marker marker) {
            renderWindowText(marker, mWindow);
            return mWindow;
        }
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int itemId = item.getItemId();
        if (itemId == android.R.id.home) {
            Intent i = new Intent(getApplicationContext(), Home.class);
            startActivity(i);
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}
