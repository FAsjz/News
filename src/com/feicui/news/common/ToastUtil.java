package com.feicui.news.common;

import android.content.Context;
import android.widget.Toast;

public class ToastUtil {

	
	public static void ToastShow(Context context,String message){
		Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
	}
}
