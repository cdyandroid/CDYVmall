package parfois.com.cdyvmall.adapter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;



import android.content.Context;
import android.graphics.Bitmap;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import parfois.com.cdyvmall.R;
import parfois.com.cdyvmall.adapter.asynctask.DownSaveImgAsync;
import parfois.com.cdyvmall.adapter.bean.SGoods;

public class SearchAdapter extends BaseAdapter{
	private Context context;
	private List<SGoods> list;
	private ViewHolder vh;
	private Map<String, Bitmap> map=new HashMap<String, Bitmap>();
	
	private ListView lv;
	private GridView gv;
	
	public SearchAdapter(Context context, List<SGoods> list ,ListView lv) {
		this.context = context;
		this.list = list;
		this.lv=lv;
	}
	
	public SearchAdapter(Context context, List<SGoods> list , GridView gv) {
		this.context = context;
		this.list = list;
		this.gv=gv;
	}

	public int getCount() {
		return list.size();
	}

	public Object getItem(int position) {
		return list.get(position);
	}

	public long getItemId(int position) {
		return position;
	}

	public View getView(int position, View convertView, ViewGroup parent) {
		if(convertView==null){
			if(lv!=null){
				convertView=View.inflate(context, R.layout.listview_search_item, null);
				vh=new ViewHolder();
				vh.ivpic=(ImageView) convertView.findViewById(R.id.list_search_ivpicUrl);
				vh.ivprdStatus=(ImageView) convertView.findViewById(R.id.list_search_ivprdStatus);
				vh.tvname=(TextView) convertView.findViewById(R.id.list_search_tvname);
				vh.tvpromotionWord=(TextView) convertView.findViewById(R.id.list_search_tvpromotionWord);
				vh.tvprice=(TextView) convertView.findViewById(R.id.list_search_tvprice);
				convertView.setTag(vh);
			}else if(gv!=null){
				convertView=View.inflate(context, R.layout.gridview_search_item, null);
				vh=new ViewHolder();
				vh.ivpic=(ImageView) convertView.findViewById(R.id.grid_search_ivpicUrl);
				vh.ivprdStatus=(ImageView) convertView.findViewById(R.id.grid_search_ivprdStatus);
				vh.tvname=(TextView) convertView.findViewById(R.id.grid_search_tvname);
				vh.tvpromotionWord=(TextView) convertView.findViewById(R.id.grid_search_tvpromotionWord);
				vh.tvprice=(TextView) convertView.findViewById(R.id.grid_search_tvprice);
				convertView.setTag(vh);
			}
		}else{
			vh=(ViewHolder) convertView.getTag();
		}
		
		vh.ivprdStatus.setImageResource(list.get(position).getPrdStatus());
		vh.tvname.setText(list.get(position).getName());
		vh.tvprice.setText(list.get(position).getPrice());
		vh.tvpromotionWord.setText(list.get(position).getPromotionWord());
		vh.ivpic.setContentDescription(list.get(position).getPrdId());
		vh.ivpic.setImageResource(R.drawable.icon_no_pic);
		
		String str=list.get(position).getPicUrl();
		vh.ivpic.setTag(str);
		if(!map.containsKey(str)){
			new DownSaveImgAsync(context, new DownSaveImgAsync.CallBack() {
				public void sendImage(Bitmap bm,String key) {
					ImageView iv=(ImageView) (lv==null?gv.findViewWithTag(key):lv.findViewWithTag(key));
					if(iv!=null){
						iv.setImageBitmap(bm);
					}
				}
			},map).execute(list.get(position).getPicUrl());
		}else{
			vh.ivpic.setImageBitmap(map.get(str));
		}
		return convertView;
	}
	
	static class ViewHolder{
		ImageView ivpic,ivprdStatus;
		TextView tvname,tvpromotionWord,tvprice;
	}
}








