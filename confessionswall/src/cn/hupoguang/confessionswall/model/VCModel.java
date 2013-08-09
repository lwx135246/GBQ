/**
 * VCModel.java
 * cn.hupoguang.confessionofwall.model
 * Function： TODO
 *
 * date ：   2013-7-11
 * author：李文响
 * Copyright (c) 2013,hupoguang All Rights Reserved.
*/


package cn.hupoguang.confessionswall.model;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import cn.hupoguang.confessionswall.thread.GetConfessionsCallable;
import cn.hupoguang.confessionswall.util.ThreadPoolManager;

/**
 * ClassName:VCModel
 * Function: 数据处理
 *
 * @author   李文响
 * @version  1.0
 * @Date	2013-7-11	上午9:45:57
 *
 */

public class VCModel {

	/**
	 * ViewConfession:(查看告白墙信息)
	 * @param confessionDate 告白日期
	 * @param page 页数
	 * @param context
	 * @return String
	 * @author   李文响
	 * @date 2013-7-11  上午9:49:33
	 */
	public static String viewConfession(String confessionDate,Integer page) {

		String result = null;
//		HttpClient client = new DefaultHttpClient();
//		client.getParams().setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, 50000);
//		client.getParams().setParameter(CoreConnectionPNames.SO_TIMEOUT, 50000);
//
//		HttpPost post = new HttpPost(ConfessionApplication.URL);
//		post.addHeader(HTTP.CONTENT_ENCODING, "UTF-8");
//		post.addHeader(HTTP.CONTENT_TYPE, "application/x-www-form-urlencoded");
//
//		JSONObject json = new JSONObject();
//		try {
//			json.put("p0", "VC");
//			json.put("p1", null == confessionDate ? "" : confessionDate);
//			json.put("p2", null == page ? "" : page+"");
//
//			String jsonString = json.toString();
//			System.out.println("---liwx---request jsonString: " + jsonString);
//
//			byte[] requestStrings = jsonString.getBytes("UTF-8");
//			ByteArrayEntity byteArrayEntity = new ByteArrayEntity(requestStrings);
//			post.setEntity(byteArrayEntity);
//			
//			HttpResponse response = client.execute(post);
//			System.out.println("连接code == " + response.getStatusLine().getStatusCode());
//			
//			if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
//				//以utf-8编码获取服务器返回值
//				result = EntityUtils.toString(response.getEntity(), "UTF-8");
//				System.out.println("---liwx---response result: " + result);
//			}
//			
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
		Callable<Object> gct=new GetConfessionsCallable(confessionDate, page);
		Future<Object> future=ThreadPoolManager.getInstance().addCall(gct);
		try {
			return future.get().toString();
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (ExecutionException e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
		}
		return result;
	}
}

