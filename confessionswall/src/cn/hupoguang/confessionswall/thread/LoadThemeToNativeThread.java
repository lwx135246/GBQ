package cn.hupoguang.confessionswall.thread;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import cn.hupoguang.confessionswall.bean.AppTheme;
import cn.hupoguang.confessionswall.db.DBManager;
import cn.hupoguang.confessionswall.util.ConfessionApplication;
import cn.hupoguang.confessionswall.util.ImageUtil;

public class LoadThemeToNativeThread implements Runnable {
	private String date;
	private DBManager dbManager;

	public LoadThemeToNativeThread(String date, DBManager dbManager) {
		this.date = date;
		this.dbManager = dbManager;
	}

	@Override
	public void run() {
		 
	}

	
	void LoadTextTheme(){
		final AppTheme theme = dbManager.getThemeByDate(date);
		if (theme != null) {
			Bitmap bitMap = ImageUtil
					.getImageFromLocal(ConfessionApplication.THEME_PATH
							+ theme.getImagePath());
			if (null == bitMap) {
				ImageUtil.loadImageFromServer(ConfessionApplication.IMAG_URL
						+ theme.getImagePath());
			} else {
				BitmapDrawable bd = new BitmapDrawable(bitMap);
			}
		} 
	}
}
