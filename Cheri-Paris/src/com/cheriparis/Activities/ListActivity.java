package com.cheriparis.Activities;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.cheriparis.adapters.StoreAdapter;
import com.cheriparis.listeners.BtnReturnListener;
import com.cheriparis.networking.PrestaRESTService;
import com.cheriparis.pojos.Store;

public class ListActivity extends Activity {
	private List<Store> _stores;
	private StoreAdapter _adapter;
	private ListView _list;
	private TextView _nbStore;
	private SharedPreferences _prefGPS;
	private SharedPreferences _prefCity;
	private Location _location;
	private String _city;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        
        _prefGPS = getSharedPreferences("gps",Activity.MODE_PRIVATE);
        _prefCity = getSharedPreferences("city",Activity.MODE_PRIVATE);
        
        this._stores = new ArrayList<Store>();
        load();
        PrestaRESTService cityStores = new PrestaRESTService(this);
        
        this._nbStore = (TextView)findViewById(R.id.labNbStore);
        this._list = (ListView)findViewById(R.id.lvStoreList);
        Button btnReturn = (Button)findViewById(R.id.btnReturnList);
        btnReturn.setOnClickListener(new BtnReturnListener(this));
        
        this._adapter = new StoreAdapter(this, R.layout.itemlist, this._stores, this);
        this._list.setAdapter(this._adapter);
        
        cityStores.execute(this._city);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_list, menu);
        return true;
    }
    
    public void setStores(List<Store> stores) {
    	this._stores.clear();
    	for(int i = 0; i < stores.size(); i++){
    		_stores.add(stores.get(i));
    	}
    	this.setNbStores(this._stores.size());
    	this._adapter.notifyDataSetChanged();
	}
    
    private void setNbStores(int nb){
    	String number = new String(String.valueOf(nb));
    	number += this._nbStore.getText().toString();
    	this._nbStore.setText(number);
    }

    public void goToShopInfoActivity(int id){
    	Intent intent = new Intent();
    	intent.setClass(ListActivity.this, ShopInfoActivity.class);
    	intent.putExtra("id", id);
    	this.startActivity(intent);
    }
    
    public void goToShopLocalisation(){	
    	PackageManager pm = getPackageManager();
    	if (pm.hasSystemFeature(PackageManager.FEATURE_LOCATION_GPS)){
    		LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
    		if ((R.id.rbGPS == _prefGPS.getInt("gps",R.id.rbCity)) && 
    			(locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER))){
    			String url = "http://maps.google.com/?ll=";
    			url += String.valueOf(_location.getLatitude());
    			url += ",";
    			url += String.valueOf(_location.getLongitude());
    			Intent intent = new Intent( Intent.ACTION_VIEW, Uri.parse( url ) );
    			startActivity(intent);
    		}
    	} else{
    		
    	}
    }
    
	public void load(){
    	setCity(_prefCity.getString("city",""));
    	Log.i("city before gps", getCity());

    	PackageManager pm = getPackageManager();
    	if (pm.hasSystemFeature(PackageManager.FEATURE_LOCATION_GPS)){
    		//GPS
    		//critere de recherche, par default
    		LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
    		if ((R.id.rbGPS == _prefGPS.getInt("gps",R.id.rbCity)) && 
    				(locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER))){
    			Criteria criteria = new Criteria();
    			String provider = locationManager.getBestProvider(criteria, false);
    			//position
    			_location = locationManager.getLastKnownLocation(provider);
    			Log.i("latitude", String.valueOf(_location.getLatitude()));
    			Log.i("longitude", String.valueOf(_location.getLongitude()));
    			//geocoder
    			Geocoder gcd = new Geocoder(this);
    			List<Address> addresses;
    			try {
    				//recup de l'adresse
    				addresses = gcd.getFromLocation(_location.getLatitude(), _location.getLongitude(), 1);
    				if (addresses.size() > 0){
    					//et donc de la ville
    					Log.i("ville gps", addresses.get(0).getLocality());
    					String city = addresses.get(0).getLocality().replaceAll(" ", "");
    					setCity(city);
    				}
    			} catch (IOException e) {
    				e.printStackTrace();
    			}
    		}
    	}
    	Log.i("city after gps", getCity());
	}

	public String getCity() {
		return _city;
	}

	public void setCity(String city) {
		this._city = city;
	}
}
