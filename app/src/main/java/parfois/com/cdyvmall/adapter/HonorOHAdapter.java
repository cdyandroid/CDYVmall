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
import parfois.com.cdyvmall.adapter.bean.Goods;

public class HonorOHAdapter extends BaseAdapter{
	private Context context;
	private List<Goods> list;
	private ViewHolder vh;
	private ListView lv;
	private Map<String, Bitmap> map=new HashMap<String, Bitmap>();
	
	public HonorOHAdapter(Context context, List<Goods> list,ListView lv) {
		this.context = context;
		this.list = list;
		this.lv=lv;
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
			convertView=View.inflate(context, R.layout.listview_otherhoners_item, null);
			vh=new ViewHolder();
			vh.list_otherhoners_ivprdPicUrl=(ImageView) convertView.findViewById(R.id.list_otherhoners_ivprdPicUrl);
			vh.list_otherhoners_ivprdStatus=(ImageView) convertView.findViewById(R.id.list_otherhoners_ivprdStatus);
			vh.list_otherhoners_tvprdDescription=(TextView) convertView.findViewById(R.id.list_otherhoners_tvprdDescription);
			vh.list_otherhoners_tvprdName=(TextView) convertView.findViewById(R.id.list_otherhoners_tvprdName);
			convertView.setTag(vh);
		}else{
			vh=(ViewHolder) convertView.getTag();
		}
		vh.list_otherhoners_ivprdPicUrl.setContentDescription(list.get(position).getPrdId());
		vh.list_otherhoners_ivprdPicUrl.setImageResource(R.drawable.icon_no_pic);
		String str=list.get(position).getPrdPicUrl();
		vh.list_otherhoners_ivprdPicUrl.setTag(str);
		if(!map.containsKey(str)){
			new DownSaveImgAsync(context, new DownSaveImgAsync.CallBack() {
				public void sendImage(Bitmap bm,String key) {
					ImageView iv=(ImageView) lv.findViewWithTag(key);
					if(iv!=null){
						iv.setImageBitmap(bm);
					}
				}
			},map).execute(list.get(position).getPrdPicUrl());
		}else{
			vh.list_otherhoners_ivprdPicUrl.setImageBitmap(map.get(str));
		}
		
		vh.list_otherhoners_ivprdStatus.setImageResource(list.get(position).getPrdStatus());
		vh.list_otherhoners_tvprdDescription.setText(list.get(position).getPrdDescription());
		vh.list_otherhoners_tvprdName.setText(list.get(position).getPrdName());
		return convertView;
	}
	
	static class ViewHolder{
		ImageView list_otherhoners_ivprdPicUrl,list_otherhoners_ivprdStatus;
		TextView list_otherhoners_tvprdDescription,list_otherhoners_tvprdName;
		
	}
}








