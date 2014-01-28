package pwr.itapps.meetme.fragments;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import android.app.Activity;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class MapsFragment extends SupportMapFragment{
	/*changed from extending Mapfragment because I've got some errors here*/ 
	GoogleMap mapView;

	@Override
	public void onCreate(Bundle arg0) {
		super.onCreate(arg0);
	}

	@Override
	public View onCreateView(LayoutInflater mInflater, ViewGroup arg1,
			Bundle arg2) {
		return super.onCreateView(mInflater, arg1, arg2);

	}

	@Override
	public void onInflate(Activity arg0, AttributeSet arg1, Bundle arg2) {
		super.onInflate(arg0, arg1, arg2);

	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		// setUpMapIfNeeded();

	}

	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		setUpMapIfNeeded();
	}

	private void setUpMapIfNeeded() {
		// Do a null check to confirm that we have not already instantiated the
		// map.
		if (mapView == null) {
			// Try to obtain the map from the SupportMapFragment.
			mapView = getMap();
			// Check if we were successful in obtaining the map.
			if (mapView != null) {
				MarkerOptions mo = new MarkerOptions().position(new LatLng(
						23.231251f, 71.648437f));
				mo.draggable(true);
				mo.icon(BitmapDescriptorFactory.defaultMarker());
				mapView.addMarker(mo);
			}
		}
	}
}
