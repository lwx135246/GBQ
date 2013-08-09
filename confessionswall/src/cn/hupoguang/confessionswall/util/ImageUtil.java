package cn.hupoguang.confessionswall.util;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.security.MessageDigest;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import cn.hupoguang.confessionswall.R;

/**
 * 通过URL图片路径md5加密的抽象路径从本地查找 无结果时网络获取图片,对路径进行md5缓存本地 调用图片时候通过ImageCallback接口回调获取
 * 
 * @author huangyeling
 * 
 */
public class ImageUtil {

	// 存储路径
	public static final String SDCARD_CACHE_IMG_PATH = Environment
			.getExternalStorageDirectory().getPath() + "/gbq/images/";

	private static Context imgContext;

	/**
	 * saveImage:(缓存image至SDCARD)
	 * 
	 * @param imagePath
	 *            保存路径
	 * @param buffer
	 * @throws IOException
	 * @author 李文响
	 * @date 2013-7-18 上午10:04:53
	 */
	public static void saveImage(String imagePath, byte[] buffer)
			throws IOException {
		System.out.println("保存到本地的路径：" + imagePath);
		File f = new File(imagePath);
		if (f.exists() && f.length() > 1) {
			return;
		} else {
			File parentFile = f.getParentFile();
			if (!parentFile.exists()) {
				parentFile.mkdir();
			}
			f.createNewFile();
			FileOutputStream fos = new FileOutputStream(imagePath);
			fos.write(buffer);
			fos.flush();
			fos.close();
		}
	}

	/**
	 * 查找本地缓存SDCARD并BitmapFactory转换Bitmap
	 * 
	 * @param imagePath
	 * @return
	 */
	public static Bitmap getImageFromLocal(String imagePath) {
		File file = new File(imagePath);
		if (file.exists()) {
			Bitmap bitmap = BitmapFactory.decodeFile(imagePath);
			// file.setLastModified(System.currentTimeMillis());
			Log.e("ImageUtil  ",
					" 本地存在===================================bitmap == "
							+ bitmap);
			return bitmap;
		}
		return null;
	}

	/**
	 * loadImage:(从本地或者服务端加载图片)
	 * 
	 * @param context
	 * @param imagePath
	 *            本地sd图片路径
	 * @param imgUrl
	 *            网络图片地址
	 * @param callback
	 *            回调函数
	 * @return Bitmap
	 * @author 李文响
	 * @date 2013-7-17 下午2:03:47
	 */
	public static Bitmap loadImage(Context context, final String imagePath,
			final String imgUrl, final ImageCallback callback) {
		imgContext = context;
		Bitmap bitmap = getImageFromLocal(imagePath);
		System.out.println("imageUtil    " + "    本地    bitmap  = " + bitmap);
		if (bitmap != null) {
			Log.e("imageUtil ", " 本地    bitmap  返回----------------------");
			callback.loadImage(bitmap, imagePath);
			return bitmap;
		} else {// 从网上加载
			final Handler handler = new Handler() {
				@Override
				public void handleMessage(Message msg) {
					if (msg.obj != null) {
						Bitmap bitmap = (Bitmap) msg.obj;
						Log.e("ImageUtil ", "callback    bitmap == " + bitmap);
						callback.loadImage(bitmap, imagePath);// 第三次回调,通过imagePath查找缓存在本地的图片,填充View
					} else {
						callback.loadImage(null, imagePath);
					}
				}
			};

			/**
			 * 异步获取图片
			 */
			Runnable runnable = new Runnable() {
				public void run() {
					try {
						Bitmap bitmap = null;
						if ("".equals(imgUrl.substring(imgUrl.lastIndexOf("/") + 1))) { // 对应的用户没有设置头像
							System.out.println("imgurl   is nulll ");
							return;
						}
						if ("".equals(PathUtil.getFileName(imgUrl))) {
							Resources res = imgContext.getResources();
//							bitmap = BitmapFactory.decodeResource(res,
//									R.drawable.pp);
						} else {
							URL url = new URL(imgUrl);
							Log.e("图片加载", imgUrl);
							URLConnection conn = url.openConnection();
							conn.setConnectTimeout(5000);
							conn.setReadTimeout(5000);
							conn.connect();
							BufferedInputStream bis = new BufferedInputStream(
									conn.getInputStream(), 8192);
							bitmap = BitmapFactory.decodeStream(bis);
						}

						saveImage(imagePath, bitmap2Bytes(bitmap));// 网络获取后缓存至SDCARD
						Log.e("图片加载   bitmap  ", "bitmap == " + bitmap);
						Message msg = handler.obtainMessage();
						msg.obj = bitmap;
						handler.sendMessage(msg);

					} catch (IOException e) {
						e.printStackTrace();
						Log.e(ImageUtil.class.getName(),
								"保存图片到本地存储卡出错！" + e.getMessage());

						Message msg = handler.obtainMessage();
						msg.obj = null;
						handler.sendMessage(msg);
					}
				}
			};
			ThreadPoolManager.getInstance().addTask(runnable);
		}
		return null;
	}

