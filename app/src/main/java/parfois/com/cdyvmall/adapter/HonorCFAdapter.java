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

public class HonorCFAdapter extends BaseAdapter{
	private Context context;
	private List<Goods> list;
	private ViewHolder vh;
	private GridView gv;
	private Map<String, Bitmap> map=new HashMap<String, Bitmap>();
	
	public HonorCFAdapter(Context context, List<Goods> list,GridView gv) {
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
			convertView=View.inflate(context, R.layout.gridview_commonfitting_item, null);
			vh=new ViewHolder();
			vh.grid_commonfitting_ivprdPicUrl=(ImageView) convertView.findViewById(R.id.grid_commonfitting_ivprdPicUrl);
			vh.grid_commonfitting_tvprdName=(TextView) convertView.findViewById(R.id.grid_commonfitting_tvprdName);
			convertView.setTag(vh);
		}else{
			vh=(ViewHolder) convertView.getTag();
		}
		vh.grid_commonfitting_ivprdPicUrl.setContentDescription(list.get(position).getPrdId());
		vh.grid_commonfitting_ivprdPicUrl.setImageResource(R.drawable.icon_no_pic);
		String str=list.get(position).getPrdPicUrl();
		vh.grid_commonfitting_ivprdPicUrl.setTag(str);
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
			vh.grid_commonfitting_ivprdPicUrl.setImageBitmap(map.get(str));
		}
		
		vh.grid_commonfitting_tvprdName.setText(list.get(position).getPrdName());
		return convertView;
	}
	
	static class ViewHolder{
		ImageView grid_commonfitting_ivprdPicUrl;
		TextView grid_commonfitting_tvprdName;
		
	}
}








