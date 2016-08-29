package com.feicui.news.ui;

import java.util.ArrayList;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.StrictMode;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AbsListView;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.feicui.news.R;
import com.feicui.news.ShowActivity;
import com.feicui.news.common.ConfigUtil;
import com.feicui.news.common.HttpUtil;
import com.feicui.news.common.ImageLoaderUtil;
import com.feicui.news.common.LogUtil;
import com.feicui.news.common.SystemUtil;
import com.feicui.news.common.ImageLoaderUtil.ImageLoaderListenter;
import com.feicui.news.model.biz.parser.GsonParse;
import com.feicui.news.model.dao.DBOpenHelper;
import com.feicui.news.model.dao.NewDBManager;
import com.feicui.news.model.dao.NewsManager;
import com.feicui.news.model.dao.NewsTypeManager;
import com.feicui.news.model.entity.News;
import com.feicui.news.model.entity.SubType;
import com.feicui.news.ui.adapter.NewsAdapter;
import com.feicui.news.ui.adapter.NewsTypeAdapter;
import com.feicui.news.view.HorizontalListView;
import com.feicui.news.view.xlistview.XListView;
import com.feicui.news.view.xlistview.XListView.IXListViewListener;

public class FragmentNews extends Fragment {
	private HorizontalListView hlv;
	private NewDBManager dbManager;
	private NewsTypeAdapter adapter;
	private NewsAdapter newsAdapter;
	private XListView vlv;
	private int nId, subId = 1;
	private ProgressBar pb;
	private ImageLoaderUtil imageLoader;
	private Bitmap bitmap;
	private Handler handler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			pb.setVisibility(View.INVISIBLE);
			vlv.setVisibility(View.VISIBLE);
			newsAdapter.notifyDataSetChanged();
			adapter.notifyDataSetChanged();
		};
	};
	private ImageLoaderListenter on = new ImageLoaderListenter() {

		@Override
		public void imageLoadOk(Bitmap bitmap, String path) {
			// LogUtil.d("ImageLoaderListenter","bitmap2"+bitmap.toString());
			ImageView iv = (ImageView) vlv.findViewWithTag(path);
			if (iv != null) {
				iv.setImageBitmap(bitmap);
			}

		}
	};

	@TargetApi(Build.VERSION_CODES.GINGERBREAD)
	@SuppressLint("NewApi")
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.activity_menu_news, null);
		pb = (ProgressBar) v.findViewById(R.id.main_progressBar);
		hlv = (HorizontalListView) v.findViewById(R.id.menu_news_hlv);
		vlv = (XListView) v.findViewById(R.id.main_list);
		adapter = new NewsTypeAdapter(getActivity());
		newsAdapter = new NewsAdapter(getActivity());
		hlv.setAdapter(adapter);
		vlv.setAdapter(newsAdapter);
		dbManager = NewDBManager.getNewDBManager(getActivity());
		/*
		 * StrictMode.ThreadPolicy policy = new
		 * StrictMode.ThreadPolicy.Builder() .permitAll().build();
		 * StrictMode.setThreadPolicy(policy);
		 */
		new Thread() {
			public void run() {
				loadNextType();
				loadNews();

			};
		}.start();

		// 设置上拉和下拉
		vlv.setPullLoadEnable(true);
		vlv.setPullRefreshEnable(true);
		vlv.setXListViewListener(new IXListViewListener() {

			@Override
			public void onRefresh() {
				Toast.makeText(getActivity(), "下拉刷新", 0).show();
				// loadNextNews(false);

				vlv.stopRefresh();
			}

			@Override
			public void onLoadMore() {
				Toast.makeText(getActivity(), "上拉加载", 0).show();
				// loadPreNews();
				vlv.stopLoadMore();
			}
		});

		// xlistview设置监听
		vlv.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				ArrayList<News> list = newsAdapter.getAdapterDate();
				if (arg2 == 0) {
					return;
				}
				News news = list.get(arg2 - 1);
				Intent it = new Intent(getActivity(), ShowActivity.class);
				Bundle bundle = new Bundle();
				bundle.putSerializable("news", news);
				it.putExtras(bundle);
				startActivity(it);
			}
		});
		hlv.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				for (int i = 0; i < arg0.getChildCount(); i++) {
					arg0.getChildAt(i).setSelected(false);
				}
				arg1.setSelected(true);
				// subId = arg2;
				/*
				 * NewsManager.loadNewsFromServer(getActivity(), subId, nId, new
				 * VolleyResponseHandler(), new VolleyErrorHandler());
				 * LogUtil.d("setOnItemClickListener", "11111");
				 * newsAdapter.notifyDataSetChanged();
				 */
			}
		});

		/*
		 * //listview滚动是设置监听 vlv.setOnScrollListener(new OnScrollListener() {
		 * int start = 0; int end = 0;
		 * 
		 * @Override public void onScrollStateChanged(AbsListView view, int
		 * scrollState) { newsAdapter.setFlag(false); switch (scrollState) {
		 * case OnScrollListener.SCROLL_STATE_TOUCH_SCROLL:
		 * LogUtil.d("SCROLL_STATE_TOUCH_SCROLL", "11"); // ImageLoaderUtil.flag
		 * = true; break;
		 * 
		 * case OnScrollListener.SCROLL_STATE_FLING:
		 * LogUtil.d("SCROLL_STATE_FLING", "22"); // ImageLoaderUtil.flag =
		 * true; break;
		 * 
		 * case OnScrollListener.SCROLL_STATE_IDLE: // ImageLoaderUtil.flag =
		 * false; //回调设置监听
		 * 
		 * 
		 * imageLoader = new ImageLoaderUtil(on, getActivity()); for (; start <=
		 * end; start++) { bitmap =
		 * imageLoader.getBitmap(newsAdapter.getItem(start).icon); if (bitmap !=
		 * null) { ImageView iv = (ImageView)
		 * vlv.findViewWithTag(newsAdapter.getItem(start).icon);
		 * iv.setImageBitmap(bitmap); } } break; }
		 * 
		 * }
		 * 
		 * @Override public void onScroll(AbsListView view, int
		 * firstVisibleItem, int visibleItemCount, int totalItemCount) { // TODO
		 * Auto-generated method stub
		 * 
		 * } });
		 */
		return v;
	}

	/*
	 * // 上拉获取旧的数据 protected void loadPreNews() { nId
	 * =newsAdapter.getItem(vlv.getLastVisiblePosition()-2).nid; if
	 * (dbManager.getCount() > 0 ) { ArrayList<News> news =
	 * dbManager.queryNews(); newsAdapter.addDataToAdapter(news);
	 * newsAdapter.notifyDataSetChanged(); }else{
	 * NewsManager.loadNewsFromServer(getActivity(), 1, 1, nId, new
	 * VolleyResponseHandler(),new VolleyErrorHandler());
	 * 
	 * } }
	 */

	/*
	 * // 下拉获取新数据 protected void loadNextNews(boolean b) { int nId = 0; if(!b) {
	 * //获得适配器第一条信息ID if(newsAdapter.getAdapterDate().size() > 0){
	 * nId=newsAdapter.getItem(0).nid; } }
	 * 
	 * //从服务器加载新的新闻信息 if(SystemUtil.isNetConn(getActivity())){
	 * NewsManager.loadNewsFromServer(getActivity(),1, subId, nId, new
	 * VolleyResponseHandler(),new VolleyErrorHandler()); }else{ //
	 * NewsManager.loadNewsFromsLocal(1,nId,new MyLocalResponseHandler()); }
	 * 
	 * 
	 * 
	 * }
	 */

	public void loadNews() {
		if (dbManager.getCount() > 0) {
			ArrayList<News> news = dbManager.queryNews();
			// LogUtil.d("loadNews1", news.toString());
			newsAdapter.clear();
			newsAdapter.addDataToAdapter(news);

		} else {
			/*
			 * try { String s = HttpUtil.httpGetString(ConfigUtil.PATH +
			 * ConfigUtil.PATH1); ArrayList<News> news = GsonParse.getNews(s);
			 * LogUtil.d("loadNews2", news.toString()); dbManager.addNews(news);
			 * newsAdapter.addDataToAdapter(news);
			 * 
			 * } catch (Exception e) { LogUtil.d("loadNews3", "error");
			 * e.printStackTrace(); }
			 */
			nId = 1;
			subId = 1;
			NewsManager.loadNewsFromServer(getActivity(), subId, nId,
					new VolleyResponseHandler(), new VolleyErrorHandler());

		}
		handler.sendEmptyMessage(0);

	}

	// 加载新闻类型
	public void loadNextType() {
		if (dbManager.getNewsTypeCount() > 0) {
			ArrayList<SubType> type = dbManager.queryNewsType();
			adapter.addDataToAdapter(type);

		} else {
			NewsTypeManager.loadNewsType(new VolleyTypeResponseHandler(),
					new VolleyErrorHandler(), getActivity());
		}
	}

	// 获取新闻类型数据
	public class VolleyTypeResponseHandler implements Listener<String> {
		@Override
		public void onResponse(String response) {
			// LogUtil.d("response", response.toString());
			ArrayList<SubType> subType = GsonParse.getNewsType(response);

			dbManager.addNewsType(subType);
			adapter.addDataToAdapter(subType);

		}
	}

	// 新闻列表回调接口
	public class VolleyResponseHandler implements Listener<String> {

		@Override
		public void onResponse(String response) {
			LogUtil.d("VolleyResponseHandler1", response);
			ArrayList<News> news = GsonParse.getNews(response);

			if (news != null) {

				dbManager.addNews(news);
			}
			newsAdapter.clear();
			newsAdapter.addDataToAdapter(news);

		}
	}

	public class VolleyErrorHandler implements ErrorListener {
		@Override
		public void onErrorResponse(VolleyError error) {
			// getActivity().cancelDialog();
			// mainActivity.showToast("服务器连接异常");
			LogUtil.d("log", "服务器连接异常");
		}
	}
}
