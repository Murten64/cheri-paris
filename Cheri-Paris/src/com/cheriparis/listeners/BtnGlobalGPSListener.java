package com.cheriparis.listeners;

import com.cheriparis.Activities.MyListActivity;

import android.view.View;
import android.view.View.OnClickListener;

public class BtnGlobalGPSListener implements OnClickListener {
	private MyListActivity _myView;

	public BtnGlobalGPSListener(MyListActivity view) {
		super();
		this._myView = view;
	}

	public void onClick(View v) {
		this._myView.goToShopLocalisation(0);

	}

}
