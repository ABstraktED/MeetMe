package pwr.itapps.meetme.adapter;

import java.util.List;

import pwr.itapps.meetme.R;
import pwr.itapps.meetme.application.MeetMeApplication;
import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import com.nostra13.universalimageloader.core.ImageLoader;

public class EventGalleryAdapter extends ArrayAdapter<String> {

	public EventGalleryAdapter(Context context, List<String> objects) {
		super(context, R.layout.event_gallery_item, objects);
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ImageView row = (ImageView)convertView;
		if (row == null) {
			LayoutInflater inflater = (LayoutInflater) getContext()
					.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
			row = (ImageView)inflater.inflate(R.layout.event_gallery_item, null);
		}
		String imageAdd = getItem(position);
		ImageLoader.getInstance().displayImage(imageAdd, row,
				MeetMeApplication.OPTIONS);
		return row;
	}

}
