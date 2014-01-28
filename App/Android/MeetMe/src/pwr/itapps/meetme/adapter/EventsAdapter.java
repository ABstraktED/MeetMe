package pwr.itapps.meetme.adapter;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import pwr.itapps.meetme.R;
import pwr.itapps.meetme.application.MeetMe;
import pwr.itapps.meetmee.model.entity.Event;
import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;

public class EventsAdapter extends ArrayAdapter<Event> {

	public EventsAdapter(Context context, List<Event> data) {
		super(context, R.layout.event_row, data);
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View row = convertView;
		if (row == null) {
			LayoutInflater inflater = (LayoutInflater) getContext()
					.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
			row = inflater.inflate(R.layout.event_row, null);
			Holder holder = new Holder();
			holder.image = (ImageView) row
					.findViewById(R.id.event_row_image_iv);
			holder.title = (TextView) row.findViewById(R.id.event_row_title_tv);
			holder.organizators = (TextView) row
					.findViewById(R.id.event_row_organizators_tv);
			holder.date = (TextView) row.findViewById(R.id.event_row_date_tv);
			row.setTag(holder);

		}
		Event event = getItem(position);
		Holder tag = (Holder) row.getTag();
		// List<String> gallery = event.getEventGallery();
		// if (gallery.size() > 0)
		// ImageLoader.getInstance().displayImage(gallery.get(0), tag.image,
		// MeetMe.OPTIONS);
		// else
		ImageLoader.getInstance().displayImage("", tag.image, MeetMe.OPTIONS);
		tag.title.setText(event.getTitle());
		if (event.getUser() != null)
			tag.organizators.setText(event.getUser().getName()); // to
																	// change
																	// in
																	// future
																	// for
																	// every
																	// organizator
		DateFormat df = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
		DateFormat df1 = new SimpleDateFormat("dd-MM-yyyy");
		Date today = null;
		try {
			today = df.parse(event.getDate());
		} catch (ParseException e) {
			e.printStackTrace();
		}
		if (today != null)
			tag.date.setText(df1.format(today));
		return row;
	}

	public class Holder {
		public ImageView image;
		public TextView title;
		public TextView organizators;
		public TextView date;
	}
}
