package adapter;

import java.text.SimpleDateFormat;
import java.util.List;

import pwr.itapps.meetme.R;
import DataModels.EventData;
import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import application.MeetMeApplication;

import com.nostra13.universalimageloader.core.ImageLoader;

public class EventsAdapter extends ArrayAdapter<EventData> {

	public EventsAdapter(Context context, List<EventData> data) {
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
		EventData event = getItem(position);
		Holder tag = (Holder) row.getTag();
		List<String> gallery = event.getEventGallery();
		if (gallery.size() > 0)
			ImageLoader.getInstance().displayImage(gallery.get(0), tag.image,
					MeetMeApplication.OPTIONS);
		else
			ImageLoader.getInstance().displayImage("", tag.image,
					MeetMeApplication.OPTIONS);
		tag.title.setText(event.getName());
		tag.organizators.setText(event.getOrganizators().get(0).toString()); // to
																				// change
																				// in
																				// future
																				// for
																				// every
																				// organizator
		tag.date.setText(new SimpleDateFormat("dd.MM.yyyy").format(event
				.getDate()));
		return row;
	}

	public class Holder {
		public ImageView image;
		public TextView title;
		public TextView organizators;
		public TextView date;
	}
}
