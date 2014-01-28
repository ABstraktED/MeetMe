package pwr.itapps.meetmee.model;

import java.util.ArrayList;

import pwr.itapps.meetmee.model.entity.Event;
import pwr.itapps.meetmee.model.entity.Invitation;
import pwr.itapps.meetmee.model.entity.Location;
import pwr.itapps.meetmee.model.entity.User;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.provider.BaseColumns;

public class LocationModel extends Model<Location> {

	public static String tableName = "location";

	public LocationModel(Context context) {
		super(tableName, context);
	}

	public LocationModel(Context context, boolean inner) {
		super(tableName, context);
		innerModel = inner;
	}

	public static final class TableInfo implements BaseColumns {

		public static final String LONGTITUDE = "longititude";
		public static final String LATITIDE = "latitude";

		public static String getCreateStatement() {
			return String
					.format("CREATE TABLE %s (%s INTEGER PRIMARY KEY, %s REAL,%s REAL);",
							tableName, _ID, LONGTITUDE, LATITIDE);
		}

		public static String getDropStatement() {
			return String.format("DROP TABLE IF EXISTS " + tableName);
		}

		public static String[] getColums() {
			return new String[] { _ID, LONGTITUDE, LATITIDE };
		}
	}

	@Override
	public long saveAction(Location location) {
		ContentValues values = new ContentValues();
		values.put(TableInfo.LONGTITUDE, location.getLongitude());
		values.put(TableInfo.LATITIDE, location.getLatitude());

		if (location.getId() != null) {
			values.put(TableInfo._ID, location.getId());
			if (fetchById(location.getId()) != null) {
				update(values);
				return location.getId();
			}
		}
		long id = insert(values);
		location.setId(id);
		return id;
	}

	@Override
	public ArrayList<Location> fill(Cursor cursor) {
		ArrayList<Location> result = new ArrayList<Location>();
		Location location;
		cursor.moveToFirst();
		while (!cursor.isAfterLast()) {
			location = new Location();
			location.setId(cursor.getLong(cursor
					.getColumnIndexOrThrow(TableInfo._ID)));
			location.setLongitude(cursor.getDouble(cursor
					.getColumnIndexOrThrow(TableInfo.LONGTITUDE)));
			location.setLatitude(cursor.getDouble(cursor
					.getColumnIndexOrThrow(TableInfo.LATITIDE)));
			result.add(location);
			cursor.moveToNext();
		}
		return result;
	}

}
