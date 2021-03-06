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
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import android.os.AsyncTask;
import android.util.Log;

import com.cheriparis.Activities.MyListActivity;
import com.cheriparis.pojos.Store;

public class PrestaRESTService extends AsyncTask<String, Void, List<Store>> {

	private final static String PRESTA_REST_KEY = "Z7MTLNZW96SEXB2LHVBQWKAM3UU41YIM";
	private final static String PRESTA_REST_URL_STORES = "http://cheri-paris.com/v1/api/stores?display=[id,name,latitude,longitude]&filter[city]=";
	
	private MyListActivity _myView;
	
	private DefaultHttpClient client;	
	
	public PrestaRESTService(MyListActivity view) {
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
		        NodeList nl = answer.getElementsByTagName("store");
		        for(int i=0; i<nbPdv; i++) {
			        Element current = (Element)nl.item(i);
		        	
			        NodeList store = current.getElementsByTagName("id");
			        Element id = (Element)store.item(0);
			        store = current.getElementsByTagName("name");
			        Element name = (Element)store.item(0);
			        store = current.getElementsByTagName("latitude");
			        Element latitude = (Element)store.item(0);
			        store = current.getElementsByTagName("longitude");
			        Element longitude = (Element)store.item(0);
		        	Store s = new Store(id.getFirstChild().getNodeValue(), 
		        						name.getFirstChild().getNodeValue(), 
		        						latitude.getFirstChild().getNodeValue(), 
		        						longitude.getFirstChild().getNodeValue());
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
