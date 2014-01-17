package com.cheriparis.listeners;

import android.view.View;
import android.view.View.OnClickListener;

import com.cheriparis.Activities.ShopInfoActivity;

public class BtnReturnListener implements OnClickListener {
	private ShopInfoActivity _myView;
	
	public BtnReturnListener(ShopInfoActivity view){
		this._myView = view;
	}
	
	public void onClick(View v) {
		this._myView.finish();

	}

}
