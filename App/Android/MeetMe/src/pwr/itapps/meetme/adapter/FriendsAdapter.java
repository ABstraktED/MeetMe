package pwr.itapps.meetme.adapter;

import java.util.List;

import pwr.itapps.meetme.R;
import pwr.itapps.meetme.application.MeetMe;
import pwr.itapps.meetme.data.PersonData;
import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;

public class FriendsAdapter extends ArrayAdapter<PersonData> {

	public FriendsAdapter(Context context, List<PersonData> data) {
		super(context, R.layout.friend_row, data);
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View row = convertView;
		if (row == null) {
			LayoutInflater inflater = (LayoutInflater) getContext()
					.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
			row = inflater.inflate(R.layout.friend_row, null);
			Holder holder = new Holder();
			holder.image = (ImageView) row
					.findViewById(R.id.friend_row_image);
			holder.name = (TextView) row.findViewById(R.id.friend_row_FirstName);
			holder.surname = (TextView) row
					.findViewById(R.id.friend_row_lastName);
			holder.status = (TextView) row.findViewById(R.id.friend_row_status);
			row.setTag(holder);

		}
		PersonData person = getItem(position);
		Holder tag = (Holder) row.getTag();
		ImageLoader.getInstance().displayImage(person.getAvatar(), tag.image,
				MeetMe.OPTIONS);
		tag.name.setText(person.getName());
		tag.surname.setText(person.getSurname()); 
		tag.status.setText(person.getStatus().toString());
		return row;
	}

	public class Holder {
		public ImageView image;
		public TextView name;
		public TextView surname;
		public TextView status;
	}
}

