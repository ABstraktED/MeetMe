package pwr.itapps.meetme.fragments;

import Session.Session;
import adapter.FriendsAdapter;
import android.app.ListFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class FriendsFragment extends ListFragment {
	
	
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		FriendsAdapter adapter = new FriendsAdapter(getActivity(), Session.getInstance().getPersons());
		setListAdapter(adapter);
		return super.onCreateView(inflater, container, savedInstanceState);
	}
}

