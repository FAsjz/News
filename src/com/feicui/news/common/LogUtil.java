package com.feicui.news.common;

import android.util.Log;

/**
 * ��־����
 * 
 * @author Administrator
 *
 */
public class LogUtil {
	public static final String  TAG = "�������⿴";
	public static boolean isDebug = true;
	
	public static void d(String tag,String msg){
		if (isDebug) {
			Log.d(tag, msg);
		}
	}
	
	
			
}
