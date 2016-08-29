package com.feicui.news;

import java.util.ArrayList;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.feicui.news.R;
import com.feicui.news.R.id;
import com.feicui.news.R.layout;
import com.feicui.news.R.menu;
import com.feicui.news.common.ImageLoaderUtil;
import com.feicui.news.common.ImageLoaderUtil.ImageLoaderListenter;
import com.feicui.news.common.LogUtil;
import com.feicui.news.common.SharedPreferencesUtil;
import com.feicui.news.common.SystemUtil;
import com.feicui.news.common.ToastUtil;
import com.feicui.news.model.biz.parser.GsonParse;
import com.feicui.news.model.dao.CommentManager;
import com.feicui.news.model.dao.LoginLogManager;
import com.feicui.news.model.entity.Comment;
import com.feicui.news.model.entity.News;
import com.feicui.news.model.entity.UploadInfo;
import com.feicui.news.ui.adapter.CommentAdapter;
import com.feicui.news.ui.base.MyBaseActivity;
import com.feicui.news.view.xlistview.XListView;
import com.feicui.news.view.xlistview.XListView.IXListViewListener;

public class CommentActivity extends MyBaseActivity {
	private TextView back;
	private CommentAdapter adapter;
	private XListView xlv;
	private EditText et;
	private ImageView iv;
	private ProgressBar pb;
	private int nid;
	private News news;
	private Bitmap bitmap;
	private ImageLoaderUtil imageLoad;
	private Handler handler = new Handler(){
		public void handleMessage(android.os.Message msg) {
			adapter.notifyDataSetChanged();
			pb.setVisibility(View.INVISIBLE);
			xlv.setVisibility(View.VISIBLE);
			/*for (int x= 1; x < adapter.getCount(); x++) {
				String iconPath = adapter.getItem(x).getPortrait();
				LogUtil.d("CommentActivity2", "0000");
				bitmap = imageLoad.getBitmap(iconPath);
				LogUtil.d("CommentActivity3", bitmap+"");
				if (bitmap != null) {
					LogUtil.d("CommentActivity4", iconPath);
					ImageView iv = (ImageView) xlv.findViewWithTag(iconPath);
					LogUtil.d("Comment5", iv+"11");
					iv.setImageBitmap(bitmap);
				}
			}*/
			
		};
	};
	private ImageLoaderListenter on = new ImageLoaderListenter(){
		public void imageLoadOk(Bitmap bitmap, String path) {
			ImageView iv = (ImageView) xlv.findViewWithTag(path);
			if (iv != null) {
				LogUtil.d("ImageLoaderListenter", "0000");
				iv.setImageBitmap(bitmap);
			}
		};
	};
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_comment);
		initView();
		setListener();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.comment, menu);
		return true;
	}

	@Override
	protected void initView() {
//		imageLoad = new ImageLoaderUtil(on, this);
		pb = (ProgressBar) findViewById(R.id.comment_progressBar);
		back = (TextView) findViewById(R.id.comment_back);
		xlv = (XListView) findViewById(R.id.comment_list);
		et = (EditText) findViewById(R.id.comment_et);
		iv = (ImageView) findViewById(R.id.comment_send);
		adapter = new CommentAdapter(this);
		xlv.setAdapter(adapter);
		Bundle bundle = getIntent().getExtras();
		nid = bundle.getInt("nid");
		news = (News) getIntent().getSerializableExtra("news");
		new Thread(){
			public void run() {
				pb.setVisibility(View.VISIBLE);
				xlv.setVisibility(View.INVISIBLE);
				if (SystemUtil.isNetConn(CommentActivity.this) == true) {
					CommentManager.loadComments(CommentActivity.this, "1", listener, errorListener, nid,1,10);
				}else {
					startActivity(new Intent(CommentActivity.this,NoNetConnectionActivity.class));
				}
			};
		}.start();
		xlv.setPullLoadEnable(true);
		xlv.setPullRefreshEnable(true);
		xlv.setXListViewListener(new IXListViewListener() {

			@Override
			public void onRefresh() {
				Toast.makeText(CommentActivity.this, "下拉刷新", 0).show();
				 loadNextComment();
				
				xlv.stopRefresh();
			}

			@Override
			public void onLoadMore() {
				Toast.makeText(CommentActivity.this, "上拉加载", 0).show();
				 loadPreComment();
				xlv.stopLoadMore();
			}
		});
	}

	@Override
	protected void setListener() {
		back.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent it = new Intent(CommentActivity.this,ShowActivity.class);
				it.putExtra("news", news);
				startActivity(it);
				finish();
				
			}
		});
		iv.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				String ccontent=et.getText().toString();
				if(ccontent==null || ccontent.equals("")){
				ToastUtil.ToastShow(CommentActivity.this,"评论内容不能为空哦，亲！");
				return;
				}
				SharedPreferences sp = getSharedPreferences("userinfo", Context.MODE_PRIVATE);
				String token = sp.getString("token", "");
				if (TextUtils.isEmpty(token)) {
					ToastUtil.ToastShow(CommentActivity.this,"登录之后才能评论哦，亲！");
//					startActivity(MainActivity.class);
				}else {
					CommentManager.loadSendComments(CommentActivity.this, nid, sendListener, errorListener, token,ccontent);
				}
				
			}
		});
		
	}

	public void loadPreComment(){
		
	}
	public void loadNextComment(){
		LogUtil.d("loadNextComment", "1111");
		CommentManager.loadComments(CommentActivity.this, "1", listener, errorListener, nid,1,5);
		 adapter.notifyDataSetChanged();
	}
	Listener<String> listener = new Listener<String>(){

		@Override
		public void onResponse(String response) {
			ArrayList<Comment> comment = GsonParse.getComment(response);
			if (comment == null) {
				return;
			}
			LogUtil.d("CommentActivity", comment.toString());
//			LogUtil.d("CommentActivity", response);
			adapter.clear();
			adapter.addDataToAdapter(comment);
			
			handler.sendEmptyMessage(0);
		}
		
	};
	Listener<String> sendListener = new Listener<String>(){
		
		@Override
		public void onResponse(String response) {
			LogUtil.d("sendListener", response);
			UploadInfo info =  (UploadInfo) GsonParse.getUploadInfo(response);
			if (info == null) {
				ToastUtil.ToastShow(CommentActivity.this, "评论失败");
				return;
			}
			if (info.getResult() == 0) {
				ToastUtil.ToastShow(CommentActivity.this, "评论成功");
				et.setText(null);
				et.clearFocus();
				loadNextComment();
				} else {
					ToastUtil.ToastShow(CommentActivity.this, "评论失败");
				}
		}
		
	};
	ErrorListener errorListener = new ErrorListener(){

		@Override
		public void onErrorResponse(VolleyError error) {
//			startActivity(new Intent(CommentActivity.this,NoNetConnectionActivity.class));
			pb.setVisibility(View.INVISIBLE);
			LogUtil.d("CommentActivity", "网络连接异常");
			ToastUtil.ToastShow(CommentActivity.this, "网络连接异常");
		}
		
	};
	
}
