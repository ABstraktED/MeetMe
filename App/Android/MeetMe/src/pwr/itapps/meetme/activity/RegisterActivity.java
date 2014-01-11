package pwr.itapps.meetme.activity;

import pwr.itapps.meetme.R;
import pwr.itapps.meetme.activity.WallActivity;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

public class RegisterActivity<MyAndroidAppActivity>  extends Activity{
	
	Button fButton;
	ImageButton imageButton;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_register);

		imageButton = (ImageButton) findViewById(R.id.ImageButton_Avatar);

		imageButton.setOnClickListener(new View.OnClickListener() {

			public void onClick(View arg0) {

				Toast.makeText(RegisterActivity.this,
						"ImageButton is clicked!", Toast.LENGTH_SHORT).show();

			}

		});

		
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
