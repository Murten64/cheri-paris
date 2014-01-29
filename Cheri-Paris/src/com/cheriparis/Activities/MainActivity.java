package com.cheriparis.Activities;

import com.cheriparis.Activities.R;
import com.cheriparis.listeners.BtnListListener;
import com.cheriparis.listeners.BtnSettingsListener;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;

public class MainActivity extends Activity {
	static final int CHANGE_MAIN_PREF = 2;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        Button btnList = (Button)findViewById(R.id.bList);
        btnList.setOnClickListener(new BtnListListener(this));
        Button btnSettings = (Button)findViewById(R.id.bSettings);
        btnSettings.setOnClickListener(new BtnSettingsListener(this));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }
    
    public boolean onOptionsItemSelected (MenuItem item){
    	if(item.getItemId() == R.id.menu_settings) {
    		Intent intent = new Intent(this, SettingsActivity.class);
    		startActivityForResult(intent,CHANGE_MAIN_PREF);
    	}

    	return super.onOptionsItemSelected(item);
    }
    
    @Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
	}

	public void goToListView(){
    	Intent intent = new Intent();
    	intent.setClass(MainActivity.this, MyListActivity.class);
    	this.startActivity(intent);
    }
    
    public void goToSettingView(){
    	Intent intent = new Intent();
    	intent.setClass(MainActivity.this, SettingsActivity.class);
    	this.startActivity(intent);
    }
}
