package com.cheriparis.listeners;

import com.cheriparis.Activities.MainActivity;

import android.view.View;
import android.view.View.OnClickListener;

public class BtnSettingsListener implements OnClickListener {
	
	private MainActivity _context;
	
	public BtnSettingsListener(MainActivity view){
		this._context = view;
	}
	
	public void onClick(View v) {
		_context.goToSettingView();
	}

}
