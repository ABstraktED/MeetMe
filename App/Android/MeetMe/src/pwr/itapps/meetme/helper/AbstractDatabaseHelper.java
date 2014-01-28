package pwr.itapps.meetme.helper;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public abstract class AbstractDatabaseHelper extends SQLiteOpenHelper {

	public AbstractDatabaseHelper(Context context, String database_name,
			int version) {
		super(context, database_name, null, version);
	}

	public abstract void onDrop(SQLiteDatabase db);

}