	/**
	 * return mnt/sdcard/qiaoqiao/images/
	 * 
	 * @return
	 */
	public static String getCacheImgPath() {
		return SDCARD_CACHE_IMG_PATH;
	}

	/**
	 * 图片URL md5
	 * 
	 * @param paramString
	 * @return
	 */
	public static String md5(String paramString) {
		String returnStr;
		try {
			MessageDigest localMessageDigest = MessageDigest.getInstance("MD5");
			localMessageDigest.update(paramString.getBytes());
			returnStr = byteToHexString(localMessageDigest.digest());
			return returnStr;
		} catch (Exception e) {
			return paramString;
		}
	}

	/**
	 * Bitmap转换到Byte[]
	 * 
	 * @param bm
	 * @return
	 */
	public static byte[] bitmap2Bytes(Bitmap bm) {
		ByteArrayOutputStream bas = new ByteArrayOutputStream();
		bm.compress(Bitmap.CompressFormat.JPEG, 100, bas);
		return bas.toByteArray();
	}

	/**
	 * 建议采用这个
	 * 
	 * @param is
	 * @return
	 * @throws IOException
	 */
	public static byte[] getBytes(InputStream is) throws IOException {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		byte[] b = new byte[1024];
		int len = 0;
		while ((len = is.read(b, 0, 1024)) != -1) {
			baos.write(b, 0, len);
			baos.flush();
		}
		byte[] bytes = baos.toByteArray();
		return bytes;
	}

	/**
	 * 将指定byte数组转换成16进制字符串
	 * 
	 * @param b
	 * @return
	 */
	public static String byteToHexString(byte[] b) {
		StringBuffer hexString = new StringBuffer();
		for (int i = 0, len = b.length; i < len; i++) {
			String hex = Integer.toHexString(b[i] & 0xFF);
			if (hex.length() == 1) {
				hex = '0' + hex;
			}
			hexString.append(hex.toUpperCase());
		}
		return hexString.toString();
	}

	/**
	 * 图片回调接口
	 * 
	 * @author huangyeling
	 * 
	 */
	public interface ImageCallback {
		public void loadImage(Bitmap bitmap, String imagePath);
	}

	/**
	 * bitmap对象转换成图片文件保存至本地
	 * 
	 * @throws IOException
	 * @author hanguoxin
	 */
	public static void bitmap2file(Bitmap bitmap, String filePath)
			throws IOException {
		saveImage2(filePath, bitmap2Bytes(bitmap));// 网络获取后缓存至SDCARD
	}

	/**
	 * 存在则删除
	 */
	public static void saveImage2(String imagePath, byte[] buffer)
			throws IOException {
		File f = new File(imagePath);
		f.deleteOnExit();
		File parentFile = f.getParentFile();
		if (!parentFile.exists()) {
			parentFile.mkdir();
		}
		f.createNewFile();
		FileOutputStream fos = new FileOutputStream(imagePath);
		fos.write(buffer);
		fos.flush();
		fos.close();
	}

	/**
	 * loadImagefromNet:(从服务端加载图片，并保存到本地)
	 * 
	 * @param path
	 * @return Bitmap
	 * @throws Exception
	 * @author 李文响
	 * @date 2013-7-17 下午5:28:34
	 */
	public static void loadImagefromNet(final String path,
			final ImageCallback callback) throws Exception {

		final Handler handler = new Handler() {
			@Override
			public void handleMessage(Message msg) {

				if (callback != null) {
					Bitmap bitmap = (Bitmap) msg.obj;
					callback.loadImage(bitmap, ConfessionApplication.THEME_PATH
							+ path.substring(path.lastIndexOf("/") + 1));
				}
			}
		};

		Runnable runnable = new Runnable() {
			public void run() {
				try {
					URL url = new URL(path);
					HttpURLConnection conn = (HttpURLConnection) url
							.openConnection();
					conn.setDoInput(true);
					// 设置超时时间和访问方法
					conn.setRequestMethod("GET");
					conn.setConnectTimeout(5000);
					// 获取返回流
					if (conn.getResponseCode() == 200) {
						InputStream is = conn.getInputStream();
						// 将is转化成bitmap
						Bitmap bitmap = BitmapFactory.decodeStream(is);
						System.out.println("save path:"
								+ (ConfessionApplication.THEME_PATH + path
										.substring(path.lastIndexOf("/") + 1)));
						ImageUtil.saveImage(ConfessionApplication.THEME_PATH
								+ path.substring(path.lastIndexOf("/") + 1),
								ImageUtil.bitmap2Bytes(bitmap));
						Message msg = handler.obtainMessage();
						msg.obj = bitmap;
						handler.sendMessage(msg);
					}

				} catch (MalformedURLException e) {
					e.printStackTrace();
				} catch (IOException e) {

					e.printStackTrace();
				}
			};
		};

		ThreadPoolManager.getInstance().addTask(runnable);
	}

