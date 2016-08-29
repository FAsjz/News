package com.feicui.news;

import com.feicui.news.R;
import com.feicui.news.R.dimen;
import com.feicui.news.R.id;
import com.feicui.news.R.layout;
import com.feicui.news.R.menu;
import com.feicui.news.ui.FragmentFavorite;
import com.feicui.news.ui.FragmentForgetPass;
import com.feicui.news.ui.FragmentLeft;
import com.feicui.news.ui.FragmentLogin;
import com.feicui.news.ui.FragmentNews;
import com.feicui.news.ui.FragmentRegister;
import com.feicui.news.ui.FragmentRight;
import com.feicui.news.ui.base.MyBaseActivity;
import com.feicui.slingmenu.SlidingMenu;

import android.os.Bundle;
import android.app.Activity;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends MyBaseActivity {
	public static SlidingMenu slidingMenu;
	private FragmentNews frag;
	private TextView title;
	private ImageView home,share;
	private long mcurTime;
	private long mtime;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_main);
		initView();
		/*if (frag == null) {
			frag = new FragmentNews();
		}
		getSupportFragmentManager().beginTransaction().replace(R.id.menu_news, frag).commit();*/
		
		setListener();
	}
	public void showNewsContent(){
		title.setText("资讯");
		//关闭侧拉菜单并打开menu信息
		slidingMenu.showContent();
		if (frag == null) {
			frag = new FragmentNews();
		}
		getSupportFragmentManager().beginTransaction().replace(R.id.menu_news, frag).commit();
	}
	public void showFragmentContent() {
		title.setText("收藏");
		slidingMenu.showContent();
		FragmentFavorite favorite = null;
		if (favorite == null) {
			favorite = new FragmentFavorite();
		}
		getSupportFragmentManager().beginTransaction().replace(R.id.menu_news, favorite).commit();
		
	}
	public void showRegisterContent() {
		title.setText("用户注册");
		slidingMenu.showContent();
		FragmentRegister favorite = null;
		if (favorite == null) {
			favorite = new FragmentRegister();
		}
		getSupportFragmentManager().beginTransaction().replace(R.id.menu_news, favorite).commit();
		
	}
	public void showForgetPassContent() {
		title.setText("密码找回");
		slidingMenu.showContent();
		FragmentForgetPass favorite = null;
		if (favorite == null) {
			favorite = new FragmentForgetPass();
		}
		getSupportFragmentManager().beginTransaction().replace(R.id.menu_news, favorite).commit();
		
	}
	public void showLoginContent() {
		if (title != null) {
			title.setText("用户登录");
		}
		slidingMenu.showContent();
		FragmentLogin favorite = null;
		if (favorite == null) {
			favorite = new FragmentLogin();
		}
		getSupportFragmentManager().beginTransaction().replace(R.id.menu_news, favorite).commit();
		
	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	protected void initView() {
		title = (TextView) findViewById(R.id.main_title_tv);
		home =(ImageView) findViewById(R.id.main_home);
		share =(ImageView) findViewById(R.id.main_share);
		slidingMenu = new SlidingMenu(this);
		//设置侧拉栏为双向测拉栏
		slidingMenu.setMode(SlidingMenu.LEFT_RIGHT);
		slidingMenu.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
		//设置菜单栏显示时偏移量，可定义在 res/values/dimens 文件中
		slidingMenu.setBehindOffsetRes(R.dimen.activity_menu_left);
		slidingMenu.attachToActivity(this,SlidingMenu.SLIDING_CONTENT);
		//设置左右菜单栏的布局
		slidingMenu.setMenu(R.layout.activity_main_left);
		slidingMenu.setSecondaryMenu(R.layout.activity_main_right);
		//设置左右菜单栏具体的样式
		FragmentLeft left = new FragmentLeft();
		FragmentRight right = new FragmentRight();
		getSupportFragmentManager().beginTransaction().replace(R.id.menu_left
				, left).commit();
		getSupportFragmentManager().beginTransaction().replace(R.id.menu_right, 
				right).commit();
		if (frag == null) {
			frag = new FragmentNews();
		}
		getSupportFragmentManager().beginTransaction().replace(R.id.menu_news, frag).commit();
	}

	@Override
	protected void setListener() {
		home.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
//				startActivity(FragmentLeft.class);
				slidingMenu.toggle();
				
			}
		});
		share.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
//				startActivity(FragmentLeft.class);
				slidingMenu.showSecondaryMenu();
			}
		});
		
	}
	/**
	 * 返回键双击退出设置
	 */
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		switch (keyCode) {
		case KeyEvent.KEYCODE_BACK:
			mcurTime = System.currentTimeMillis();
			if (mcurTime - mtime <= 1000) {
				this.finish();
			}
			Toast.makeText(this, "再按一次返回键退出", 0).show();
			mtime = mcurTime;
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}


}
