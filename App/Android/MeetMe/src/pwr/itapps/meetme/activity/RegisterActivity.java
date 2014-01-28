package pwr.itapps.meetme.activity;

import pwr.itapps.meetme.R;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;


public class RegisterActivity<MyAndroidAppActivity>  extends Activity{
	
	Button fButton;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_register);

				
		fButton = (Button) findViewById(R.id.Button_Register);

		// Listening to button event
		fButton.setOnClickListener(new View.OnClickListener() {

			public void onClick(View arg0) {
				// Starting a new Intent
				Intent nextScreen = new Intent(getApplicationContext(),
						WallActivity.class);
				startActivity(nextScreen);

			}
		});
   
		
	}
	
}
