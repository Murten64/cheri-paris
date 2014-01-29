package com.cheriparis.listeners;

import com.cheriparis.Activities.MyListActivity;

import android.view.View;
import android.view.View.OnClickListener;

public class BtnRequestListener implements OnClickListener {
	private MyListActivity _myView;

	public BtnRequestListener(MyListActivity view) {
		super();
		this._myView = view;
	}

	public void onClick(View v) {
		this._myView.requestShop();

	}

}
