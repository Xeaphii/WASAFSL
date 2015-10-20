package com.example.survey;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

import com.markupartist.android.widget.ActionBar;

public class Water_Main extends Activity implements OnClickListener {

	GPSTracker gps;
	Updator updates;
	ConnectionDetector cd;
	Button but, offline, rectified_comp,ChangePassword;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.water_main);
		ActionBar actionBar = (ActionBar) findViewById(R.id.actionbar);
		// You can also assign the title programmatically by passing a
		// CharSequence or resource id.
		// actionBar.setTitle(R.string.some_title);

		actionBar.setTitle("					Water Problems");

		actionBar.setHomeLogo(R.drawable.tap);
		actionBar.setDisplayHomeAsUpEnabled(true);
		findviewbyid();
		setonclicklisters();
		
	}

	private void setonclicklisters() {
		// TODO Auto-generated method stub
		but.setOnClickListener(this);
		offline.setOnClickListener(this);
		rectified_comp.setOnClickListener(this);
		ChangePassword.setOnClickListener(this);

	}

	private void findviewbyid() {
		// TODO Auto-generated method stub
		but = (Button) findViewById(R.id.bWater);
		offline = (Button) findViewById(R.id.bOffline);
		rectified_comp = (Button) findViewById(R.id.bRectified);
		ChangePassword = (Button) findViewById(R.id.bPassword);

	}

	@Override
	public void onClick(View arg0) {

		switch (arg0.getId()) {
		case R.id.bPassword:
			try {
				Class ourClass = Class
						.forName("com.example.survey.ChangePassword");
				Intent ourIntent = new Intent(Water_Main.this, ourClass);
				startActivity(ourIntent);
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
			break;

		case R.id.bWater:
			
			try {
				Class ourClass = Class
						.forName("com.example.survey.WaterProblem");
				Intent ourIntent = new Intent(Water_Main.this, ourClass);
				startActivity(ourIntent);
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
			break;
		case R.id.bOffline:
			try {
				Class ourClass = Class.forName("com.example.survey.SelectedItemActivity");
				Intent ourIntent = new Intent(Water_Main.this, ourClass);
				startActivity(ourIntent);
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
			break;
		case R.id.bRectified:
			try {
				Class ourClass = Class
						.forName("com.example.survey.GetAllComplains");
				Intent ourIntent = new Intent(Water_Main.this, ourClass);
				startActivity(ourIntent);
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
			break;

		}

	}
}
