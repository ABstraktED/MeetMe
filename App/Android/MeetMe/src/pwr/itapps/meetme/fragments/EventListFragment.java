package pwr.itapps.meetme.fragments;

import pwr.itapps.meetme.activity.EventDetailsActivity;
import pwr.itapps.meetme.adapter.EventsAdapter;
import pwr.itapps.meetme.session.Session;
import android.app.ListFragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

public class EventListFragment extends ListFragment {

	public static final String EVENT_ID = "eventId";

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		EventsAdapter adapter = new EventsAdapter(getActivity(), Session
				.getInstance().getEvents());
		setListAdapter(adapter);
		return super.onCreateView(inflater, container, savedInstanceState);
	}

	@Override
	public void onListItemClick(ListView l, View v, int position, long id) {
		Intent i = new Intent(getActivity(), EventDetailsActivity.class);
		i.putExtra(EVENT_ID, position);
		startActivity(i);
	}
}
