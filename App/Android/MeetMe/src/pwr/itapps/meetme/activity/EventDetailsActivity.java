package pwr.itapps.meetme.activity;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import pwr.itapps.meetme.R;
import pwr.itapps.meetme.adapter.EventGalleryAdapter;
import pwr.itapps.meetme.adapter.PersonGalleryAdapter;
import pwr.itapps.meetme.data.EventData;
import pwr.itapps.meetme.data.PersonData;
import pwr.itapps.meetme.fragments.EventListFragment;
import pwr.itapps.meetme.session.Session;
import android.app.Activity;
import android.os.Bundle;
import android.widget.Gallery;
import android.widget.TextView;

public class EventDetailsActivity extends Activity {

	private EventData event;
	
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
		int id = getIntent().getIntExtra(EventListFragment.EVENT_ID, 0);
		event = Session.getInstance().getEvents().get(id);
		findViews();
		setUpData(event);
	}

	protected void setUpData(EventData event) {
		setAdapters(event);
		setTitle(event.getName());
		setDescription(event.getDescription());
		setDate(event.getDate());
		setAlarmDate(event.getAlarmDate());
	}

	private void setAlarmDate(Date alarmDate) {
		alarmDateTv.setText(new SimpleDateFormat("HH:mm dd.MM.yyyy")
				.format(event.getDate()));
	}

	private void setDate(Date date) {
		getActionBar().setSubtitle(
				new SimpleDateFormat("dd.MM.yyyy").format(event.getDate()));
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

}
