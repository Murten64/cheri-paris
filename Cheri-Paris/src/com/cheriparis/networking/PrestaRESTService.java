package com.cheriparis.networking;

import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.xml.sax.SAXException;

import android.os.AsyncTask;
import android.util.Log;

public class PrestaRESTService extends AsyncTask<Void, Void, Void> {

	private final static String PRESTA_REST_KEY = "Z7MTLNZW96SEXB2LHVBQWKAM3UU41YIM";
	private final static String PRESTA_REST_URL_STORES = "http://cheri-paris.com/v1/api/stores";
	
	private final static String PRESTA_POSTALCODE_FIELD = "postcode";
	
	private DefaultHttpClient client;
	//http://cheri-paris.com/v1/api/stores?filter[postcode]=64000
	//http://cheri-paris.com/v1/api/stores?display=full&filter[postcode]=64000
	//http://cheri-paris.com/v1/api/stores?display=[id,name,address1]&filter[city]=Pau
	
	
	public PrestaRESTService() {
		super();
		this.client = new DefaultHttpClient();
		this.client.getCredentialsProvider().setCredentials(
				new AuthScope(AuthScope.ANY_HOST, AuthScope.ANY_PORT),
				new UsernamePasswordCredentials(PRESTA_REST_KEY, null));
	}
	
	
	@Override
	protected Void doInBackground(Void... arg0) {
		
		HttpGet cible = new HttpGet(PRESTA_REST_URL_STORES);
		
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
		return null;
	}

	
	
}
