package com.example.aquaadventure;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class Map extends AppCompatActivity implements OnMapReadyCallback {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        assert mapFragment != null;
        mapFragment.getMapAsync(this);

    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        LatLng calangute = new LatLng(15.5494, 73.7535);
        LatLng miramar = new LatLng(15.4827, 73.8074);
        googleMap.addMarker(new MarkerOptions().position(calangute).title("Calangute beach"));
        googleMap.addMarker(new MarkerOptions().position(miramar).title("Miramar beach"));
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(calangute,12));
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(miramar,12));
    }
}