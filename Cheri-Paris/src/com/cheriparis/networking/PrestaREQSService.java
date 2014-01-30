package com.cheriparis.networking;

import java.io.IOException;

import org.apache.http.HttpResponse;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import com.cheriparis.Activities.MyListActivity;

import android.content.Context;
import android.location.Location;
import android.os.AsyncTask;
import android.telephony.TelephonyManager;
import android.util.Log;

public class PrestaREQSService extends AsyncTask<Location, Void, Integer> {
	private final static String PRESTA_INFO_KEY = "Z7MTLNZW96SEXB2LHVBQWKAM3UU41YIM";
	private String _url;
	private DefaultHttpClient _client;
	private MyListActivity _myActivity;
	
	public PrestaREQSService(MyListActivity myActivity){
		super();
		
		_myActivity = myActivity;
		this._client = new DefaultHttpClient();
		this._client.getCredentialsProvider().setCredentials(
				new AuthScope(AuthScope.ANY_HOST, AuthScope.ANY_PORT),
				new UsernamePasswordCredentials(PRESTA_INFO_KEY, null));
		
	}

	@Override
	protected Integer doInBackground(Location... location) {
		int valid = 0;
		TelephonyManager tm = (TelephonyManager) _myActivity.getSystemService(Context.TELEPHONY_SERVICE);
		_url = "http://mobile.cheri-paris.com/requestStore.php?GPS_LOCATION=";
		_url += String.valueOf(location[0].getLatitude());  //"47.6193757";
		_url += ",";
		_url += String.valueOf(location[0].getLongitude()); //"6.1529374";
		_url += "&LOCALE=";
		_url += _myActivity.getResources().getConfiguration().locale.getDisplayName();
		_url += "&DEVICE_ID=";
		_url += tm.getDeviceId();
		Log.i("url",_url);
		HttpGet cible = new HttpGet(_url);

		try{
			HttpResponse response = this._client.execute(cible);
			if (response.getStatusLine().getStatusCode() == 200){
				valid = 200;
			}
			
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (IllegalStateException e) {
			e.printStackTrace();
		}
		
		return valid;
	}

	@Override
	protected void onPostExecute(Integer result) {
		if(result.equals(200)){
			this._myActivity.requestOk();
		}
		else {
			this._myActivity.requestAborted();
		}
	}
}
