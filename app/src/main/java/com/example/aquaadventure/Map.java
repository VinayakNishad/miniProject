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

public class Map extends AppCompatActivity implements OnMapReadyCallback {
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        assert mapFragment != null;
        mapFragment.getMapAsync(this);

        toolbar=findViewById(R.id.toolBar_back);
        setSupportActionBar(toolbar);
        if(getSupportActionBar()!=null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle("Map");
        }

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

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int itemId = item.getItemId();
        if(itemId==android.R.id.home){
            Intent i  = new Intent(getApplicationContext(),Home.class);
            startActivity(i);
            finish();
        }

        return super.onOptionsItemSelected(item);
    }
}