	/**
	 * 压缩图片
	 */
	public static Bitmap ysPic(File file) {
		if (!file.exists() || file.length() <= 0) {
			return null;
		} else {
			BitmapFactory.Options opts = new BitmapFactory.Options();
			opts.inJustDecodeBounds = true;
			BitmapFactory.decodeFile(file.getPath(), opts);
			int srcWidth = opts.outWidth;
			int srcHeight = opts.outHeight;
			int destWidth = 0;
			int destHeight = 0;
			// 缩放的比例
			double ratio = 0.0;
			Log.e("原图片宽高 ", "Width:" + srcWidth + " Height:" + srcHeight);

			int maxLength = 150;

			if (srcWidth > srcHeight) {
				ratio = srcWidth / maxLength;
				destWidth = maxLength;
				destHeight = (int) (srcHeight / ratio);
			} else {
				ratio = srcHeight / maxLength;
				destHeight = maxLength;
				destWidth = (int) (srcWidth / ratio);
			}

			BitmapFactory.Options newOpts = new BitmapFactory.Options();

			Log.e("ratio + 1", "ratio + 1:" + ((int) ratio + 1));

			newOpts.inSampleSize = (int) ratio + 1;
			newOpts.inJustDecodeBounds = false;
			newOpts.outHeight = destHeight;
			newOpts.outWidth = destWidth;

			Bitmap bitMap = BitmapFactory.decodeFile(file.getPath(), newOpts);
			Log.e("压缩后的高度", "压缩后的高度：" + bitMap.getWidth() + "   压缩后的高度："
					+ bitMap.getHeight());

			return bitMap;
		}

	}

	/**
	 * 压缩图片
	 */
	public static Bitmap ysPicBitmap(byte[] bt) {
		if (0 == bt.length) {
			return null;
		} else {
			BitmapFactory.Options opts = new BitmapFactory.Options();
			opts.inJustDecodeBounds = true;
			BitmapFactory.decodeByteArray(bt, 0, bt.length, opts);
			int srcWidth = opts.outWidth;
			int srcHeight = opts.outHeight;
			int destWidth = 0;
			int destHeight = 0;
			// 缩放的比例
			double ratio = 0.0;
			Log.e("原图片宽高 ", "Width:" + srcWidth + " Height:" + srcHeight);

			int maxLength = 150;
			// 计算缩放比例
			if (srcWidth > srcHeight) {
				ratio = srcWidth / maxLength;
				destWidth = maxLength;
				destHeight = (int) (srcHeight / ratio);
			} else {
				ratio = srcHeight / maxLength;
				destHeight = maxLength;
				destWidth = (int) (srcWidth / ratio);
			}

			BitmapFactory.Options newOpts = new BitmapFactory.Options();

			Log.e("ratio + 1", "ratio + 1:" + ((int) ratio + 1));

			newOpts.inSampleSize = (int) ratio + 1;
			newOpts.inJustDecodeBounds = false;
			newOpts.outHeight = destHeight;
			newOpts.outWidth = destWidth;

			Bitmap bitMap = BitmapFactory.decodeByteArray(bt, 0, bt.length,
					newOpts);
			Log.e("压缩后的高度", "压缩后的高度：" + bitMap.getWidth() + "   压缩后的高度："
					+ bitMap.getHeight());

			return bitMap;
		}

	}

	/**
	 * loadImageFromServer:(从服务端加载图片,[设置背景]，保存到本地)
	 * 
	 * @param path
	 * @author 李文响
	 * @date 2013-7-30 上午11:02:24
	 */
	public static BitmapDrawable loadImageFromServer(String path) {

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
				BitmapDrawable bd = new BitmapDrawable(bitmap);
				ImageUtil.saveImage(
						ConfessionApplication.THEME_PATH
								+ path.substring(path.lastIndexOf("/") + 1),
						ImageUtil.bitmap2Bytes(bitmap));
				return bd;
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
