package com.cheriparis.listeners;

import com.cheriparis.Activities.R;
import com.cheriparis.Activities.SettingsActivity;

import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;

public class CheckListener implements OnCheckedChangeListener {
	private SettingsActivity _myActivity;
	
	public CheckListener(SettingsActivity sa){
		_myActivity = sa;
	}

	public void onCheckedChanged(RadioGroup group, int checkedId) {
		if(checkedId == (int) R.id.rbCity){
			this._myActivity.setEditableCity(true);
		}
		else{
			this._myActivity.setEditableCity(false);
		}

	}

}
