package com.cheriparis.Activities;

import com.cheriparis.listeners.BtnListListener;
import com.cheriparis.listeners.BtnSettingsListener;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;

public class MainActivity extends Activity {
	private static int CODE_RETOUR = 1;
	
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
    		startActivity(new Intent(this, SettingsActivity.class));
    	}

    	return super.onOptionsItemSelected(item);
    }
    
    public void goToListView(){
    	Intent intent = new Intent();
    	intent.setClass(MainActivity.this, ListActivity.class);
    	this.startActivity(intent);
    }
    
    public void goToSettingView(){
    	Intent intent = new Intent();
    	intent.setClass(MainActivity.this, SettingsActivity.class);
    	this.startActivity(intent);
    }
}
