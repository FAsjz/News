package com.feicui.news.model.dao;

import android.content.Context;
import android.support.v4.app.FragmentActivity;

import com.android.volley.RequestQueue;
import com.android.volley.Request.Method;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.toolbox.StringRequest;
import com.feicui.news.common.ConfigUtil;
import com.feicui.news.common.MyVolleyUtil;
import com.feicui.news.ui.FragmentNews.VolleyErrorHandler;
import com.feicui.news.ui.FragmentNews.VolleyResponseHandler;
import com.feicui.news.volley.VolleySingleton;

public class NewsManager {
	public static void loadNewsFromServer(Context context,
int subId, int nId, VolleyResponseHandler listence, VolleyErrorHandler error){
		/*VolleySingleton vs = VolleySingleton.getVolleySingleton(context);
		RequestQueue queue = vs.getRequestQueue();
		StringRequest sr = new StringRequest(Method.GET, ConfigUtil.PATH
				+ "news_list?ver=1&subid=" + subId + "dir=1&nid="+nId+"&stamp=20140321&cnt=20", response, error);
		queue.add(sr);*/
//		news_list?ver=版本号&subid=分类名&dir=1&nid=新闻id&stamp=20140321&cnt=2
		String url = "news_list?ver=1&subid=" + subId + "&dir=1&nid="+nId+"&stamp=20140321&cnt=20";
		MyVolleyUtil.setVolleyParameter(context, url, listence, error);
		
	}
	
	
	
	
	
}
