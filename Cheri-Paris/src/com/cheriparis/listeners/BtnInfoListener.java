package com.cheriparis.listeners;

import com.cheriparis.Activities.ListActivity;

import android.content.Context;
import android.text.NoCopySpan.Concrete;
import android.view.View;
import android.view.View.OnClickListener;

public class BtnInfoListener implements OnClickListener {
	private ListActivity _myView;
	private int _myId;
	
	public BtnInfoListener(ListActivity view, int id){
		this._myView = view;
		this._myId = id;
	}

	public void onClick(View v) {
		this._myView.goToShopInfoActivity(this._myId);
	}

}
