package parfois.com.cdyvmall.adapter.utils;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class DBManager {
	private static MySQLeteHelper helper;

	public static MySQLeteHelper getHelper(Context context) {
		if (helper == null) {
			helper = new MySQLeteHelper(context);
		}
		return helper;
	}

	public static void execSQL(SQLiteDatabase db, String sql) {
		if (db != null) {
			if (sql != null && !"".equals(sql)) {
				db.execSQL(sql);
			}
		}
	}

	public static Cursor rawQuery(SQLiteDatabase db, String sql,
			String[] selectionArgs) {
		Cursor cursor = null;
		if (db != null) {
			if (sql != null && !"".equals(sql)) {
				cursor = db.rawQuery(sql, selectionArgs);
			}
		}
		return cursor;
	}

	public static byte[] CursorToByte(Cursor cursor) {
		byte[] buff = null;
		while (cursor.moveToNext()) {
			String content = (cursor.getString(cursor.getColumnIndex(Constant.CONTENT)));
			buff = content.getBytes();
		}
		return buff;
	}
}
