package parfois.com.cdyvmall.adapter.utils;

import java.lang.ref.SoftReference;
import java.util.LinkedHashMap;

import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;

public class MyStatic {
	public static LinkedHashMap<String, SoftReference<Bitmap>> hashMap = new LinkedHashMap<String, SoftReference<Bitmap>>();
	public static MyLru lru = new MyLru(
			(int) (Runtime.getRuntime().maxMemory() / 8.0), hashMap);
	
	public static SQLiteDatabase db;
}
