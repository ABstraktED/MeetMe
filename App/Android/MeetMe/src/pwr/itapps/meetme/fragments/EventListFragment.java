package pwr.itapps.meetme.fragments;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import pwr.itapps.meetme.R;
import pwr.itapps.meetme.activity.EventDetailsActivity;
import pwr.itapps.meetme.adapter.EventsAdapter;
import pwr.itapps.meetme.helper.CommunicationHelper;
import pwr.itapps.meetme.helper.SharedPreferencesHelper;
import pwr.itapps.meetmee.model.EventModel;
import pwr.itapps.meetmee.model.UserModel;
import pwr.itapps.meetmee.model.entity.Event;
import pwr.itapps.meetmee.model.in.dto.EventInDto;
import pwr.itapps.meetmee.model.mapper.EventMapper;
import pwr.itapps.meetmee.model.out.dto.EventsOutDto;
import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class EventListFragment extends ListFragment {

	public static final String EVENT_ID = "eventId";

	private GetEventsTask mAuthTask = null;
	private List<Event> events = null;

	private View mLoginFormView;
	private View mLoginStatusView;
	private TextView mLoginStatusMessageView;
	private EventModel eventModel;
	private EventsAdapter adapter;
	private SharedPreferencesHelper prefs;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_friends_list, container,
				false);
		// setupIds(view);
		mLoginFormView = view.findViewById(R.id.login_form);
		mLoginStatusView = view.findViewById(R.id.login_status);
		mLoginStatusMessageView = (TextView) view
				.findViewById(R.id.login_status_message);
		prefs = SharedPreferencesHelper.getInstance();
		eventModel = new EventModel(getActivity());
		events = eventModel.fetchAll();
		adapter = new EventsAdapter(getActivity(), events);
		setListAdapter(adapter);
		return view;

	}

	@Override
	public void onListItemClick(ListView l, View v, int position, long id) {
		Intent i = new Intent(getActivity(), EventDetailsActivity.class);
		i.putExtra(EVENT_ID, adapter.getItem(position).getId());
		startActivity(i);
	}

	@Override
	public void onResume() {
		long lastSynch = prefs.getLong(SharedPreferencesHelper.EVENTS_SYNCH,
				SharedPreferencesHelper.EVENTS_SYNCH_DEF, getActivity());
		if (new Date().getTime() - lastSynch > 1000 * 60 * 5) {
			runRefresh();
		}
		super.onResume();
	}

	public void runRefresh() {
		mLoginStatusMessageView.setText(R.string.login_progress_down_contacts);
		showProgress(true);
		mAuthTask = new GetEventsTask(getActivity());
		mAuthTask.execute((Void[]) null);

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

	public class GetEventsTask extends AsyncTask<Void, Void, Boolean> {

		long userId;
		EventModel eventModel;
		CommunicationHelper comm;

		public GetEventsTask(Context context) {
			super();
			comm = new CommunicationHelper(context);
			eventModel = new EventModel(context);
			userId = new UserModel(context).fetchOwner().getId();
		}

		@Override
		protected Boolean doInBackground(Void... params) {
			Boolean result = null;
			EventsOutDto dto = new EventsOutDto(String.valueOf(userId));
			try {
				List<EventInDto> evs = comm.getEvents(dto);
				if (evs != null) {
					EventMapper mapper = new EventMapper();
					eventModel.deleteByWhere("1==1");
					eventModel.save(mapper.mapDtoToEntity((ArrayList) evs));
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
				Toast.makeText(getActivity(), "Problems with communication",
						Toast.LENGTH_SHORT).show();
			} else if (success) {
				List<Event> events = eventModel.fetchAll();
				EventListFragment.this.events.clear();
				EventListFragment.this.events.addAll(events);
				adapter = new EventsAdapter(getActivity(), EventListFragment.this.events);
				setListAdapter(adapter);
				prefs.putLong(SharedPreferencesHelper.EVENTS_SYNCH,
						new Date().getTime(), getActivity());
			} else {
				Toast.makeText(getActivity(), "U may have no events... ",
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
