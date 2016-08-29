package com.feicui.news.model.dao;

import java.io.File;
import java.io.FileNotFoundException;

import android.content.Context;

import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.feicui.news.common.ConfigUtil;
import com.feicui.news.common.MyVolleyUtil;
import com.feicui.news.common.TelePhonyManagerUtil;
import com.feicui.news.model.httpclient.AsyncHttpClient;
import com.feicui.news.model.httpclient.RequestParams;
import com.feicui.news.model.httpclient.ResponseHandlerInterface;
import com.feicui.news.model.httpclient.TextHttpResponseHandler;

public class LoginLogManager {

	public static void LoadLoginLog(Context context,Listener<String> listence,ErrorListener error
			,String token){
//		String token = SharedPreferencesUtil.getRegister(context).getToken();TelePhonyManagerUtil.getImei(context)
		String url = "user_home?ver=1&imei="+TelePhonyManagerUtil.getImei(context)+"&token="+token;
//				user_home?ver=版本号&imei=手机标识符& token =用户令牌	
		MyVolleyUtil.setVolleyParameter(context, url, listence, error);
	}
	
	//上传头像到服务器
	public static void uploadIcon(Context context,String token,File portrait,TextHttpResponseHandler in){
		AsyncHttpClient client = new AsyncHttpClient();
		RequestParams param = new RequestParams();
		param.put("token", token);
		try {
			param.put("portrait", portrait);
			client.post(context, ConfigUtil.PATH + "user_image", param, in);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}
