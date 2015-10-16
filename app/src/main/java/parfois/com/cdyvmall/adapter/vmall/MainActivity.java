package parfois.com.cdyvmall.adapter.vmall;



import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.Toast;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;

import parfois.com.cdyvmall.R;
import parfois.com.cdyvmall.adapter.utils.DBManager;
import parfois.com.cdyvmall.adapter.utils.MyStatic;

@SuppressLint("NewApi")
public class MainActivity extends Activity {
	private FragmentManager manager;
	private FragmentTransaction transaction;
	private ImageView title_home_iv, title_honor_iv, title_category_iv,
			title_me_iv, main_ivsearch, main_ivshop;

	private HomeFragment frag1;
	private HonorFragment frag2;
	private CategoryFragment frag3;
	private MeFragment frag4;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		title_home_iv = (ImageView) findViewById(R.id.title_home_iv);
		title_honor_iv = (ImageView) findViewById(R.id.title_honor_iv);
		title_category_iv = (ImageView) findViewById(R.id.title_category_iv);
		title_me_iv = (ImageView) findViewById(R.id.title_me_iv);
		main_ivsearch = (ImageView) findViewById(R.id.main_ivsearch);
		main_ivshop = (ImageView) findViewById(R.id.main_ivshop);
		
		MyStatic.db = DBManager.getHelper(MainActivity.this).getWritableDatabase();

		manager = getFragmentManager();
		transaction = manager.beginTransaction();
		transaction.add(R.id.layoutfragment, (frag1 = new HomeFragment()));
		transaction.commit();

		main_ivsearch.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				Intent intent = new Intent();
				intent.setClass(MainActivity.this, SearchActivity.class);
				startActivity(intent);
			}
		});
		main_ivshop.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				Intent intent = new Intent();
				intent.setClass(MainActivity.this, CartActivity.class);
				startActivity(intent);
			}
		});
	}

	private void hideAllFrag() {
		if (frag1 != null) {
			transaction.hide(frag1);
		}
		if (frag2 != null) {
			transaction.hide(frag2);
		}
		if (frag3 != null) {
			transaction.hide(frag3);
		}
		if (frag4 != null) {
			transaction.hide(frag4);
		}
	}

	public void changePage(View view) {
		transaction = manager.beginTransaction();
		hideAllFrag();
		switch (view.getId()) {
		case R.id.title_home:
			// transaction.replace(R.id.layoutfragment, new HomeFragment());
			transaction.show(frag1);
			title_home_iv.setVisibility(View.VISIBLE);
			title_honor_iv.setVisibility(View.INVISIBLE);
			title_category_iv.setVisibility(View.INVISIBLE);
			title_me_iv.setVisibility(View.INVISIBLE);
			break;
		case R.id.title_honor:
			// transaction.replace(R.id.layoutfragment, new HonorFragment());
			if (frag2 == null) {
				transaction.add(R.id.layoutfragment,
						(frag2 = new HonorFragment()));
			} else {
				transaction.show(frag2);
			}
			title_home_iv.setVisibility(View.INVISIBLE);
			title_honor_iv.setVisibility(View.VISIBLE);
			title_category_iv.setVisibility(View.INVISIBLE);
			title_me_iv.setVisibility(View.INVISIBLE);
			break;
		case R.id.title_category:
			// transaction.replace(R.id.layoutfragment, new CategoryFragment());
			if (frag3 == null) {
				transaction.add(R.id.layoutfragment,
						(frag3 = new CategoryFragment()));
			} else {
				transaction.show(frag3);
			}
			title_home_iv.setVisibility(View.INVISIBLE);
			title_honor_iv.setVisibility(View.INVISIBLE);
			title_category_iv.setVisibility(View.VISIBLE);
			title_me_iv.setVisibility(View.INVISIBLE);
			break;
		case R.id.title_me:
			// transaction.replace(R.id.layoutfragment, new MeFragment());
			if (frag4 == null) {
				transaction
						.add(R.id.layoutfragment, (frag4 = new MeFragment()));
			} else {
				transaction.show(frag4);
			}
			title_home_iv.setVisibility(View.INVISIBLE);
			title_honor_iv.setVisibility(View.INVISIBLE);
			title_category_iv.setVisibility(View.INVISIBLE);
			title_me_iv.setVisibility(View.VISIBLE);
			break;
		}
		transaction.commit();
	}

	private long TIME;

	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			if (frag4!=null&&frag4.isVisible()) {
				WebView wv = (WebView) frag4.getView().findViewById(R.id.me_wv);
				if (wv.canGoBack()) {
					wv.goBack();
					return true;
				}
			}
			if (System.currentTimeMillis() - TIME > 2000) {
				Toast.makeText(MainActivity.this, "再按一次退出程序",
						Toast.LENGTH_SHORT).show();
				TIME = System.currentTimeMillis();
			} else {
				finish();
			}
			return true;
		}

		return super.onKeyDown(keyCode, event);
	}
	
	@Override
	public void finish() {
		if(MyStatic.db.isOpen()){
			MyStatic.db.close();
		}
		super.finish();
	}
}
