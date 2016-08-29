package com.feicui.news.common;

import com.android.volley.RequestQueue;
import com.android.volley.Request.Method;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.toolbox.StringRequest;
import com.feicui.news.volley.VolleySingleton;

import android.content.Context;

public class MyVolleyUtil {

	public static void setVolleyParameter(Context context,String url,Listener<String> listence,ErrorListener error){
		VolleySingleton vs = VolleySingleton.getVolleySingleton(context);
		RequestQueue queue = vs.getRequestQueue();
		StringRequest sr = new StringRequest(Method.GET, ConfigUtil.PATH+url,listence , error);
		queue.add(sr);
	}
	
}
