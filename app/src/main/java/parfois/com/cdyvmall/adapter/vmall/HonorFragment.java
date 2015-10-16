package parfois.com.cdyvmall.adapter.vmall;



import android.annotation.SuppressLint;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.AdapterView.OnItemClickListener;

import parfois.com.cdyvmall.R;
import parfois.com.cdyvmall.adapter.asynctask.HonorListAsync;
import parfois.com.cdyvmall.adapter.utils.PathUtils;

@SuppressLint("NewApi")
public class HonorFragment extends Fragment {
	private HonorViewList hvl;

	private ScrollView sv_honor;
	private ImageView iv;

	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_honor, null);
		hvl = new HonorViewList();

		iv = (ImageView) getActivity().findViewById(R.id.ivtotop);
		sv_honor = (ScrollView) view.findViewById(R.id.honorScrollView);
		sv_honor.smoothScrollTo(0, 0);
		sv_honor.setOnTouchListener(new OnTouchListener() {
			public boolean onTouch(View v, MotionEvent event) {
				if (event.getAction() == MotionEvent.ACTION_MOVE) {
					iv.setVisibility(View.GONE);
				}else if(event.getAction() == MotionEvent.ACTION_UP){
					iv.setVisibility(View.VISIBLE);
					if (sv_honor.getScrollY() == 0) {
						iv.setVisibility(View.GONE);
					}
				}
				return false;
			}
		});
		iv.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				sv_honor.smoothScrollTo(0, 0);
				iv.setVisibility(View.GONE);
			}
		});

		hvl.honor_lvactivityPrds = (ListView) view
				.findViewById(R.id.honor_lvactivityPrds);
		hvl.honor_gvcommonFitting = (GridView) view
				.findViewById(R.id.honor_gvcommonFitting);
		hvl.honor_lvotherHoners = (ListView) view
				.findViewById(R.id.honor_lvotherHoners);
		new HonorListAsync(getActivity(), hvl).execute(PathUtils.honor);
		hvl.honor_lvactivityPrds
				.setOnItemClickListener(new OnItemClickListener() {
					public void onItemClick(AdapterView<?> parent, View view,
							int position, long id) {
						ImageView iv = (ImageView) view
								.findViewById(R.id.list_activityprds_ivactivityPicUrl);
						Intent intent = new Intent(getActivity(),
								DetailActivity.class);
						intent.putExtra("url", iv.getContentDescription()
								.toString());
						startActivity(intent);
					}
				});
		hvl.honor_gvcommonFitting
				.setOnItemClickListener(new OnItemClickListener() {
					public void onItemClick(AdapterView<?> parent, View view,
							int position, long id) {
						ImageView iv = (ImageView) view
								.findViewById(R.id.grid_commonfitting_ivprdPicUrl);
						Intent intent = new Intent(getActivity(),
								DetailActivity.class);
						intent.putExtra("url", iv.getContentDescription()
								.toString());
						startActivity(intent);
					}
				});
		hvl.honor_lvotherHoners
				.setOnItemClickListener(new OnItemClickListener() {
					public void onItemClick(AdapterView<?> parent, View view,
							int position, long id) {
						ImageView iv = (ImageView) view
								.findViewById(R.id.list_otherhoners_ivprdPicUrl);
						Intent intent = new Intent(getActivity(),
								DetailActivity.class);
						intent.putExtra("url", iv.getContentDescription()
								.toString());
						startActivity(intent);
					}
				});

		return view;
	}

	public void onHiddenChanged(boolean hidden) {
		if (!hidden && sv_honor.getScrollY() == 0) {
			sv_honor.smoothScrollTo(0, 0);
		}
		super.onHiddenChanged(hidden);
	}

	public static class HonorViewList {
		public ListView honor_lvactivityPrds, honor_lvotherHoners;
		public GridView honor_gvcommonFitting;
	}
}
