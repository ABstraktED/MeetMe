package pwr.itapps.meetmee.model;

import java.util.ArrayList;

import pwr.itapps.meetmee.model.entity.Event;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.provider.BaseColumns;

public class EventModel extends Model<Event> {

	public static String tableName = "event";
	private UserModel userModel;
	private LocationModel locationModel;

	public EventModel(Context context) {
		super(tableName, context);
		userModel = new UserModel(context, true);
		locationModel = new LocationModel(context, true);
	}

	public EventModel(Context context, boolean inner) {
		super(tableName, context);
		innerModel = inner;
	}

	public static final class TableInfo implements BaseColumns {
		public static final String ALL_CAN_INVITE = "all_can_invite";
		public static final String ALL_CAN_JOIN = "all_can_join";
		public static final String DATE = "date";
		public static final String DESCRIPTION = "description";
		public static final String TITLE = "title";
		public static final String LOCAITON_ID = "location_id";
		public static final String USER_ID = "user_id";

		public static String getCreateStatement() {
			return String
					.format("CREATE TABLE %s (%s INTEGER PRIMARY KEY, %s INTEGER, %s INTEGER,%s TEXT, %s TEXT, %s TEXT,%s INTEGER,%s INTEGER);",
							tableName, _ID, ALL_CAN_INVITE, ALL_CAN_JOIN, DATE,
							DESCRIPTION, TITLE, LOCAITON_ID, USER_ID);
		}

		public static String getDropStatement() {
			return String.format("DROP TABLE IF EXISTS " + tableName);
		}

		public static String[] getColums() {
			return new String[] { _ID, ALL_CAN_INVITE, ALL_CAN_JOIN, DATE,
					DESCRIPTION, TITLE, LOCAITON_ID, USER_ID };
		}
	}

	@Override
	public long saveAction(Event event) {
		ContentValues values = new ContentValues();
		values.put(TableInfo._ID, event.getId());
		values.put(TableInfo.ALL_CAN_INVITE, event.getAll_can_invite() ? 1 : 0);
		values.put(TableInfo.ALL_CAN_INVITE, event.getAll_can_join() ? 1 : 0);
		values.put(TableInfo.DATE, event.getDate());
		values.put(TableInfo.DESCRIPTION, event.getDescription());
		values.put(TableInfo.TITLE, event.getTitle());
		if (event.getLocation() != null)
			values.put(TableInfo.LOCAITON_ID, event.getLocation().getId());
		if (event.getUser() != null) {
			userModel.saveAction(event.getUser());
			values.put(TableInfo.USER_ID, event.getUser().getId());
		}

		if (event.getId() != null) {
			values.put(TableInfo._ID, event.getId());
			if (fetchById(event.getId()) != null) {
				update(values);
				return event.getId();
			}
		}
		long id = insert(values);
		event.setId(id);
		return id;
	}

	@Override
	public ArrayList<Event> fill(Cursor cursor) {
		ArrayList<Event> result = new ArrayList<Event>();
		Event event;
		cursor.moveToFirst();
		while (!cursor.isAfterLast()) {
			event = new Event();
			event.setId(cursor.getLong(cursor
					.getColumnIndexOrThrow(TableInfo._ID)));
			event.setTitle(cursor.getString(cursor
					.getColumnIndexOrThrow(TableInfo.TITLE)));
			event.setDescription(cursor.getString(cursor
					.getColumnIndexOrThrow(TableInfo.DESCRIPTION)));
			event.setDate(cursor.getString(cursor
					.getColumnIndexOrThrow(TableInfo.DATE)));
			event.setAll_can_invite(cursor.getInt(cursor
					.getColumnIndexOrThrow(TableInfo.ALL_CAN_INVITE)) == 1 ? true
					: false);
			event.setAll_can_join(cursor.getInt(cursor
					.getColumnIndexOrThrow(TableInfo.ALL_CAN_JOIN)) == 1 ? true
					: false);

			Long userId = (long) cursor.getInt(cursor
					.getColumnIndexOrThrow(TableInfo.USER_ID));
			if (userId != null) {
				event.setUser(userModel.fetchById(userId));
			}
			Long locId = (long) cursor.getInt(cursor
					.getColumnIndexOrThrow(TableInfo.LOCAITON_ID));
			if (userId != null) {
				event.setLocation(locationModel.fetchById(locId));
			}
			result.add(event);
			cursor.moveToNext();
		}
		return result;
	}

}
