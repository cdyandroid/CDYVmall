package parfois.com.cdyvmall.adapter.vmall;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import parfois.com.cdyvmall.R;

public class CartActivity extends Activity {
	private Button btn;
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_cart);
		
		btn=(Button) findViewById(R.id.cart_btn);
		btn.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				Intent intent=new Intent();
				intent.setClass(CartActivity.this, MainActivity.class);
				startActivity(intent);
			}
		});
		
	}

	
}
