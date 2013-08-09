/**
 * GATModel.java
 * cn.hupoguang.confessionofwall.model
 * Function： TODO
 *
 * date ：   2013-7-11
 * author：李文响
 * Copyright (c) 2013,hupoguang All Rights Reserved.
 */

package cn.hupoguang.confessionswall.model;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.CoreConnectionPNames;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;

import cn.hupoguang.confessionswall.thread.GetBaseThemeCallable;
import cn.hupoguang.confessionswall.util.ConfessionApplication;
import cn.hupoguang.confessionswall.util.ThreadPoolManager;

/**
 * ClassName:GATModel Function: 主题信息处理
 * 
 * @author 李文响
 * @version 1.0
 * @Date 2013-7-11 上午10:09:41
 * 
 */

public class GATModel {

	public static String getAppThemes() {

		String result = null;

		HttpClient client = new DefaultHttpClient();
		client.getParams().setParameter(
				CoreConnectionPNames.CONNECTION_TIMEOUT, 50000);
		client.getParams().setParameter(CoreConnectionPNames.SO_TIMEOUT, 50000);

		HttpPost post = new HttpPost(ConfessionApplication.URL);
		post.addHeader(HTTP.CONTENT_ENCODING, "UTF-8");
		post.addHeader(HTTP.CONTENT_TYPE, "application/x-www-form-urlencoded");

		JSONObject json = new JSONObject();
		try {
			json.put("p0", "GAT");

			String jsonString = json.toString();
			System.out.println("---liwx---request jsonString: " + jsonString);

			byte[] requestStrings = jsonString.getBytes("UTF-8");
			ByteArrayEntity byteArrayEntity = new ByteArrayEntity(
					requestStrings);
			post.setEntity(byteArrayEntity);

			HttpResponse response = client.execute(post);
			System.out.println("连接code == "
					+ response.getStatusLine().getStatusCode());

			if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
				// 以utf-8编码获取服务器返回值
				result = EntityUtils.toString(response.getEntity(), "UTF-8");
				System.out.println("---liwx---response result: " + result);
			}

		} catch (Exception e) {
			e.printStackTrace();

		}
		return result;
	}

	/**
	 * getDayTheme:(获取某日主题)
	 * 
	 * @param date
	 *            日期
	 * @return String
	 * @author 李文响
	 * @date 2013-7-17 下午3:36:24
	 */
	public static String getDayTheme(String date) {
		String result = null;
		GetBaseThemeCallable getBaseThemeCallable = new GetBaseThemeCallable(
				date);
		Future<Object> future = ThreadPoolManager.getInstance().addCall(
				getBaseThemeCallable);

		try {
			result=(String)future.get();
		} catch (InterruptedException e) {
			e.printStackTrace();
			
		} catch (ExecutionException e) {
			e.printStackTrace();
		}
		
		return result;
	}
}
