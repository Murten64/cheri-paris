package com.cheriparis.Activities;

import com.cheriparis.listeners.BtnReturnListener;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.widget.Button;

public class ListActivity extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        
        Button btnReturn = (Button)findViewById(R.id.btnReturnList);
        btnReturn.setOnClickListener(new BtnReturnListener(this));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_list, menu);
        return true;
    }
}
