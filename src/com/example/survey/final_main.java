package com.example.survey;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.markupartist.android.widget.ActionBar;

public class final_main extends Activity implements OnClickListener {

	Updator updates;
	Button drainage, water, pipeline, offline, wwm, rectified_comp,
			ChangePassword;
	SharedPreferences someData;
	public static String filename = "MySharedString";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.final_main);
		someData = getSharedPreferences(filename, 0);
		ActionBar actionBar = (ActionBar) findViewById(R.id.actionbar);
		// You can also assign the title programmatically by passing a
		// CharSequence or resource id.
		// actionBar.setTitle(R.string.some_title);

		actionBar.setTitle("					CMS, WASA, Faisalabad");

		actionBar.setHomeLogo(R.drawable.complain);
		actionBar.setDisplayHomeAsUpEnabled(true);
		findviewbyid();
		setonclicklisters();

	}

	private void setonclicklisters() {
		// TODO Auto-generated method stub
		drainage.setOnClickListener(this);
		water.setOnClickListener(this);
		pipeline.setOnClickListener(this);
		offline.setOnClickListener(this);
		wwm.setOnClickListener(this);
		rectified_comp.setOnClickListener(this);
		ChangePassword.setOnClickListener(this);
	}

	private void findviewbyid() {
		// TODO Auto-generated method stub
		drainage = (Button) findViewById(R.id.bDrainage);
		water = (Button) findViewById(R.id.bWater);
		pipeline = (Button) findViewById(R.id.bSewage);
		offline = (Button) findViewById(R.id.bOffline);
		wwm = (Button) findViewById(R.id.bWWM);
		rectified_comp = (Button) findViewById(R.id.bRectified);
		ChangePassword = (Button) findViewById(R.id.bPassword);

	}

	private static Intent createIntent(final_main final_main) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		switch (arg0.getId()) {
		case R.id.bPassword:
			try {
				Class ourClass = Class
						.forName("com.example.survey.ChangePassword");
				Intent ourIntent = new Intent(final_main.this, ourClass);
				startActivity(ourIntent);
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
			break;
		case R.id.bWWM:
			try {
				Class ourClass = Class.forName("com.example.survey.WWM");
				Intent ourIntent = new Intent(final_main.this, ourClass);
				startActivity(ourIntent);
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
			break;
		case R.id.bRectified:
			try {
				Class ourClass = Class
						.forName("com.example.survey.GetAllComplains");
				Intent ourIntent = new Intent(final_main.this, ourClass);
				startActivity(ourIntent);
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
			break;

		case R.id.bDrainage:
			try {
				Class ourClass = Class.forName("com.example.survey.NewForm");
				Intent ourIntent = new Intent(final_main.this, ourClass);
				startActivity(ourIntent);
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
			break;
		case R.id.bSewage:
			try {
				Class ourClass = Class
						.forName("com.example.survey.FinalDrainage");
				Intent ourIntent = new Intent(final_main.this, ourClass);
				startActivity(ourIntent);
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
			break;
		case R.id.bWater:
			try {
				Class ourClass = Class
						.forName("com.example.survey.WaterProblem");
				Intent ourIntent = new Intent(final_main.this, ourClass);
				startActivity(ourIntent);
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
			break;
		case R.id.bOffline:
			try {
				Class ourClass = Class
						.forName("com.example.survey.SelectedItemActivity");
				Intent ourIntent = new Intent(final_main.this, ourClass);
				startActivity(ourIntent);
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
			break;

		}

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater menuInflater = getMenuInflater();
		menuInflater.inflate(R.layout.menu, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {

		switch (item.getItemId()) {
		case R.id.menu_about:
			AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
					this);

			// set title
			alertDialogBuilder.setTitle("About us");

			// set dialog message
			alertDialogBuilder
					.setMessage(
							"This app is created, in order to submit problems of people regarding their sanitation and other drainage problems, you wanna exist the application?")
					.setCancelable(false)
					.setPositiveButton("Yes",
							new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog,
										int id) {
									// if this button is clicked, close
									// current activity
									final_main.this.finish();
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

			return true;

		case R.id.menu_share:

			final Intent emailIntent = new Intent(
					android.content.Intent.ACTION_SEND);
			emailIntent.setType("plain/text");
			emailIntent.putExtra(android.content.Intent.EXTRA_EMAIL,
					new String[] { "" });
			emailIntent.putExtra(android.content.Intent.EXTRA_SUBJECT,
					"CMS APP");
			emailIntent.putExtra(android.content.Intent.EXTRA_TEXT,
					"Hey, i am sending you this CMS app for submitting problems."
							+ "");
			final_main.this.startActivity(Intent.createChooser(emailIntent,
					"Send mail..."));

			return true;

		case R.id.menu_settings:
			LayoutInflater factory = LayoutInflater.from(this);
			final View textEntryView = factory.inflate(R.layout.text_entry,
					null);

			final EditText input1 = (EditText) textEntryView
					.findViewById(R.id.editText1);
			final EditText input2 = (EditText) textEntryView
					.findViewById(R.id.editText2);

			final AlertDialog.Builder alert = new AlertDialog.Builder(this);
			alert.setIcon(R.drawable.port)
					.setTitle("Setting panel:")
					.setView(textEntryView)
					.setPositiveButton("Save",
							new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog,
										int whichButton) {
									if (!input1.getText().toString().trim()
											.equals("")
											&& !input2.getText().toString()
													.trim().equals("")) {

										SharedPreferences.Editor editor = someData
												.edit();
										editor.putString("ip", input1.getText()
												.toString());
										editor.putString("port", input2
												.getText().toString());
										editor.commit();

										Toast.makeText(getApplicationContext(),
												"Saved!", Toast.LENGTH_SHORT)
												.show();

										updates.ip = input1.getText()
												.toString();
										updates.port = input2.getText()
												.toString();
									}

								}
							})
					.setNegativeButton("Cancel",
							new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog,
										int whichButton) {
								}
							});
			alert.show();

			return true;

		default:
			return super.onOptionsItemSelected(item);
		}
	}

	public static Intent createIntent(Context context) {
		Intent i = new Intent(context, final_main.class);
		i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		return i;
	}
}
