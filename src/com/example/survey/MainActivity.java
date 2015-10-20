package com.example.survey;

import android.app.Activity;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.os.Bundle;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;

public class MainActivity extends Activity {
	

	public void onAttachedToWindow() {
		super.onAttachedToWindow();
		Window window = getWindow();
		window.setFormat(PixelFormat.RGBA_8888);
	}

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.splash_layout);
		
		StartAnimations();

		Thread timer = new Thread() {
			public void run() {
				try {
					sleep(2000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				} finally {
					try {
						Class ourClass = Class
								.forName("com.example.survey.LoginForm");
						Intent ourIntent = new Intent(MainActivity.this,
								ourClass);
						startActivity(ourIntent);
					} catch (ClassNotFoundException e) {
						e.printStackTrace();
					}
				}
			}
		};
		timer.start();
	}

	private void StartAnimations() {
		Animation anim = AnimationUtils.loadAnimation(this, R.anim.alpha);
		anim.reset();
		LinearLayout l = (LinearLayout) findViewById(R.id.lin_lay);
		l.clearAnimation();
		l.startAnimation(anim);

		anim = AnimationUtils.loadAnimation(this, R.anim.translate);
		anim.reset();
		ImageView iv = (ImageView) findViewById(R.id.logo);
		iv.clearAnimation();
		iv.startAnimation(anim);
	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		finish();
	}
}
