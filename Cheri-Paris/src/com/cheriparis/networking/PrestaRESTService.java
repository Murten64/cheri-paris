package com.cheriparis.networking;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.xml.sax.SAXException;

import android.os.AsyncTask;
import android.util.Log;

import com.cheriparis.Activities.ListActivity;
import com.cheriparis.pojos.Store;

public class PrestaRESTService extends AsyncTask<String, Void, List<Store>> {

	private final static String PRESTA_REST_KEY = "Z7MTLNZW96SEXB2LHVBQWKAM3UU41YIM";
	private final static String PRESTA_REST_URL_STORES = "http://cheri-paris.com/v1/api/stores?display=[id,name,latitude,longitude]&filter[city]=";
	//private final static String PRESTA_POSTALCODE_FIELD = "postcode";
	
	private ListActivity _myView;
	
	private DefaultHttpClient client;
	//http://cheri-paris.com/v1/api/stores?filter[postcode]=64000
	//http://cheri-paris.com/v1/api/stores?display=full&filter[postcode]=64000
	//http://cheri-paris.com/v1/api/stores?display=[id,name,address1]&filter[city]=Pau
	
	
	public PrestaRESTService(ListActivity view) {
		super();
		this.client = new DefaultHttpClient();
		this.client.getCredentialsProvider().setCredentials(
				new AuthScope(AuthScope.ANY_HOST, AuthScope.ANY_PORT),
				new UsernamePasswordCredentials(PRESTA_REST_KEY, null));
		this._myView = view;
	}
	
	
	@Override
	protected List<Store> doInBackground(String... city) {
		String url = new String(PRESTA_REST_URL_STORES);
		url += city[0];
		Log.i("CHERI", url);
		List<Store> stores = new ArrayList<Store>();
		
		HttpGet cible = new HttpGet(url);
		try {
			HttpResponse response = this.client.execute(cible);  //essayes d'atteindre la cible
			
			if (response.getStatusLine().getStatusCode()==200) {

				HttpEntity responseEntity = response.getEntity();
				
				DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		        //factory.setExpandEntityReferences(false);
		        DocumentBuilder builder = factory.newDocumentBuilder();
		        Document answer = builder.parse(responseEntity.getContent());
		        
		        int nbPdv = answer.getElementsByTagName("store").getLength();

		        Log.i("CHERI", Integer.toString(nbPdv) + " points de ventes");
		        
		        for(int i=0; i<nbPdv; i++) {
		        	Node current = answer.getElementsByTagName("store").item(i);
		        	Log.i("DEBUG", current.getChildNodes().item(0).getNodeValue());
		        	Store s = new Store(current.getChildNodes().item(0).getNodeValue(), 
		        						current.getChildNodes().item(1).getNodeValue(), 
		        						current.getChildNodes().item(2).getNodeValue(), 
		        						current.getChildNodes().item(3).getNodeValue());
		        	stores.add(s);
		        	Log.i("CHERI", current.toString());
		        }
		        	
				
			} else {
				Log.i("CHERI", "Echec de l'appel du webservice");
			}	
			
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (IllegalStateException e) {
			e.printStackTrace();
		} catch (SAXException e) {
			e.printStackTrace();
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		}
		return stores;
	}


	@Override
	protected void onPostExecute(List<Store> result) {
		this._myView.setStores(result);
	}

	
	
}
