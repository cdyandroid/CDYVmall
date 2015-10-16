package parfois.com.cdyvmall.adapter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;



import android.content.Context;
import android.graphics.Bitmap;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import parfois.com.cdyvmall.R;
import parfois.com.cdyvmall.adapter.asynctask.DownSaveImgAsync;
import parfois.com.cdyvmall.adapter.bean.Product;

public class CategoryRightAdapter extends BaseAdapter {
	private Context context;
	private List<Product> list;
	private ViewHolder vh;
	private ListView lv;
	private Map<String, Bitmap> map=new HashMap<String, Bitmap>();

	public CategoryRightAdapter(Context context, List<Product> list,ListView lv) {
		this.context = context;
		this.list = list;
		this.lv=lv;
	}

	public void setList(List<Product> list){
		this.list=list;
	}
	
	public int getCount() {
		return list.size();
	}

	@Override
	public Object getItem(int position) {
		return list.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if(convertView==null){
			convertView=View.inflate(context, R.layout.listview_categoryright_item, null);
			vh=new ViewHolder();
			vh.list_category_ivpicUrl=(ImageView) convertView.findViewById(R.id.list_category_ivpicUrl);
			vh.list_category_tvname=(TextView) convertView.findViewById(R.id.list_category_tvname);
			convertView.setTag(vh);
		}else{
			vh=(ViewHolder) convertView.getTag();
		}
		
		vh.list_category_ivpicUrl.setContentDescription(list.get(position).getCategoryUrl());
		vh.list_category_ivpicUrl.setImageResource(R.drawable.gallery_no_pic);
		String str=list.get(position).getPicUrl();
		vh.list_category_ivpicUrl.setTag(str);
		if(!map.containsKey(str)){
			new DownSaveImgAsync(context, new DownSaveImgAsync.CallBack() {
				public void sendImage(Bitmap bm,String key) {
					ImageView iv=(ImageView) lv.findViewWithTag(key);
					if(iv!=null){
						iv.setImageBitmap(bm);
					}
				}
			},map).execute(list.get(position).getPicUrl());
		}else{
			vh.list_category_ivpicUrl.setImageBitmap(map.get(str));
		}
		
		vh.list_category_tvname.setText(list.get(position).getName());
		
		return convertView;
	}
	
	static class ViewHolder{
		ImageView list_category_ivpicUrl;
		TextView list_category_tvname;
		
	}

}
