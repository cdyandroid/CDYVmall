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
import android.widget.TextView;

import parfois.com.cdyvmall.R;
import parfois.com.cdyvmall.adapter.asynctask.DownSaveImgAsync;
import parfois.com.cdyvmall.adapter.bean.Goods;

public class HomeListAdapter extends BaseAdapter{
	private Context context;
	private List<Goods> list;
	private ViewHolder vh;
	private GridView gv;
	private Map<String, Bitmap> map=new HashMap<String, Bitmap>();
	
	public HomeListAdapter(Context context, List<Goods> list,GridView gv) {
		this.context = context;
		list.add(2, null);
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
			convertView=View.inflate(context, R.layout.gridview_star_item, null);
			if(position==0){
				convertView=View.inflate(context, R.layout.gridview_star_item_frist, null);
			}
			vh=new ViewHolder();
			vh.grid_star_tvprdDescription=(TextView) convertView.findViewById(R.id.grid_star_tvprdDescription);
			vh.grid_star_tvprdName=(TextView) convertView.findViewById(R.id.grid_star_tvprdName);
			vh.grid_stat_tvprdUnitPrice=(TextView) convertView.findViewById(R.id.grid_stat_tvprdUnitPrice);
			vh.grid_stat_ivprdPic=(ImageView) convertView.findViewById(R.id.grid_stat_ivprdPic);
			vh.grid_stat_ivprdStatus=(ImageView) convertView.findViewById(R.id.grid_stat_ivprdStatus);
			convertView.setTag(vh);
		}else{
			vh=(ViewHolder) convertView.getTag();
		}
		if(list.get(position)!=null){
			vh.grid_star_tvprdDescription.setText(list.get(position).getPrdDescription());
			vh.grid_star_tvprdName.setText(list.get(position).getPrdName());
			vh.grid_stat_tvprdUnitPrice.setText(list.get(position).getPrdUnitPrice());
			vh.grid_stat_ivprdStatus.setImageResource(list.get(position).getPrdStatus());
			
			vh.grid_stat_ivprdPic.setContentDescription(list.get(position).getPrdId());
			vh.grid_stat_ivprdPic.setImageResource(R.drawable.icon_no_pic);
			String str=list.get(position).getPrdPicUrl();
			vh.grid_stat_ivprdPic.setTag(str);
			if(!map.containsKey(str)){
				new DownSaveImgAsync(context, new DownSaveImgAsync.CallBack() {
					public void sendImage(Bitmap bm,String key) {
						ImageView iv=(ImageView) gv.findViewWithTag(key);
						if(iv!=null){
							iv.setImageBitmap(bm);
						}
					}
				},map).execute(list.get(position).getPrdPicUrl());
			}else{
				vh.grid_stat_ivprdPic.setImageBitmap(map.get(str));
			}
		}else{
			convertView.setVisibility(View.INVISIBLE);
		}
		
		return convertView;
	}
	
	static class ViewHolder{
		ImageView grid_stat_ivprdPic,grid_stat_ivprdStatus;
		TextView grid_star_tvprdName,grid_star_tvprdDescription,grid_stat_tvprdUnitPrice;
		
	}
}








