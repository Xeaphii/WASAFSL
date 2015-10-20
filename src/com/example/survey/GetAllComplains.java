package com.example.survey;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.CalendarView.OnDateChangeListener;
import android.widget.Spinner;
import android.widget.Toast;

import com.markupartist.android.widget.ActionBar;

public class GetAllComplains extends Activity implements OnClickListener,
		OnDateChangeListener {
	Button Get;
	SharedPreferences someData;
	public static String filename = "MySharedString";
	Updator updates;

	Boolean isInternetPresent = false, photo_taken = false;
	ConnectionDetector cd;

	String complain_id = "";
	CalendarView cv;

	String res = "400";

	String date = "";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.get_all_complaints);

		ActionBar actionBar = (ActionBar) findViewById(R.id.actionbar);

		actionBar.setTitle("			Rectified Complain");

		actionBar.setHomeLogo(R.drawable.fileedit);
		actionBar.setDisplayHomeAsUpEnabled(true);
		initialize();
		addItemsOnSpinner();
		cv = (CalendarView) findViewById(R.id.calendarView1);
		initialization_view();

	}

	private void initialization_view() {
		// TODO Auto-generated method stub

		cv.setOnDateChangeListener(this);
		Calendar ci = Calendar.getInstance();
		Spinner SpinnerExample = (Spinner) findViewById(R.id.s_complain_ids);
		date = ci.get(Calendar.YEAR) + "-" + (ci.get(Calendar.MONTH) + 1) + "-"
				+ ci.get(Calendar.DAY_OF_MONTH);

	}

	public void addItemsOnSpinner() {

		List<String> list = new ArrayList<String>();

	}

	private void initialize() {

		cd = new ConnectionDetector(getApplicationContext());

		// TODO Auto-generated method stub
		someData = getSharedPreferences(filename, 0);
		Get = (Button) findViewById(R.id.btn_get_complains);
		Get.setOnClickListener(this);

	}

	@Override
	public void onClick(View arg0) {

		switch (arg0.getId()) {

		case R.id.btn_get_complains:
			if (cd.isConnectingToInternet()) {
				new LongOperation().execute(date);
			} else {
				Toast.makeText(getApplicationContext(), "No Internet Present",
						Toast.LENGTH_SHORT).show();
			}

			// if (isfound) {
			// cv.setVisibility(View.GONE);
			// tv_temp.setVisibility(View.GONE);
			// tv_rect.setVisibility(View.VISIBLE);
			// iv.setVisibility(View.VISIBLE);
			// ib.setVisibility(View.VISIBLE);
			// Update.setVisibility(View.VISIBLE);
			// complains_id.setVisibility(View.VISIBLE);
			// isfound = false;
			// } else {
			// isfound = true;
			// cv.setVisibility(View.VISIBLE);
			// tv_temp.setVisibility(View.VISIBLE);
			// tv_rect.setVisibility(View.GONE);
			// iv.setVisibility(View.GONE);
			// ib.setVisibility(View.GONE);
			// Update.setVisibility(View.GONE);
			// complains_id.setVisibility(View.GONE);
			//
			// }
			//
			break;

		}

	}

	private class LongOperation extends AsyncTask<String, Void, Void> {

		// Required initialization
		String mess = "";
		String temp = "";

		private ProgressDialog Dialog = new ProgressDialog(GetAllComplains.this);

		protected void onPreExecute() {
			// NOTE: You can call UI Element here.

			// Start Progress Dialog (Message)

			Dialog.setMessage("Please wait..");

			Dialog.show();

		}

		// Call after onPreExecute method
		protected Void doInBackground(String... urls) {

			/************ Make Post Call To Web Server ***********/

			ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();

			nameValuePairs.add(new BasicNameValuePair("date", urls[0]));
			nameValuePairs.add(new BasicNameValuePair("role", updates.status));
			try {
				temp = urls[0];
				HttpClient httpclient = new DefaultHttpClient();
				HttpPost httppost = new HttpPost(
						"http://"
								+ someData.getString("ip", "182.180.121.20") + ":"
								+ someData.getString("port", "81")
								+ "/~ictinnov/spatialcms/php/GetComplaintIdFromLatLng.php"
				// + someData.getString("ip", "184.172.185.189")
				// + ":"
				// + someData.getString("port", "80")
				// + "/~ictinnov/spatialcms/php/GetComplaintIdFromLatLng.php"
				// "http://10.0.2.2/ServerSide/sendallcomplains.php"
				);

				httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));

				ResponseHandler<String> responseHandler = new BasicResponseHandler();
				res = httpclient.execute(httppost, responseHandler);
				if (res.equals("400"))
					mess = "Not Complaint Found On Selected Date";

			} catch (Exception e) {
				mess = "Server Down....";
				res = "400";

			}

			/*****************************************************/
			return null;
		}

		protected void onPostExecute(Void unused) {
			// NOTE: You can call UI Element here.

			// Close progress dialog
			Dialog.dismiss();
			//Toast.makeText(getApplicationContext(), res, Toast.LENGTH_LONG)
				//	.show();
			if (!mess.equals("Server Down....")
					&& !mess.equals("Not found any data")) {
				if (!res.equals("400")) {

					Intent nextScreen = new Intent(getApplicationContext(),
							Rect_Comp_View_All.class);

					// Sending data to another Activity

					nextScreen.putExtra("response", res);

					// starting new activity
					startActivity(nextScreen);

				}

			}

			if (!mess.equals(""))
				Toast.makeText(getApplicationContext(), mess, Toast.LENGTH_LONG)
						.show();

		}

	}

	@Override
	public void onSelectedDayChange(CalendarView arg0, int arg1, int arg2,
			int arg3) {
		// TODO Auto-generated method stub

		date = Integer.toString(arg1) + "-" + Integer.toString(arg2 + 1) + "-"
				+ Integer.toString(arg3);

	}

}
