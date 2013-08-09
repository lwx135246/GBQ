package cn.hupoguang.confessionswall.thread;

import java.util.concurrent.Callable;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicHeader;
import org.apache.http.params.CoreConnectionPNames;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;

import cn.hupoguang.confessionswall.bean.Confession;
import cn.hupoguang.confessionswall.util.ConfessionApplication;

public class PublishConfessionCallable implements Callable<Object> {
	private static BasicHeader[] headers = new BasicHeader[10];
	static {
		headers[0] = new BasicHeader("Appkey", "");
		//"Content-Type", "application/x-www-form-urlencoded"
		headers[1] = new BasicHeader("Udid", "");
		headers[2] = new BasicHeader("Os", "");
		headers[3] = new BasicHeader("Osversion", "");
		headers[4] = new BasicHeader("Appversion", "");
		headers[5] = new BasicHeader("Sourceid", "");
		headers[6] = new BasicHeader("Ver", "");
		headers[7] = new BasicHeader("Userid", "");
		headers[8] = new BasicHeader("Usersession", "");
		headers[9] = new BasicHeader("Unique", "");
	}
	private Confession confession;
	private String result;

	public PublishConfessionCallable(Confession confession) {
		this.confession = confession;
	}

	@Override
	public String call() throws Exception {
		HttpClient client = new DefaultHttpClient();
		client.getParams().setParameter(
				CoreConnectionPNames.CONNECTION_TIMEOUT, 50000);
		client.getParams().setParameter(CoreConnectionPNames.SO_TIMEOUT, 50000);

		HttpPost post = new HttpPost(ConfessionApplication.URL);
		post.addHeader(HTTP.CONTENT_ENCODING, "GB2312");
		post.addHeader(HTTP.CONTENT_TYPE, "application/x-www-form-urlencoded");
		
		JSONObject json = new JSONObject();
		try {
			json.put("p0", "PC");
			json.put("p1", null == confession.getPublishUserName() ? ""
					: confession.getPublishUserName());
			json.put("p2", null == confession.getReceiveUserName() ? ""
					: confession.getReceiveUserName());
			json.put("p3", null == confession.getConfessionContent() ? ""
					: confession.getConfessionContent());
			json.put("p4", null == confession.getConfessionTime() ? ""
					: confession.getConfessionTime());

			String jsonString = json.toString();
			System.out.println("---liwx---request jsonString: " + jsonString);
			byte[] requestStrings = jsonString.getBytes("GB2312");
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
			System.out.println(e.getMessage());
		}
		return result;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

}
