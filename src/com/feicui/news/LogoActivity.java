package com.feicui.news;

import android.os.Bundle;
import android.view.Menu;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.ScaleAnimation;
import android.widget.ImageView;

import com.feicui.news.R;
import com.feicui.news.R.id;
import com.feicui.news.R.layout;
import com.feicui.news.R.menu;
import com.feicui.news.ui.base.MyBaseActivity;

public class LogoActivity extends MyBaseActivity {
	private ImageView iv;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.activity_logo);
		initView();
		setListener();
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.logo, menu);
		return true;
	}

	@Override
	protected void initView() {
		iv = (ImageView) findViewById(R.id.logo_iv);
		
	}

	@Override
	protected void setListener() {
		AlphaAnimation alpha = new AlphaAnimation((float) 0.2, 1);
		alpha.setDuration(2000);
		alpha.setFillAfter(true);
		alpha.setAnimationListener(new AnimationListener() {
			
			@Override
			public void onAnimationStart(Animation animation) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onAnimationRepeat(Animation animation) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onAnimationEnd(Animation animation) {
				startActivity(MainActivity.class);
				finish();
				
			}
		});
		
		
		
		iv.setAnimation(alpha);
		
	}

}
