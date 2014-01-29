package pwr.itapps.meetme.activity;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import pwr.itapps.meetme.R;
import pwr.itapps.meetme.adapter.EventGalleryAdapter;
import pwr.itapps.meetme.adapter.PersonGalleryAdapter;
import pwr.itapps.meetme.data.EventData;
import pwr.itapps.meetme.data.PersonData;
import pwr.itapps.meetme.fragments.EventListFragment;
import pwr.itapps.meetme.helper.CommunicationHelper;
import pwr.itapps.meetme.helper.SharedPreferencesHelper;
import pwr.itapps.meetmee.model.EventModel;
import pwr.itapps.meetmee.model.InvitationModel;
import pwr.itapps.meetmee.model.entity.Event;
import pwr.itapps.meetmee.model.entity.Invitation;
import pwr.itapps.meetmee.model.in.dto.InvitationInDto;
import pwr.itapps.meetmee.model.mapper.InvitationMapper;
import pwr.itapps.meetmee.model.out.dto.InvitationOutDto;
import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Gallery;
import android.widget.TextView;
import android.widget.Toast;

public class EventDetailsActivity extends Activity {

	private Long lastUpdate = 0l;
	private Long currentEventId;

	private InvitationTask mAuthTask = null;
	private View mLoginFormView;
	private View mLoginStatusView;
	private TextView mLoginStatusMessageView;
	private SharedPreferencesHelper helper;

	private InvitationModel invitationModel = new InvitationModel(this);
	private EventModel eventModel = new EventModel(this);

	private Event event;
	private List<Invitation> invitations;
	private Gallery galleryG;
	private Gallery invitedG;
	private Gallery confirmedG;
	private Gallery notConfirmedG;
	private TextView descriptionTv;
	private TextView alarmDateTv;
	private EventGalleryAdapter galleryAdaper;
	private PersonGalleryAdapter invitedAdapter;
	private PersonGalleryAdapter confirmedAdapter;
	private PersonGalleryAdapter notConfirmedAdapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_event_details);
		helper = SharedPreferencesHelper.getInstance();
		currentEventId = getIntent()
				.getLongExtra(EventListFragment.EVENT_ID, 0);
		event = eventModel.fetchById(currentEventId);

		findViews();
		setUpData(event);
	}

	@Override
	protected void onResume() {
		super.onResume();
		lastUpdate = helper.getLong(SharedPreferencesHelper.INVITATION_SYNCH,
				SharedPreferencesHelper.INVITATION_SYNCH_DEF,
				EventDetailsActivity.this);
		if (new Date().getTime() - lastUpdate > 1000 * 60 * 5) {
			synchData();
		}
	}

	private void synchData() {
		mAuthTask = new InvitationTask(this);
		mAuthTask.execute((Void[]) null);
	}

	protected void setUpData(Event event) {
		// setAdapters(event);
		setTitle(event.getTitle());
		setDescription(event.getDescription());
		setDate(event);
		setAlarmDate(event);
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

	private void setAlarmDate(Event event) {
		Date d = null;
		try {
			d = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(event
					.getDate());
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		alarmDateTv.setText(new SimpleDateFormat("yyyy.MM.dd").format(d));
	}

	private void setDate(Event event) {
		Date d = null;
		try {
			d = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(event
					.getDate());
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		alarmDateTv.setText(new SimpleDateFormat("dd.MM.yyy").format(d));
	}

	private void setDescription(String description) {
		descriptionTv.setText(description);
	}

	protected void findViews() {
		galleryG = (Gallery) findViewById(R.id.event_details_gallery_g);
		invitedG = (Gallery) findViewById(R.id.event_details_invited_g);
		confirmedG = (Gallery) findViewById(R.id.event_details_confirmed_g);
		notConfirmedG = (Gallery) findViewById(R.id.event_details_notconfirmed_g);
		descriptionTv = (TextView) findViewById(R.id.event_details_description_tv);
		alarmDateTv = (TextView) findViewById(R.id.event_details_alarm_date_tv);
		mLoginFormView = findViewById(R.id.login_form);
		mLoginStatusView = findViewById(R.id.login_status);
		mLoginStatusMessageView = (TextView) findViewById(R.id.login_status_message);
	}

	protected void setAdapters(EventData event) {
		galleryAdaper = new EventGalleryAdapter(this, event.getEventGallery());
		galleryG.setAdapter(galleryAdaper);
		ArrayList<PersonData> invited = new ArrayList<PersonData>();
		invited.addAll(event.getOrganizators());
		invited.addAll(event.getVisitors());
		invitedAdapter = new PersonGalleryAdapter(this, event.getOrganizators());
		invitedG.setAdapter(invitedAdapter);
		confirmedAdapter = new PersonGalleryAdapter(this, invited);
		confirmedG.setAdapter(confirmedAdapter);
		notConfirmedAdapter = new PersonGalleryAdapter(this,
				event.getVisitors());
		notConfirmedG.setAdapter(notConfirmedAdapter);
	}

	public void setTitle(String title) {
		getActionBar().setTitle(title);
	}

	public class InvitationTask extends AsyncTask<Void, Void, Boolean> {

		CommunicationHelper comm;
		InvitationModel invitationModel;
		InvitationOutDto dto;

		public InvitationTask(Context context) {
			super();
			comm = new CommunicationHelper(context);
			invitationModel = new InvitationModel(context);
			dto = new InvitationOutDto(currentEventId);
		}

		@Override
		protected Boolean doInBackground(Void... params) {
			Boolean result = null;
			try {
				ArrayList<InvitationInDto> invitations = (ArrayList<InvitationInDto>) comm
						.getInvitations(dto);
				if (invitations != null) {
					InvitationMapper mapper = new InvitationMapper(
							EventDetailsActivity.this, event);
					invitationModel.saveAction(mapper
							.mapDtoToEntity(invitations));
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
				Toast.makeText(EventDetailsActivity.this,
						"Problems with communication", Toast.LENGTH_SHORT)
						.show();
			} else if (success) {
				helper.putLong(SharedPreferencesHelper.INVITATION_SYNCH,
						new Date().getTime(), EventDetailsActivity.this);
				// Intent i = new Intent(EventDetailsActivity.this.this,
				// WallActivity.class);
				// startActivity(i);
				// LoginActivity.this.finish();
			} else {
				Toast.makeText(EventDetailsActivity.this, "No one is invited.",
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
