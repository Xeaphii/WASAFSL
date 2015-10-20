package com.example.survey;

public class Complain {

	// private variables
	int _id;
	String _type, _sub_type, _lat, _lng, _date, _latitude, _longititude, _role;
	byte[] _image;

	// Empty constructor
	public Complain() {

	}

	// constructor
	public Complain(int keyId, String type, String sub_type, String lat,
			String lng, String Latitude, String Longititude, String date,
			String Role, byte[] image) {
		this._id = keyId;
		this._type = type;
		this._sub_type = sub_type;
		this._lat = lat;
		this._lng = lng;
		this._image = image;
		this._date = date;
		this._latitude = Latitude;
		this._longititude = Longititude;
		this._role = Role;

	}

	// // constructor
	public Complain(String type, String sub_type, String lat, String lng,
			String Latitude, String Longititude, String date, String Role,
			byte[] image) {
		this._type = type;
		this._sub_type = sub_type;
		this._lat = lat;
		this._lng = lng;
		this._image = image;
		this._date = date;
		this._latitude = Latitude;
		this._longititude = Longititude;
		this._role = Role;

	}

	// getting ID
	public int getID() {
		return this._id;
	}

	// setting id
	public void setID(int keyId) {
		this._id = keyId;
	}

	// getting name
	public String getName() {
		return this._type;
	}

	// setting name
	public void setName(String name) {
		this._type = name;
	}

	// getting phone number
	public byte[] getImage() {
		return this._image;
	}

	// setting phone number
	public void setImage(byte[] image) {
		this._image = image;
	}
}