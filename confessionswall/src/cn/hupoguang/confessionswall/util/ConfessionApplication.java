/**
 * ConfessionApplication.java
 * cn.hupoguang.confessionofwall.util
 * Function： 全局参数管理
 *
 * date ：   2013-7-10
 * author：李文响
 * Copyright (c) 2013,hupoguang All Rights Reserved.
*/


package cn.hupoguang.confessionswall.util;

import android.app.Application;

/**
 * ClassName:ConfessionApplication
 * Function: 告白墙参数管理
 *
 * @author   李文响
 * @version  1.0
 * @Date	2013-7-10	下午3:26:08
 *
 */

public class ConfessionApplication extends Application {

	/** 正确处理数据*/
	public static final String OK = "200";
	/** 处理数据出错*/
	public static final String ERROR = "500";
	/** 数据访问URL*/
	public static final String URL = "http://app.gaobaiqiang.com:8091/app/WebInterface.aspx";
	/** 访问图片的URL*/
	public static final String IMAG_URL = "http://app.gaobaiqiang.com:8091/app/themeImages/";
	/** 告白墙赞表*/
	public static final String PRAISE_DB = "praise_db";
	/** 主题表*/
	public static final String THEMES_DB = "themes_db";
	/** 主题颜色*/
	public static final String COLOR = "#3BBF00";
	 
	/** 主题图片本地保存路径 */
	public static final String THEME_PATH = PathUtil.getThemePath() + "/";
	
	
	/**
	 * 内存溢出发送广播清理内存
	 */
	@Override
	public void onLowMemory() {
		super.onLowMemory();
	}
}

