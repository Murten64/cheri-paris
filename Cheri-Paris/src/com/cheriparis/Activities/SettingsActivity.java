package com.cheriparis.Activities;

import java.io.IOException;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.cheriparis.listeners.BtnCancelSettingsListener;
import com.cheriparis.listeners.BtnValidateSettingsListener;
import com.cheriparis.listeners.CheckListener;

public class SettingsActivity extends Activity {
	private RadioGroup _group;
	private RadioButton _rbGps;
	private EditText _etpCity;
	private SharedPreferences _prefGPS;
	private SharedPreferences _prefCity;
	private	boolean _hasGps;
	private	boolean _gpsEnabled = false;
	
	public void setEditableCity(boolean edit){
		this._etpCity.setEnabled(edit);
	}
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        setContentView(R.layout.activity_settings);

        _prefGPS = getPreferences(Activity.MODE_PRIVATE);
        _prefCity = getPreferences(Activity.MODE_PRIVATE);
        
        this._group = (RadioGroup)findViewById(R.id.radioGroup1);
        _group.setOnCheckedChangeListener(new CheckListener(this));        
        _rbGps = (RadioButton)findViewById(R.id.rbGPS);
        
        this._etpCity = (EditText)findViewById(R.id.etpCity);
        
        Button btnVal = (Button)findViewById(R.id.btnVal);
        btnVal.setOnClickListener(new BtnValidateSettingsListener(this));
        Button btnCan = (Button)findViewById(R.id.btnCan);
        btnCan.setOnClickListener(new BtnCancelSettingsListener(this));
        
        this.load();
        
        //verifie la presence du gps
        PackageManager pm = getPackageManager();
        _hasGps = pm.hasSystemFeature(PackageManager.FEATURE_LOCATION_GPS);
        
        if (_hasGps){
        	_rbGps.setClickable(true);
        	
        	//si gps present, on verifie qu'il est actif
            LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
            _gpsEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
            
            if (_gpsEnabled && (R.id.rbGPS == _prefGPS.getInt("gps",R.id.rbCity))){
    			_group.check(R.id.rbGPS);
    			Log.i("gps", "active");
    			
    			//GPS
    			//critere de recherche, par default
    		    Criteria criteria = new Criteria();
    		    String provider = locationManager.getBestProvider(criteria, false);
    		    //position
    		    Location location = locationManager.getLastKnownLocation(provider);
    			Log.i("latitude", String.valueOf(location.getLatitude()));
    			Log.i("longitude", String.valueOf(location.getLongitude()));
    			//geocoder
    			Geocoder gcd = new Geocoder(this);
    			List<Address> addresses;
				try {
					//recup de l'adresse
					addresses = gcd.getFromLocation(location.getLatitude(), location.getLongitude(), 1);
	    			if (addresses.size() > 0){
	    				//et donc de la ville
	    				Log.i("ville", addresses.get(0).getLocality());
	    			}
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				//googlemaps
				//maps.google.com/?ll=43.3063239,-0.3379071
            }
            //si gps actif, mais non demlande
            else{
    			_group.check(R.id.rbCity);
    			Log.i("gps", "unactive");
            }
        }
        //si pas de gps
        else{
        	_rbGps.setClickable(false);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_settings, menu);
        return true;
    }
    
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(this);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
    
    public void savePref(){
    	Editor ed = _prefGPS.edit();
    	ed.putInt("gps", (int) _group.getCheckedRadioButtonId());
    	ed.commit();
    	
    	//si gps demande et inactif, on demande a l'activer
    	if (_rbGps.isChecked() && !_gpsEnabled){
    		startActivityForResult(new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS), 0);
    	}
    	//si gps non demande et actif, on demande a le desactiver
    	if (!_rbGps.isChecked() && _gpsEnabled){
    		startActivityForResult(new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS), 0);
    	}
    	
    	ed = _prefCity.edit();
    	ed.putString("city", _etpCity.getText().toString());
    	ed.commit();
    }
    
    public void load(){
    	int i = _prefGPS.getInt("gps",R.id.rbCity);
    	if (i == R.id.rbGPS){
    		_group.check(R.id.rbGPS);
    	}
    	String s = _prefCity.getString("city","");
    	_etpCity.setText(s);
    }

}
