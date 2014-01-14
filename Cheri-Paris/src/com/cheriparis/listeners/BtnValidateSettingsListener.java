package com.cheriparis.listeners;

import com.cheriparis.Activities.SettingsActivity;

import android.view.View;
import android.view.View.OnClickListener;

public class BtnValidateSettingsListener implements OnClickListener {
	
	private SettingsActivity _settings;
	
	public BtnValidateSettingsListener(SettingsActivity view){
		this._settings = view;
	}

	public void onClick(View v) {
		_settings.savePref();
		this._settings.finish();
	}

}
