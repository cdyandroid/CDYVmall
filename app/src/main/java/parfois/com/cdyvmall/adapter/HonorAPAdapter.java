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
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import parfois.com.cdyvmall.R;
import parfois.com.cdyvmall.adapter.asynctask.DownSaveImgAsync;
import parfois.com.cdyvmall.adapter.bean.ActivityPrds;

public class HonorAPAdapter extends BaseAdapter {
	private Context context;
	private List<ActivityPrds> list;
	private ViewHolder vh;
	private ListView lv;
	private Map<String, Bitmap> map=new HashMap<String, Bitmap>();

	public HonorAPAdapter(Context context, List<ActivityPrds> list,ListView lv) {
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
		if (convertView == null) {
			convertView = View.inflate(context,R.layout.listview_activityprds_item, null);
			vh=new ViewHolder();
			vh.list_activityprds_ivactivityPicUrl=(ImageView) convertView.findViewById(R.id.list_activityprds_ivactivityPicUrl);
			vh.list_activityprds_ivfittingPicUrl=(ImageView) convertView.findViewById(R.id.list_activityprds_ivfittingPicUrl);
			vh.list_activityprds_llstatus2=(LinearLayout) convertView.findViewById(R.id.list_activityprds_llstatus2);
			vh.list_activityprds_llstatus3=(LinearLayout) convertView.findViewById(R.id.list_activityprds_llstatus3);
			vh.list_activityprds_llstatus4=(LinearLayout) convertView.findViewById(R.id.list_activityprds_llstatus4);
			vh.list_activityprds_tvactivityDescription=(TextView) convertView.findViewById(R.id.list_activityprds_tvactivityDescription);
			vh.list_activityprds_tvactivityTime=(TextView) convertView.findViewById(R.id.list_activityprds_tvactivityTime);
			vh.list_activityprds_tvfittingTitle=(TextView) convertView.findViewById(R.id.list_activityprds_tvfittingTitle);
			vh.list_activityprds_tvfittingDescription=(TextView) convertView.findViewById(R.id.list_activityprds_tvfittingDescription);
			convertView.setTag(vh);
		} else {
			vh = (ViewHolder) convertView.getTag();
		}
		
		vh.list_activityprds_ivactivityPicUrl.setContentDescription(list.get(position).getPrdUrl());
		vh.list_activityprds_ivactivityPicUrl.setImageResource(R.drawable.icon_no_pic);
		String str=list.get(position).getActivityPicUrl();
		vh.list_activityprds_ivactivityPicUrl.setTag(str);
		if(!map.containsKey(str)){
			new DownSaveImgAsync(context, new DownSaveImgAsync.CallBack() {
				public void sendImage(Bitmap bm,String key) {
					ImageView iv=(ImageView) lv.findViewWithTag(key);
					if(iv!=null){
						iv.setImageBitmap(bm);
					}
				}
			},map).execute(list.get(position).getActivityPicUrl());
		}else{
			vh.list_activityprds_ivactivityPicUrl.setImageBitmap(map.get(str));
		}
		vh.list_activityprds_ivfittingPicUrl.setImageResource(R.drawable.icon_no_pic);
		String str1=list.get(position).getFittingPicUrl();
		vh.list_activityprds_ivfittingPicUrl.setTag(str1);
		if(!map.containsKey(str1)){
			new DownSaveImgAsync(context, new DownSaveImgAsync.CallBack() {
				public void sendImage(Bitmap bm,String key) {
					ImageView iv=(ImageView) lv.findViewWithTag(key);
					if(iv!=null){
						iv.setImageBitmap(bm);
					}
				}
			},map).execute(list.get(position).getFittingPicUrl());
		}else{
			vh.list_activityprds_ivfittingPicUrl.setImageBitmap(map.get(str1));
		}
		
		vh.list_activityprds_tvactivityDescription.setText(list.get(position).getActivityDescription());
		vh.list_activityprds_tvactivityTime.setText(list.get(position).getActivityTime());
		vh.list_activityprds_tvfittingTitle.setText(list.get(position).getFittingTitle());
		vh.list_activityprds_tvfittingDescription.setText(list.get(position).getFittingDescription());
		if(list.get(position).getActivityStatus()==2){
			vh.list_activityprds_llstatus2.setVisibility(View.VISIBLE);
			vh.list_activityprds_llstatus3.setVisibility(View.INVISIBLE);
			vh.list_activityprds_llstatus4.setVisibility(View.INVISIBLE);
		}else if(list.get(position).getActivityStatus()==3){
			vh.list_activityprds_llstatus2.setVisibility(View.INVISIBLE);
			vh.list_activityprds_llstatus3.setVisibility(View.VISIBLE);
			vh.list_activityprds_llstatus4.setVisibility(View.INVISIBLE);
		}else if(list.get(position).getActivityStatus()==4){
			vh.list_activityprds_llstatus2.setVisibility(View.INVISIBLE);
			vh.list_activityprds_llstatus3.setVisibility(View.INVISIBLE);
			vh.list_activityprds_llstatus4.setVisibility(View.VISIBLE);
		}
		
		return convertView;
	}

	static class ViewHolder {
		ImageView list_activityprds_ivactivityPicUrl,
				list_activityprds_ivfittingPicUrl;
		TextView list_activityprds_tvactivityTime,
				list_activityprds_tvactivityDescription,
				list_activityprds_tvfittingTitle,
				list_activityprds_tvfittingDescription;
		LinearLayout list_activityprds_llstatus4, list_activityprds_llstatus3,
				list_activityprds_llstatus2;
	}
}
