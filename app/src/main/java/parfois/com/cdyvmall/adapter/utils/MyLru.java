package parfois.com.cdyvmall.adapter.utils;

import java.lang.ref.SoftReference;
import java.util.LinkedHashMap;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.util.LruCache;

@SuppressLint("NewApi")
public class MyLru extends LruCache<String, Bitmap> {
	private LinkedHashMap<String,SoftReference<Bitmap>> hashMap;

	public MyLru(int maxSize,LinkedHashMap<String,SoftReference<Bitmap>> hashMap) {
		super(maxSize);
		this.hashMap=hashMap;
	}
	
	protected int sizeOf(String key, Bitmap value) {
		return value.getHeight()*value.getRowBytes();
	}
	
	protected void entryRemoved(boolean evicted, String key, Bitmap oldValue,
			Bitmap newValue) {
		if(evicted){
			SoftReference<Bitmap> sr=new SoftReference<Bitmap>(oldValue);
			hashMap.put(key, sr);
		}
	}

}
