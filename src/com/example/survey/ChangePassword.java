package com.example.survey;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.markupartist.android.widget.ActionBar;

public class ChangePassword extends Activity implements OnClickListener {

	EditText OldPassword, NewPassword, ConfirmPassword;
	Button Update;
	SharedPreferences someData;
	public static String filename = "LoginData";
	Updator updates;
	SharedPreferences someData1;
	public static String filename1 = "MySharedString";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.change_password);
		findviewbyIds();
		setonclicklisteners();
		someData = getSharedPreferences(filename, 0);
		someData1 = getSharedPreferences(filename1, 0);
		initializeactionbar();
		initialize();

	}

	private void initialize() {
		// TODO Auto-generated method stub

	}

	private void initializeactionbar() {
		// TODO Auto-generated method stub
		ActionBar actionBar = (ActionBar) findViewById(R.id.actionbar);

		actionBar.setTitle("			Change Password");

		actionBar.setHomeLogo(R.drawable.fileedit);
		actionBar.setDisplayHomeAsUpEnabled(true);

	}

	private void setonclicklisteners() {
		// TODO Auto-generated method stub
		Update.setOnClickListener(this);
	}

	private void findviewbyIds() {
		// TODO Auto-generated method stub
		OldPassword = (EditText) findViewById(R.id.et_old_password);
		NewPassword = (EditText) findViewById(R.id.et_new_password);
		ConfirmPassword = (EditText) findViewById(R.id.et_new_password_confirm);
		Update = (Button) findViewById(R.id.b_change_password);

	}

	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		if (arg0.getId() == R.id.b_change_password) {

			new LongOperation().execute("");

		}
	}

	private class LongOperation extends AsyncTask<String, Void, Void> {

		// Required initialization
		String mess = "";

		private ProgressDialog Dialog = new ProgressDialog(ChangePassword.this);

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
			if (OldPassword.getText().toString().equals(updates.password)) {
				if (NewPassword.getText().toString()
						.equals(ConfirmPassword.getText().toString())) {
					mess = "Password changed successfully.";
				} else {
					mess = "Passwords do not match.";

				}
			} else {
				mess = "Old password is incorrect.";
			}

			/*****************************************************/
			return null;
		}

		protected void onPostExecute(Void unused) {
			// NOTE: You can call UI Element here.

			// Close progress dialog
			Dialog.dismiss();
			if (mess.equals("Password changed successfully.")) {
				SharedPreferences.Editor editor = someData.edit();
				editor.putString(updates.name, NewPassword.getText().toString());
				editor.commit();
			}
			Toast.makeText(getApplicationContext(), mess, Toast.LENGTH_LONG)
					.show();
			if (mess.equals("Password changed successfully."))
				finish();

		}
	}

}
