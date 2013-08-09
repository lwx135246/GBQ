/**
 * LCModel.java
 * cn.hupoguang.confessionofwall.model
 * Function： TODO
 *
 * date ：   2013-7-11
 * author：李文响
 * Copyright (c) 2013,hupoguang All Rights Reserved.
*/


package cn.hupoguang.confessionswall.model;

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

import cn.hupoguang.confessionswall.util.ConfessionApplication;

/**
 * ClassName:LCModel
 * Function: 处理赞告白信息
 *
 * @author   李文响
 * @version  1.0
 * @Date	2013-7-11	上午10:03:32
 *
 */

public class LCModel {

	/**
	 * LikeConfession:(赞告白)
	 * @param likeUserName 赞的用户
	 * @param confessionId 被赞的告白ID
	 * @return String
	 * @author   李文响
	 * @date 2013-7-11  上午10:05:14
	 */
	public static String LikeConfession(String likeUserName,String confessionId) {

		String result = null;
		
		HttpClient client = new DefaultHttpClient();
		client.getParams().setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, 50000);
		client.getParams().setParameter(CoreConnectionPNames.SO_TIMEOUT, 50000);

		HttpPost post = new HttpPost(ConfessionApplication.URL);
		post.addHeader(HTTP.CONTENT_ENCODING, "UTF-8");
		post.addHeader(HTTP.CONTENT_TYPE, "application/x-www-form-urlencoded");

		JSONObject json = new JSONObject();
		try {
			json.put("p0", "LC");
			json.put("p1", null == likeUserName ? "" : likeUserName);
			json.put("p2", null == confessionId ? "" : confessionId);

			String jsonString = json.toString();
			System.out.println("---liwx---request jsonString: " + jsonString);

			byte[] requestStrings = jsonString.getBytes("UTF-8");
			ByteArrayEntity byteArrayEntity = new ByteArrayEntity(requestStrings);
			post.setEntity(byteArrayEntity);
			
			HttpResponse response = client.execute(post);
			System.out.println("连接code == " + response.getStatusLine().getStatusCode());
			
			if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
				//以utf-8编码获取服务器返回值
				result = EntityUtils.toString(response.getEntity(), "UTF-8");
				System.out.println("---liwx---response result: " + result);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
}

