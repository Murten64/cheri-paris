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
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.cheriparis.adapters.StoreAdapter;
import com.cheriparis.listeners.BtnGlobalGPSListener;
import com.cheriparis.listeners.BtnRequestListener;
import com.cheriparis.networking.PrestaREQSService;
import com.cheriparis.networking.PrestaRESTService;
import com.cheriparis.pojos.Store;

public class MyListActivity extends Activity {
	private static final int CHANGE_LIST_PREFS = 1;
	
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

		this._nbStore = (TextView)findViewById(R.id.labNbStore);
		this._list = (ListView)findViewById(R.id.lvStoreList);
		ImageButton btnGPS = (ImageButton)findViewById(R.id.btnGlobalGPS);
		btnGPS.setOnClickListener(new BtnGlobalGPSListener(this));
		Button btnRequest = (Button)findViewById(R.id.btnRequest);
		btnRequest.setOnClickListener(new BtnRequestListener(this));
		PackageManager pm = getPackageManager();
		if (!pm.hasSystemFeature(PackageManager.FEATURE_LOCATION_GPS)){
			btnRequest.setEnabled(false);
		}

		this._adapter = new StoreAdapter(this, R.layout.itemlist, this._stores, this);
		this._list.setAdapter(this._adapter);

		loadView();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.activity_list, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		if(item.getItemId() == R.id.menu_settings) {
			Intent intent = new Intent(this, SettingsActivity.class);
			startActivityForResult(intent, MyListActivity.getChangeListPrefs());
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if(requestCode == getChangeListPrefs()){
			if(resultCode == RESULT_OK){
				this.loadView();
			}
		}
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
		number += " " + getString(R.string.nbStores);
		this._nbStore.setText(number);
	}

	public void goToShopInfoActivity(int id){
		Intent intent = new Intent();
		intent.setClass(MyListActivity.this, ShopInfoActivity.class);
		intent.putExtra("id", id);
		this.startActivity(intent);
	}

	public void goToShopLocalisation(int id){
		Intent intent = new Intent();
		double[] lats;
		double[] longs;
		String[] names;
		if(id == 0){
			lats = new double[this._stores.size()+1];
			longs = new double[this._stores.size()+1];
			names = new String[this._stores.size()+1];
			lats[0] = this.localiseMe()[0];
			longs[0] = this.localiseMe()[1];
			names[0] = getString(R.string.me);
			for(int i = 0; i < this._stores.size(); i++){
				lats[i+1] = this._stores.get(i).getLatitude();
				longs[i+1] = this._stores.get(i).getLongitude();
				names[i+1] = this._stores.get(i).getName();
			}
			intent.setClass(MyListActivity.this, MyMapActivity.class);
		}else {
			lats = new double[2];
			longs = new double[2];
			names = new String[2];
			lats[0] = this.localiseMe()[0];
			longs[0] = this.localiseMe()[1];
			names[0] = getString(R.string.me);
			int i = 0;
			while(this._stores.get(i).getId() != id){
				i++;
			}
			lats[1] = this._stores.get(i).getLatitude();
			longs[1] = this._stores.get(i).getLongitude();
			names[1] = this._stores.get(i).getName();
			intent.setClass(MyListActivity.this, MyMapActivity.class);
			intent.putExtra("id", id);
		}
		intent.putExtra("latitudes", lats);
		intent.putExtra("longitudes", longs);
		intent.putExtra("names", names);
		startActivity(intent);
	}

	private double[] localiseMe(){
		double loc[] = new double[2];
		if (_location != null){
			loc[0] = _location.getLatitude();
			loc[1] = _location.getLongitude();
		}
		else{
			Geocoder gcd = new Geocoder(this);
			Address addr = new Address(null);
			try {
				addr = gcd.getFromLocationName(_city, 1).get(0);
			} catch (IOException e) {
				e.printStackTrace();
			}
			loc[0] = addr.getLatitude();
			loc[1] = addr.getLongitude();
		}
		return loc;
	}

	public void load(){
		setCity(_prefCity.getString("city","Santiago"));
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
				if (_location != null){
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
				}else {
					Log.i("gps", "pb gps");
					Toast toast = Toast.makeText(this, R.string.satellite, Toast.LENGTH_LONG);
					toast.show();
				}
			}
		}
		Log.i("city after gps", getCity());
	}
	
	public void loadView(){
		load();	
		PrestaRESTService cityStores = new PrestaRESTService(this);
		cityStores.execute(this._city);
	}

	public String getCity() {
		return _city;
	}

	public void setCity(String city) {
		this._city = city;
	}

	public static int getChangeListPrefs() {
		return CHANGE_LIST_PREFS;
	}
	
	public void requestShop(){
		PrestaREQSService requestService = new PrestaREQSService(this);
		requestService.execute(this._location);
	}
}
