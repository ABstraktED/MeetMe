package pwr.itapps.meetme.activity;

import pwr.itapps.meetme.R;
import pwr.itapps.meetme.activity.LoginActivity.UserLoginTask;
import pwr.itapps.meetme.helper.CommunicationHelper;
import pwr.itapps.meetme.helper.SharedPreferencesHelper;
import pwr.itapps.meetmee.model.UserModel;
import pwr.itapps.meetmee.model.in.dto.UserInDto;
import pwr.itapps.meetmee.model.mapper.OwnerMapper;
import pwr.itapps.meetmee.model.out.dto.LoginOutDto;
import pwr.itapps.meetmee.model.out.dto.UserOutDto;
import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

public class RegisterActivity<MyAndroidAppActivity> extends Activity {

	private UserRegisterTask mAuthTask = null;

	private Button mRegisterBtn;

	private EditText mFirstNameEt;
	private EditText mLastNameEt;
	private EditText mEmailEt;
	private EditText mPhoneEt;
	private EditText mPasswordEt;
	private EditText mConfirmPasswordEt;
	private EditText mUsernameEt;

	private String mFirstName;
	private String mLastName;
	private String mEmail;
	private String mPhone;
	private String mPassword;
	private String mConfirmPassword;
	private String mUsername;

	private View mLoginFormView;
	private View mLoginStatusView;
	private TextView mLoginStatusMessageView;
	SharedPreferencesHelper helper;

