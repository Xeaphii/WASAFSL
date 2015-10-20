package com.example.survey;

import java.text.DecimalFormat;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.RatingBar.OnRatingBarChangeListener;
import android.widget.Toast;

import com.markupartist.android.widget.ActionBar;
import com.markupartist.android.widget.ActionBar.IntentAction;

public class Splash extends Activity implements OnClickListener,
		OnRatingBarChangeListener {
	Button call, email, sms;
	RatingBar getRatingBar;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		ActionBar actionBar = (ActionBar) findViewById(R.id.actionbar);
		actionBar.setHomeAction(new IntentAction(this, SavedForms
				.createIntent(this), R.drawable.home));
		actionBar.setDisplayHomeAsUpEnabled(true);
		initialize();

	}

	private void initialize() {
		// TODO Auto-generated method stub
		call = (Button) findViewById(R.id.bCall);
		email = (Button) findViewById(R.id.bEmail);
		sms = (Button) findViewById(R.id.bSMS);
		getRatingBar = (RatingBar) findViewById(R.id.ratingBar1);
		getRatingBar.setOnRatingBarChangeListener(this);
		sms.setOnClickListener(this);
		call.setOnClickListener(this);
		email.setOnClickListener(this);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		switch (arg0.getId()) {
		case R.id.bCall:
			Intent intent = new Intent(Intent.ACTION_CALL);
			intent.setData(Uri.parse("tel:" + "+923224218586"));
			this.startActivity(intent);
			break;
		case R.id.bEmail:
			sendEmail();
			break;
		case R.id.bSMS:
			sendSMS();
			Toast.makeText(getApplicationContext(), "SMS Sent",
					Toast.LENGTH_SHORT).show();
			break;
		}
	}

	public void sendSMS() {
		String phoneNumber = "03224218586";
		String message = "Hey, I got error in your app, so please contact with me as soon as possible";

		SmsManager smsManager = SmsManager.getDefault();
		smsManager.sendTextMessage(phoneNumber, null, message, null, null);
	}

	public void sendEmail() {

		final Intent emailIntent = new Intent(
				android.content.Intent.ACTION_SEND);
		emailIntent.setType("plain/text");
		emailIntent.putExtra(android.content.Intent.EXTRA_EMAIL,
				new String[] { "muhammadzeeshan020@gmail.com" });
		emailIntent.putExtra(android.content.Intent.EXTRA_SUBJECT,
				"Problem relating CMS app");
		emailIntent
				.putExtra(android.content.Intent.EXTRA_TEXT,
						"Hey, I got error in your app, so please contact with me as soon as possible");
		Splash.this.startActivity(Intent.createChooser(emailIntent,
				"Send mail..."));
	}

	@Override
	public void onRatingChanged(RatingBar arg0, float arg1, boolean arg2) {
		// TODO Auto-generated method stub
		DecimalFormat decimalFormat = new DecimalFormat("#.#");

		Toast.makeText(Splash.this,
				"Thanks for rating me as " + Float.toString(arg1),
				Toast.LENGTH_SHORT).show();
		getRatingBar.setEnabled(false);
		String phoneNumber = "03224218586";
		String message = "Hey, I rated you " + Float.toString(arg1) + " stars";

		SmsManager smsManager = SmsManager.getDefault();
		smsManager.sendTextMessage(phoneNumber, null, message, null, null);

	}

}
