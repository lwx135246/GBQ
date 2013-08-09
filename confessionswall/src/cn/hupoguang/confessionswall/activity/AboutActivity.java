package cn.hupoguang.confessionswall.activity;

import com.umeng.analytics.MobclickAgent;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import cn.hupoguang.confessionswall.R;

/**
 * 关于页面
 * 
 * @author   wang
 * 
 */
public class AboutActivity extends Activity implements OnClickListener {
	private ImageView imgHome;
	private LinearLayout we_product;
	private LinearLayout custom_face;
	private LinearLayout feed_back;
	private TextView product_desc;
	private TextView custom;
	private TextView feek;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.about);
		initView();
	}

	private void initView() {
		imgHome = (ImageView) findViewById(R.id.backtohome);
		we_product = (LinearLayout) findViewById(R.id.we_product);
		custom_face = (LinearLayout) findViewById(R.id.custom_face);
		feed_back = (LinearLayout) findViewById(R.id.feed_back);
		product_desc = (TextView) findViewById(R.id.product_desc);
		custom=(TextView) findViewById(R.id.custom);
		feek=(TextView)findViewById(R.id.feedback);
	
		imgHome.setOnClickListener(this);
		we_product.setOnClickListener(this);
		custom_face.setOnClickListener(this);
		feed_back.setOnClickListener(this);
	}
	
	boolean flag1=true;
	boolean flag2=true;
	boolean flag3=true;
	
	@Override
	public void onClick(View v) {
		if(v == imgHome){
			imgHome.setImageResource(R.drawable.backtomain2);
			finish();
			overridePendingTransition(R.anim.right_in, R.anim.right_out);
		} else {
			allVisibGone();
			if (v == we_product) {
				if(flag1){
					product_desc.setVisibility(View.VISIBLE);
					flag1=!flag1;
					return;
				}else{
					product_desc.setVisibility(View.GONE);
				}
			}else if(v == custom_face){
				if(flag2){
					custom.setVisibility(View.VISIBLE);
					flag2=!flag2;
					return;
				}else{
					custom.setVisibility(View.GONE);
				}
			} else if(v == feed_back){
				if(flag3){
					feek.setVisibility(View.VISIBLE);
					flag3=!flag3;
					return;
				}else{
					feek.setVisibility(View.GONE);
				}
			}
			  flag1=true;
			  flag2=true;
			  flag3=true;
		}
		
		
	}
	
	void allVisibGone(){
		product_desc.setVisibility(View.GONE);
		custom.setVisibility(View.GONE);
		feek.setVisibility(View.GONE);
	}
	
	@Override
	protected void onPause() {
		super.onPause();
		MobclickAgent.onPause(this);
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		 MobclickAgent.onResume(this);
	}
}
