package com.cheriparis.adapters;

import java.util.List;

import com.cheriparis.Activities.R;
import com.cheriparis.pojos.Store;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class StoreAdapter extends ArrayAdapter<Store> {
	private List<Store> _stores;
	private Context _context;
	
	public StoreAdapter(Context context, int textViewRessourceId, List<Store> objects){
		super(context, textViewRessourceId, objects);
		
		this._stores = objects;
		this._context = context;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		//get a menu inflater
		LayoutInflater li = (LayoutInflater)this._context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		//instanciate the view
		if(convertView == null){
			//get xml file
			li.inflate(R.layout.itemlist, parent, false);
		}
		
		//injection of view's values
		TextView tv = (TextView)convertView.findViewById(R.id.tvItemName);
		tv.setText(_stores.get(position).getName());
		
		return convertView;
	}
}
