package com.feicui.news.model.dao;

import android.content.Context;

import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.feicui.news.common.MyVolleyUtil;

public class LoginManager {

	public static void loadLogin(Context context,Listener<String> listence,ErrorListener error,String...s){
		String url = "user_login?ver=1&uid="+s[0]+"&pwd="+s[1]+"&device=0";
//		user_login?ver=版本号&uid=用户名&pwd=密码&device=0
		MyVolleyUtil.setVolleyParameter(context, url, listence, error);
	}
}
