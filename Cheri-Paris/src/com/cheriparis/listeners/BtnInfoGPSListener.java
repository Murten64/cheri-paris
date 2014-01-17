package com.cheriparis.listeners;

import com.cheriparis.Activities.ListActivity;

import android.view.View;
import android.view.View.OnClickListener;

public class BtnInfoGPSListener implements OnClickListener {
	private ListActivity _myView;
	
	public BtnInfoGPSListener(ListActivity view){
		this._myView = view;
	}

	public void onClick(View v) {
		this._myView.goToShopLocalisation();
	}

}
