package pwr.itapps.meetmee.model;

import java.util.List;

import pwr.itapps.meetme.helper.AbstractDatabaseHelper;
import pwr.itapps.meetmee.model.entity.Entity;
import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

public abstract class Model<T extends Entity> extends AbstractModel<T> {

	public Model(String name, Context nContext) {
		super(name, nContext);
	}

	private static class DatabaseHelper extends AbstractDatabaseHelper {

		private static final int DATABASE_VERSION = 6;
		private static final String DATABASE_NAME = "meetme_db";

		@SuppressWarnings("unused")
		private Context context;

		public DatabaseHelper(Context context) {
			super(context, DATABASE_NAME, DATABASE_VERSION);
			this.context = context;
		}

		@Override
		public void onCreate(SQLiteDatabase db) {
			db.execSQL(UserModel.TableInfo.getCreateStatement());
			db.execSQL(EventModel.TableInfo.getCreateStatement());
			db.execSQL(InvitationModel.TableInfo.getCreateStatement());
			db.execSQL(LocationModel.TableInfo.getCreateStatement());
		}

		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
			onDrop(db);
			onCreate(db);
		}

		public void onDrop(SQLiteDatabase db) {
			db.execSQL(UserModel.TableInfo.getDropStatement());
			db.execSQL(EventModel.TableInfo.getDropStatement());
			db.execSQL(LocationModel.TableInfo.getDropStatement());
			db.execSQL(InvitationModel.TableInfo.getDropStatement());
		}
	}

	@Override
	public void open(Context context) throws SQLException {
		if (!hasDb()) {
			databaseHelper = new DatabaseHelper(context);
		}
	}

	public void saveAction(List<T> objects) {
		for (T o : objects) {
			saveAction(o);
		}
	}
}
