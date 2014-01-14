package com.cheriparis.pojos;

public class Store {
	private int _id;
	private String _name;
	private double _latitude;
	private double _longitude;
	
	public Store(int id, String name, double latitude, double longitude){
		this._id = id;
		this._name = name;
		this._latitude = latitude;
		this._longitude = longitude;
	}

	public int getId() {
		return _id;
	}

	public void setId(int id) {
		this._id = id;
	}

	public String getName() {
		return _name;
	}

	public void setName(String name) {
		this._name = name;
	}

	public double getLatitude() {
		return _latitude;
	}

	public void setLatitude(double latitude) {
		this._latitude = latitude;
	}

	public double getLongitude() {
		return _longitude;
	}

	public void setLongitude(double longitude) {
		this._longitude = longitude;
	}
}
