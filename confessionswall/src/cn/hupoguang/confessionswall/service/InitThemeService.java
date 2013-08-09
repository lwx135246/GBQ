package cn.hupoguang.confessionswall.service;

import java.io.File;
import java.util.List;
import java.util.Map;

import android.app.Service;
import android.content.Intent;
import android.os.Bundle;
import android.os.IBinder;
import cn.hupoguang.confessionswall.bean.AppTheme;
import cn.hupoguang.confessionswall.controller.ConfessionController;
import cn.hupoguang.confessionswall.db.DBManager;
import cn.hupoguang.confessionswall.util.ConfessionApplication;
import cn.hupoguang.confessionswall.util.ImageUtil;
import cn.hupoguang.confessionswall.util.ThreadPoolManager;

/**
 * 
 * @author Administrator
 * 
 */
public class InitThemeService extends Service {

	private ConfessionController controller;
	private DBManager dbManager;

	@Override
	public void onCreate() {
		controller = new ConfessionController();
		dbManager = new DBManager(getApplicationContext());
		super.onCreate();
	}

	@Override
	public IBinder onBind(Intent arg0) {
		return null;
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		Bundle bundle = intent.getExtras();
		String[] themesArray = bundle.getStringArray("date");
		loadTheme(themesArray);
		return super.onStartCommand(intent, flags, startId);
	}

	/**
	 * 加载主题信息
	 * 
	 * @param themesArray
	 */
	private void loadTheme(final String[] themesArray) {

		Runnable runnable = new Runnable() { 
			@Override
			public void run() {
				AppTheme appTheme = null;
				for (int i = 0; i < themesArray.length; i++) {
					String publishDate = themesArray[i];
					appTheme = dbManager.getThemeByDate(publishDate);
					// 本地数据库中有数据
					if (appTheme != null && appTheme.getImagePath() != null && appTheme.getImagePath().length() > 0) {// 本地已经存在主题信息
						File file = new File(ConfessionApplication.THEME_PATH + appTheme.getImagePath());
						if (!file.exists()) {// 主题背景图图片在sdcard中不存在
							ImageUtil.loadImageFromServer(ConfessionApplication.IMAG_URL + appTheme.getImagePath());
						}
						continue;
					} else {
						Map<String, Object> resultMap = controller.getAppTheme(publishDate);
						List<AppTheme> list = (List<AppTheme>) resultMap.get("r3");
						appTheme = list.get(0);
						appTheme.setPublishDate(publishDate);
						dbManager.insertTheme(appTheme);
						// 加載并保存到本地
						ImageUtil.loadImageFromServer(ConfessionApplication.IMAG_URL + appTheme.getImagePath());

					}
				}
				stopSelf();
			}
		};
		ThreadPoolManager.getInstance().addTask(runnable);
	}

	
	
	@Override
	public void onDestroy() {

		dbManager.closeDB();
		super.onDestroy();
	}
}
