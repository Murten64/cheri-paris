package com.cheriparis.listeners;

import com.cheriparis.Activities.SettingsActivity;

import android.view.View;
import android.view.View.OnClickListener;

public class BtnCancelSettingsListener implements OnClickListener {
	
	private SettingsActivity _myView;
	
	

	public BtnCancelSettingsListener(SettingsActivity view) {
		this._myView = view;
	}



	public void onClick(View v) {
			this._myView.cancel();

	}

}
