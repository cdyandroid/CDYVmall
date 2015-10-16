package parfois.com.cdyvmall.adapter.asynctask;

import java.util.List;



import android.app.ProgressDialog;
import android.content.Context;
import android.database.Cursor;
import android.os.AsyncTask;
import android.view.ViewGroup;

import parfois.com.cdyvmall.adapter.HomeHotAdapter;
import parfois.com.cdyvmall.adapter.utils.Constant;
import parfois.com.cdyvmall.adapter.utils.DBManager;
import parfois.com.cdyvmall.adapter.utils.HttpUtils;
import parfois.com.cdyvmall.adapter.utils.MyStatic;
import parfois.com.cdyvmall.adapter.utils.PaserUtils;
import parfois.com.cdyvmall.adapter.vmall.HomeFragment;

public class HomeHotAsync extends AsyncTask<String, Void, List<String>> {
	private Context context;
	private HomeFragment.HomeViewHot hvh;
	private ProgressDialog pd;
	
	public HomeHotAsync(Context context,HomeFragment.HomeViewHot hvh){
		this.context=context;
		this.hvh=hvh;
	}
	
	protected void onPreExecute() {
		pd=new ProgressDialog(context);
		pd.setTitle("Loading...");
		pd.show();
	}

	protected List<String> doInBackground(String... params) {
		List<String> list=null;
		Cursor cursor = DBManager.rawQuery(MyStatic.db, "select * from " + Constant.TABLE_NAME + " where " + Constant._ID + "='" + params[0] + "'", null);
		if (cursor.getCount() == 1) {
			byte[] buff = DBManager.CursorToByte(cursor);
			list= PaserUtils.parserHomeHotJson(buff);
		} else {
			if(HttpUtils.isHaveInternet(context)){
				byte[] buff = HttpUtils.getDataFromHttp(params[0]);
				String sql = "insert into " + Constant.TABLE_NAME + " values('" + params[0] + "','" + new String(buff, 0, buff.length)+ "')";
				DBManager.execSQL(MyStatic.db, sql);
				list=PaserUtils.parserHomeHotJson(buff);
			}
		}
		return list;
	}

	protected void onPostExecute(List<String> result) {
		if(result!=null&&result.size()!=0){
			result.remove(0);
			HomeHotAdapter hha=new HomeHotAdapter(context, result);
			hvh.home_gvhot.setAdapter(hha);
			ViewGroup.LayoutParams linearParams = hvh.home_gvhot.getLayoutParams();
			linearParams.height = (int) (42*context.getResources().getDisplayMetrics().density*3);
			hvh.home_gvhot.setLayoutParams(linearParams);
		}
		pd.dismiss();
	}
	
}
