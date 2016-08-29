package com.feicui.news.ui.base;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

public abstract class MyBaseActivity extends FragmentActivity{
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
	}
	
	//½çÃæÌø×ª
	protected void startActivity(Class<?> cls){
		startActivity(new Intent(this,cls));
	}
	protected void startActivity(Class<?> cls, Bundle bundle){
		Intent it = new Intent();
		it.putExtra("bundle", bundle);
		startActivity(it);
	}
	protected abstract void initView();

	protected abstract void setListener();
}
