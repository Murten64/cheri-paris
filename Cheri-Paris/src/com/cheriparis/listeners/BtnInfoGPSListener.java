package com.cheriparis.listeners;

import com.cheriparis.Activities.ListActivity;

import android.view.View;
import android.view.View.OnClickListener;

public class BtnInfoGPSListener implements OnClickListener {
	private ListActivity _myView;
	private int _myId;
	
	public BtnInfoGPSListener(ListActivity view, int id){
		this._myView = view;
		this._myId = id;
	}

	public void onClick(View v) {
		this._myView.goToShopLocalisation(this._myId);
	}

}
