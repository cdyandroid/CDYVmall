package parfois.com.cdyvmall.adapter.asynctask;

import java.util.List;



import android.app.ProgressDialog;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Color;
import android.os.AsyncTask;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Toast;

import parfois.com.cdyvmall.R;
import parfois.com.cdyvmall.adapter.SearchAdapter;
import parfois.com.cdyvmall.adapter.bean.SGoods;
import parfois.com.cdyvmall.adapter.utils.Constant;
import parfois.com.cdyvmall.adapter.utils.DBManager;
import parfois.com.cdyvmall.adapter.utils.HttpUtils;
import parfois.com.cdyvmall.adapter.utils.MyStatic;
import parfois.com.cdyvmall.adapter.utils.PaserUtils;
import parfois.com.cdyvmall.adapter.vmall.SearchActivity;

public class SearchAsync extends AsyncTask<String, Void, List<SGoods>>{
	private Context context;
	private SearchActivity.ViewSearch vs;
	private SearchAdapter sa,sagv;
	private List<SGoods> salist;
	private ProgressDialog pd;
	
	private boolean isshow;
	public SearchAsync(Context context, SearchActivity.ViewSearch vs,SearchAdapter sa,SearchAdapter sagv,List<SGoods> salist,boolean isshow) {
		this.context = context;
		this.vs=vs;
		this.sa=sa;
		this.sagv=sagv;
		this.salist=salist;
		this.isshow=isshow;
	}

	protected void onPreExecute() {
		show();
		pd=new ProgressDialog(context);
		pd.setTitle("Loading...");
		pd.show();
	}

	protected List<SGoods> doInBackground(String... params) {
		List<SGoods> list=null;
		Cursor cursor = DBManager.rawQuery(MyStatic.db, "select * from " + Constant.TABLE_NAME + " where " + Constant._ID + "='" + params[0] + "'", null);
		if (cursor.getCount() == 1) {
			byte[] buff = DBManager.CursorToByte(cursor);
			list= PaserUtils.parserSearchListJson(buff);
		} else {
			if(HttpUtils.isHaveInternet(context)){
				byte[] buff = HttpUtils.getDataFromHttp(params[0]);
				String sql = "insert into " + Constant.TABLE_NAME + " values('" + params[0] + "','" + new String(buff, 0, buff.length)+ "')";
				DBManager.execSQL(MyStatic.db, sql);
				list=PaserUtils.parserSearchListJson(buff);
			}
		}
		return list;
	}

	protected void onPostExecute(final List<SGoods> result) {
		if(result!=null&&result.size()!=0){
			salist.addAll(result);
			
			sa.notifyDataSetChanged();
			sagv.notifyDataSetChanged();
		}else{
			Toast.makeText(context, "没有更多数据！", Toast.LENGTH_SHORT).show();
		}
		vs.search_ivchangeview.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				change();
			}
		});
		pd.dismiss();
	}
	
	private void show() {
		vs.search_ivchangeview.setVisibility(View.VISIBLE);
		vs.search_ll_tv1.setText("相关度");
		vs.search_ll_tv1.setTextColor(Color.parseColor("#aa0000"));
		vs.search_ll_tv2.setVisibility(View.VISIBLE);
		vs.search_ll_tv3.setVisibility(View.VISIBLE);
		vs.search_ll_tv4.setVisibility(View.VISIBLE);
		vs.search_ll_tv1.setClickable(true);
		vs.search_gvword.setVisibility(View.GONE);
		if(isshow){
			showlv();
		}else{
			showgv();
		}
	}
	
	private void change() {
		if(vs.search_gv.getVisibility()==View.GONE){
			showgv();
		}else{
			showlv();
		}
	}
	private void showgv() {
		vs.search_ivchangeview.setImageResource(R.drawable.list_normal);
		vs.search_gv.setVisibility(View.VISIBLE);
		vs.search_lv.setVisibility(View.GONE);
	}
	private void showlv() {
		vs.search_ivchangeview.setImageResource(R.drawable.grid_normal);
		vs.search_lv.setVisibility(View.VISIBLE);
		vs.search_gv.setVisibility(View.GONE);
	}
}
