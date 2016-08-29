package com.feicui.news.model.dao;

import android.content.Context;

import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.feicui.news.common.MyVolleyUtil;

public class ForgetPassManager {

	public static void loadForgetPass(Context context, Listener<String> listence,
			ErrorListener error, String s) {
		String url = "user_forgetpass?ver=1&email=" + s;
				
//		user_forgetpass?ver=∞Ê±æ∫≈&email=” œ‰
		MyVolleyUtil.setVolleyParameter(context, url, listence, error);
	}
}
