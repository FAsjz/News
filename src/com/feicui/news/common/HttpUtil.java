package com.feicui.news.common;

import java.io.InputStream;
import java.net.HttpURLConnection;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.util.EntityUtils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class HttpUtil {

	public static Bitmap httpGetBitmap(String path) throws Exception {
		HttpClient client = HttpClientUtil.getInstance();
		path = path.replace("localhost", "192.168.1.55");
		// 获取get对象
		HttpGet hg = new HttpGet(path);
		// 服务器获取响应，包含响应头和响应体
		synchronized (client) {
			HttpResponse response = client.execute(hg);
			// 判断服务器是否连接成功
			if (response.getStatusLine().getStatusCode() == HttpURLConnection.HTTP_OK) {
				HttpEntity entity = response.getEntity();
				InputStream is = entity.getContent();
				Bitmap bitmap = BitmapFactory.decodeStream(is);
				is.close();
				return bitmap;
			} else {
				throw new Exception();
			}
		}
		
	}

	public static String httpGetString(String path) throws Exception {
//		LogUtil.d("httpGetString1", path);
		HttpClient client = HttpClientUtil.getInstance();
		// 获取get对象
		HttpGet hg = new HttpGet(path);
		// 服务器获取响应，包含响应头和响应体
		HttpResponse response = client.execute(hg);
		// 判断服务器是否连接成功
		if (response.getStatusLine().getStatusCode() == HttpURLConnection.HTTP_OK) {
			HttpEntity entity = response.getEntity();
			String data = EntityUtils.toString(entity, "utf-8");
			return data;
		} else {
			throw new Exception();
		}

	}

}
