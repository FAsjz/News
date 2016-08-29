package com.feicui.news.ui;

import java.util.ArrayList;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.feicui.news.R;
import com.feicui.news.ShowActivity;
import com.feicui.news.R.dimen;
import com.feicui.news.R.id;
import com.feicui.news.R.layout;
import com.feicui.news.R.menu;
import com.feicui.news.common.ConfigUtil;
import com.feicui.news.common.HttpUtil;
import com.feicui.news.common.ImageLoaderUtil;
import com.feicui.news.common.ImageLoaderUtil.ImageLoaderListenter;
import com.feicui.news.common.LogUtil;
import com.feicui.news.model.biz.parser.GsonParse;
import com.feicui.news.model.dao.NewDBManager;
import com.feicui.news.model.entity.News;
import com.feicui.news.ui.adapter.NewsAdapter;
import com.feicui.news.ui.base.MyBaseActivity;
import com.feicui.slingmenu.SlidingMenu;

public class FragmentMain extends MyBaseActivity {
	private ArrayList<News> list;
	private SlidingMenu slidingMenu;
	private FragmentNews frag;
	private NewsAdapter adapter;
	private ListView lv;
	private TextView back;
	private NewDBManager helper;
	private ImageLoaderUtil imageLoader;
	private Bitmap bitmap;
	private ImageLoaderListenter on = new ImageLoaderListenter() {
		
		@Override
		public void imageLoadOk(Bitmap bitmap, String path) {
			LogUtil.d("ImageLoaderListenter","bitmap2"+bitmap.toString());
			ImageView iv = (ImageView) lv.findViewWithTag(path);
			if (iv != null) {
				iv.setImageBitmap(bitmap);
			}
			
		}
	};
	private Handler handler = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			for (int i = 0; i < 40; i++) {
				adapter.addDataToAdapter(list);
			}
			lv.setAdapter(adapter);
			
			

		}

	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_menu_news);
		initView();
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

		
		setListener();
	}
	
	public void showNewsContent(){
		//关闭侧拉菜单并打开menu信息
		slidingMenu.showContent();
		if (frag == null) {
			frag = new FragmentNews();
		}
		getSupportFragmentManager().beginTransaction().replace(R.id.menu_news, frag).commit();
	}
	
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	protected void initView() {
		back = (TextView) findViewById(R.id.main_back);
		lv = (ListView) findViewById(R.id.main_list);
		adapter = new NewsAdapter(this);
		helper = NewDBManager.getNewDBManager(this);
		if (helper.getCount() > 0) {
			new Thread() {
				public void run() {
					list = helper.queryNews();
					LogUtil.d("helper", list.toString());
					handler.sendEmptyMessage(0);
				};
			}.start();

		} else {
			new Thread() {
				public void run() {
//					list = ParseNewsUtil.getNews();
					try {
						String s = HttpUtil.httpGetString(ConfigUtil.PATH + ConfigUtil.PATH1);
						list = GsonParse.getNews(s);
						helper.addNews(list);
//						LogUtil.d("添加数据1", list.toString() + "数量" + list.size());
//						LogUtil.d("添加数据2", helper.queryNews().toString() + "数量"
//								+ helper.getCount());
						handler.sendEmptyMessage(0);
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				};
			}.start();

		}

	}

	@Override
	protected void setListener() {
		//返回键设置监听
		back.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				finish();

			}
		});
		//listview设置监听
		lv.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				ArrayList<News> list = adapter.getAdapterDate();
				News news = list.get(arg2);
				Intent it = new Intent(FragmentMain.this, ShowActivity.class);
				Bundle bundle = new Bundle();
				bundle.putSerializable("news", news);
				it.putExtras(bundle);
				startActivity(it);
			}
		});
		
		//listview滚动是设置监听
		lv.setOnScrollListener(new OnScrollListener() {
			int start = 0;
			int end = 0;
			@Override
			public void onScrollStateChanged(AbsListView view, int scrollState) {
				adapter.setFlag(false);
				switch (scrollState) {
				case OnScrollListener.SCROLL_STATE_TOUCH_SCROLL:
					LogUtil.d("SCROLL_STATE_TOUCH_SCROLL", "11");
//					ImageLoaderUtil.flag = true;
					break;
					
				case OnScrollListener.SCROLL_STATE_FLING:
					LogUtil.d("SCROLL_STATE_FLING", "22");
//					ImageLoaderUtil.flag = true;
					break;

				case OnScrollListener.SCROLL_STATE_IDLE:
//					ImageLoaderUtil.flag = false;
					//回调设置监听
					
					
					imageLoader = new ImageLoaderUtil(on, FragmentMain.this);
					for (; start <= end; start++) {
						bitmap = imageLoader.getBitmap(adapter.getItem(start).icon);
						if (bitmap != null) {
							ImageView iv = (ImageView) lv.findViewWithTag(adapter.getItem(start).icon);
							iv.setImageBitmap(bitmap);
						}
					}
					break;
				}
				
			}
			
			@Override
			public void onScroll(AbsListView view, int firstVisibleItem,
					int visibleItemCount, int totalItemCount) {
				start = firstVisibleItem;
				end = visibleItemCount + firstVisibleItem;
				if (start > end ) {
					end--;
				}
				LogUtil.d("onScroll", firstVisibleItem+","+visibleItemCount+","+totalItemCount+",");
			}
		});
		
	}

}
