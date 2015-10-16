package parfois.com.cdyvmall.adapter.vmall;



import android.annotation.SuppressLint;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;

import parfois.com.cdyvmall.R;
import parfois.com.cdyvmall.adapter.asynctask.CategoryListAsync;
import parfois.com.cdyvmall.adapter.utils.PathUtils;

@SuppressLint("NewApi")
public class CategoryFragment extends Fragment {
	private CategoryView cv;

	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_category, null);
		cv=new CategoryView();
		
		cv.category_lvleft=(ListView) view.findViewById(R.id.category_lvleft);
		cv.category_lvright=(ListView) view.findViewById(R.id.category_lvright);
		new CategoryListAsync(getActivity(), cv).execute(PathUtils.category);
		cv.category_lvright.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				ImageView iv=(ImageView) view.findViewById(R.id.list_category_ivpicUrl);
				Intent intent=new Intent(getActivity(), DetailActivity.class);
				intent.putExtra("url", iv.getContentDescription().toString());
				startActivity(intent);
			}
		});
		return view;
	}
	
	public static class CategoryView{
		public ListView category_lvleft,category_lvright;
	}

}
