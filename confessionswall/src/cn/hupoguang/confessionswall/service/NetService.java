package cn.hupoguang.confessionswall.service;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;
import cn.hupoguang.confessionswall.R;
import cn.hupoguang.confessionswall.util.DialogHelp;

/**
 * 
 * @author Administrator
 * 
 */
public class NetService extends Service {
	/** 系统版本信息 */
	int sdkVersion = Build.VERSION.SDK_INT;

	@Override
	public void onCreate() {
		super.onCreate();
		IntentFilter mFilter = new IntentFilter();
		mFilter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);
		registerReceiver(mReceiver, mFilter);

	}

	@Override
	public IBinder onBind(Intent arg0) {
		return null;
	}

	private BroadcastReceiver mReceiver = new BroadcastReceiver() {

		@Override
		public void onReceive(Context context, Intent intent) {
			String action = intent.getAction();
			//if (action.equals(ConnectivityManager.CONNECTIVITY_ACTION)) {
				Log.d("mark", "网络状态已经改变");
				ConnectivityManager connManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
				NetworkInfo info = connManager.getActiveNetworkInfo();
				if (info == null || !info.isConnected() || !info.isAvailable()) {
					showSetNetWorkDialog(context);
				}
			//}
		}
	};

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		System.out.println("*********启动service***********");
		// showSetNetWorkDialog(getApplicationContext());
		return super.onStartCommand(intent, flags, startId);
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		unregisterReceiver(mReceiver);
		this.stopSelf();
	}

	private void showSetNetWorkDialog(final Context context) {
		DialogHelp dh = new DialogHelp(context);
		dh.showDialog(context.getString(R.string.net_setting), context.getString(R.string.net_setting_msg), context.getString(R.string.setting), context.getString(R.string.calcel),
				new OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						if (sdkVersion > 14) {
							Intent intent = new Intent(android.provider.Settings.ACTION_WIFI_SETTINGS);
							intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
							startActivity(intent);
						} else {
							Intent intent = new Intent("/");
							ComponentName cm = new ComponentName("com.android.settings", "com.android.settings.WirelessSettings");
							intent.setComponent(cm);
							intent.setAction("android.intent.action.VIEW");
							intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
							startActivity(intent);
						}
						dialog.dismiss();
					}

				}, new OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						// Intent intent = new Intent(context,
						// Main2Activity.class);
						// context.startActivity(intent);
						// 把当前activity从任务栈里面移除
						// ((Activity) context).finish();
						Toast.makeText(context, "网络断开", Toast.LENGTH_LONG).show();
						dialog.dismiss();
					}
				});

	}
}