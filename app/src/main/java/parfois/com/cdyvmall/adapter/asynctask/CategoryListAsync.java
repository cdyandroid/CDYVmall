package parfois.com.cdyvmall.adapter.asynctask;

import java.util.List;



import android.app.ProgressDialog;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Color;
import android.os.AsyncTask;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.TextView;

import parfois.com.cdyvmall.R;
import parfois.com.cdyvmall.adapter.CategoryLeftAdapter;
import parfois.com.cdyvmall.adapter.CategoryRightAdapter;
import parfois.com.cdyvmall.adapter.bean.Category;
import parfois.com.cdyvmall.adapter.bean.Product;
import parfois.com.cdyvmall.adapter.utils.Constant;
import parfois.com.cdyvmall.adapter.utils.DBManager;
import parfois.com.cdyvmall.adapter.utils.HttpUtils;
import parfois.com.cdyvmall.adapter.utils.MyStatic;
import parfois.com.cdyvmall.adapter.utils.PaserUtils;
import parfois.com.cdyvmall.adapter.vmall.CategoryFragment;

public class CategoryListAsync extends AsyncTask<String, Void, List<Category>> {
	private Context context;
	private CategoryFragment.CategoryView cv;
	
	private ProgressDialog pd;
	private List<Product> rightlist;
	private CategoryRightAdapter cra ;

	public CategoryListAsync(Context context, CategoryFragment.CategoryView cv) {
		this.context = context;
		this.cv = cv;
	}

	protected void onPreExecute() {
		pd = new ProgressDialog(context);
		pd.setTitle("Loading...");
		pd.show();
	}

	protected List<Category> doInBackground(String... params) {
		List<Category> list = null;
		Cursor cursor = DBManager.rawQuery(MyStatic.db, "select * from " + Constant.TABLE_NAME + " where " + Constant._ID + "='" + params[0] + "'", null);
		if (cursor.getCount() == 1) {
			byte[] buff = DBManager.CursorToByte(cursor);
			list = PaserUtils.parserCategoryJson(buff);
		} else {
			if(HttpUtils.isHaveInternet(context)){
				byte[] buff = HttpUtils.getDataFromHttp(params[0]);
				String sql = "insert into " + Constant.TABLE_NAME + " values('" + params[0] + "','" + new String(buff, 0, buff.length)+ "')";
				DBManager.execSQL(MyStatic.db, sql);
				list = PaserUtils.parserCategoryJson(buff);
			}
		}
		return list;
	}

	@SuppressWarnings("deprecation")
	protected void onPostExecute(final List<Category> result) {
		if (result != null && result.size() != 0) {
			CategoryLeftAdapter cla = new CategoryLeftAdapter(context, result);
			cv.category_lvleft.setAdapter(cla);
			ViewGroup.LayoutParams linearParams = cv.category_lvleft
					.getLayoutParams();
			WindowManager wm = (WindowManager) context
					.getSystemService(Context.WINDOW_SERVICE);
			linearParams.height = (int) (wm.getDefaultDisplay().getHeight() * context
					.getResources().getDisplayMetrics().density);
			cv.category_lvleft.setLayoutParams(linearParams);
			
			rightlist=result.get(0).getList();
			cra = new CategoryRightAdapter(context, rightlist,cv.category_lvright);
			cv.category_lvright.setAdapter(cra);
			cv.category_lvleft.setOnItemClickListener(new OnItemClickListener() {
						public void onItemClick(AdapterView<?> parent,
								View view, int position, long id) {
							for (int i = 0; i < parent.getCount(); i++) {
								View v = parent.getChildAt(parent.getCount()
										- i - 1);
								if (position == parent.getCount() - i - 1) {
									v.setBackgroundResource(R.drawable.left_arrow_onclick);
									TextView tv = (TextView) v
											.findViewById(R.id.list_category_left_tvname);
									tv.setTextColor(Color.parseColor("#960007"));
								} else {
									v.setBackgroundResource(R.drawable.cglist_bg_solid);
									TextView tv = (TextView) v
											.findViewById(R.id.list_category_left_tvname);
									tv.setTextColor(Color.parseColor("#000000"));
								}
							}
//							CategoryRightAdapter cra = new CategoryRightAdapter(context, result.get(position).getList(),cv.category_lvright);
//							cv.category_lvright.setAdapter(cra);
							cra.setList(result.get(position).getList());
							cra.notifyDataSetChanged();
						}
					});
		}
		pd.dismiss();
	}
}
