package com.cheriparis.Activities;

import java.util.ArrayList;
import java.util.List;

import com.cheriparis.Activities.R;
import com.cheriparis.listeners.BtnReturnListener;
import com.cheriparis.networking.PrestaINFOService;
import com.cheriparis.pojos.InfoStore;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;

public class ShopInfoActivity extends Activity {
	private List<TextView> _infos;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_shop_info);
		
		Intent intent = getIntent();
		int id = intent.getIntExtra("id", 0);
		
		this._infos = new ArrayList<TextView>(7);
		this._infos.add((TextView)findViewById(R.id.labNameStore));
		this._infos.add((TextView)findViewById(R.id.labHoursStore));
		this._infos.add((TextView)findViewById(R.id.labAddressStore));
		this._infos.add((TextView)findViewById(R.id.labPostCodeStore));
		this._infos.add((TextView)findViewById(R.id.labCityStore));
		this._infos.add((TextView)findViewById(R.id.labPhoneStore));
		this._infos.add((TextView)findViewById(R.id.labMailStore));
		
		Button btnReturn = (Button)findViewById(R.id.btnReturnShop);
		btnReturn.setOnClickListener(new BtnReturnListener(this));
		
		if(id == 0){
			this._infos.get(0).setText(R.string.notFound);
		}else{
			PrestaINFOService service = new PrestaINFOService(this);
			service.execute(id);
		}
	}
	
	public void setInfos(InfoStore store){
		TextView infos = this._infos.get(0);
		infos.setText(store.getName());
		infos = this._infos.get(1);
		infos.setText(store.getHours());
		infos = this._infos.get(2);
		infos.setText(store.getAddress());
		infos = this._infos.get(3);
		infos.setText(store.getPostcode());
		infos = this._infos.get(4);
		infos.setText(store.getCity());
		infos = this._infos.get(5);
		infos.setText(store.getPhone());
		infos = this._infos.get(6);
		infos.setText(store.getMail());
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.shop_info, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
    	if(item.getItemId() == R.id.menu_settings) {
    		startActivity(new Intent(this, SettingsActivity.class));
    	}

    	return super.onOptionsItemSelected(item);
	}

}
