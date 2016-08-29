package com.feicui.news.model.dao;

import com.android.volley.RequestQueue;
import com.android.volley.Request.Method;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.toolbox.StringRequest;
import com.feicui.news.common.ConfigUtil;
import com.feicui.news.common.MyVolleyUtil;
import com.feicui.news.common.TelePhonyManagerUtil;
import com.feicui.news.volley.VolleySingleton;

import android.content.Context;

public class CommentManager {
	public static void loadComments(Context context,String ver, 
			Listener<String> listener,ErrorListener errorListener, int... args) {
		String url = ConfigUtil.PATH + "/cmt_list?ver=" + ver + "&nid=" + args[0] +
				"&dir=" + args[1] + "&cid=" + args[2] + "&type=" + 1 + "&stamp=" + "20140707"; 
		VolleySingleton vs = VolleySingleton.getVolleySingleton(context);
		RequestQueue queue = vs.getRequestQueue();
		StringRequest sr = new StringRequest(Method.GET, url, listener, errorListener);
		queue.add(sr);
	}
	public static void loadCommentsNumber(Context context,int nid,Listener<String> listence,ErrorListener error){
//		cmt_num?ver=版本号& nid=新闻编号
		String url = "cmt_num?ver=1&nid=" + nid;
		MyVolleyUtil.setVolleyParameter(context, url, listence, error);
	}
	public static void loadSendComments(Context context,int nid,Listener<String> listence,ErrorListener error,String... s){
//		cmt_commit?ver=版本号&nid=新闻编号&token=用户令牌 &imei=手机标识符&ctx=评论内容
//		cmt_commit?nid=" + nid + "&ver="+ args[0] + "&token=" + args[1] + "&imei=" + args[2] + "&ctx="
//				+ args[3];
		String mei = TelePhonyManagerUtil.getImei(context);
		String url ="cmt_commit?nid=" + nid + "&ver=1&token=" + s[0] + "&imei=" + mei + "&ctx="+ s[1];
		MyVolleyUtil.setVolleyParameter(context, url, listence, error);
	}
	
	
	
}
