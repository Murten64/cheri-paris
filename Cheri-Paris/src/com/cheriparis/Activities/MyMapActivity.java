package com.cheriparis.Activities;

import com.cheriparis.Activities.R;
import com.google.android.gms.maps.*;
import com.google.android.gms.maps.model.*;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

public class MyMapActivity extends FragmentActivity {
	private GoogleMap _map;
	private double _lat[];
	private double _long[];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mymap_activity);

        Intent intent = new Intent();
        _lat = intent.getDoubleArrayExtra("latitudes");
        _long = intent.getDoubleArrayExtra("longitudes");
        
        // Get a handle to the Map Fragment
        _map = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map)).getMap();
        
        LatLng marker = new LatLng(_lat[0], _long[0]);
        _map.moveCamera(CameraUpdateFactory.newLatLngZoom(marker, 12));
        
        for (int i=1; i<_lat.length; i++){
        	marker = new LatLng(_lat[i],_long[i]);
        	_map.addMarker(new MarkerOptions()
        	.icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_launcher))
        	.position(marker));
        }
    }
}

