package parfois.com.cdyvmall.adapter.utils;

import java.io.IOException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class HttpUtils {
	
	public static boolean isHaveInternet(Context context) {
		ConnectivityManager cm=(ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo info = cm.getActiveNetworkInfo();
		if(info!=null){
			return info.isConnected();
		}else{
			return false;
		}
	}
	
	public static byte[] getDataFromHttp(String url) {
		byte[] buff=null;
		try {
			HttpClient hc=new DefaultHttpClient();
			HttpGet hg=new HttpGet(url);
			HttpResponse hr=hc.execute(hg);
			if(hr.getStatusLine().getStatusCode()==200){
				HttpEntity he=hr.getEntity();
				buff=EntityUtils.toByteArray(he);
			}
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return buff;
	}
	
}












