package com.feicui.news;

import com.feicui.news.common.LogUtil;

import cn.jpush.android.api.JPushInterface;
import android.app.Application;

public class MyApplication extends Application {

	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
		LogUtil.d("MyApplication","MyApplication");
		JPushInterface.setDebugMode(true);
        JPushInterface.init(this);
	}
}
