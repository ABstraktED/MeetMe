package pwr.itapps.meetmee.model;

import java.util.ArrayList;

import pwr.itapps.meetmee.model.entity.User;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.provider.BaseColumns;

public class UserModel extends Model<User> {

	public static String tableName = "user";

	public UserModel(Context context) {
		super(tableName, context);
	}

	public UserModel(Context context, boolean inner) {
		super(tableName, context);
		innerModel = inner;
	}

	public static final class TableInfo implements BaseColumns {
		public static final String NAME = "name";
		public static final String USERNAME = "username";
		public static final String EMAIL = "email";
		public static final String PASSWORD = "password";
		public static final String PHONE = "phone";
		public static final String STATUS = "status";
		public static final String IMAGE_ADDRESS = "imageAddress";
		public static final String IS_OWNER = "isOwner";

		public static String getCreateStatement() {
			return String
					.format("CREATE TABLE %s (%s INTEGER PRIMARY KEY, %s TEXT,%s TEXT, %s TEXT,%s TEXT, %s TEXT, %s TEXT,%s TEXT,%s INTEGER);",

					tableName, _ID, NAME, USERNAME, EMAIL, PASSWORD, PHONE,
							STATUS, IMAGE_ADDRESS, IS_OWNER);
		}

		public static String getDropStatement() {
			return String.format("DROP TABLE IF EXISTS " + tableName);
		}

		public static String[] getColums() {
			return new String[] { _ID, NAME, USERNAME, EMAIL, PASSWORD, PHONE,
					STATUS, IMAGE_ADDRESS, IS_OWNER };
		}
	}

	public void deleteOwner() {
		String where = TableInfo.IS_OWNER + "==" + 1;
		deleteByWhere(where);
	}

	public User fetchOwner() {
		String where = TableInfo.IS_OWNER + "==" + 1;
		return fetchByWhere(where).get(0);
	}

	public ArrayList<User> fetchAllUsers() {
		String where = TableInfo.IS_OWNER + "!=" + 1;
		return fetchByWhere(where);
	}

	
	@Override
	public long saveAction(User user) {
		ContentValues values = new ContentValues();
		values.put(TableInfo._ID, user.getId());
		values.put(TableInfo.NAME, user.getName());
		values.put(TableInfo.USERNAME, user.getUsername());
		values.put(TableInfo.EMAIL, user.getEmail());
		values.put(TableInfo.PASSWORD, user.getPassword());
		values.put(TableInfo.PHONE, user.getPhone());
		values.put(TableInfo.STATUS, user.getStatus());
		values.put(TableInfo.IMAGE_ADDRESS, user.getImageAddress());
		values.put(TableInfo.IS_OWNER, user.isOwner() ? 1 : 0);

		if (user.getId() != null) {
			values.put(TableInfo._ID, user.getId());
			if (fetchById(user.getId()) != null) {
				update(values);
				return user.getId();
			}
		}
		long id = insert(values);
		user.setId(id);
		return id;
	}

	@Override
	public ArrayList<User> fill(Cursor cursor) {
		ArrayList<User> result = new ArrayList<User>();
		User user;
		cursor.moveToFirst();
		while (!cursor.isAfterLast()) {
			user = new User();

			user.setId(cursor.getLong(cursor
					.getColumnIndexOrThrow(TableInfo._ID)));
			user.setName(cursor.getString(cursor
					.getColumnIndexOrThrow(TableInfo.NAME)));
			user.setUsername(cursor.getString(cursor
					.getColumnIndexOrThrow(TableInfo.USERNAME)));
			user.setEmail(cursor.getString(cursor
					.getColumnIndexOrThrow(TableInfo.EMAIL)));
			user.setPassword(cursor.getString(cursor
					.getColumnIndexOrThrow(TableInfo.PASSWORD)));
			user.setPhone(cursor.getString(cursor
					.getColumnIndexOrThrow(TableInfo.PHONE)));
			user.setStatus(cursor.getString(cursor
					.getColumnIndexOrThrow(TableInfo.STATUS)));
			user.setImageAddress(cursor.getString(cursor
					.getColumnIndexOrThrow(TableInfo.IMAGE_ADDRESS)));
			user.setOwner(cursor.getInt(cursor
					.getColumnIndexOrThrow(TableInfo.STATUS)) == 1 ? true
					: false);
			result.add(user);
			cursor.moveToNext();
		}

		return result;
	}

}
