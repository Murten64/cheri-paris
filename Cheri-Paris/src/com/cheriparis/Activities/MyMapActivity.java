package com.cheriparis.Activities;

import com.google.android.gms.maps.*;
import com.google.android.gms.maps.model.*;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

public class MyMapActivity extends FragmentActivity {
	private GoogleMap _map;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mymap_activity);

        // Get a handle to the Map Fragment
        _map = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map)).getMap();
        
        LatLng sydney = new LatLng(-33.867, 151.206);

        _map.setMyLocationEnabled(true);
        _map.moveCamera(CameraUpdateFactory.newLatLngZoom(sydney, 13));

        _map.addMarker(new MarkerOptions()
                .title("Sydney")
                .snippet("The most populous city in Australia.")
                .position(sydney));
    }
}

