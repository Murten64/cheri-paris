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
	private String _title[];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mymap_activity);

        Intent intent = getIntent();
        _lat = intent.getDoubleArrayExtra("latitudes");
        _long = intent.getDoubleArrayExtra("longitudes");
        _title = intent.getStringArrayExtra("names");
        
        
        // Get a handle to the Map Fragment
        _map = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map)).getMap();
        
        //cree le premier marker
        LatLng marker = new LatLng(_lat[0], _long[0]);
        //le positionne au bon endroit
    	_map.addMarker(new MarkerOptions()
    		.position(marker)
    		.title(_title[0]));
    	//centre la camera dessus
        _map.moveCamera(CameraUpdateFactory.newLatLngZoom(marker, 13));
        
        //place les autres marker
        for (int i=1; i<_lat.length; i++){
        	marker = new LatLng(_lat[i],_long[i]);
        	_map.addMarker(new MarkerOptions()
        	.icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_launcher))
        	.position(marker)
        	.title(_title[i]));
        }
    }
}

