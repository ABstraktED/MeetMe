package pwr.itapps.meetmee.model;

import java.util.ArrayList;
import java.util.List;

import pwr.itapps.meetme.helper.AbstractDatabaseHelper;
import pwr.itapps.meetmee.model.entity.Entity;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.provider.BaseColumns;

public abstract class AbstractModel<T extends Entity> {

	public String tableName;
	public static Context context;

	protected static AbstractDatabaseHelper databaseHelper;

	protected boolean innerModel;

	public AbstractModel(String name, Context nContext) {
		tableName = name;
		if (context == null)
			context = nContext;
		innerModel = false;
	}


	public abstract void open(Context context) throws SQLException;
//	{
//		if (!hasDb()) {
//			databaseHelper = new DatabaseHelper(context);
//		}
//	}

	public static void dropDatabase() {
		if (hasDb()) {
			databaseHelper.onDrop(databaseHelper.getWritableDatabase());
		}
	}

	public static void createDatabase() {
		if (hasDb()) {
			databaseHelper.onCreate(databaseHelper.getWritableDatabase());
		}
	}

	public static void close() {
		if (databaseHelper != null)
			databaseHelper.close();
	}

	public static boolean hasDb() {
		if (databaseHelper != null) {
			return databaseHelper.getWritableDatabase() != null;
		} else {
			return false;
		}
	}

	protected long insert(ContentValues values) {
		long result = -1;
		synchronized (AbstractModel.class) {
			if (!hasDb()) {
				open(context);
			}
			SQLiteDatabase database = databaseHelper.getWritableDatabase();
			result = database.insert(tableName, null, values);
		}
		return result;
	}

	protected long update(ContentValues values) {
		long result = -1;
		synchronized (AbstractModel.class) {
			if (!hasDb())
				open(context);
			SQLiteDatabase database = databaseHelper.getWritableDatabase();
			result = database.update(tableName, values, BaseColumns._ID
					+ " = ?",
					new String[] { values.getAsString(BaseColumns._ID) });
		}
		return result;
	}

	protected long update(ContentValues values, String where, String[] args) {
		long result = -1;
		synchronized (AbstractModel.class) {
			if (!hasDb())
				open(context);
			SQLiteDatabase database = databaseHelper.getWritableDatabase();
			result = database.update(tableName, values, where, args);
		}
		return result;
	}

	public int delete(long id) {
		int result = -1;
		synchronized (AbstractModel.class) {
			if (!hasDb())
				open(context);
			result = databaseHelper.getWritableDatabase().delete(tableName,
					BaseColumns._ID + " = ?",
					new String[] { String.valueOf(id) });
		}

		return result;
	}

	public int deleteByWhere(String where) {
		int result = -1;
		synchronized (AbstractModel.class) {
			if (!hasDb())
				open(context);
			result = databaseHelper.getWritableDatabase().delete(tableName,
					where, null);
		}

		return result;
	}

	public int deleteByWhere(String where, String[] args) {
		int result = -1;
		synchronized (AbstractModel.class) {
			if (!hasDb())
				open(context);
			result = databaseHelper.getWritableDatabase().delete(tableName,
					where, args);
		}
		return result;
	}

	protected Cursor fetch(long id) {
		String where = BaseColumns._ID + "=" + id;
		return fetchByWhereClause(where);
	}

	protected Cursor fetchByWhereClause(String where) {
		return fetchByWhereClause(where, null);
	}

	protected Cursor fetchByWhereClause(String where, String orderBy) {
		Cursor result = null;
		SQLiteDatabase database;
		synchronized (AbstractModel.class) {
			if (!hasDb())
				open(context);
			database = databaseHelper.getReadableDatabase();
			result = database.query(tableName, null, where, null, null, null,
					orderBy);
		}
		return result;
	}

	protected Cursor fetchByWhereClause(String[] what, String where) {
		Cursor result = null;
		SQLiteDatabase database;
		synchronized (AbstractModel.class) {
			if (!hasDb())
				open(context);
			database = databaseHelper.getReadableDatabase();
			result = database.query(tableName, what, where, null, null, null,
					null);
		}
		return result;
	}

	protected Cursor fetchDistinctByWhereClause(String where) {
		Cursor result = null;
		SQLiteDatabase database;
		synchronized (AbstractModel.class) {
			if (!hasDb())
				open(context);
			database = databaseHelper.getReadableDatabase();
			result = database.query(true, tableName, null, where, null, null,
					null, null, null);
		}
		return result;
	}

	public long save(T object) {
		return save(object, false);
	}

	public long save(T object, boolean isList) {
		long result = -1;

		if(!isList)
			beforeSave();

		try {
			result = saveAction(object);

			if(!isList)
				setTransactionSuccessful();

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if(!isList)
				endTransaction();
		}

		return result;
	}

	protected void beforeSave() {
		if (!innerModel)
			beginTransaction();
	}

	protected void afterSave() {
		if (!innerModel)
			endTransaction();
	}

	protected abstract long saveAction(T object);

	public void save(List<T> objects) {
		
		beforeSave();

		try {

			for (T obj : objects)
				obj.setId(save(obj, true));
	
			setTransactionSuccessful();

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			endTransaction();
		}
	}

	public T fetchById(long id) {

		Cursor cursor = fetch(id);

		if (cursor == null)
			return null;

		ArrayList<T> list = fill(cursor);

		if (list == null || list.size() == 0)
			return null;

		return list.get(0);
	}

	public ArrayList<T> fetchAll() {
		Cursor cursor = fetchByWhereClause(null);

		if (cursor == null)
			return null;

		return fill(cursor);
	}

	public ArrayList<T> fetchByWhere(String where) {
		Cursor cursor = fetchByWhereClause(where);

		if (cursor == null)
			return null;

		return fill(cursor);
	}

	public abstract ArrayList<T> fill(Cursor cursor);

	protected static boolean beginTransaction() {
		synchronized (AbstractModel.class) {
			if (databaseHelper != null
					&& !databaseHelper.getWritableDatabase().inTransaction()) {

				databaseHelper.getWritableDatabase().beginTransaction();

				return true;
			}
			return false;
		}
	}

	protected static synchronized boolean endTransaction() {
		synchronized (AbstractModel.class) {
			if (databaseHelper != null
					&& databaseHelper.getWritableDatabase().inTransaction()) {

				databaseHelper.getWritableDatabase().endTransaction();
				return true;
			}
			return false;
		}
	}

	protected static synchronized void setTransactionSuccessful() {
		synchronized (AbstractModel.class) {
			if (databaseHelper != null
					&& databaseHelper.getWritableDatabase().inTransaction()) {

				databaseHelper.getWritableDatabase().setTransactionSuccessful();
			}
		}
	}
}
