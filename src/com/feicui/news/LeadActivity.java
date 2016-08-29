package com.feicui.news;

import com.feicui.news.R;
import com.feicui.news.R.drawable;
import com.feicui.news.R.id;
import com.feicui.news.R.layout;
import com.feicui.news.R.menu;
import com.feicui.news.ui.base.MyBaseActivity;
import com.feicui.news.ui.base.MyBasePagerAdapter;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class LeadActivity extends MyBaseActivity {
	private ViewPager vp;
	private MyBasePagerAdapter adapter;
	private ImageView[] ivs = new ImageView[4];
	private TextView tv;
	private Button bt;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.activity_lead);
		//判断是否是第一次打开
		SharedPreferences shared = getSharedPreferences("first", Context.MODE_PRIVATE);
		boolean isFirst = shared.getBoolean("first", true);
		if (isFirst) {
			Editor ed = shared.edit();
			ed.putBoolean("first", false);
			ed.commit();
			initView();
			setListener();
		}else {
			
			startActivity(LogoActivity.class);
			finish();
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.lead, menu);
		return true;
	}

	@Override
	protected void initView() {
		vp = (ViewPager) findViewById(R.id.lead_vp);
		ivs[0] = (ImageView) findViewById(R.id.lead_iv1);
		ivs[1] = (ImageView) findViewById(R.id.lead_iv2);
		ivs[2] = (ImageView) findViewById(R.id.lead_iv3);
		ivs[3] = (ImageView) findViewById(R.id.lead_iv4);
		tv = (TextView) findViewById(R.id.lead_tv);
		bt = (Button) findViewById(R.id.lead_bt);
		adapter = new MyBasePagerAdapter(this);
		vp.setAdapter(adapter);
	}

	@Override
	protected void setListener() {
		tv.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				startActivity(LogoActivity.class);
				finish();
				
			}
		});
		bt.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				startActivity(LogoActivity.class);
				finish();
				
			}
		});
		vp.setOnPageChangeListener(new OnPageChangeListener() {
			
			@Override
			public void onPageSelected(int arg0) {
				for (int i = 0; i < ivs.length; i++) {
					if (arg0 == i) {
						ivs[i].setImageResource(R.drawable.lead_rhq_b);
					}else {
						ivs[i].setImageResource(R.drawable.lead_rhq_a);
					}
					if (arg0 == ivs.length-1) {
						bt.setVisibility(View.VISIBLE);
						tv.setVisibility(View.INVISIBLE);
					}else {
						bt.setVisibility(View.INVISIBLE);
						tv.setVisibility(View.VISIBLE);
					}
				}
				
			}
			
			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onPageScrollStateChanged(int arg0) {
				// TODO Auto-generated method stub
				
			}
		});
	}

}
