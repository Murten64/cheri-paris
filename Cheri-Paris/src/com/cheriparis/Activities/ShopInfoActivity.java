package com.cheriparis.Activities;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;

public class ShopInfoActivity extends Activity {
	// TODO ajouter un service pour récupérer les données complètes d'un magasin
	// TODO lier les données récupérés aux champs de cette activité

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_shop_info);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.shop_info, menu);
		return true;
	}

}
