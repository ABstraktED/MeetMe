package pwr.itapps.meetme.activity;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import pwr.itapps.meetme.R;
import pwr.itapps.meetme.activity.RegisterActivity.UserRegisterTask;
import pwr.itapps.meetme.helper.CommunicationHelper;
import pwr.itapps.meetme.helper.SharedPreferencesHelper;
import pwr.itapps.meetmee.model.UserModel;
import pwr.itapps.meetmee.model.in.dto.UserInDto;
import pwr.itapps.meetmee.model.mapper.OwnerMapper;
import pwr.itapps.meetmee.model.out.dto.EventsOutDto;
import pwr.itapps.meetmee.model.out.dto.UserOutDto;
import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class CreateEventActivity<MyAndroidAppActivity> extends Activity {

	private static int RESULT_LOAD_IMAGE = 1;
	private Pattern pattern;
	private Matcher matcher;
	private static final String DATE_PATTERN = "(0?[1-9]|1[012]) [/.-] (0?[1-9]|[12][0-9]|3[01]) [/.-] ((19|20)\\d\\d)";

	private static final String TIME24HOURS_PATTERN = "([01]?[0-9]|2[0-3]):[0-5][0-9]";

	private Button ChooseImageBtn;
	private Button CreateEventBtn;

	private ImageView EventImage;
	private EditText mEventNameEt;
	private EditText mEventDateEt;
	private EditText mEventTimeEt;
	private EditText mEventAddressEt;
	private EditText mEventDecritptionEt;

	private String mEventName;
	private String mEventDate;
	private String mEventTime;
	private String mEventAddress;
	private String mEventDecritption;

	private View mCreateEventFormView;
	private View mCreateEventStatusView;
	private TextView mCreateEventStatusMessageView;

	private void findViews() {
		ChooseImageBtn = (Button) findViewById(R.id.button_ChooseImage);
		EventImage = (ImageView) findViewById(R.id.imageView_EventImage);
		mEventNameEt = (EditText) findViewById(R.id.editText_EventName);
		mEventDateEt = (EditText) findViewById(R.id.editText_EventDate);
		mEventTimeEt = (EditText) findViewById(R.id.editText_EventTime);
		mEventAddressEt = (EditText) findViewById(R.id.editText_EventAddress);
		mEventDecritptionEt = (EditText) findViewById(R.id.editText_EventDecritption);
		CreateEventBtn = (Button) findViewById(R.id.button_CreateEvent);
		mCreateEventFormView = findViewById(R.id.createEvent_form);
		mCreateEventStatusView = findViewById(R.id.createEvent_status);
		mCreateEventStatusMessageView = (TextView) findViewById(R.id.createEvent_status_message);

	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_create_event);
		findViews();
		// Listening to createEvent button event
		CreateEventBtn.setOnClickListener(new View.OnClickListener() {

			public void onClick(View arg0) {
				createEvent();
			}
		});

		// Listening to ChooseImage button event
		ChooseImageBtn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View arg0) {
				Intent i = new Intent(
						Intent.ACTION_PICK,
						android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
				startActivityForResult(i, RESULT_LOAD_IMAGE);
			}
		});
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (requestCode == RESULT_LOAD_IMAGE && resultCode == RESULT_OK
				&& null != data) {
			Uri selectedImage = data.getData();
			String[] filePathColumn = { MediaStore.Images.Media.DATA };
			Cursor cursor = getContentResolver().query(selectedImage,
					filePathColumn, null, null, null);
			cursor.moveToFirst();
			int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
			String picturePath = cursor.getString(columnIndex);
			cursor.close();
			EventImage.setImageBitmap(getScaledBitmap(picturePath, 200, 200));
		}
	}

	private Bitmap getScaledBitmap(String picturePath, int width, int height) {
		BitmapFactory.Options sizeOptions = new BitmapFactory.Options();
		sizeOptions.inJustDecodeBounds = true;
		BitmapFactory.decodeFile(picturePath, sizeOptions);

		int inSampleSize = calculateInSampleSize(sizeOptions, width, height);

		sizeOptions.inJustDecodeBounds = false;
		sizeOptions.inSampleSize = inSampleSize;

		return BitmapFactory.decodeFile(picturePath, sizeOptions);
	}

	private int calculateInSampleSize(BitmapFactory.Options options,
			int reqWidth, int reqHeight) {
		// Raw height and width of image
		final int height = options.outHeight;
		final int width = options.outWidth;
		int inSampleSize = 1;

		if (height > reqHeight || width > reqWidth) {

			// Calculate ratios of height and width to requested height and
			// width
			final int heightRatio = Math.round((float) height
					/ (float) reqHeight);
			final int widthRatio = Math.round((float) width / (float) reqWidth);

			// Choose the smallest ratio as inSampleSize value, this will
			// guarantee
			// a final image with both dimensions larger than or equal to the
			// requested height and width.
			inSampleSize = heightRatio < widthRatio ? heightRatio : widthRatio;
		}

		return inSampleSize;
	}

	private boolean createEvent() {

		// Reset errors.
		mEventNameEt.setError(null);
		mEventDateEt.setError(null);
		mEventTimeEt.setError(null);
		mEventAddressEt.setError(null);
		mEventDecritptionEt.setError(null);

		// Store values at the time of the creation attempt.
		mEventName = mEventNameEt.getText().toString();
		mEventDate = mEventDateEt.getText().toString();
		mEventTime = mEventTimeEt.getText().toString();
		mEventAddress = mEventAddressEt.getText().toString();
		mEventDecritption = mEventDecritptionEt.getText().toString();

		boolean cancel = false;
		View focusView = null;

		// Check for a valid password.

		if (TextUtils.isEmpty(mEventName)) {
			mEventNameEt.setError(getString(R.string.error_field_required));
			focusView = mEventNameEt;
			cancel = true;
		} else if (TextUtils.isEmpty(mEventDate)) {
			mEventDateEt.setError(getString(R.string.error_field_required));
			focusView = mEventDateEt;
			cancel = true;
		} else if (!mEventDate.matches(DATE_PATTERN)) {
			mEventDateEt.setError(getString(R.string.error_invalid_date));
			focusView = mEventDateEt;
			cancel = true;
		} else if (!validate(mEventDate)) {
			mEventDateEt.setError(getString(R.string.error_invalid_date));
			focusView = mEventDateEt;
			cancel = true;
		} else if (TextUtils.isEmpty(mEventTime)) {
			mEventTimeEt.setError(getString(R.string.error_field_required));
			focusView = mEventTimeEt;
			cancel = true;
		} else if (!mEventTime.matches(TIME24HOURS_PATTERN)) {
			mEventTimeEt.setError(getString(R.string.error_invalid_time));
			focusView = mEventTimeEt;
			cancel = true;

		} else if (TextUtils.isEmpty(mEventAddress)) {
			mEventAddressEt.setError(getString(R.string.error_field_required));
			focusView = mEventAddressEt;
			cancel = true;
		} else if (TextUtils.isEmpty(mEventDecritption)) {
			mEventDecritptionEt
					.setError(getString(R.string.error_field_required));
			focusView = mEventDecritptionEt;
			cancel = true;
		}
		if (cancel) {

			focusView.requestFocus();
			return false;
		} else {
			// Show a progress spinner, and kick off a background task to
			// perform the user login attempt.
			mCreateEventStatusMessageView.setText(R.string.creating_event_task);
			showProgress(true);

			// EventsOutDto = new EventsOutDto(mEventName,mEventDate,
			// mEventTime, mEventAddress, mEventDecritption);

			return true;
		}

	}

	// Extra Date validation
	public boolean validate(final String date) {

		matcher = pattern.matcher(date);

		if (matcher.matches()) {
			matcher.reset();

			if (matcher.find()) {
				String day = matcher.group(1);
				String month = matcher.group(2);
				int year = Integer.parseInt(matcher.group(3));

				if (day.equals("31")
						&& (month.equals("4") || month.equals("6")
								|| month.equals("9") || month.equals("11")
								|| month.equals("04") || month.equals("06") || month
									.equals("09"))) {
					return false; // only 1,3,5,7,8,10,12 has 31 days
				}

				else if (month.equals("2") || month.equals("02")) {
					// leap year
					if (year % 4 == 0) {
						if (day.equals("30") || day.equals("31")) {
							return false;
						} else {
							return true;
						}
					} else {
						if (day.equals("29") || day.equals("30")
								|| day.equals("31")) {
							return false;
						} else {
							return true;
						}
					}
				}

				else {
					return true;
				}
			}

			else {
				return false;
			}
		} else {
			return false;
		}
	}

	@TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
	private void showProgress(final boolean show) {
		// for very easy animations. If available, use these APIs to fade-in
		// the progress spinner.
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
			int shortAnimTime = getResources().getInteger(
					android.R.integer.config_shortAnimTime);

			mCreateEventStatusView.setVisibility(View.VISIBLE);
			mCreateEventStatusView.animate().setDuration(shortAnimTime)
					.alpha(show ? 1 : 0)
					.setListener(new AnimatorListenerAdapter() {
						@Override
						public void onAnimationEnd(Animator animation) {
							mCreateEventStatusView
									.setVisibility(show ? View.VISIBLE
											: View.GONE);
						}
					});

			mCreateEventFormView.setVisibility(View.VISIBLE);
			mCreateEventFormView.animate().setDuration(shortAnimTime)
					.alpha(show ? 0 : 1)
					.setListener(new AnimatorListenerAdapter() {
						@Override
						public void onAnimationEnd(Animator animation) {
							mCreateEventFormView.setVisibility(show ? View.GONE
									: View.VISIBLE);
						}
					});
		} else {
			// The ViewPropertyAnimator APIs are not available, so simply show
			// and hide the relevant UI components.
			mCreateEventStatusView.setVisibility(show ? View.VISIBLE
					: View.GONE);
			mCreateEventFormView.setVisibility(show ? View.GONE : View.VISIBLE);
		}
	}

}