package com.example.survey;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
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
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout.LayoutParams;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.markupartist.android.widget.ActionBar;

public class Rect_Comp_View_All extends Activity implements OnClickListener {

	public static String filename = "MySharedString";
	SharedPreferences someData;
	ImageButton ib;
	String CiDateTime;
	final static int cameraData = 0;
	Updator updates;
	Bitmap bmp;
	Boolean isInternetPresent = false, photo_taken = false;
	ConnectionDetector cd;
	ImageView iv, prev_image; //
	public Button Update;
	String Selected_id = "";
	Spinner complains_id;
	public ArrayList<SpinnerModel> CustomListViewValuesArr = new ArrayList<SpinnerModel>();
	Rect_Comp_View_All activity = null;
	CustomAdapter adapter;
	Intent i;
	EditText comment_text;
	String[] temp2 = { "", "" };
	List<String> list;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.rec_com_view_all);
		initialization();
		someData = getSharedPreferences(filename, 0);
		list = new ArrayList<String>();
		Intent temp = getIntent();
		// Receiving the Data
		String res = temp.getStringExtra("response");
		//Toast.makeText(getApplicationContext(), res, Toast.LENGTH_LONG).show();
		String[] words = res.split(":");

		if (words.length > 0) {
			ib.setEnabled(true);
		} else {
			ib.setEnabled(false);
		}
		Spinner SpinnerExample = (Spinner) findViewById(R.id.s_complain_ids);
		CustomListViewValuesArr = new ArrayList<SpinnerModel>();
		for (int i = 0; i < words.length; i++) {
			String[] temp1 = words[i].split(",");
			final SpinnerModel sched = new SpinnerModel();

			/******* Firstly take data in model object ******/
			sched.setCompanyName(temp1[0]);
			list.add(temp1[1]);
			/******** Take Model Object in ArrayList **********/
			CustomListViewValuesArr.add(sched);
		}

		Resources res1 = getResources();
		adapter = new CustomAdapter(activity, R.layout.spinner_rows,
				CustomListViewValuesArr, res1);
		SpinnerExample.setAdapter(adapter);
	}

	private void initialization() {
		// TODO Auto-generated method xstub
		ActionBar actionBar = (ActionBar) findViewById(R.id.actionbar);

		actionBar.setTitle("			Rectified Complain");

		actionBar.setHomeLogo(R.drawable.fileedit);
		actionBar.setDisplayHomeAsUpEnabled(true);
		Spinner SpinnerExample = (Spinner) findViewById(R.id.s_complain_ids);
		cd = new ConnectionDetector(getApplicationContext());
		iv = (ImageView) findViewById(R.id.imageView1);
		prev_image = (ImageView) findViewById(R.id.img_rec);
		ib = (ImageButton) findViewById(R.id.ibTakePic);
		ib.setOnClickListener(this);
		Update = (Button) findViewById(R.id.b_update);
		Update.setEnabled(false);
		Update.setOnClickListener(this);
		Update.setBackgroundResource(R.drawable.custom_update_button);
		complains_id = (Spinner) findViewById(R.id.s_complain_ids);
		activity = this;
		comment_text = (EditText) findViewById(R.id.ETComment);
		setListData();

		Resources res = getResources();
		adapter = new CustomAdapter(activity, R.layout.spinner_rows,
				CustomListViewValuesArr, res);
		SpinnerExample.setAdapter(adapter);
		SpinnerExample.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> parentView, View v,
					int position, long id) {

				Toast.makeText(getApplicationContext(), Integer.toString(position), Toast.LENGTH_LONG).show();
				// your code here

				Selected_id = ((TextView) v.findViewById(R.id.company))
						.getText().toString();
				GetXMLTask task = new GetXMLTask();
				// Execute the task
				task.execute(new String[] { "http://"
						+ someData.getString("ip", "182.180.121.20") + ":"
						+ someData.getString("port", "81")
						+ "/~ictinnov/spatialcms/php/complaints_images/"
						+ list.get(position)});
			}

			@Override
			public void onNothingSelected(AdapterView<?> parentView) {
				// your code here
			}

		});
		ib.setEnabled(false);
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

	private class GetXMLTask extends AsyncTask<String, Void, Bitmap> {

		private ProgressDialog Dialog = new ProgressDialog(
				Rect_Comp_View_All.this);

		protected void onPreExecute() {
			// NOTE: You can call UI Element here.

			// Start Progress Dialog (Message)

			Dialog.setMessage("Please wait..");
			Dialog.show();

		}

		@Override
		protected Bitmap doInBackground(String... urls) {
			Bitmap map = null;
			for (String url : urls) {
				map = downloadImage(url);
			}
			return map;
		}

		// Sets the Bitmap returned by doInBackground
		@Override
		protected void onPostExecute(Bitmap result) {
			Dialog.dismiss();
			prev_image.setImageBitmap(result);
		}

		// Creates Bitmap from InputStream and returns it
		private Bitmap downloadImage(String url) {
			Bitmap bitmap = null;
			InputStream stream = null;
			BitmapFactory.Options bmOptions = new BitmapFactory.Options();
			bmOptions.inSampleSize = 1;

			try {
				stream = getHttpConnection(url);
				bitmap = BitmapFactory.decodeStream(stream, null, bmOptions);
				stream.close();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			return bitmap;
		}

		// Makes HttpURLConnection and returns InputStream
		private InputStream getHttpConnection(String urlString)
				throws IOException {
			InputStream stream = null;
			URL url = new URL(urlString);
			URLConnection connection = url.openConnection();

			try {
				HttpURLConnection httpConnection = (HttpURLConnection) connection;
				httpConnection.setRequestMethod("GET");
				httpConnection.connect();

				if (httpConnection.getResponseCode() == HttpURLConnection.HTTP_OK) {
					stream = httpConnection.getInputStream();
				}
			} catch (Exception ex) {
				ex.printStackTrace();
			}
			return stream;
		}
	}

	private class LongOperation1 extends AsyncTask<String, Void, Void> {

		// Required initialization
		String mess = "";

		private ProgressDialog Dialog = new ProgressDialog(
				Rect_Comp_View_All.this);

		protected void onPreExecute() {
			// NOTE: You can call UI Element here.

			// Start Progress Dialog (Message)

			Dialog.setMessage("Please wait..");
			Dialog.show();

		}

		// Call after onPreExecute method
		protected Void doInBackground(String... urls) {

			/************ Make Post Call To Web Server ***********/

			ByteArrayOutputStream bao = new ByteArrayOutputStream();
			//
			bmp.compress(Bitmap.CompressFormat.JPEG, 100, bao);

			byte[] ba = bao.toByteArray();

			String ba1 = Base64.encodeBytes(ba);
			ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
			nameValuePairs.add(new BasicNameValuePair("image", ba1));
			nameValuePairs.add(new BasicNameValuePair("id", Selected_id));

			nameValuePairs.add(new BasicNameValuePair("comments", comment_text
					.getText().toString()));
			String imagename = "";

			imagename = CiDateTime + ".jpg";

			nameValuePairs.add(new BasicNameValuePair("image_name", imagename));
			try {

				HttpClient httpclient = new DefaultHttpClient();
				HttpPost httppost = new HttpPost("http://"
						+ someData.getString("ip", "182.180.121.20") + ":"
						+ someData.getString("port", "81")
						+ "/~ictinnov/spatialcms/php/UpdateComplaintsData.php"
				// "http://10.0.2.2/ServerSide/sendallcom.php"
				);

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

	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		ImageView image;
		switch (arg0.getId()) {
		case R.id.b_update:
			if (cd.isConnectingToInternet()) {
				new LongOperation1().execute("");
				Update.setBackgroundResource(R.drawable.custom_update_button);
			} else {
				DataBaseHandler db = new DataBaseHandler(this);
				ByteArrayOutputStream stream = new ByteArrayOutputStream();
				bmp.compress(Bitmap.CompressFormat.JPEG, 100, stream);
				byte imageInByte[] = stream.toByteArray();

				db.addComplain(new Complain("Rectified", "", Selected_id, "",
						"", "", CiDateTime, updates.status, imageInByte));

				Toast.makeText(getApplicationContext(),
						"No internet connection", Toast.LENGTH_SHORT).show();
				Update.setEnabled(false);
				Update.setBackgroundResource(R.drawable.custom_save_button);
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

	private void setListData() {
		// TODO Auto-generated method stub
		final SpinnerModel sched = new SpinnerModel();

		CustomListViewValuesArr.add(sched);

	}

}
