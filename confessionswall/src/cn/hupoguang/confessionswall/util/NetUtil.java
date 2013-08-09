/**
 * 
 */
package cn.hupoguang.confessionswall.util;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * @author Administrator
 *
 */
public class NetUtil {

	public static boolean hashNetWork(Context context){
		ConnectivityManager connManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo info = connManager.getActiveNetworkInfo();
		return (info != null && info.isAvailable() && info.isConnected());
	}
}
