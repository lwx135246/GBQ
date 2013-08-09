/**
 * @FILE:NetStateReceiver.java
 * @AUTHOR:李文响
 * @DATE:2013-5-22 上午10:12:32
 */
package cn.hupoguang.confessionswall.broadcastreceiver;

import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.util.Log;
import android.widget.Toast;
import cn.hupoguang.confessionswall.R;
import cn.hupoguang.confessionswall.util.DialogHelp;

/*******************************************
 * @CLASS:NetStateReceiver
 * @DESCRIPTION: 监听网络状态
 * @AUTHOR:李文响
 * @VERSION:v1.0
 * @DATE:2013-5-22 上午10:12:32
 *******************************************/
public class NetStateReceiver extends BroadcastReceiver {

	private static final String TAG = NetStateReceiver.class.getSimpleName();
	/** 系统版本信息 */
	int sdkVersion = Build.VERSION.SDK_INT;
	@Override
	public void onReceive(Context context, Intent intent) {
		Log.e(TAG, "网络状态改变");
		if(!isNetVisible(context)){
			showSetNetWorkDialog(context);
		}
	}
	
	// 判断网络状态
	public static boolean isNetVisible(Context context) {
		ConnectivityManager connManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo info = connManager.getActiveNetworkInfo();
		return info != null && info.isConnected();
	}
	
	
	// 弹出提示框，设置网络
	private void showSetNetWorkDialog(final Context context) {
		DialogHelp dh = new DialogHelp(context);
		dh.showDialog(context.getString(R.string.net_setting), context.getString(R.string.net_setting_msg),
				context.getString(R.string.setting), context.getString(R.string.calcel), 
				new OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						if (sdkVersion > 14) {
							Intent intent = new Intent(new Intent(android.provider.Settings.ACTION_WIFI_SETTINGS));
							intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
							context.startActivity(intent);
						} else {
							Intent intent = new Intent("/");
							ComponentName cm = new ComponentName("com.android.settings", "com.android.settings.WirelessSettings");
							intent.setComponent(cm);
							intent.setAction("android.intent.action.VIEW");
							intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
							context.startActivity(intent);
						}

					}
					
				}, new OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						//Intent intent = new Intent(context, Main2Activity.class);
						//context.startActivity(intent);
						// 把当前activity从任务栈里面移除
						//((Activity) context).finish();
						Toast.makeText(context, "网络断开", Toast.LENGTH_LONG).show();
					}
				});

	}
}
