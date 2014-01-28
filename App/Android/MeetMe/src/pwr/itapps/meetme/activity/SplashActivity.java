package pwr.itapps.meetme.activity;

import pwr.itapps.meetme.R;
import pwr.itapps.meetme.helper.SharedPreferencesHelper;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

public class SplashActivity extends Activity {

	private final int SPLASH_DISPLAY_LENGHT = 1000;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle icicle) {
		super.onCreate(icicle);
		setContentView(R.layout.splash);

		new Handler().postDelayed(new Runnable() {
			@Override
			public void run() {
				SharedPreferencesHelper helper = SharedPreferencesHelper
						.getInstance();
				boolean remebered = helper.getBoolean(
						SharedPreferencesHelper.REMEMBER_USER,
						SharedPreferencesHelper.REMEMBER_USER_DEF,
						SplashActivity.this);
				if (remebered) {
					Intent mainIntent = new Intent(SplashActivity.this,
							WallActivity.class);
					SplashActivity.this.startActivity(mainIntent);
					SplashActivity.this.finish();
				} else {
					Intent mainIntent = new Intent(SplashActivity.this,
							LoginActivity.class);
					SplashActivity.this.startActivity(mainIntent);
					SplashActivity.this.finish();
				}
			}
		}, SPLASH_DISPLAY_LENGHT);
	}
}