	private void findViews() {
		mRegisterBtn = (Button) findViewById(R.id.Button_Register);
		mFirstNameEt = (EditText) findViewById(R.id.EditText_Firstname);
		mLastNameEt = (EditText) findViewById(R.id.EditText_Lastname);
		mUsernameEt = (EditText) findViewById(R.id.EditText_Usernamemail);
		mEmailEt = (EditText) findViewById(R.id.EditText_Email);
		mPhoneEt = (EditText) findViewById(R.id.EditText_Phone);
		mPasswordEt = (EditText) findViewById(R.id.EditText_Password);
		mConfirmPasswordEt = (EditText) findViewById(R.id.EditText_ConfirmPassword);
		mLoginFormView = findViewById(R.id.login_form);
		mLoginStatusView = findViewById(R.id.login_status);
		mLoginStatusMessageView = (TextView) findViewById(R.id.login_status_message);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_register);
		findViews();
		helper = SharedPreferencesHelper.getInstance();
		mRegisterBtn.setOnClickListener(new View.OnClickListener() {
			public void onClick(View arg0) {
				attemptRegister();
			}

		});
	}

	private boolean attemptRegister() {
		if (mAuthTask != null) {
			return false;
		}

		// Reset errors.
		mFirstNameEt.setError(null);
		mLastNameEt.setError(null);
		mEmailEt.setError(null);
		mPhoneEt.setError(null);
		mPasswordEt.setError(null);
		mConfirmPasswordEt.setError(null);
		mUsernameEt.setError(null);

		// Store values at the time of the login attempt.
		mFirstName = mFirstNameEt.getText().toString();
		mLastName = mLastNameEt.getText().toString();
		mEmail = mEmailEt.getText().toString();
		mPhone = mPhoneEt.getText().toString();
		mPassword = mPasswordEt.getText().toString();
		mConfirmPassword = mConfirmPasswordEt.getText().toString();
		mUsername = mUsernameEt.getText().toString();

		boolean cancel = false;
		View focusView = null;

		// Check for a valid password.

		if (TextUtils.isEmpty(mFirstName)) {
			mFirstNameEt.setError(getString(R.string.error_field_required));
			focusView = mFirstNameEt;
			cancel = true;
		} else if (TextUtils.isEmpty(mLastName)) {
			mLastNameEt.setError(getString(R.string.error_field_required));
			focusView = mLastNameEt;
			cancel = true;
		} else if (TextUtils.isEmpty(mUsername)) {
			mUsernameEt.setError(getString(R.string.error_field_required));
			focusView = mUsernameEt;
			cancel = true;
		} else if (TextUtils.isEmpty(mEmail)) {
			mEmailEt.setError(getString(R.string.error_field_required));
			focusView = mEmailEt;
			cancel = true;
		} else if (!mEmail
				.matches("([\\w-\\.]+)@((?:[\\w]+\\.)+)([a-zA-Z]{2,4})")) {
			mEmailEt.setError(getString(R.string.error_invalid_email));
			focusView = mEmailEt;
			cancel = true;
		} else if (TextUtils.isEmpty(mPassword)) {
			mPasswordEt.setError(getString(R.string.error_field_required));
			focusView = mPasswordEt;
			cancel = true;
		} else if (TextUtils.isEmpty(mConfirmPassword)) {
			mConfirmPasswordEt
					.setError(getString(R.string.error_field_required));
			focusView = mConfirmPasswordEt;
			cancel = true;
		} else if (mPassword.length() < 3) {
			mPasswordEt.setError(getString(R.string.error_invalid_password));
			focusView = mPasswordEt;
			cancel = true;
		} else if (mConfirmPassword.length() < 3) {
			mConfirmPasswordEt
					.setError(getString(R.string.error_invalid_password));
			focusView = mConfirmPasswordEt;
			cancel = true;
		} else if (!mPassword.equals(mConfirmPassword)) {
			mConfirmPasswordEt
					.setError(getString(R.string.error_not_equal_password));
			focusView = mConfirmPasswordEt;
			cancel = true;
		}

		if (cancel) {
			// There was an error; don't attempt login and focus the first
			// form field with an error.
			focusView.requestFocus();
			return false;
		} else {
			// Show a progress spinner, and kick off a background task to
			// perform the user login attempt.
			mLoginStatusMessageView
					.setText(R.string.login_progress_registering);
			showProgress(true);
			mAuthTask = new UserRegisterTask(this);
			UserOutDto dto = new UserOutDto(mFirstName + " " + mLastName,
					mUsername, mEmail, mPhone, mPassword);

			mAuthTask.execute(dto);
			return true;
		}
	}

	public class UserRegisterTask extends AsyncTask<UserOutDto, Void, Boolean> {

		CommunicationHelper comm;

		public UserRegisterTask(Context context) {
			super();
			comm = new CommunicationHelper(context);
		}

		@Override
		protected Boolean doInBackground(UserOutDto... params) {
			Boolean result = null;
			UserOutDto dto = params[0];
			try {
				UserInDto user = comm.register(dto);
				if (user != null) {
					OwnerMapper mapper = new OwnerMapper();
					UserModel model = new UserModel(RegisterActivity.this);
					model.saveAction(mapper.mapDtoToEntity(user));
					return true;
				} else {
					return false;
				}
			} catch (Exception e) {
				e.printStackTrace();
				return null;
			}
		}

		@Override
		protected void onPostExecute(final Boolean success) {
			mAuthTask = null;
			showProgress(false);

			if (success == null) {
				Toast.makeText(RegisterActivity.this,
						"Problems with communication", Toast.LENGTH_SHORT)
						.show();
			} else if (success) {
				helper.putBoolean(SharedPreferencesHelper.REMEMBER_USER, true,
						RegisterActivity.this);
				Intent i = new Intent(RegisterActivity.this, WallActivity.class);
				startActivity(i);
				RegisterActivity.this.finish();
			} else {
				Toast.makeText(RegisterActivity.this, "User exists!",
						Toast.LENGTH_SHORT).show();
			}
		}

		@Override
		protected void onCancelled() {
			mAuthTask = null;
			showProgress(false);
		}
	}

	@TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
	private void showProgress(final boolean show) {
		// for very easy animations. If available, use these APIs to fade-in
		// the progress spinner.
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
			int shortAnimTime = getResources().getInteger(
					android.R.integer.config_shortAnimTime);

			mLoginStatusView.setVisibility(View.VISIBLE);
			mLoginStatusView.animate().setDuration(shortAnimTime)
					.alpha(show ? 1 : 0)
					.setListener(new AnimatorListenerAdapter() {
						@Override
						public void onAnimationEnd(Animator animation) {
							mLoginStatusView.setVisibility(show ? View.VISIBLE
									: View.GONE);
						}
					});

			mLoginFormView.setVisibility(View.VISIBLE);
			mLoginFormView.animate().setDuration(shortAnimTime)
					.alpha(show ? 0 : 1)
					.setListener(new AnimatorListenerAdapter() {
						@Override
						public void onAnimationEnd(Animator animation) {
							mLoginFormView.setVisibility(show ? View.GONE
									: View.VISIBLE);
						}
					});
		} else {
			// The ViewPropertyAnimator APIs are not available, so simply show
			// and hide the relevant UI components.
			mLoginStatusView.setVisibility(show ? View.VISIBLE : View.GONE);
			mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
		}
	}

}
