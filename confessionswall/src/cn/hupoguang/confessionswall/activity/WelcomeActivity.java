package cn.hupoguang.confessionswall.activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;
import cn.hupoguang.confessionswall.R;
import cn.hupoguang.confessionswall.util.DialogHelp;

import com.umeng.analytics.MobclickAgent;

public class WelcomeActivity extends Activity {

	/** 显示欢迎界面 */
	private static final int SHOW_WELCOME_FACE = 1;
	/** 系统版本信息 */
	int sdkVersion = Build.VERSION.SDK_INT;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_welcome);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		
	}
	
	@Override
	protected void onStart() {
		showWelcomeFace(); 
		super.onStart();
	}
	
	@SuppressLint("HandlerLeak")
	private Handler handler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case SHOW_WELCOME_FACE:
				Intent intent = new Intent(WelcomeActivity.this,
						ViewPagerActivity.class);
				startActivity(intent);
				WelcomeActivity.this.finish();
				overridePendingTransition(R.anim.zoomin, R.anim.zoomout);
				break;
			}
		}
	};

	/**
	 * showWelcomeFace:(显示欢迎界面)
	 * 
	 * @author 李文响
	 * @date 2013-7-16 上午10:45:17
	 */
	private void showWelcomeFace() {
		ConnectivityManager connManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo info = connManager.getActiveNetworkInfo();
		if (info == null || !info.isAvailable() || !info.isConnected() ) {
			showSetNetWorkDialog(this);
			return;
		}
		
		handler.postDelayed(new Runnable() {
			@Override
			public void run() {
				Message msg = Message.obtain();
				msg.what = SHOW_WELCOME_FACE;
				handler.sendMessage(msg);
			}
		}, 2000);
	}
	
	private void showSetNetWorkDialog(final Context context) {
		
		DialogHelp dh = new DialogHelp(context);
		dh.showDialog(context.getString(R.string.net_setting), context.getString(R.string.net_setting_msg), context.getString(R.string.setting), context.getString(R.string.calcel),
				new OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						if (sdkVersion > 14) {
							Intent intent = new Intent(android.provider.Settings.ACTION_WIFI_SETTINGS);
							startActivity(intent);
						} else {
							Intent intent = new Intent("/");
							ComponentName cm = new ComponentName("com.android.settings", "com.android.settings.WirelessSettings");
							intent.setComponent(cm);
							intent.setAction("android.intent.action.VIEW");
							startActivity(intent);
						}
						dialog.dismiss();
					}

				}, new OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						dialog.dismiss();
						WelcomeActivity.this.finish();
					}
				});

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
