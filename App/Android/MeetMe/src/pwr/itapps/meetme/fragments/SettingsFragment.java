package pwr.itapps.meetme.fragments;

import pwr.itapps.meetme.R;
import pwr.itapps.meetme.helper.SharedPreferencesHelper;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;

public class SettingsFragment extends Fragment implements
		OnCheckedChangeListener {

	private SharedPreferencesHelper helper;
	private CheckBox mRememberMeCb;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.fragment_settings, container, false);
		mRememberMeCb = (CheckBox) v.findViewById(R.id.remember_CB);
		mRememberMeCb.setOnCheckedChangeListener(this);
		return v;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		helper = SharedPreferencesHelper.getInstance();
		mRememberMeCb.setChecked(helper.getBoolean(
				SharedPreferencesHelper.REMEMBER_USER,
				SharedPreferencesHelper.REMEMBER_USER_DEF, getActivity()));
	}

	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
	}

	@Override
	public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
		helper.putBoolean(SharedPreferencesHelper.REMEMBER_USER, isChecked,
				getActivity());
	}
}
