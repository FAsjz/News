package com.feicui.news.model.dao;

import android.content.Context;
import android.telephony.TelephonyManager;

import com.android.volley.Request.Method;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.feicui.news.common.ConfigUtil;
import com.feicui.news.common.TelePhonyManagerUtil;
import com.feicui.news.ui.FragmentNews.VolleyErrorHandler;
import com.feicui.news.ui.FragmentNews.VolleyTypeResponseHandler;
import com.feicui.news.volley.VolleySingleton;

/**
 * 
 * 发送请求给服务器要求下载数据
 * 
 * @author Administrator
 * 
 */
public class NewsTypeManager {
	public static void loadNewsType(VolleyTypeResponseHandler response,
			VolleyErrorHandler error, Context context) {
			VolleySingleton vs = VolleySingleton.getVolleySingleton(context);
			RequestQueue queue = vs.getRequestQueue();
			StringRequest sr = new StringRequest(Method.GET, ConfigUtil.PATH
					+ "news_sort?ver=1&imei=" + TelePhonyManagerUtil.getImei(context), response, error);
			queue.add(sr);
			
			
			
			
	}
	
	
	

}
