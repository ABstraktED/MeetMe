package pwr.itapps.meetmee.model;

import java.util.ArrayList;

import pwr.itapps.meetmee.model.entity.Event;
import pwr.itapps.meetmee.model.entity.Invitation;
import pwr.itapps.meetmee.model.entity.User;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.provider.BaseColumns;

public class InvitationModel extends Model<Invitation> {

	private UserModel userModel;
	private EventModel eventModel;

	public static String tableName = "invitation";

	public InvitationModel(Context context) {
		super(tableName, context);
		userModel = new UserModel(context, true);
		eventModel = new EventModel(context, true);
	}

	public InvitationModel(Context context, boolean inner) {
		super(tableName, context);
		innerModel = inner;
		userModel = new UserModel(context, true);
		eventModel = new EventModel(context, true);
	}

	public static final class TableInfo implements BaseColumns {

		public static final String EVENT_ID = "eventId";
		public static final String USER_ID = "userId";
		public static final String CONFIRMED = "confirmed";
		public static final String STATUS = "status";

		public static String getCreateStatement() {
			return String
					.format("CREATE TABLE %s (%s INTEGER PRIMARY KEY, %s INTEGER,%s INTEGER, %s TEXT,%s TEXT);",
							tableName, _ID, EVENT_ID, USER_ID, CONFIRMED,
							STATUS);
		}

		public static String getDropStatement() {
			return String.format("DROP TABLE IF EXISTS " + tableName);
		}

		public static String[] getColums() {
			return new String[] { _ID, EVENT_ID, USER_ID, CONFIRMED, STATUS };
		}
	}

	@Override
	public long saveAction(Invitation invitation) {
		ContentValues values = new ContentValues();
		if (invitation.getEvent() != null)
			values.put(TableInfo.EVENT_ID, invitation.getEvent().getId());
		if (invitation.getUser() != null)
			values.put(TableInfo.USER_ID, invitation.getUser().getId());

		values.put(TableInfo.CONFIRMED, invitation.getConfirmation() ? true
				: false);
		values.put(TableInfo.STATUS, invitation.getStatus() ? true : false);

		if (invitation.getId() != null) {
			values.put(TableInfo._ID, invitation.getId());
			if (fetchById(invitation.getId()) != null) {
				update(values);
				return invitation.getId();
			}
		}
		long id = insert(values);
		invitation.setId(id);
		return id;
	}

	@Override
	public ArrayList<Invitation> fill(Cursor cursor) {
		ArrayList<Invitation> result = new ArrayList<Invitation>();
		Invitation invitation;
		cursor.moveToFirst();
		while (!cursor.isAfterLast()) {
			invitation = new Invitation();
			invitation.setId(cursor.getLong(cursor
					.getColumnIndexOrThrow(TableInfo._ID)));
			invitation.setConfirmation(cursor.getInt(cursor
					.getColumnIndexOrThrow(TableInfo.CONFIRMED)) == 1 ? true
					: false);
			invitation.setStatus(cursor.getInt(cursor
					.getColumnIndexOrThrow(TableInfo.STATUS)) == 1 ? true
					: false);
			Long userId = cursor.getLong(cursor
					.getColumnIndexOrThrow(TableInfo.USER_ID));
			User user = null;
			if (userId != null) {
				user = userModel.fetchById(userId);
			}
			invitation.setUser(user);
			Long eventId = cursor.getLong(cursor
					.getColumnIndexOrThrow(TableInfo.EVENT_ID));
			Event event = null;
			if (eventId != null) {
				event = eventModel.fetchById(userId);
			}
			invitation.setEvent(event);
			result.add(invitation);
			cursor.moveToNext();
		}
		return result;
	}

}
