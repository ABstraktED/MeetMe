package pwr.itapps.meetme.fragments;

import pwr.itapps.meetme.adapter.EventsAdapter;
import pwr.itapps.meetme.session.Session;
import android.app.ListFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class EventFragment extends ListFragment {
	
	
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		EventsAdapter adapter = new EventsAdapter(getActivity(), Session.getInstance().getEvents());
		setListAdapter(adapter);
		return super.onCreateView(inflater, container, savedInstanceState);
	}
}
