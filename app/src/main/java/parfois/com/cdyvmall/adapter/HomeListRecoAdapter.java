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

public class HomeListRecoAdapter extends BaseAdapter {
	private Context context;
	private List<Goods> list;
	private ViewHolder vh;
	private GridView gv;
	private Map<String, Bitmap> map=new HashMap<String, Bitmap>();

	public HomeListRecoAdapter(Context context, List<Goods> list,GridView gv) {
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
		if (convertView == null) {
			convertView = View.inflate(context, R.layout.gridview_reco_item,
					null);
			vh = new ViewHolder();
			vh.grid_reco_tvprdDescription = (TextView) convertView
					.findViewById(R.id.grid_reco_tvprdDescription);
			vh.grid_reco_tvprdName = (TextView) convertView
					.findViewById(R.id.grid_reco_tvprdName);
			vh.grid_reco_tvprdUnitPrice = (TextView) convertView
					.findViewById(R.id.grid_reco_tvprdUnitPrice);
			vh.grid_reco_ivprdPic = (ImageView) convertView
					.findViewById(R.id.grid_reco_ivprdPic);
			vh.grid_reco_ivprdStatus = (ImageView) convertView
					.findViewById(R.id.grid_reco_ivprdStatus);
			convertView.setTag(vh);
		} else {
			vh = (ViewHolder) convertView.getTag();
		}
		vh.grid_reco_tvprdDescription.setText(list.get(position)
				.getPrdDescription());
		vh.grid_reco_tvprdName.setText(list.get(position).getPrdName());
		vh.grid_reco_tvprdUnitPrice.setText(list.get(position)
				.getPrdUnitPrice());
		vh.grid_reco_ivprdStatus.setImageResource(list.get(position)
				.getPrdStatus());
		
		vh.grid_reco_ivprdPic.setContentDescription(list.get(position).getPrdId());
		vh.grid_reco_ivprdPic.setImageResource(R.drawable.icon_no_pic);
		String str=list.get(position).getPrdPicUrl();
		vh.grid_reco_ivprdPic.setTag(str);
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
			vh.grid_reco_ivprdPic.setImageBitmap(map.get(str));
		}
		
		if(list.get(position).getIsDisplayPrice()==0){
			vh.grid_reco_tvprdUnitPrice.setVisibility(View.INVISIBLE);
		}

		return convertView;
	}

	static class ViewHolder {
		ImageView grid_reco_ivprdPic, grid_reco_ivprdStatus;
		TextView grid_reco_tvprdName, grid_reco_tvprdDescription,
				grid_reco_tvprdUnitPrice;

	}
}
