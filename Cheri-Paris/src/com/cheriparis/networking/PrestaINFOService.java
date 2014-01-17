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
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import android.os.AsyncTask;
import android.util.Log;

import com.cheriparis.Activities.ShopInfoActivity;
import com.cheriparis.pojos.InfoStore;

public class PrestaINFOService extends AsyncTask<Integer, Void, InfoStore> {
	private final static String PRESTA_INFO_KEY = "Z7MTLNZW96SEXB2LHVBQWKAM3UU41YIM";
	private final static String PRESTA_INFO_URL_STORE = "http://cheri-paris.com/v1/api/stores?display=[hours,name,address1,postcode,city,phone,email]&filter[id]=";
	//private final static String PRESTA_POSTALCODE_FIELD = "postcode";
	
	private ShopInfoActivity _myView;
	
	private DefaultHttpClient client;
	//http://cheri-paris.com/v1/api/stores?filter[postcode]=64000
	//http://cheri-paris.com/v1/api/stores?display=full&filter[postcode]=64000
	//http://cheri-paris.com/v1/api/stores?display=[id,name,address1]&filter[city]=Pau
	
	public PrestaINFOService(ShopInfoActivity view){
		super();
		this.client = new DefaultHttpClient();
		this.client.getCredentialsProvider().setCredentials(
				new AuthScope(AuthScope.ANY_HOST, AuthScope.ANY_PORT),
				new UsernamePasswordCredentials(PRESTA_INFO_KEY, null));
		this._myView = view;
	}
	
	@Override
	protected InfoStore doInBackground(Integer... id) {
		String url = new String(PRESTA_INFO_URL_STORE);
		url += id[0].toString();
		
		HttpGet cible = new HttpGet(url);
		InfoStore is = new InfoStore(null, null, null, null, null, null, null);
		try {
			HttpResponse response = this.client.execute(cible);  //essayes d'atteindre la cible
			
			if (response.getStatusLine().getStatusCode()==200) {

				HttpEntity responseEntity = response.getEntity();
				
				DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		        DocumentBuilder builder = factory.newDocumentBuilder();
		        Document answer = builder.parse(responseEntity.getContent());

		        Element current = (Element)answer.getElementsByTagName("store").item(0);

		        NodeList store = current.getElementsByTagName("hours");
		        Element hours = (Element)store.item(0);
		        store = current.getElementsByTagName("name");
		        Element name = (Element)store.item(0);
		        store = current.getElementsByTagName("address1");
		        Element address = (Element)store.item(0);
		        store = current.getElementsByTagName("postcode");
		        Element postcode = (Element)store.item(0);
		        store = current.getElementsByTagName("city");
		        Element city = (Element)store.item(0);
		        store = current.getElementsByTagName("phone");
		        Element phone = (Element)store.item(0);
		        store = current.getElementsByTagName("email");
		        Element mail = (Element)store.item(0);
		        
		        if(name.hasChildNodes())
		        	is.setName(name.getFirstChild().getNodeValue());
		        if(hours.hasChildNodes())
		        	is.setHours(hours.getFirstChild().getNodeValue()); 
		        if(address.hasChildNodes())
		        	is.setAddress(address.getFirstChild().getNodeValue());
		        if(postcode.hasChildNodes())
		        	is.setPostcode(postcode.getFirstChild().getNodeValue());
		        if(city.hasChildNodes())
		        	is.setCity(city.getFirstChild().getNodeValue());
		        if(phone.hasChildNodes())
		        	is.setPhone(phone.getFirstChild().getNodeValue());
		        if(mail.hasChildNodes())
		        	is.setMail(mail.getFirstChild().getNodeValue());
		        
		        Log.i("CHERI", current.toString());
		        	
				
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
		return is;
	}

	@Override
	protected void onPostExecute(InfoStore result) {
		this._myView.setInfos(result);
	}

}
