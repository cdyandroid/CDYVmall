package parfois.com.cdyvmall.adapter.vmall;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings.LayoutAlgorithm;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import parfois.com.cdyvmall.R;

@SuppressLint({ "NewApi", "SetJavaScriptEnabled" })
public class DetailActivity extends Activity {
	private TextView tv;
	private WebView wv;
	private ImageView iv_backmain, iv_share;
	private String url;
	private AlertDialog dialog;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_detail);

		tv = (TextView) findViewById(R.id.textview9);
		wv = (WebView) findViewById(R.id.detail_wv);
		iv_backmain = (ImageView) findViewById(R.id.detail_ivbackmain);
		iv_share = (ImageView) findViewById(R.id.detail_ivshare);
		Intent intent = getIntent();
		url = intent.getStringExtra("url");

		iv_backmain.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				Intent intent = new Intent();
				intent.setClass(DetailActivity.this, MainActivity.class);
				startActivity(intent);
			}
		});
		iv_share.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				dialog = new AlertDialog.Builder(DetailActivity.this).create();
				View view = View.inflate(DetailActivity.this,
						R.layout.dialog_share, null);
				LinearLayout llcopy = (LinearLayout) view
						.findViewById(R.id.dialog_llcopy);
				llcopy.setOnClickListener(new OnClickListener() {
					public void onClick(View v) {
						ClipboardManager cm = (ClipboardManager) DetailActivity.this
								.getSystemService(Context.CLIPBOARD_SERVICE);
						cm.setPrimaryClip(ClipData.newPlainText("url", url));
						Toast.makeText(DetailActivity.this, "已复制",
								Toast.LENGTH_SHORT).show();
						dialog.dismiss();
					}
				});
				Button btn = (Button) view.findViewById(R.id.btn);
				btn.setOnClickListener(new OnClickListener() {
					public void onClick(View v) {
						dialog.dismiss();
					}
				});
				dialog.setView(view, 0, 0, 0, 0);
				dialog.show();
			}
		});
		wv.loadUrl(url);
		WebChromeClient wvcc = new WebChromeClient() {
			public void onReceivedTitle(WebView view, String title) {
				super.onReceivedTitle(view, title);
				tv.setText(title);
			}
		};
		wv.setWebChromeClient(wvcc);
		wv.getSettings().setLayoutAlgorithm(LayoutAlgorithm.SINGLE_COLUMN);
		wv.getSettings().setUseWideViewPort(true);
		wv.getSettings().setLoadWithOverviewMode(true);

		wv.getSettings().setJavaScriptEnabled(true);
		wv.setWebViewClient(new WebViewClient() {
			public boolean shouldOverrideUrlLoading(WebView view, String url) {
				view.loadUrl(url);
				return true;
			}
		});
		// wv.getSettings().setBuiltInZoomControls(true);
		// wv.getSettings().setRenderPriority(RenderPriority.HIGH);
		// wv.getSettings().setBlockNetworkImage(true);
	}

	public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK) && wv.canGoBack()) {
        	wv.goBack();
            return true;
        }
        return super.onKeyDown(keyCode,event);
    }
}
