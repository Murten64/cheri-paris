package com.cheriparis.Activities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.pm.PackageManager;
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

import com.cheriparis.Activities.R;
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

        _prefGPS = getSharedPreferences("gps",0);
        _prefCity = getSharedPreferences("city",0);
        
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
            }
            //si gps actif, mais non demande
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
    	String city = _etpCity.getText().toString();
    	city = city.replaceAll(" ", "");
    	ed.putString("city", city);
    	ed.commit();
    }
    
    public void load(){
    	int i = _prefGPS.getInt("gps",R.id.rbCity);
    	if (i == R.id.rbGPS){
    		_group.check(R.id.rbGPS);
    	}
    	String s = _prefCity.getString("city","");
    	_etpCity.setText(s);
    	_etpCity.requestFocus();
    	_etpCity.setSelection(s.length());
    }

}
