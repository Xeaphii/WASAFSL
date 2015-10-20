package com.example.survey;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class LoginForm extends Activity implements OnClickListener {
	EditText id, password;
	String response = "";
	Button login, setPassword;
	Updator updates;
	ConnectionDetector cd;
	SharedPreferences someData;
	public static String filename = "LoginData";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.login_layout);
		findviewbyid();
		onclicklisteners();
		someData = getSharedPreferences(filename, 0);
	}

	// @Override
	// public boolean onCreateOptionsMenu(Menu menu) {
	// MenuInflater menuInflater = getMenuInflater();
	// menuInflater.inflate(R.layout.menu_for_change_paassword, menu);
	// return true;
	// }

	// void Alert(String temp)
	// {
	// AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
	// this);
	//
	// // set title
	// alertDialogBuilder.setTitle("Recovered Password");
	//
	// // set dialog message
	// alertDialogBuilder
	// .setMessage(
	// "Your recovered password is:"+temp+"\n"+"")
	// .setCancelable(false)
	// .setPositiveButton("Yes",
	// new DialogInterface.OnClickListener() {
	// public void onClick(DialogInterface dialog,
	// int id) {
	// // if this button is clicked, close
	// // current activity
	// LoginForm.this.finish();
	// }
	// })
	// .setNegativeButton("No",
	// new DialogInterface.OnClickListener() {
	// public void onClick(DialogInterface dialog,
	// int id) {
	// // if this button is clicked, just close
	// // the dialog box and do nothing
	// dialog.cancel();
	// }
	// });
	//
	// // create alert dialog
	// AlertDialog alertDialog = alertDialogBuilder.create();
	//
	// // show it
	// alertDialog.show();
	// }
	//
	// @Override
	// public boolean onOptionsItemSelected(MenuItem item) {
	//
	// switch (item.getItemId()) {
	//
	// case R.id.menu_get_password:
	// LayoutInflater factory = LayoutInflater.from(this);
	// final View textEntryView = factory.inflate(
	// R.layout.text_get_password, null);
	//
	// final EditText input1 = (EditText) textEntryView
	// .findViewById(R.id.editText1);
	// final EditText input2 = (EditText) textEntryView
	// .findViewById(R.id.editText2);
	//
	// final AlertDialog.Builder alert = new AlertDialog.Builder(this);
	// alert.setIcon(R.drawable.ic_password_change)
	// .setTitle("Password Recovery Password:")
	// .setView(textEntryView)
	// .setPositiveButton("Get",
	// new DialogInterface.OnClickListener() {
	// public void onClick(DialogInterface dialog,
	// int whichButton) {
	// String pass = "";
	// if (input1.getText().toString()
	// .equals("md@wasa")
	// && input2
	// .getText()
	// .toString()
	// .equals(someData
	// .getString(
	// "md@wasa",
	// "179269")))
	// pass = someData.getString("md@wasa",
	// "179269");
	// // dmd
	// else if (input1.getText().toString()
	// .equals("dmd@wasa")
	// && input2
	// .getText()
	// .toString()
	// .equals(someData.getString(
	// "dmd@wasa",
	// "7d67d9")))
	// pass = someData.getString("dmd@wasa",
	// "7d67d9");
	// // wmm
	// else if (input1.getText().toString()
	// .equals("adwwmeast@wasa")
	// && input2
	// .getText()
	// .toString()
	// .equals(someData.getString(
	// "adwwmeast@wasa",
	// "ca33e5")))
	// pass = someData.getString(
	// "adwwmeast@wasa", "ca33e5");
	// else if (input1.getText().toString()
	// .equals("adwwmwest@wasa")
	// && input2
	// .getText()
	// .toString()
	// .equals(someData.getString(
	// "adwwmwest@wasa",
	// "79d82f")))
	// pass = someData.getString(
	// "adwwmwest@wasa", "79d82f");
	// // sewerage
	// else if (input1.getText().toString()
	// .equals("adgma@wasa")
	// && input2
	// .getText()
	// .toString()
	// .equals(someData.getString(
	// "adgma@wasa",
	// "05647e")))
	// pass = someData.getString("adgma@wasa",
	// "05647e");
	// else if (input1.getText().toString()
	// .equals("adgul@wasa")
	// && input2
	// .getText()
	// .toString()
	// .equals(someData.getString(
	// "adgul@wasa",
	// "bd9918")))
	// pass = someData.getString("adgul@wasa",
	// "bd9918");
	// else if (input1.getText().toString()
	// .equals("adcl@wasa")
	// && input2
	// .getText()
	// .toString()
	// .equals(someData.getString(
	// "adcl@wasa",
	// "c130b5")))
	// pass = someData.getString("adcl@wasa",
	// "c130b5");
	//
	// else if (input1.getText().toString()
	// .equals("admt@wasa")
	// && input2
	// .getText()
	// .toString()
	// .equals(someData.getString(
	// "admt@wasa",
	// "36e63a")))
	// pass = someData.getString("admt@wasa",
	// "36e63a");
	// else if (input1.getText().toString()
	// .equals("adaic@wasa")
	// && input2
	// .getText()
	// .toString()
	// .equals(someData.getString(
	// "adaic@wasa",
	// "910c27")))
	// pass = someData.getString("adaic@wasa",
	// "910c27");
	// else if (input1.getText().toString()
	// .equals("adpc@wasa")
	// && input2
	// .getText()
	// .toString()
	// .equals(someData.getString(
	// "adpc@wasa",
	// "3d7600")))
	// pass = someData.getString("adpc@wasa",
	// "3d7600");
	// // water
	// else if (input1.getText().toString()
	// .equals("adwreast@wasa")
	// && input2
	// .getText()
	// .toString()
	// .equals(someData.getString(
	// "adwreast@wasa",
	// "f34bf4")))
	// pass = someData.getString(
	// "adwreast@wasa", "f34bf4");
	// else if (input1.getText().toString()
	// .equals("adwrwest@wasa")
	// && input2
	// .getText()
	// .toString()
	// .equals(someData.getString(
	// "adwrwest@wasa",
	// "62b090")))
	// pass = someData.getString(
	// "adwrwest@wasa", "62b090");
	// // Drainage
	// else if (input1.getText().toString()
	// .equals("addrain@wasa")
	// && input2
	// .getText()
	// .toString()
	// .equals(someData.getString(
	// "addrain@wasa",
	// "cc4a81")))
	// pass = someData.getString(
	// "addrain@wasa", "cc4a81");
	//
	// }
	// })
	// .setNegativeButton("Cancel",
	// new DialogInterface.OnClickListener() {
	// public void onClick(DialogInterface dialog,
	// int whichButton) {
	// }
	// });
	// alert.show();
	//
	// return true;
	//
	// default:
	// return super.onOptionsItemSelected(item);
	// }
	// }

	private void onclicklisteners() {
		// TODO Auto-generated method stub
		login.setOnClickListener(this);
		setPassword.setOnClickListener(this);
	}

	private void findviewbyid() {
		// TODO Auto-generated method stub
		id = (EditText) findViewById(R.id.tv_id);
		password = (EditText) findViewById(R.id.tv_password);
		login = (Button) findViewById(R.id.btnLogin);
		setPassword = (Button) findViewById(R.id.btn_set_password);
	}

	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		if (arg0.getId() == R.id.btnLogin) {

			new LongOperation().execute("");

		} else if (arg0.getId() == R.id.btn_set_password) {

			AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
					this);

			// set title
			alertDialogBuilder.setTitle("Password Recovery:");

			// set dialog message
			alertDialogBuilder
					.setMessage(
							"Are you sure you want to set password to default values")
					.setCancelable(false)
					.setPositiveButton("Yes",
							new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog,
										int id) {
									// if this button is clicked, close
									// current activity
									SharedPreferences.Editor editor = someData
											.edit();
									editor.putString("md@wasa", "179269");
									editor.putString("dmd@wasa", "7d67d9");
									editor.putString("adwwmeast@wasa", "ca33e5");
									editor.putString("adwwmwest@wasa", "79d82f");
									editor.putString("adgma@wasa", "05647e");
									editor.putString("adgul@wasa", "bd9918");
									editor.putString("adcl@wasa", "c130b5");
									editor.putString("admt@wasa", "36e63a");
									editor.putString("adaic@wasa", "910c27");
									editor.putString("adpc@wasa", "3d7600");
									editor.putString("adwreast@wasa", "f34bf4");
									editor.putString("adwrwest@wasa", "62b090");
									editor.putString("addrain@wasa", "cc4a81");
									editor.commit();
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
		}

	}

	private class LongOperation extends AsyncTask<String, Void, Void> {

		// Required initialization
		String mess = "";

		private ProgressDialog Dialog = new ProgressDialog(LoginForm.this);

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

			String ID = id.getText().toString();
			String PASSWORD = password.getText().toString();
			updates.name = id.getText().toString();
			// md
			if (ID.equals("md@wasa")
					&& PASSWORD.equals(someData.getString("md@wasa", "179269")))
				response = "md";
			// dmd
			else if (ID.equals("dmd@wasa")
					&& PASSWORD
							.equals(someData.getString("dmd@wasa", "7d67d9")))
				response = "dmd";
			// wmm
			else if (ID.equals("adwwmeast@wasa")
					&& PASSWORD.equals(someData.getString("adwwmeast@wasa",
							"ca33e5")))
				response = "adwwmeast";
			else if (ID.equals("adwwmwest@wasa")
					&& PASSWORD.equals(someData.getString("adwwmwest@wasa",
							"79d82f")))
				response = "adwwmwest";
			// sewerage

			else if (ID.equals("adgma@wasa")
					&& PASSWORD.equals(someData.getString("adgma@wasa",
							"05647e")))
				response = "adgma";
			else if (ID.equals("adgul@wasa")
					&& PASSWORD.equals(someData.getString("adgul@wasa",
							"bd9918")))
				response = "adgul";
			else if (ID.equals("adcl@wasa")
					&& PASSWORD.equals(someData
							.getString("adcl@wasa", "c130b5")))
				response = "adcl";

			else if (ID.equals("admt@wasa")
					&& PASSWORD.equals(someData
							.getString("admt@wasa", "36e63a")))
				response = "admt";
			else if (ID.equals("adaic@wasa")
					&& PASSWORD.equals(someData.getString("adaic@wasa",
							"910c27")))
				response = "adaic";
			else if (ID.equals("adpc@wasa")
					&& PASSWORD.equals(someData
							.getString("adpc@wasa", "3d7600")))
				response = "adpc";
			// water
			else if (ID.equals("adwreast@wasa")
					&& PASSWORD.equals(someData.getString("adwreast@wasa",
							"f34bf4")))
				response = "adwreast";
			else if (ID.equals("adwrwest@wasa")
					&& PASSWORD.equals(someData.getString("adwrwest@wasa",
							"62b090")))
				response = "adwrwest";
			// Drainage
			else if (ID.equals("addrain@wasa")
					&& PASSWORD.equals(someData.getString("addrain@wasa",
							"cc4a81")))
				response = "addrain";
			else
				response = "Not found";

			updates.status = response;
			updates.password = PASSWORD;

			/*****************************************************/
			return null;
		}

		protected void onPostExecute(Void unused) {
			// NOTE: You can call UI Element here.
			// Close progress dialog
			Dialog.dismiss();

			if (response.equals("adwwmeast") || response.equals("adwwmwest")) {
				// WWM
				try {
					Toast.makeText(getApplicationContext(),
							"Successfully logged in..", Toast.LENGTH_SHORT)
							.show();
					Class ourClass = Class
							.forName("com.example.survey.WWM_Main");
					Intent ourIntent = new Intent(LoginForm.this, ourClass);
					startActivity(ourIntent);
				} catch (ClassNotFoundException e) {
					e.printStackTrace();
				}

			} else if (response.equals("adgma") || response.equals("adgul")
					|| response.equals("adcl") || response.equals("admt")
					|| response.equals("adaic") || response.equals("adpc")) {
				// sewerage
				try {
					Toast.makeText(getApplicationContext(),
							"Successfully logged in..", Toast.LENGTH_SHORT)
							.show();
					Class ourClass = Class
							.forName("com.example.survey.Sewerage_Main");
					Intent ourIntent = new Intent(LoginForm.this, ourClass);
					startActivity(ourIntent);
				} catch (ClassNotFoundException e) {
					e.printStackTrace();
				}

			} else if (response.equals("adwreast")
					|| response.equals("adwrwest")) {
				// water
				try {
					Toast.makeText(getApplicationContext(),
							"Successfully logged in..", Toast.LENGTH_SHORT)
							.show();
					Class ourClass = Class
							.forName("com.example.survey.Water_Main");
					Intent ourIntent = new Intent(LoginForm.this, ourClass);
					startActivity(ourIntent);
				} catch (ClassNotFoundException e) {
					e.printStackTrace();
				}
			} else if (response.equals("addrain")) {
				// drainage
				try {
					Toast.makeText(getApplicationContext(),
							"Successfully logged in..", Toast.LENGTH_SHORT)
							.show();
					Class ourClass = Class
							.forName("com.example.survey.Drainage_Main");
					Intent ourIntent = new Intent(LoginForm.this, ourClass);
					startActivity(ourIntent);
				} catch (ClassNotFoundException e) {
					e.printStackTrace();
				}
			} else if (response.equals("md") || response.equals("dmd")) {
				try {
					Toast.makeText(getApplicationContext(),
							"Successfully logged in..", Toast.LENGTH_SHORT)
							.show();

					Class ourClass = Class
							.forName("com.example.survey.final_main");
					Intent ourIntent = new Intent(LoginForm.this, ourClass);
					startActivity(ourIntent);
				} catch (ClassNotFoundException e) {
					e.printStackTrace();
				}
			} else {
				if (!mess.equals("Server down...."))
					mess = "Wrong username or password";
				Toast.makeText(getApplicationContext(), mess, Toast.LENGTH_LONG)
						.show();
			}

		}
	}

}
