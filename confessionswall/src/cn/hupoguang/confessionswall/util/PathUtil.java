package cn.hupoguang.confessionswall.util;

import java.io.File;

import android.os.Environment;

/**
 * ClassName:PathUtil
 * Function: 路径获取类
 *
 * @author   李文响
 * @version  1.0
 * @Date	2013-7-17	下午1:48:50
 *
 */
public class PathUtil {
	
	/**
	 * getSDPath:(SD路径)
	 * @return
	 * @author   李文响
	 * @date 2013-7-17  下午1:49:06
	 */
	public static String getSDPath(){
		if (Environment.getExternalStorageState().equals(android.os.Environment.MEDIA_MOUNTED)) {
			File sdDir = Environment.getExternalStorageDirectory();
			return sdDir.getPath();
		}
		return "/mnt/sdcard";
	}
	/**
	 * 获取告白墙SD路径
	 */
	public static String getMainPath(){
		String mainPath = getSDPath()+"/gbq";
		File file = new File(mainPath);
		if(!file.exists()){
			file.mkdirs();
		}
		return mainPath;
	}
	
	/**
	 * 图片路径
	 * @return
	 */
	public static String getSDImagePath(){
		String imagePath = getMainPath()+"/images";
		File file = new File(imagePath);
		if(!file.exists()){
			file.mkdirs();
		}
		return imagePath;
	}
	/**
	 * 头像存储路径
	 * @return
	 */
	
	public static String getSDHeadPath(){
		String headPath = getSDImagePath()+"/head";
		File file = new File(headPath);
		if(!file.exists()){
			file.mkdirs();
		}
		return headPath;
	}
	/**
	 * Splash主题图片存储路径
	 */
	public static String getThemePath(){
		String themePath = getSDImagePath()+"/theme";
		File file = new File(themePath);
		if(!file.exists()){
			file.mkdirs();
		}
		return themePath;
	}
	/**
	 * 获取文件名
	 * 
	 * @param path
	 * @return
	 */
	public static String getFileName(String path) {
		int start = path.lastIndexOf("/") + 1;
		return path.substring(start);
	}
	
	/**
	 * Splash安装包存放路径
	 */
	public static String getAppPath(){
		String appPath = getMainPath()+"/app/";
		File file = new File(appPath);
		if(!file.exists()){
			file.mkdirs();
		}
		return appPath;
	}
	/**
	 * 语音目录
	 */
	public static String getVoicePath(){
		String voicePath = getMainPath()+"/voice/";
		File file = new File(voicePath);
		if(!file.exists()){
			file.mkdirs();
		}
		return voicePath;
	}
	/**
	 * 背景大图
	 */
	public static String getSDBackImagePath(){
		String imagePath = getMainPath()+"/image/backimg";
		File file = new File(imagePath);
		if(!file.exists()){
			file.mkdirs();
		}
		return imagePath;
	}
	

}
