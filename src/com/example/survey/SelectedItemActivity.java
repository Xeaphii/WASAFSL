package com.example.survey;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.markupartist.android.widget.ActionBar;

public class SelectedItemActivity extends Activity implements OnClickListener {
	ConnectionDetector cd;
	InputStream is;
	boolean isInternetPresent = false;
	Updator updates;

	ArrayList<Complain> imageArry = new ArrayList<Complain>();
	ContactImageAdapter adapter;
	GPSTracker gps;
	ListView dataList;
	byte[] comp = null;
	Button Upload, Delete;
	String CiDateTime;
	String Long = "";
	String Lat = "";
	int size = 0;
	SharedPreferences someData;
	public static String filename = "MySharedString";

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		for (int i = 0; i < comp.length; i++) {

			comp[i] = 0;
		}
		Delete.setText("Delete(0)");
		Delete.setEnabled(false);
		Upload.setText("Upload(0)");
		Upload.setEnabled(false);
		imageArry.clear();
		DataBaseHandler db = new DataBaseHandler(this);

		List<Complain> complain = db.getAllComplains();

		for (Complain cn : complain) {
			// // add contacts data in arrayList
			imageArry.add(cn);
			//
		}
		size = complain.size();
		adapter = new ContactImageAdapter(this,
				R.layout.selected_items_layouts, imageArry);
		dataList = (ListView) findViewById(R.id.list);
		dataList.setAdapter(adapter);

	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.select_saved_items);
		ActionBar actionBar = (ActionBar) findViewById(R.id.actionbar);

		actionBar.setTitle("			Saved Records");

		actionBar.setHomeLogo(R.drawable.fileedit);
		actionBar.setDisplayHomeAsUpEnabled(true);
		cd = new ConnectionDetector(getApplicationContext());
		DataBaseHandler db = new DataBaseHandler(this);

		List<Complain> complain = db.getAllComplains();
		someData = getSharedPreferences(filename, 0);
		for (Complain cn : complain) {
			// // add contacts data in arrayList
			imageArry.add(cn);
			//
		}

		adapter = new ContactImageAdapter(this,
				R.layout.selected_items_layouts, imageArry);
		dataList = (ListView) findViewById(R.id.list);
		dataList.setAdapter(adapter);
		comp = new byte[complain.size()];
		for (int i = 0; i < comp.length; i++) {
			comp[i] = 0;
		}
		size = complain.size();
		intialize();
		Delete.setText("Delete(0)");
		Delete.setEnabled(false);
		Upload.setText("Upload(0)");
		Upload.setEnabled(false);

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater menuInflater = getMenuInflater();
		menuInflater.inflate(R.layout.selecteditemmenu, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		ViewGroup row;
		switch (item.getItemId()) {
		case R.id.menu_select:

			for (int i = 0; i < size; i++) {
				comp[i] = 1;
				row = (ViewGroup) dataList.getChildAt(i);
				CheckBox check = (CheckBox) row.findViewById(R.id.checkBox1);
				check.setChecked(true);
			}
			Delete.setEnabled(true);
			Upload.setEnabled(true);
			Delete.setText("Delete(" + Integer.toString(size) + ")");
			Upload.setText("Upload(" + Integer.toString(size) + ")");

			return true;

		case R.id.menu_unselect:

			for (int i = 0; i < size; i++) {
				comp[i] = 0;

				row = (ViewGroup) dataList.getChildAt(i);
				CheckBox check = (CheckBox) row.findViewById(R.id.checkBox1);
				check.setChecked(false);

			}
			Delete.setEnabled(false);
			Upload.setEnabled(false);
			Delete.setText("Delete(0)");
			Upload.setText("Upload(0)");
			return true;

		default:
			return super.onOptionsItemSelected(item);
		}
	}

	private void intialize() {
		// TODO Auto-generated method stub
		gps = new GPSTracker(SelectedItemActivity.this);
		if (gps.canGetLocation()) {
			double latitude = gps.getLatitude();
			double longitude = gps.getLongitude();
			Long = new Double(longitude).toString();
			Lat = new Double(latitude).toString();
		} else {
			gps.showSettingsAlert();
		}
		Delete = (Button) findViewById(R.id.bDelete);
		Upload = (Button) findViewById(R.id.bUpload);
		Delete.setOnClickListener(this);
		Upload.setOnClickListener(this);

	}

	public class ContactImageAdapter extends ArrayAdapter<Complain> {
		Context context;
		int layoutResourceId;
		ArrayList<Complain> data = new ArrayList<Complain>();

		public ContactImageAdapter(Context context, int layoutResourceId,
				ArrayList<Complain> data) {
			super(context, layoutResourceId, data);
			this.layoutResourceId = layoutResourceId;
			this.context = context;
			this.data = data;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			View row = convertView;
			final int posit = position;
			ImageHolder holder = null;
			if (convertView == null) {
				LayoutInflater inflater = ((Activity) context)
						.getLayoutInflater();
				row = inflater.inflate(layoutResourceId, parent, false);
				holder = new ImageHolder();
				holder.txtTitle = (TextView) row.findViewById(R.id.txtTitle);
				holder.sub_type = (TextView) row.findViewById(R.id.txtTitle1);
				holder.imgIcon = (ImageView) row.findViewById(R.id.imgIcon);
				(row.findViewById(R.id.checkBox1))
						.setOnClickListener(new OnClickListener() {
							public void onClick(View arg0) {
								int total = 0;
								if (((CheckBox) arg0).isChecked()) {
									comp[posit] = 1;

								} else
									comp[posit] = 0;
								for (int i = 0; i < comp.length; i++) {
									if (comp[i] == 1) {
										total++;
									}
								}
								Delete.setEnabled(true);
								Upload.setEnabled(true);
								if (total == 0) {
									Delete.setEnabled(false);
									Upload.setEnabled(false);
								}
								Upload.setText("Upload("
										+ Integer.toString(total) + ")");
								Delete.setText("Delete("
										+ Integer.toString(total) + ")");

							}
						});
				row.setTag(holder);
			} else {
				holder = (ImageHolder) row.getTag();
			}
			Complain picture = data.get(position);

			String type = "";
			if (picture._type.equals("1")) {
				type = "Sewerage";
			} else if (picture._type.equals("2")) {
				type = "Water Problem";

			} else if (picture._type.equals("3")) {
				type = "Drainage Channels";

			} else if (picture._type.equals("4")) {
				type = "Road Side Drains";

			} else if (picture._type.equals("5")) {
				type = "WWM";

			} else {
				type = picture._type;
			}
			String subtype = "";

			// subtype mux
			if (picture._sub_type.equals("1")) {
				subtype = "Sewer Overflow / Blockage of Sewer";
			} else if (picture._sub_type.equals("2")) {
				subtype = "Missing Manhole Cover";
			} else if (picture._sub_type.equals("3")) {
				subtype = "Broken Manhole Cover / Slab";
			} else if (picture._sub_type.equals("4")) {
				subtype = "Damaged / Settled Sewer Line";
			} else if (picture._sub_type.equals("5")) {
				subtype = "Others";
			} else if (picture._sub_type.equals("6")) {
				subtype = "Water Leakage";
			} else if (picture._sub_type.equals("7")) {
				subtype = "Contamination";
			} else if (picture._sub_type.equals("8")) {
				subtype = "Brusting of pipe";
			} else if (picture._sub_type.equals("9")) {
				subtype = "Water Shortage / low pressure";
			} else if (picture._sub_type.equals("10")) {
				subtype = "Others";
			} else if (picture._sub_type.equals("11")) {
				subtype = "Others";
			} else if (picture._sub_type.equals("12")) {
				subtype = "Breaching of Channel";
			} else if (picture._sub_type.equals("13")) {
				subtype = "Damaged / Broken Old Wall / Slab";
			} else if (picture._sub_type.equals("14")) {
				subtype = "Overflow";
			} else if (picture._sub_type.equals("15")) {
				subtype = "Removal of solid waste / Garbage / Silt";
			} else if (picture._sub_type.equals("16")) {
				subtype = "Blockage of Covered / Opened portion";
			} else if (picture._sub_type.equals("17")) {
				subtype = "Others";
			} else if (picture._sub_type.equals("18")) {
				subtype = "Broken Slab";
			} else if (picture._sub_type.equals("19")) {
				subtype = "Removal of Solid Waste / Garbage";
			} else if (picture._sub_type.equals("20")) {
				subtype = "Overflow";
			} else if (picture._sub_type.equals("21")) {
				subtype = "Connected wiht WASA / or Not";
			} else if (picture._sub_type.equals("22")) {
				subtype = "Blockage of Covered / Open poriton";
			} else if (picture._sub_type.equals("23")) {
				subtype = "Others";
			} else if (picture._sub_type.equals("24")) {
				subtype = "Power Failure";
			} else if (picture._sub_type.equals("25")) {
				subtype = "Internal Fault/Screening chamber problem";
			} else if (picture._sub_type.equals("26")) {
				subtype = "Internal Fault/Mechanical";
			} else if (picture._sub_type.equals("27")) {
				subtype = "Internal Fault/Electrical";
			} else if (picture._sub_type.equals("28")) {
				subtype = "Others";
			} else {
				subtype = picture._sub_type;
			}
			holder.txtTitle.setText(type);
			holder.sub_type.setText(subtype);
			// convert byte to bitmap take from contact class
			byte[] outImage = picture._image;
			ByteArrayInputStream imageStream = new ByteArrayInputStream(
					outImage);
			Bitmap theImage = BitmapFactory.decodeStream(imageStream);
			holder.imgIcon.setImageBitmap(theImage);
			return row;
		}

		class ImageHolder {
			ImageView imgIcon;
			TextView txtTitle;
			TextView sub_type;
		}
	}

	public static Intent createIntent(Context context) {
		Intent i = new Intent(context, SavedForms.class);
		i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		return i;
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.bDelete:
			AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
					this);

			// set title
			alertDialogBuilder.setTitle("Delete Selected Items");

			// set dialog message
			alertDialogBuilder
					.setMessage("Are you sure you want to delete these items?")
					.setCancelable(false)
					.setPositiveButton("Yes",
							new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog,
										int id) {
									int counter = 0;
									int len = comp.length;
									for (int i = 0; i < len; i++) {
										if (comp[i] == 1) {
											counter++;
											DataBaseHandler db = new DataBaseHandler(
													getBaseContext());
											db.deleteComplain(new Complain(
													imageArry.get(i).getID(),
													imageArry.get(i)._type,
													imageArry.get(i)._sub_type,
													imageArry.get(i)._lat,
													imageArry.get(i)._lng, "",
													"", imageArry.get(i)._date,
													"", imageArry.get(i)
															.getImage()));
										}
									}
									SelectedItemActivity.this.onResume();
									if (counter != 0)
										Toast.makeText(getBaseContext(),
												"Record Deleted...",
												Toast.LENGTH_SHORT).show();
								}
							})
					.setNegativeButton("No",
							new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog,
										int id) {
									// if this button is clicked, just close
									// the dialog box and do nothing
									dialog.cancel();
								}
							});

			// create alert dialog
			AlertDialog alertDialog = alertDialogBuilder.create();

			// show it
			alertDialog.show();
			break;
		case R.id.bUpload:
			AlertDialog.Builder alertDialogBuilder1 = new AlertDialog.Builder(
					this);

			// set title
			alertDialogBuilder1.setTitle("Upload Selected Items");

			// set dialog message
			alertDialogBuilder1
					.setMessage("Are you sure you want to upload these items?")
					.setCancelable(false)
					.setPositiveButton("Yes",
							new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog,
										int id) {
									if (cd.isConnectingToInternet()) {
										new LongOperation().execute("");
									} else {
										Toast.makeText(getApplicationContext(),
												"Sorry no internet present",
												Toast.LENGTH_SHORT).show();

									}

								}
							})
					.setNegativeButton("No",
							new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog,
										int id) {
									// if this button is clicked, just close
									// the dialog box and do nothing
									dialog.cancel();
								}
							});

			// create alert dialog
			AlertDialog alertDialog1 = alertDialogBuilder1.create();

			// show it
			alertDialog1.show();
			break;
		}

	}

	private class LongOperation extends AsyncTask<String, Void, Void> {

		// Required initialization
		String mess = "";

		private ProgressDialog Dialog = new ProgressDialog(
				SelectedItemActivity.this);

		protected void onPreExecute() {
			// NOTE: You can call UI Element here.

			// Start Progress Dialog (Message)

			Dialog.setMessage("Please wait..");
			Dialog.show();

		}

		// Call after onPreExecute method
		protected Void doInBackground(String... urls) {

			int len = comp.length;
			int counter = 0;
			for (int i = 0; i < len; i++) {
				if (comp[i] == 1) {
					counter++;
					if (!(imageArry.get(i)._type.equals("Rectified"))) {
						ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
						nameValuePairs
								.add(new BasicNameValuePair("image", Base64
										.encodeBytes(imageArry.get(i)
												.getImage())));
						nameValuePairs.add(new BasicNameValuePair("lng",
								imageArry.get(i)._longititude));
						nameValuePairs.add(new BasicNameValuePair("lat",
								imageArry.get(i)._latitude));
						String imagename = imageArry.get(i)._sub_type + "_"
								+ imageArry.get(i)._date + ".jpg";
						nameValuePairs.add(new BasicNameValuePair("image_name",
								imagename));
						nameValuePairs.add(new BasicNameValuePair("username",
								updates.name));
						nameValuePairs.add(new BasicNameValuePair("role",
								updates.status));
						nameValuePairs.add(new BasicNameValuePair("type",
								imageArry.get(i)._type));

						nameValuePairs.add(new BasicNameValuePair("sub_type",
								imageArry.get(i)._sub_type));
						if (imageArry.get(i)._type.equals("1")) {
							nameValuePairs.add(new BasicNameValuePair(
									"sw_pipe_type", imageArry.get(i)._lat));
							nameValuePairs.add(new BasicNameValuePair("val",
									imageArry.get(i)._lng));

						} else if (imageArry.get(i)._type.equals("2"))
							nameValuePairs.add(new BasicNameValuePair("val",
									imageArry.get(i)._lat));

						try {
							HttpClient httpclient = new DefaultHttpClient();
							HttpPost httppost = new HttpPost(
									
									
									"http://"
											+ someData.getString("ip", "184.172.185.189")
											+ ":"
											+ someData.getString("port", "80")
											+ "/~ictinnov/spatialcms/php/ReceivedComplaintsData.php"
									);
							httppost.setEntity(new UrlEncodedFormEntity(
									nameValuePairs));
							HttpResponse response = httpclient
									.execute(httppost);
							HttpEntity entity = response.getEntity();
							is = (InputStream) entity.getContent();
							mess = "Succesfully uploaded....";
							DataBaseHandler db = new DataBaseHandler(
									getBaseContext());
							db.deleteComplain(new Complain(imageArry.get(i)
									.getID(), imageArry.get(i)._type, imageArry
									.get(i)._sub_type, imageArry.get(i)._lat,
									imageArry.get(i)._lng, "", "", imageArry
											.get(i)._date, "", imageArry.get(i)
											.getImage()));

						} catch (Exception e) {
							mess = "Error in uploading....";
						}
					} else {
						ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
						nameValuePairs
								.add(new BasicNameValuePair("image", Base64
										.encodeBytes(imageArry.get(i)
												.getImage())));

						String imagename = imageArry.get(i)._sub_type + "_"
								+ imageArry.get(i)._date + ".jpg";
						nameValuePairs.add(new BasicNameValuePair("image_name",
								imagename));

						nameValuePairs.add(new BasicNameValuePair("role",
								updates.status));

						nameValuePairs.add(new BasicNameValuePair("id",
								imageArry.get(i)._lat));

						try {
							HttpClient httpclient = new DefaultHttpClient();
							HttpPost httppost = new HttpPost(
									
									
									
									"http://"
											+ someData.getString("ip", "184.172.185.189")
											+ ":"
											+ someData.getString("port", "80")
											+ "/~ictinnov/spatialcms/php/ReceivedComplaintsData.php"
									);
							httppost.setEntity(new UrlEncodedFormEntity(
									nameValuePairs));
							HttpResponse response = httpclient
									.execute(httppost);
							HttpEntity entity = response.getEntity();
							is = (InputStream) entity.getContent();
							mess = "Succesfully uploaded....";
							DataBaseHandler db = new DataBaseHandler(
									getBaseContext());
							db.deleteComplain(new Complain(imageArry.get(i)
									.getID(), imageArry.get(i)._type, imageArry
									.get(i)._sub_type, imageArry.get(i)._lat,
									imageArry.get(i)._lng, "", "", imageArry
											.get(i)._date, "", imageArry.get(i)
											.getImage()));

						} catch (Exception e) {
							mess = "Error in uploading....";
						}
					}

				}
			}
			return null;
		}

		protected void onPostExecute(Void unused) {

			Dialog.dismiss();
			Toast.makeText(getApplicationContext(), mess, Toast.LENGTH_LONG)
					.show();
			SelectedItemActivity.this.onResume();

		}
	}
}