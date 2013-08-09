package cn.hupoguang.confessionswall.thread;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.Callable;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import cn.hupoguang.confessionswall.util.ConfessionApplication;
import cn.hupoguang.confessionswall.util.ImageUtil;

/**
 * 网络获取图片线程
 * @author wang
 *
 */
public class GetImageFromWebCallable implements Callable<Object> {

	private String path;
	
	public GetImageFromWebCallable(String path) {
		this.path = path;
	}
	
	private BitmapDrawable bitDrawable;

	public BitmapDrawable getBitDrawable() {
		return bitDrawable;
	}


	public void setBitDrawable(BitmapDrawable bitDrawable) {
		this.bitDrawable = bitDrawable;
	}


	@Override
	public BitmapDrawable call() throws Exception {
		try {
			URL url = new URL(path);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setDoInput(true);
			// 设置超时时间和访问方法
			conn.setRequestMethod("GET");
			conn.setConnectTimeout(5000);
			// 获取返回流
			if (conn.getResponseCode() == 200) {
				InputStream is = conn.getInputStream();
				// 将is转化成bitmap
				Bitmap bitmap = BitmapFactory.decodeStream(is);
				bitDrawable = new BitmapDrawable(bitmap);
				ImageUtil.saveImage(
						ConfessionApplication.THEME_PATH
								+ path.substring(path.lastIndexOf("/") + 1),
						ImageUtil.bitmap2Bytes(bitmap));
				return bitDrawable;
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return bitDrawable;
	}
	
}
