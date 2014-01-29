package com.cheriparis.networking;

import java.io.IOException;

import org.apache.http.HttpResponse;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import android.location.Location;
import android.os.AsyncTask;

public class PrestaREQSService extends AsyncTask<Location, Void, Integer> {
	private final static String PRESTA_INFO_KEY = "Z7MTLNZW96SEXB2LHVBQWKAM3UU41YIM";
	private String _url;
	private DefaultHttpClient _client;
	
	public PrestaREQSService(){
		super();
		
		this._client = new DefaultHttpClient();
		this._client.getCredentialsProvider().setCredentials(
				new AuthScope(AuthScope.ANY_HOST, AuthScope.ANY_PORT),
				new UsernamePasswordCredentials(PRESTA_INFO_KEY, null));
		
	}

	@Override
	protected Integer doInBackground(Location... location) {
		int valid = 0;
		_url = "http://mobile.cheri-paris.com/requestStore.php?GPS_LOCATION=";
		_url += location[0].getLatitude();  //"47.6193757";
		_url += ",";
		_url += location[0].getLongitude(); //"6.1529374";
		_url += "&LOCALE=";
		_url += ""; //this.getResources().getConfiguration().locale.getDisplayName();
		_url += "&DEVICE_ID=";
		_url += ""; //TelephonyManager.getDeviceId();
		
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
}