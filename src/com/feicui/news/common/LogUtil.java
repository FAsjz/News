package com.feicui.news.common;

import android.util.Log;

/**
 * 日志管理
 * 
 * @author Administrator
 *
 */
public class LogUtil {
	public static final String  TAG = "新闻随意看";
	public static boolean isDebug = true;
	
	public static void d(String tag,String msg){
		if (isDebug) {
			Log.d(tag, msg);
		}
	}
	
	
			
}
