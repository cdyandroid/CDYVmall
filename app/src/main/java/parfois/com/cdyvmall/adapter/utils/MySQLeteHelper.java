package parfois.com.cdyvmall.adapter.utils;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class MySQLeteHelper extends SQLiteOpenHelper {

	public MySQLeteHelper(Context context, String name, CursorFactory factory,
			int version) {
		super(context, name, factory, version);
	}

	public MySQLeteHelper(Context context) {
		super(context, Constant.DATABASE_NAME, null, Constant.DATABASE_VERSION);
	}

	public void onCreate(SQLiteDatabase db) {
		String sql = "create table " + Constant.TABLE_NAME + " (" + Constant._ID
				+ " varchar(225) primary key," + Constant.CONTENT
				+ " text)";
		db.execSQL(sql);
	}

	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

	}

}
