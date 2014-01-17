package com.cheriparis.pojos;

public class InfoStore {
	private String _name;
	private String _hours;
	private String _address;
	private String _postcode;
	private String _city;
	private String _phone;
	private String _mail;
	
	public InfoStore(String name, String hours, String address,
			String postcode, String city, String phone, String mail) {
		this._name = name;
		this._hours = hours;
		this._address = address;
		this._postcode = postcode;
		this._city = city;
		this._phone = phone;
		this._mail = mail;
	}

	public String getName() {
		return _name;
	}

	public void setName(String name) {
		this._name = name;
	}

	public String getHours() {
		return _hours;
	}

	public void setHours(String hours) {
		this._hours = hours;
	}

	public String getAddress() {
		return _address;
	}

	public void setAddress(String address) {
		this._address = address;
	}

	public String getPostcode() {
		return _postcode;
	}

	public void setPostcode(String postcode) {
		this._postcode = postcode;
	}

	public String getCity() {
		return _city;
	}

	public void setCity(String city) {
		this._city = city;
	}

	public String getPhone() {
		return _phone;
	}

	public void setPhone(String phone) {
		this._phone = phone;
	}

	public String getMail() {
		return _mail;
	}

	public void setMail(String mail) {
		this._mail = mail;
	}
}
