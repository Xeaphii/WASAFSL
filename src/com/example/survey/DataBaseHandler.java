package com.example.survey;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DataBaseHandler extends SQLiteOpenHelper {

	// All Static variables
	// Database Version
	private static final int DATABASE_VERSION = 1;
	Updator updates;

	// Database Name
	private static final String DATABASE_NAME = "imag";

	// Contacts table name
	private static final String TABLE_COMPLAIN = "COMPLAIN_ROLE";

	// Contacts Table Columns names
	private static final String KEY_ID = "id";
	private static final String KEY_TYPE = "TYPE";
	private static final String KEY_SUB_TYPE = "SUB_TYPE";
	private static final String KEY_LAT = "LAT";
	private static final String KEY_LNG = "LNG";
	private static final String KEY_LATITUDE = "LATITUDE";
	private static final String KEY_LONGITUDE = "LONGITUDE";
	private static final String KEY_DAT = "DATE";
	private static final String KEY_ROLE = "ROLE";
	private static final String KEY_IMAGE = "image";

	public DataBaseHandler(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	// Creating Tables
	@Override
	public void onCreate(SQLiteDatabase db) {
		String CREATE_CONTACTS_TABLE = "CREATE TABLE " + TABLE_COMPLAIN + "("
				+ KEY_ID + " INTEGER PRIMARY KEY ," + KEY_TYPE + " TEXT,"
				+ KEY_SUB_TYPE + " TEXT," + KEY_LAT + " TEXT," + KEY_LNG
				+ " TEXT," + KEY_LATITUDE + " TEXT," + KEY_LONGITUDE + " TEXT,"
				+ KEY_DAT + " TEXT," + KEY_ROLE + " TEXT," + KEY_IMAGE
				+ " BLOB" + ")";
		db.execSQL(CREATE_CONTACTS_TABLE);
	}

	// Upgrading database
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// Drop older table if existed
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_COMPLAIN);

		// Create tables again
		onCreate(db);
	}

	void DB_Create() {
		SQLiteDatabase db = this.getWritableDatabase();
		String CREATE_CONTACTS_TABLE = "CREATE TABLE " + TABLE_COMPLAIN + "("
				+ KEY_ID + " INTEGER PRIMARY KEY ," + KEY_TYPE + " TEXT,"
				+ KEY_SUB_TYPE + " TEXT," + KEY_LAT + " TEXT," + KEY_LNG
				+ " TEXT," + KEY_LATITUDE + " TEXT," + KEY_LONGITUDE + " TEXT,"
				+ KEY_DAT + " TEXT," + KEY_ROLE + " TEXT," + KEY_IMAGE
				+ " BLOB" + ")";

		db.execSQL(CREATE_CONTACTS_TABLE);
	}

	public// Adding new contact
	void addComplain(Complain complain) {

		SQLiteDatabase db = this.getWritableDatabase();

		// onCreate(db);
		ContentValues values = new ContentValues();
		//
		values.put(KEY_TYPE, complain._type); // Contact type
		values.put(KEY_SUB_TYPE, complain._sub_type);
		values.put(KEY_LAT, complain._lat);
		values.put(KEY_LNG, complain._lng);
		values.put(KEY_LATITUDE, complain._latitude);
		values.put(KEY_LONGITUDE, complain._longititude);
		values.put(KEY_DAT, complain._date);
		values.put(KEY_ROLE, complain._role);
		values.put(KEY_IMAGE, complain._image);

		// Contact Phone
		//
		//
		// // Inserting Row
		db.insert(TABLE_COMPLAIN, null, values);
		db.close(); // Closing database connection
	}

	// Getting All Contacts
	public List<Complain> getAllComplains() {
		List<Complain> complaintList = new ArrayList<Complain>();
		// // Select All Query
		//
		String selectQuery = "SELECT * FROM COMPLAIN_ROLE " +
				"WHERE "+KEY_ROLE+ " = '" +
						updates.status+
				"' ORDER BY DATE DESC ";
		SQLiteDatabase db = this.getWritableDatabase();

		Cursor cursor = db.rawQuery(selectQuery, null);
		// // looping through all rows and adding to list
		if (cursor.moveToFirst()) {
			do {
				Complain complain = new Complain();
				complain._id = Integer.parseInt(cursor.getString(0));
				complain._type = cursor.getString(1);
				complain._sub_type = cursor.getString(2);
				complain._lat = cursor.getString(3);
				complain._lng = cursor.getString(4);
				complain._latitude = cursor.getString(5);
				complain._longititude = cursor.getString(6);
				complain._date = cursor.getString(7);
				complain._role = cursor.getString(8);
				complain.setImage(cursor.getBlob(9));
				// Adding contact to list
				complaintList.add(complain);
			} while (cursor.moveToNext());
		}

		// // close inserting data from database
		db.close();
		// // return contact list
		return complaintList;

	}

	// Deleting single contact
	public void deleteComplain(Complain complain) {

		SQLiteDatabase db = this.getWritableDatabase();

		db.delete(TABLE_COMPLAIN, KEY_ID + " = ?",
				new String[] { String.valueOf(complain.getID()) });
		db.close();
	}

	// Getting contacts Count
	public int getComplainCount() {
		String countQuery = "SELECT * FROM " + TABLE_COMPLAIN;
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = db.rawQuery(countQuery, null);
		int len = cursor.getCount();
		cursor.close();
		// return count
		return len;
	}
}