package parfois.com.cdyvmall.adapter.asynctask;

import java.util.List;
import java.util.Map;


import android.app.ProgressDialog;
import android.content.Context;
import android.database.Cursor;
import android.os.AsyncTask;
import android.view.ViewGroup;

import parfois.com.cdyvmall.adapter.HomeListAdapter;
import parfois.com.cdyvmall.adapter.HomeListRecoAdapter;
import parfois.com.cdyvmall.adapter.bean.Goods;
import parfois.com.cdyvmall.adapter.utils.Constant;
import parfois.com.cdyvmall.adapter.utils.DBManager;
import parfois.com.cdyvmall.adapter.utils.HttpUtils;
import parfois.com.cdyvmall.adapter.utils.MyStatic;
import parfois.com.cdyvmall.adapter.utils.PaserUtils;
import parfois.com.cdyvmall.adapter.vmall.HomeFragment;

public class HomeListAsync extends AsyncTask<String, Void, Map<String,Object>> {
	private Context context;
	private HomeFragment.HomeViewList hvl;
	private ProgressDialog pd;
	
	public HomeListAsync(Context context,HomeFragment.HomeViewList hvl){
		this.context=context;
		this.hvl=hvl;
	}
	
	protected void onPreExecute() {
		pd=new ProgressDialog(context);
		pd.setTitle("Loading...");
		pd.show();
	}

	protected Map<String,Object> doInBackground(String... params) {
		Map<String,Object> map=null;
		Cursor cursor = DBManager.rawQuery(MyStatic.db, "select * from " + Constant.TABLE_NAME + " where " + Constant._ID + "='" + params[0] + "'", null);
		if (cursor.getCount() == 1) {
			byte[] buff = DBManager.CursorToByte(cursor);
			map= PaserUtils.parserHomeListJson(buff);
		} else {
			if(HttpUtils.isHaveInternet(context)){
				byte[] buff = HttpUtils.getDataFromHttp(params[0]);
				String sql = "insert into " + Constant.TABLE_NAME + " values('" + params[0] + "','" + new String(buff, 0, buff.length)+ "')";
				DBManager.execSQL(MyStatic.db, sql);
				map=PaserUtils.parserHomeListJson(buff);
			}
		}
		return map;
	}

	@SuppressWarnings("unchecked")
	protected void onPostExecute(Map<String,Object> result) {
		if(result!=null&&result.size()!=0){
			List<Goods> list0=(List<Goods>) result.get("明星商品栏目");
			HomeListAdapter hla=new HomeListAdapter(context, list0,hvl.home_gvstar);
			hvl.home_gvstar.setAdapter(hla);
			ViewGroup.LayoutParams linearParams = hvl.home_gvstar.getLayoutParams();
			linearParams.height = (int) (102*context.getResources().getDisplayMetrics().density*(hvl.home_gvstar.getCount()/2));
			hvl.home_gvstar.setLayoutParams(linearParams);
			
			List<Goods> list1=(List<Goods>) result.get("精品推荐");
			HomeListRecoAdapter hlra=new HomeListRecoAdapter(context, list1,hvl.home_gvreco);
			hvl.home_gvreco.setAdapter(hlra);
			ViewGroup.LayoutParams linearParams1 = hvl.home_gvreco.getLayoutParams();
			linearParams1.height = (int) (200*context.getResources().getDisplayMetrics().density*(hvl.home_gvreco.getCount()/2));
			hvl.home_gvreco.setLayoutParams(linearParams1);
		}
		pd.dismiss();
	}
}
