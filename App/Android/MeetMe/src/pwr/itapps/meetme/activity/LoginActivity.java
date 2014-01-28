package pwr.itapps.meetme.activity;

import pwr.itapps.meetme.R;
import pwr.itapps.meetme.helper.CommunicationHelper;
import pwr.itapps.meetme.helper.SharedPreferencesHelper;
import pwr.itapps.meetmee.model.Model;
import pwr.itapps.meetmee.model.UserModel;
import pwr.itapps.meetmee.model.in.dto.UserInDto;
import pwr.itapps.meetmee.model.mapper.OwnerMapper;
import pwr.itapps.meetmee.model.out.dto.LoginOutDto;
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
import android.view.Menu;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class LoginActivity extends Activity {
	public static final String EXTRA_EMAIL = "email";

	private UserLoginTask mAuthTask = null;

	// Values for email and password at the time of the login attempt.
	private String mEmail;
	private String mPassword;
	private boolean mRememberUser = false;

	// UI references.
	private EditText mEmailView;
	private EditText mPasswordView;
	private View mLoginFormView;
	private View mLoginStatusView;
	private CheckBox mRememberCb;
	private TextView mLoginStatusMessageView;
	SharedPreferencesHelper helper;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_login);

		// Set up the login form.
		mEmail = getIntent().getStringExtra(EXTRA_EMAIL);
		mEmailView = (EditText) findViewById(R.id.email);
		mEmailView.setText(mEmail);

		mPasswordView = (EditText) findViewById(R.id.password);

		mLoginFormView = findViewById(R.id.login_form);
		mLoginStatusView = findViewById(R.id.login_status);
		mLoginStatusMessageView = (TextView) findViewById(R.id.login_status_message);

		findViewById(R.id.register_TV).setOnClickListener(
				new View.OnClickListener() {
					@Override
					public void onClick(View v) {
						Intent i = new Intent(LoginActivity.this,
								RegisterActivity.class);
						startActivity(i);
					}
				});

		findViewById(R.id.sign_in_button).setOnClickListener(
				new View.OnClickListener() {
					@Override
					public void onClick(View view) {
						attemptLogin();
					}
				});
		helper = SharedPreferencesHelper.getInstance();
		mRememberCb = ((CheckBox) findViewById(R.id.remember_CB));
		mRememberCb.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(CompoundButton buttonView,
					boolean isChecked) {
				mRememberUser = isChecked;
			}
		});
	}

	@Override
	protected void onResume() {
		super.onResume();
		mRememberUser = helper.getBoolean(
				SharedPreferencesHelper.REMEMBER_USER,
				SharedPreferencesHelper.REMEMBER_USER_DEF, LoginActivity.this);
		mRememberCb.setChecked(mRememberUser);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		super.onCreateOptionsMenu(menu);
		getMenuInflater().inflate(R.menu.login, menu);
		return true;
	}

	public boolean attemptLogin() {
		if (mAuthTask != null) {
			return false;
		}

		// Reset errors.
		mEmailView.setError(null);
		mPasswordView.setError(null);

		// Store values at the time of the login attempt.
		mEmail = mEmailView.getText().toString();
		mPassword = mPasswordView.getText().toString();

		boolean cancel = false;
		View focusView = null;

		// Check for a valid password.

		if (TextUtils.isEmpty(mPassword)) {
			mPasswordView.setError(getString(R.string.error_field_required));
			focusView = mPasswordView;
			cancel = true;
		} else if (mPassword.length() < 3) {
			mPasswordView.setError(getString(R.string.error_invalid_password));
			focusView = mPasswordView;
			cancel = true;
		}

		// Check for a valid email address.
		if (TextUtils.isEmpty(mEmail)) {
			mEmailView.setError(getString(R.string.error_field_required));
			focusView = mEmailView;
			cancel = true;
		}
		// } else if (!mEmail
		// .matches("([\\w-\\.]+)@((?:[\\w]+\\.)+)([a-zA-Z]{2,4})")) {
		// mEmailView.setError(getString(R.string.error_invalid_email));
		// focusView = mEmailView;
		// cancel = true;
		// }

		if (cancel) {
			// There was an error; don't attempt login and focus the first
			// form field with an error.
			focusView.requestFocus();
			return false;
		} else {
			// Show a progress spinner, and kick off a background task to
			// perform the user login attempt.
			mLoginStatusMessageView.setText(R.string.login_progress_signing_in);
			showProgress(true);
			mAuthTask = new UserLoginTask(this);
			mAuthTask.execute(mEmail, mPassword);
			return true;
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

	/**
	 * Represents an asynchronous login/registration task used to authenticate
	 * the user.
	 */
	// 0 - login correct, 1 - error (incorrect credintials), 2 -no such person
	// in database
	public class UserLoginTask extends AsyncTask<String, Void, Boolean> {

		CommunicationHelper comm;

		public UserLoginTask(Context context) {
			super();
			comm = new CommunicationHelper(context);
		}

		@Override
		protected Boolean doInBackground(String... params) {
			Boolean result = null;
			LoginOutDto dto = new LoginOutDto(params[0], params[1]);
			try {
				UserInDto user = comm.logIn(dto);
				if (user != null) {
					Model.dropDatabase();
					Model.createDatabase();
					OwnerMapper mapper = new OwnerMapper();
					UserModel model = new UserModel(LoginActivity.this);
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
				Toast.makeText(LoginActivity.this,
						"Problems with communication", Toast.LENGTH_SHORT)
						.show();
			} else if (success) {
				helper.putBoolean(SharedPreferencesHelper.REMEMBER_USER,
						mRememberUser, LoginActivity.this);
				Intent i = new Intent(LoginActivity.this, WallActivity.class);
				startActivity(i);
				LoginActivity.this.finish();
			} else {
				Toast.makeText(LoginActivity.this, "Wrong password or email!",
						Toast.LENGTH_SHORT).show();
			}
		}

		@Override
		protected void onCancelled() {
			mAuthTask = null;
			showProgress(false);
		}
	}
}
