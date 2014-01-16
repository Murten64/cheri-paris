package com.cheriparis.Activities;

import java.util.ArrayList;
import java.util.List;

import com.cheriparis.adapters.StoreAdapter;
import com.cheriparis.listeners.BtnReturnListener;
import com.cheriparis.pojos.Store;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.widget.Button;
import android.widget.ListView;

public class ListActivity extends Activity {
	private List<Store> _stores;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        
        this._stores = new ArrayList<Store>();
        
        ListView list = (ListView)findViewById(R.id.lvStoreList);
        Button btnReturn = (Button)findViewById(R.id.btnReturnList);
        btnReturn.setOnClickListener(new BtnReturnListener(this));
        
        StoreAdapter adapter = new StoreAdapter(this, R.layout.itemlist, this._stores);
        list.setAdapter(adapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_list, menu);
        return true;
    }
}
