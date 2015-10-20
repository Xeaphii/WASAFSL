package com.example.survey;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Calendar;

import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;
import android.widget.Toast;

import com.markupartist.android.widget.ActionBar;

public class RectifiedComplaint extends Activity implements OnClickListener {
	Boolean isInternetPresent = false, photo_taken = false;
	ConnectionDetector cd;
	public Button Update;
	ImageView iv, complainview;
	Intent i;
	final static int cameraData = 0;
	Bitmap bmp;
	ActionBar actionBar;
	GPSTracker gps;
	ImageButton ib;
	Uri uri;
	String CiDateTime;
	String ID = "";
	String Long = "";
	String Lat = "";
	boolean isIdValid = false;
	TextView tv_id, tv_type, tv_sub_type;
	SharedPreferences someData;
	public static String filename = "MySharedString";
	String id = "", type = "", sub_type = "", subtypeid = "";
	Updator updates;
	String url = "";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.rectified_complaint_view);

		ActionBar actionBar = (ActionBar) findViewById(R.id.actionbar);

		actionBar.setTitle("			Submit Rectified Complain");

		actionBar.setHomeLogo(R.drawable.fileedit);
		actionBar.setDisplayHomeAsUpEnabled(true);
		initialize();
		showdialogue("Retreive Complain");
		someData = getSharedPreferences(filename, 0);
	}

	private void showdialogue(String title) {
		// TODO Auto-generated method stub
		LayoutInflater factory = LayoutInflater.from(this);
		final View textEntryView = factory.inflate(R.layout.get_comp, null);

		final AlertDialog.Builder alert = new AlertDialog.Builder(this);
		alert.setIcon(R.drawable.fileedit)
				.setTitle(title + " :")
				.setView(textEntryView)
				.setPositiveButton("Get Complain Data",
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog,
									int whichButton) {
								if (cd.isConnectingToInternet()) {
									new LongOperation1().execute("");

								} else {
									Toast.makeText(getApplicationContext(),
											"No internet present",
											Toast.LENGTH_SHORT).show();
									finish();
								}

							}
						})
				.setNegativeButton("Cancel",
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog,
									int whichButton) {
								finish();
							}
						});
		alert.show();
	}

	private void initialize() {
		tv_id = (TextView) findViewById(R.id.tv_comp_id);
		tv_type = (TextView) findViewById(R.id.tv_compl_type);
		tv_sub_type = (TextView) findViewById(R.id.tv_compl_sub_type);
		gps = new GPSTracker(RectifiedComplaint.this);
		if (gps.canGetLocation()) {
			double latitude = gps.getLatitude();
			double longitude = gps.getLongitude();
			Long = new Double(longitude).toString();
			Lat = new Double(latitude).toString();
		} else {
			gps.showSettingsAlert();
		}

		// TODO Auto-generated method stub
		cd = new ConnectionDetector(getApplicationContext());

		// others.setFocusable(false);
		iv = (ImageView) findViewById(R.id.imageView1);
		complainview = (ImageView) findViewById(R.id.imageView2);
		ib = (ImageButton) findViewById(R.id.ibTakePic);
		ib.setOnClickListener(this);
		iv.setOnClickListener(this);
		Update = (Button) findViewById(R.id.b_update);
		Update.setEnabled(false);
		Update.setOnClickListener(this);
		isInternetPresent = cd.isConnectingToInternet();
		Update.setBackgroundResource(R.drawable.custom_update_button);

		try {
			File filePath = getApplicationContext().getFileStreamPath(
					"complain.png");
			FileInputStream fi = new FileInputStream(filePath);
			bmp = BitmapFactory.decodeStream(fi);
			iv.setImageBitmap(bmp);
		} catch (Exception ex) {

		}

	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);

		if (requestCode == cameraData)
			if (resultCode == RESULT_OK) {
				Bundle extras = data.getExtras();
				bmp = (Bitmap) extras.get("data");
				iv.setImageBitmap(bmp);
				Update.setEnabled(true);
				uri = data.getData();
				photo_taken = true;
				Calendar ci = Calendar.getInstance();

				CiDateTime = ci.get(Calendar.YEAR) + "_"
						+ (ci.get(Calendar.MONTH) + 1) + "_"
						+ ci.get(Calendar.DAY_OF_MONTH) + "_"
						+ ci.get(Calendar.HOUR) + "_" + ci.get(Calendar.MINUTE)
						+ "_" + ci.get(Calendar.SECOND);
				if (cd.isConnectingToInternet()) {
					Update.setBackgroundResource(R.drawable.custom_update_button);
				} else {
					Update.setBackgroundResource(R.drawable.custom_save_button);
				}

			}

	}

	public void onClick(View v) {
		ImageView image;
		// TODO Auto-generated method stub
		switch (v.getId()) {

		case R.id.b_update:
			if (isIdValid) {
				if (cd.isConnectingToInternet()) {
					if (gps.canGetLocation()) {
						double latitude = gps.getLatitude();
						double longitude = gps.getLongitude();
						Long = new Double(longitude).toString();
						Lat = new Double(latitude).toString();
					} else {
						gps.showSettingsAlert();
					}

					new LongOperation().execute("");
				} else {

					DataBaseHandler db = new DataBaseHandler(this);
					ByteArrayOutputStream stream = new ByteArrayOutputStream();
					bmp.compress(Bitmap.CompressFormat.JPEG, 100, stream);
					byte imageInByte[] = stream.toByteArray();

					db.addComplain(new Complain("Rectified", subtypeid, id, "",
							"", "", CiDateTime, updates.status, imageInByte));

					Toast.makeText(getApplicationContext(),
							"No internet connection", Toast.LENGTH_SHORT)
							.show();
					Update.setEnabled(false);
					Update.setBackgroundResource(R.drawable.custom_save_button);
				}
			} else {
				showdialogue("No Complain Found.Retreive Complain First");
			}
			break;
		case R.id.imageView1:
			if (photo_taken) {
				Dialog dialog = new Dialog(this,
						android.R.style.Theme_Translucent);
				dialog.setContentView(R.layout.custom_dialog);
				dialog.getWindow().getAttributes().width = LayoutParams.FILL_PARENT;
				dialog.getWindow().getAttributes().height = LayoutParams.FILL_PARENT;
				dialog.setTitle("complain pic");
				image = (ImageView) dialog.findViewById(R.id.image);
				image.setScaleType(ImageView.ScaleType.FIT_XY);
				image.setImageBitmap(bmp);
				TextView text = (TextView) dialog.findViewById(R.id.TVTime);
				text.setText(CiDateTime);
				dialog.show();
			}
			break;
		case R.id.ibTakePic:
			i = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
			startActivityForResult(i, cameraData);
			break;

		}
	}

	private class LongOperation1 extends AsyncTask<String, Void, Void> {

		// Required initialization

		String mess = "";
		Bitmap temp;
		private ProgressDialog Dialog = new ProgressDialog(
				RectifiedComplaint.this);

		protected void onPreExecute() {
			// NOTE: You can call UI Element here.

			// Start Progress Dialog (Message)

			Dialog.setMessage("Please wait..");
			Dialog.show();

		}

		// Call after onPreExecute method
		protected Void doInBackground(String... urls) {

			/************ Make Post Call To Web Server ***********/
			// get selected radio button from radioGroup

			ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
			if (Lat.equals("") || Long.equals("")) {
				Lat = "0.00000";
				Long = "0.00000";
			}
			nameValuePairs.add(new BasicNameValuePair("lng", Long));
			nameValuePairs.add(new BasicNameValuePair("lat", Lat));

			try {

				HttpClient httpclient = new DefaultHttpClient();
				HttpPost httppost = new HttpPost(
						"http://"
								+ someData.getString("ip", "182.180.121.20") + ":"
								+ someData.getString("port", "81")
								+ "/~ictinnov/spatialcms/php/GetComplaintIdFromLatLng.php"

				);

				httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));

				ResponseHandler<String> responseHandler = new BasicResponseHandler();
				ID = httpclient.execute(httppost, responseHandler);

			} catch (Exception e) {
				mess = "Server Down....";

			}
			if (!mess.equals("Server Down....")) {
				if (ID.equals("400")) {
					ID = "";
					mess = "No complain found";
					isIdValid = false;
				} else {
					if (!ID.equals("")) {
						String[] words = ID.split(":");
						if (words.length >= 5) {
							id = words[0];
							type = words[1];
							sub_type = words[2];
							subtypeid = words[3];
							url = words[4] + ":" + words[5];
							mess = "Complain id is " + id;
							isIdValid = true;
							temp = downloadImage(url);
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

			if (isIdValid) {

				tv_id.setText(id);
				tv_type.setText(type);
				tv_sub_type.setText(sub_type);
				complainview.setImageBitmap(temp);
			}

		}

		private Bitmap downloadImage(String urlString) {

			int count = 0;
			Bitmap bitmap = null;

			URL url;
			InputStream inputStream = null;
			BufferedOutputStream outputStream = null;

			try {
				url = new URL(urlString);
				URLConnection connection = url.openConnection();
				int lenghtOfFile = connection.getContentLength();

				inputStream = new BufferedInputStream(url.openStream());
				ByteArrayOutputStream dataStream = new ByteArrayOutputStream();

				outputStream = new BufferedOutputStream(dataStream);

				byte data[] = new byte[512];
				long total = 0;

				while ((count = inputStream.read(data)) != -1) {
					total += count;
					/*
					 * publishing progress update on UI thread. Invokes
					 * onProgressUpdate()
					 */

					// writing data to byte array stream
					outputStream.write(data, 0, count);
				}
				outputStream.flush();

				BitmapFactory.Options bmOptions = new BitmapFactory.Options();
				bmOptions.inSampleSize = 1;

				byte[] bytes = dataStream.toByteArray();
				bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length,
						bmOptions);

			} catch (MalformedURLException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				FileUtils.close(inputStream);
				FileUtils.close(outputStream);
			}
			return bitmap;
		}
	}

	private class LongOperation extends AsyncTask<String, Void, Void> {

		// Required initialization
		String mess = "";

		private ProgressDialog Dialog = new ProgressDialog(
				RectifiedComplaint.this);

		protected void onPreExecute() {
			// NOTE: You can call UI Element here.

			// Start Progress Dialog (Message)

			Dialog.setMessage("Please wait..");
			Dialog.show();

		}

		// Call after onPreExecute method
		protected Void doInBackground(String... urls) {

			/************ Make Post Call To Web Server ***********/
			// get selected radio button from radioGroup

			Bitmap bitmapOrg = bmp;

			ByteArrayOutputStream bao = new ByteArrayOutputStream();

			bitmapOrg.compress(Bitmap.CompressFormat.JPEG, 100, bao);

			byte[] ba = bao.toByteArray();

			String ba1 = Base64.encodeBytes(ba);

			ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
			nameValuePairs.add(new BasicNameValuePair("image", ba1));
			nameValuePairs.add(new BasicNameValuePair("id", id));
			nameValuePairs.add(new BasicNameValuePair("role", updates.status));
			String imagename = subtypeid + "_" + CiDateTime + ".jpg";
			nameValuePairs.add(new BasicNameValuePair("image_name", imagename));

			try {

				HttpClient httpclient = new DefaultHttpClient();
				HttpPost httppost = new HttpPost("http://"
						+ someData.getString("ip", "182.180.121.20") + ":"
						+ someData.getString("port", "81")
						+ "/~ictinnov/spatialcms/php/UpdateComplaintsData.php"

				);

				httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));

				ResponseHandler<String> responseHandler = new BasicResponseHandler();
				String res = httpclient.execute(httppost, responseHandler);
				if (res.equals("200"))
					mess = "Succesfully uploaded....";
				else
					mess = "Not uploaded";

			} catch (Exception e) {
				mess = "Server Down....";

			}

			/*****************************************************/
			return null;
		}

		protected void onPostExecute(Void unused) {
			// NOTE: You can call UI Element here.

			// Close progress dialog
			Dialog.dismiss();
			Toast.makeText(getApplicationContext(), mess, Toast.LENGTH_LONG)
					.show();

			if (mess.equals("Succesfully uploaded...."))
				finish();

		}

	}
}