package cn.hupoguang.confessionswall.thread;

import java.util.concurrent.Callable;

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

public class GetBaseThemeCallable implements Callable<Object> {

	private String date;
	private String result;
	
	public GetBaseThemeCallable(String date) {
		this.date=date;
	}
	
	public String getResult() {
		return result;
	}


	public void setResult(String result) {
		this.result = result;
	}


	@Override
	public String call() throws Exception {
		HttpClient client = new DefaultHttpClient();
		client.getParams().setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, 50000);
		client.getParams().setParameter(CoreConnectionPNames.SO_TIMEOUT, 50000);
		
		HttpPost post = new HttpPost(ConfessionApplication.URL);
		post.addHeader(HTTP.CONTENT_ENCODING, "UTF-8");
		post.addHeader(HTTP.CONTENT_TYPE, "application/x-www-form-urlencoded");

		JSONObject json = new JSONObject();
		try {
			json.put("p0", "GAT");
			json.put("p1", date);

			String jsonString = json.toString();
			System.out.println("---liwx---request jsonString: " + jsonString);

			byte[] requestStrings = jsonString.getBytes("UTF-8");
			ByteArrayEntity byteArrayEntity = new ByteArrayEntity(requestStrings);
			post.setEntity(byteArrayEntity);

			HttpResponse response = client.execute(post);
			System.out.println("连接code == " + response.getStatusLine().getStatusCode());

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

}
