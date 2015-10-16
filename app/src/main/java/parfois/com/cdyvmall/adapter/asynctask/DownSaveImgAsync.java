package parfois.com.cdyvmall.adapter.asynctask;

import java.lang.ref.SoftReference;
import java.util.Map;



import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;

import parfois.com.cdyvmall.adapter.utils.HttpUtils;
import parfois.com.cdyvmall.adapter.utils.MyStatic;
import parfois.com.cdyvmall.adapter.utils.ReadWriteUtils;

public class DownSaveImgAsync extends AsyncTask<String, Integer, Bitmap> {
	private Context context;
	private CallBack cb;
	private Map<String, Bitmap> map;
	private String key;
	
	public DownSaveImgAsync(Context context, CallBack cb,Map<String,Bitmap> map) {
		this.context = context;
		this.cb = cb;
		this.map=map;
	}

	protected Bitmap doInBackground(String... params) {
		key=params[0];
		params[0]=params[0].replace(" ", "%20");
		Bitmap bm = new MyStatic().lru.get(params[0]);
		if(bm==null){
			SoftReference<Bitmap> sr =  new MyStatic().hashMap.get(params[0]);
			if (sr != null) {
				bm = sr.get();
				new MyStatic().hashMap.remove(params[0]);
				new MyStatic().lru.put(params[0], bm);
			}else{
				String str = params[0].replace("/", "").replace(":", "").replace(".", "");
				if (ReadWriteUtils.hasFile(str)) {
					byte[] buff = ReadWriteUtils.readImg(str);
					bm=BitmapFactory.decodeByteArray(buff, 0, buff.length);
					Log.e("空指针异常1！",params[0]);
					Log.e("空指针异常2！",bm+"");
					try {
						new MyStatic().lru.put(params[0], bm);
					} catch (Exception e) {
						e.printStackTrace();
					}
				} else {
					if (HttpUtils.isHaveInternet(context)) {
						byte[] buff = HttpUtils.getDataFromHttp(params[0]);
						ReadWriteUtils.writeImg(buff, str);
						bm=BitmapFactory.decodeByteArray(buff, 0, buff.length);
						new MyStatic().lru.put(params[0], bm);
					}
				}
			}
		}
		
		return bm;
	}

	protected void onPostExecute(Bitmap result) {
		if (result != null && result.getByteCount() != 0) {
			map.put(key, result);			
			cb.sendImage(result,key);
		}
	}

	public interface CallBack {
		public abstract void sendImage(Bitmap bm, String key);
	}
}
