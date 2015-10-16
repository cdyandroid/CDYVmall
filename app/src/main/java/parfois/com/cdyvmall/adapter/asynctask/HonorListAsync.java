package parfois.com.cdyvmall.adapter.asynctask;

import java.util.List;
import java.util.Map;



import android.app.ProgressDialog;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.view.ViewGroup;

import parfois.com.cdyvmall.adapter.HonorAPAdapter;
import parfois.com.cdyvmall.adapter.HonorCFAdapter;
import parfois.com.cdyvmall.adapter.HonorOHAdapter;
import parfois.com.cdyvmall.adapter.bean.ActivityPrds;
import parfois.com.cdyvmall.adapter.bean.Goods;
import parfois.com.cdyvmall.adapter.utils.Constant;
import parfois.com.cdyvmall.adapter.utils.DBManager;
import parfois.com.cdyvmall.adapter.utils.HttpUtils;
import parfois.com.cdyvmall.adapter.utils.MyStatic;
import parfois.com.cdyvmall.adapter.utils.PaserUtils;
import parfois.com.cdyvmall.adapter.vmall.HonorFragment;

public class HonorListAsync extends AsyncTask<String, Void, Map<String,Object>> {
	private Context context;
	private HonorFragment.HonorViewList hvl;
	private ProgressDialog pd;
	
	public HonorListAsync(Context context,HonorFragment.HonorViewList hvl){
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
			map= PaserUtils.parserHonorListJson(buff);
		} else {
			if(HttpUtils.isHaveInternet(context)){
				byte[] buff = HttpUtils.getDataFromHttp(params[0]);
				String sql = "insert into " + Constant.TABLE_NAME + " values('" + params[0] + "','" + new String(buff, 0, buff.length)+ "')";
				DBManager.execSQL(MyStatic.db, sql);
				map=PaserUtils.parserHonorListJson(buff);
			}
		}
		return map;
	}

	@SuppressWarnings("unchecked")
	protected void onPostExecute(Map<String,Object> result) {
		if(result!=null&&result.size()!=0){
			List<ActivityPrds> listap=(List<ActivityPrds>) result.get("activityPrds");
			HonorAPAdapter haa=new HonorAPAdapter(context, listap,hvl.honor_lvactivityPrds);
			hvl.honor_lvactivityPrds.setAdapter(haa);
			ViewGroup.LayoutParams linearParams = hvl.honor_lvactivityPrds.getLayoutParams();
			linearParams.height = (int) (270*context.getResources().getDisplayMetrics().density*12);
			hvl.honor_lvactivityPrds.setLayoutParams(linearParams);
			
			List<Goods> listoh=(List<Goods>) result.get("otherHoners");
			HonorOHAdapter hoa=new HonorOHAdapter(context, listoh,hvl.honor_lvotherHoners);
			hvl.honor_lvotherHoners.setAdapter(hoa);
			ViewGroup.LayoutParams linearParams2 = hvl.honor_lvotherHoners.getLayoutParams();
			linearParams2.height = (int) (82*context.getResources().getDisplayMetrics().density*8);
			hvl.honor_lvotherHoners.setLayoutParams(linearParams2);
			
			List<Goods> listcf=(List<Goods>) result.get("commonFitting");
			HonorCFAdapter hca=new HonorCFAdapter(context, listcf,hvl.honor_gvcommonFitting);
			hvl.honor_gvcommonFitting.setAdapter(hca);
			ViewGroup.LayoutParams linearParams3 = hvl.honor_gvcommonFitting.getLayoutParams();
			linearParams3.height = (int) (200*context.getResources().getDisplayMetrics().density*4);
			hvl.honor_gvcommonFitting.setLayoutParams(linearParams3);
			hvl.honor_gvcommonFitting.setSelector(new ColorDrawable(Color.TRANSPARENT));
			
		}
		pd.dismiss();
	}
}
