package com.feicui.news.model.dao;

import android.content.Context;

import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.feicui.news.common.MyVolleyUtil;

public class RegistManager {

	public static void loadRegist(Context context,Listener<String> listence,ErrorListener error,String...s){
		String url = "user_register?ver=1&uid="+s[0]+"&email="+s[1]+"&pwd="+s[2];
		MyVolleyUtil.setVolleyParameter(context, url, listence, error);
	}
	
}
