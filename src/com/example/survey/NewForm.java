package com.example.survey;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
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
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout.LayoutParams;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.TextView;
import android.widget.Toast;

import com.markupartist.android.widget.ActionBar;

public class NewForm extends Activity implements OnClickListener {
	Boolean isInternetPresent = false, photo_taken = false;
	ConnectionDetector cd;
	InputStream is;
	ImageButton ib;
	Button Update;

	ImageView iv;
	Intent i;
	final static int cameraData = 0;
	Bitmap bmp;
	ActionBar actionBar;
	private RadioGroup radioComplainGroup, radioComplainGroup0;
	private RadioButton radioComplainButton, radioComplainButton1;
	EditText others, Val;
	Updator updates;
	GPSTracker gps;
	String CiDateTime;
	String sub_type = "1";
	String Long = "";
	String Lat = "";
	SharedPreferences someData;
	public static String filename = "MySharedString";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.new_form_layout);
		ActionBar actionBar = (ActionBar) findViewById(R.id.actionbar);

		actionBar.setTitle("			Sewage");

		actionBar.setHomeLogo(R.drawable.sewage);
		actionBar.setDisplayHomeAsUpEnabled(true);
		initialize();
		InputStream is = getResources().openRawResource(R.drawable.ic_launcher);
		bmp = BitmapFactory.decodeStream(is);
		addListenerOnButton();
		someData = getSharedPreferences(filename, 0);
	}

	public void addListenerOnButton() {

		radioComplainGroup = (RadioGroup) findViewById(R.id.RGPipeProblem);
		radioComplainGroup0 = (RadioGroup) findViewById(R.id.RGPipeline);
		radioComplainGroup
				.setOnCheckedChangeListener(new OnCheckedChangeListener() {
					@Override
					public void onCheckedChanged(RadioGroup group, int checkedId) {
						switch (checkedId) {
						case R.id.radio0:
							// TODO Something
							others.setEnabled(false);
							others.clearFocus();
							InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
							imm.hideSoftInputFromWindow(
									others.getWindowToken(), 0);
							others.setText("");
							others.setHint("If others, then fill this");
							sub_type = "1";
							break;
						case R.id.radio1:
							// TODO Something
							others.setEnabled(false);
							others.clearFocus();
							InputMethodManager imm1 = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
							imm1.hideSoftInputFromWindow(
									others.getWindowToken(), 0);
							others.setText("");
							others.setHint("If others, then fill this");
							sub_type = "2";
							break;
						case R.id.radio2:
							// TODO Something
							others.setEnabled(false);
							others.clearFocus();
							InputMethodManager imm2 = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
							imm2.hideSoftInputFromWindow(
									others.getWindowToken(), 0);
							others.setText("");
							others.setHint("If others, then fill this");
							sub_type = "3";
							break;
						case R.id.radio3:
							// TODO Something
							others.setEnabled(false);
							others.clearFocus();
							InputMethodManager imm4 = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
							imm4.hideSoftInputFromWindow(
									others.getWindowToken(), 0);
							others.setText("");
							others.setHint("If others, then fill this");
							sub_type = "4";
							break;
						case R.id.radio4:
							// TODO Something
							others.setEnabled(true);
							others.clearFocus();
							others.requestFocus();
							InputMethodManager imm5 = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
							imm5.toggleSoftInput(0,
									InputMethodManager.SHOW_IMPLICIT);
							sub_type = "5";
							break;

						}
					}
				});

	}

	private void initialize() {
		gps = new GPSTracker(NewForm.this);

		// TODO Auto-generated method stub
		cd = new ConnectionDetector(getApplicationContext());
		others = (EditText) findViewById(R.id.editText1);
		Val = (EditText) findViewById(R.id.et_val);
		others.setEnabled(false);
		others.clearFocus();
		// others.setFocusable(false);
		iv = (ImageView) findViewById(R.id.imageView1);
		iv.setOnClickListener(this);
		ib = (ImageButton) findViewById(R.id.ibTakePic);
		Update = (Button) findViewById(R.id.b_update);
		Update.setEnabled(false);
		Update.setOnClickListener(this);
		ib.setOnClickListener(this);
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
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		if (resultCode == RESULT_OK) {
			Bundle extras = data.getExtras();
			bmp = (Bitmap) extras.get("data");
			iv.setImageBitmap(bmp);
			Update.setEnabled(true);
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
				Update.setBackgroundResource(R.drawable.custom_update_button);
			} else {
				int selectedpipeline = radioComplainGroup0
						.getCheckedRadioButtonId();
				radioComplainButton = (RadioButton) findViewById(selectedpipeline);

				int selectedId = radioComplainGroup.getCheckedRadioButtonId();
				radioComplainButton = (RadioButton) findViewById(selectedId);
				DataBaseHandler db = new DataBaseHandler(this);
				ByteArrayOutputStream stream = new ByteArrayOutputStream();
				bmp.compress(Bitmap.CompressFormat.JPEG, 100, stream);
				byte imageInByte[] = stream.toByteArray();
				if (!radioComplainButton.getText().toString().equals("Others"))
					db.addComplain(new Complain("1", sub_type,
							radioComplainButton.getText().toString(), Val
									.getText().toString(), Lat, Long,
							CiDateTime,updates.status, imageInByte));
				else
					db.addComplain(new Complain("1", others.getText()
							.toString(), radioComplainButton.getText()
							.toString(), Val.getText().toString(), Lat, Long,
							CiDateTime, updates.status,imageInByte));
				Toast.makeText(getApplicationContext(), "Saved",
						Toast.LENGTH_SHORT).show();
				Update.setEnabled(false);
				Update.setBackgroundResource(R.drawable.custom_save_button);
			}
			break;
		case R.id.ibTakePic:
			i = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
			startActivityForResult(i, cameraData);
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

		}
	}

	private class LongOperation extends AsyncTask<String, Void, Void> {

		// Required initialization
		String mess = "";

		private ProgressDialog Dialog = new ProgressDialog(NewForm.this);

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

			// get selected radio button from radioGroup
			int selectedId = radioComplainGroup.getCheckedRadioButtonId();
			int selectedpipeline = radioComplainGroup0
					.getCheckedRadioButtonId();

			// find the radiobutton by returned id
			radioComplainButton = (RadioButton) findViewById(selectedId);

			Bitmap bitmapOrg = bmp;

			ByteArrayOutputStream bao = new ByteArrayOutputStream();

			bitmapOrg.compress(Bitmap.CompressFormat.JPEG, 100, bao);

			byte[] ba = bao.toByteArray();

			String ba1 = Base64.encodeBytes(ba);

			ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
			nameValuePairs.add(new BasicNameValuePair("image", ba1));
			nameValuePairs.add(new BasicNameValuePair("lng", Long));
			nameValuePairs.add(new BasicNameValuePair("lat", Lat));
			nameValuePairs
					.add(new BasicNameValuePair("username", updates.name));
			nameValuePairs.add(new BasicNameValuePair("type", "1"));
			nameValuePairs.add(new BasicNameValuePair("role", updates.status));
			String imagename = "";
			if (!radioComplainButton.getText().toString().equals("Others")) {
				nameValuePairs
						.add(new BasicNameValuePair("sub_type", sub_type));
				imagename = sub_type + "_" + CiDateTime + ".jpg";
			} else {
				nameValuePairs.add(new BasicNameValuePair("sub_type", others
						.getText().toString()));
				imagename = others.getText().toString() + "_" + CiDateTime
						+ ".jpg";
			}
			nameValuePairs.add(new BasicNameValuePair("val", Val.getText()
					.toString()));
			nameValuePairs.add(new BasicNameValuePair("image_name", imagename));
			radioComplainButton = (RadioButton) findViewById(selectedpipeline);
			nameValuePairs.add(new BasicNameValuePair("sw_pipe_type",
					radioComplainButton.getText().toString()));
			try {
				HttpClient httpclient = new DefaultHttpClient();
				HttpPost httppost = new HttpPost(
						"http://"
								+ someData.getString("ip", "182.180.121.20") + ":"
								+ someData.getString("port", "81")
								+ "/~ictinnov/spatialcms/php/ReceivedComplaintsData.php");

				httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));

				ResponseHandler<String> responseHandler = new BasicResponseHandler();

				String res = httpclient.execute(httppost, responseHandler);
				if (res.equals("200"))
					mess = "Succesfully uploaded....";
				else if (res.equals("400"))
					mess = "Not proper GPS location";
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
