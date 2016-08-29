package com.feicui.news.common;

import org.apache.http.HttpVersion;
import org.apache.http.client.HttpClient;
import org.apache.http.conn.params.ConnManagerParams;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.params.HttpProtocolParams;
import org.apache.http.protocol.HTTP;

public class HttpClientUtil {
	private static HttpClient client;
	public static final String CHARSET = HTTP.UTF_8;
	private HttpClientUtil(){}
	
	public static synchronized HttpClient getInstance(){
		if (client == null) {
			HttpParams param = new BasicHttpParams();
			//设置一些参数
			HttpProtocolParams.setContentCharset(param, CHARSET);
			HttpProtocolParams.setVersion(param, HttpVersion.HTTP_1_1);
			HttpProtocolParams.setUseExpectContinue(param, true);
			//设置超时连接
			ConnManagerParams.setTimeout(param, 1000);
			//连接超时
			HttpConnectionParams.setConnectionTimeout(param, 2000);
			//请求超时
			HttpConnectionParams.setSoTimeout(param, 4000);
			client = new DefaultHttpClient();
		}
		
		return client;
		
	}
}
