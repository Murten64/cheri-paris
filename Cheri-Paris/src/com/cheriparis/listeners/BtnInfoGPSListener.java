package com.cheriparis.listeners;

import com.cheriparis.Activities.MyListActivity;

import android.view.View;
import android.view.View.OnClickListener;

public class BtnInfoGPSListener implements OnClickListener {
	private MyListActivity _myView;
	private int _myId;
	
	public BtnInfoGPSListener(MyListActivity view, int id){
		this._myView = view;
		this._myId = id;
	}

	public void onClick(View v) {
		this._myView.goToShopLocalisation(this._myId);
	}

}
