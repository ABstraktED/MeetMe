package pwr.itapps.meetme.fragments;

import Session.Session;
import adapter.EventsAdapter;
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
