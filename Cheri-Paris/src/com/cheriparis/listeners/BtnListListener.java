package com.cheriparis.listeners;

import com.cheriparis.Activities.MainActivity;

import android.view.View;
import android.view.View.OnClickListener;

public class BtnListListener implements OnClickListener {
	
	private MainActivity _context;
	
	public BtnListListener(MainActivity view){
		this._context = view;
	}

	public void onClick(View arg0) {
		_context.goToListView();
	}

}
