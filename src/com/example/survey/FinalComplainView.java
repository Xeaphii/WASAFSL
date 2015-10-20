package com.example.survey;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.ArrayList;

import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;
import android.widget.Toast;

import com.markupartist.android.widget.ActionBar;

public class FinalComplainView extends Activity implements OnClickListener {
	Boolean isInternetPresent = false;
	ConnectionDetector cd;
	InputStream is;
	Button Update;
	public static final int progress_bar_type = 0;
	ImageView iv;
	ActionBar actionBar;
	Updator updates;
	GPSTracker gps;
	TextView Type, SubType;
	Uri uri;
	Complain complain;
	Bitmap theImage;
	String Long = "";
	String Lat = "";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.final_complain_view);
		ActionBar actionBar = (ActionBar) findViewById(R.id.actionbar);

		actionBar.setTitle("			Complain View");

		actionBar.setHomeLogo(R.drawable.complain);
		actionBar.setDisplayHomeAsUpEnabled(true);
		findViewsById();

		Intent intent = getIntent();

		Parcelablecomplain parcelableLaptop = (Parcelablecomplain) intent
				.getParcelableExtra("complain");
		complain = parcelableLaptop.getComplain();
		setfields();
		ByteArrayInputStream imageStream = new ByteArrayInputStream(
				complain._image);
		theImage = BitmapFactory.decodeStream(imageStream);
		iv.setImageBitmap(theImage);
		cd = new ConnectionDetector(getApplicationContext());
		gps = new GPSTracker(FinalComplainView.this);
		if (gps.canGetLocation()) {
			double latitude = gps.getLatitude();
			double longitude = gps.getLongitude();
			Long = new Double(longitude).toString();
			Lat = new Double(latitude).toString();
		} else {
			gps.showSettingsAlert();
		}
	}

	private void setfields() {
		// TODO Auto-generated method stub
		Type.setText(complain._type);
		SubType.setText(complain._sub_type);
	}

	private void findViewsById() {
		// TODO Auto-generated method stub
		Type = (TextView) findViewById(R.id.TVComplainTypeText);
		SubType = (TextView) findViewById(R.id.TVComplainSubTypeText);
		iv = (ImageView) findViewById(R.id.imageView1);
		iv.setOnClickListener(this);
		Update = (Button) findViewById(R.id.b_update);
		Update.setOnClickListener(this);
	}

	@Override
	public void onClick(View arg0) {
		ImageView image;
		// TODO Auto-generated method stub
		switch (arg0.getId()) {
		case R.id.b_update:
			if (cd.isConnectingToInternet()) {
				new LongOperation().execute("");
			} else {
				Toast.makeText(getApplicationContext(),
						"Sorry no internet present", Toast.LENGTH_SHORT).show();
			}
			break;
		case R.id.imageView1:
			Dialog dialog = new Dialog(this, android.R.style.Theme_Translucent);
			dialog.setContentView(R.layout.custom_dialog);
			dialog.getWindow().getAttributes().width = LayoutParams.FILL_PARENT;
			dialog.getWindow().getAttributes().height = LayoutParams.FILL_PARENT;
			dialog.setTitle("complain pic");
			image = (ImageView) dialog.findViewById(R.id.image);
			image.setScaleType(ImageView.ScaleType.FIT_XY);
			image.setImageBitmap(theImage);
			TextView text = (TextView) dialog.findViewById(R.id.TVTime);
			text.setText(complain._date);
			dialog.show();
			break;
		}

	}

	private class LongOperation extends AsyncTask<String, Void, Void> {

		// Required initialization
		String mess = "";

		private ProgressDialog Dialog = new ProgressDialog(
				FinalComplainView.this);

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

			Bitmap bitmapOrg = theImage;
			ByteArrayOutputStream bao = new ByteArrayOutputStream();
			bitmapOrg.compress(Bitmap.CompressFormat.JPEG, 100, bao);
			byte[] ba = bao.toByteArray();
			String ba1 = Base64.encodeBytes(ba);
			String imagename = complain._sub_type + "_" + complain._date
					+ ".jpg";

			ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
			nameValuePairs.add(new BasicNameValuePair("image", ba1));
			nameValuePairs.add(new BasicNameValuePair("lng", Long));
			nameValuePairs.add(new BasicNameValuePair("lat", Lat));
			nameValuePairs.add(new BasicNameValuePair("image_name", imagename));
			nameValuePairs
					.add(new BasicNameValuePair("username", updates.name));
			nameValuePairs.add(new BasicNameValuePair("role", updates.status));
			nameValuePairs.add(new BasicNameValuePair("type", complain._type));

			nameValuePairs.add(new BasicNameValuePair("sub_type",
					complain._sub_type));
			if (complain._type.equals("1"))
				nameValuePairs.add(new BasicNameValuePair("sw_pipe_type",
						complain._lat));
			else if (complain._sub_type.equals("10"))
				nameValuePairs
						.add(new BasicNameValuePair("val", complain._lat));
			try {
				HttpClient httpclient = new DefaultHttpClient();
				HttpPost httppost = new HttpPost(
						"http://184.172.185.189/~ictinnov/spatialcms/php/ReceivedComplaintsData.php");

				httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
				ResponseHandler<String> responseHandler = new BasicResponseHandler();

				String res = httpclient.execute(httppost, responseHandler);
				if (res.equals("200"))
					mess = "Succesfully uploaded....";
				else if (res.equals("400"))
					mess = "Not proper GPS location";
				else
					mess = "Not uploaded";
				DataBaseHandler db = new DataBaseHandler(getBaseContext());
				db.deleteComplain(new Complain(complain.getID(),
						complain._type, complain._sub_type, complain._lat,
						complain._lng, complain._date, "", "", "", complain
								.getImage()));

			} catch (Exception e) {
				mess = "Error in uploading....";
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
