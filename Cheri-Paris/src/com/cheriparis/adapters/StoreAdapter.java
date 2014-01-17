package com.cheriparis.adapters;

import java.util.List;

import com.cheriparis.Activities.ListActivity;
import com.cheriparis.Activities.R;
import com.cheriparis.listeners.BtnInfoGPSListener;
import com.cheriparis.listeners.BtnInfoListener;
import com.cheriparis.pojos.Store;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

public class StoreAdapter extends ArrayAdapter<Store> {
	private List<Store> _stores;
	private Context _context;
	private ListActivity _myActivity;
	
	public StoreAdapter(Context context, int textViewRessourceId, List<Store> objects, ListActivity activity){
		super(context, textViewRessourceId, objects);
		
		this._stores = objects;
		this._context = context;
		this._myActivity = activity;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		//get a menu inflater
		LayoutInflater li = (LayoutInflater)this._context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		//instanciate the view
		if(convertView == null){
			//get xml file
			convertView = li.inflate(R.layout.itemlist, parent, false);
		}
		
		//injection of view's values
		TextView tv = (TextView)convertView.findViewById(R.id.tvItemName);
		tv.setText(_stores.get(position).getName());
		ImageButton btnInfo = (ImageButton)convertView.findViewById(R.id.btnInfo);
		btnInfo.setOnClickListener(new BtnInfoListener(this._myActivity,_stores.get(position).getId()));
		ImageButton btnGPS = (ImageButton)convertView.findViewById(R.id.btnInfoGPS);
		btnGPS.setOnClickListener(new BtnInfoGPSListener(this._myActivity));
		
		return convertView;
	}
}
