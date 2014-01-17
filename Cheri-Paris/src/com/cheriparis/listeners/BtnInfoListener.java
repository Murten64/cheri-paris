package com.cheriparis.listeners;

import android.view.View;
import android.view.View.OnClickListener;

import com.cheriparis.Activities.MyListActivity;

public class BtnInfoListener implements OnClickListener {
	private MyListActivity _myView;
	private int _myId;
	
	public BtnInfoListener(MyListActivity view, int id){
		this._myView = view;
		this._myId = id;
	}

	public void onClick(View v) {
		this._myView.goToShopInfoActivity(this._myId);
	}

}
