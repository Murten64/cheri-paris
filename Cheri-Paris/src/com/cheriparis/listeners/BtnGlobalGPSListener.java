package com.cheriparis.listeners;

import com.cheriparis.Activities.ListActivity;

import android.view.View;
import android.view.View.OnClickListener;

public class BtnGlobalGPSListener implements OnClickListener {
	private ListActivity _myView;

	public BtnGlobalGPSListener(ListActivity view) {
		super();
		this._myView = view;
	}

	public void onClick(View v) {
		this._myView.goToShopLocalisation(0);

	}

}
