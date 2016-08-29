package com.feicui.news;

import java.lang.reflect.Type;

import android.annotation.SuppressLint;
import android.app.ActionBar.LayoutParams;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.onekeyshare.OnekeyShare;
import cn.sharesdk.sina.weibo.SinaWeibo;
import cn.sharesdk.tencent.qq.QQ;
import cn.sharesdk.wechat.friends.Wechat;
import cn.sharesdk.wechat.moments.WechatMoments;

import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.feicui.news.R;
import com.feicui.news.R.id;
import com.feicui.news.R.layout;
import com.feicui.news.R.menu;
import com.feicui.news.common.LogUtil;
import com.feicui.news.common.SystemUtil;
import com.feicui.news.common.ToastUtil;
import com.feicui.news.model.biz.parser.GsonParse;
import com.feicui.news.model.dao.CommentManager;
import com.feicui.news.model.dao.LoginLogManager;
import com.feicui.news.model.dao.NewDBManager;
import com.feicui.news.model.entity.BaseEntity;
import com.feicui.news.model.entity.CommentNumEntity;
import com.feicui.news.model.entity.News;
import com.feicui.news.ui.base.MyBaseActivity;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class ShowActivity extends MyBaseActivity implements OnClickListener {
	private TextView back, intopopup, comment, share;
	private ProgressBar pb;
	private WebView webView;
	private News news;
	private PopupWindow popup1, popup2;
	private ImageView qq, weixin, friend, sine;
	private LinearLayout linear;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_two);
		initView();
		intoPopupWindow();
		setListener();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.two, menu);
		return true;
	}

	@Override
	protected void initView() {
		back = (TextView) findViewById(R.id.main_back);
		webView = (WebView) findViewById(R.id.two_wb);
		pb = (ProgressBar) findViewById(R.id.two_progressBar);
		news = (News) getIntent().getSerializableExtra("news");
		intopopup = (TextView) findViewById(R.id.show_intopopup);
		comment = (TextView) findViewById(R.id.title_comment_tv);
		share = (TextView) findViewById(R.id.title_comment_tv2);
		linear = (LinearLayout) findViewById(R.id.linear);
		View v = LayoutInflater.from(this).inflate(
				R.layout.activity_item_pop_share, null);
		qq = (ImageView) v.findViewById(R.id.qq);
		weixin = (ImageView) v.findViewById(R.id.weixin);
		friend = (ImageView) v.findViewById(R.id.friend);
		sine = (ImageView) v.findViewById(R.id.weibo);
		popup2 = new PopupWindow(v, LayoutParams.MATCH_PARENT,
				LayoutParams.WRAP_CONTENT, true);
		popup2.setBackgroundDrawable(new BitmapDrawable());
		qq.setOnClickListener(this);
		weixin.setOnClickListener(this);
		friend.setOnClickListener(this);
		sine.setOnClickListener(this);
		if (SystemUtil.isNetConn(this) == true) {
			CommentManager.loadCommentsNumber(this, news.nid, numListener,
					error);
		} else {
			startActivity(new Intent(this, NoNetConnectionActivity.class));
		}

	}

	@Override
	protected void setListener() {

		comment.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent it = new Intent(ShowActivity.this, CommentActivity.class);
				it.putExtra("nid", news.nid);
				it.putExtra("news", news);
				startActivity(it);
				finish();

			}
		});

		//
		intopopup.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (popup1 != null && popup1.isShowing()) {
					popup1.dismiss();
				} else if (popup1 != null) {
					popup1.showAsDropDown(intopopup, 0, 0);
				}

			}
		});
		share.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				if (popup2.isShowing() && popup2 != null) {
					popup2.dismiss();
				} else if (popup2 != null) {
					popup2.showAtLocation(linear, Gravity.BOTTOM, 0, 0);

				}

			}
		});

		back.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				finish();

			}
		});
		// 设置离线打开方式
		webView.getSettings().setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);

		// 设置加载时监听，进度条设置
		webView.setWebChromeClient(new WebChromeClient() {

			@Override
			public void onProgressChanged(WebView view, int newProgress) {
				super.onProgressChanged(view, newProgress);
				pb.setProgress(newProgress);
				if (newProgress >= 100) {
					pb.setVisibility(View.GONE);
				}
			}

		});
		// 设置加载后监听，网页打开方式
		webView.setWebViewClient(new WebViewClient() {

			@Override
			public boolean shouldOverrideUrlLoading(WebView view, String url) {
				webView.loadUrl(url);
				return true;
			}
		});

		webView.loadUrl(news.link);

	}

	public void intoPopupWindow() {
		View popview = LayoutInflater.from(this).inflate(
				R.layout.activity_item_pop_save, null);
		// 对弹窗进行设置
		popup1 = new PopupWindow(popview, LayoutParams.WRAP_CONTENT,
				LayoutParams.WRAP_CONTENT);
		popup1.setFocusable(true);
		popup1.setBackgroundDrawable(new BitmapDrawable());
		LinearLayout ll = (LinearLayout) popview.findViewById(R.id.saveLocal);
		// 设置点击监听，点击后添加新闻到数据库
		ll.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				popup1.dismiss();
				NewDBManager manager = NewDBManager
						.getNewDBManager(ShowActivity.this);
				if (manager.saveLoveNews(news)) {
					Toast.makeText(ShowActivity.this, "收藏成功！\n在主界面侧滑菜单中查看", 0)
							.show();
				} else {
					Toast.makeText(ShowActivity.this,
							"已经收藏过这条新闻了！\n在主界面侧滑菜单中查看", 0).show();
				}
			}

		});

	}

	Listener<String> numListener = new Listener<String>() {

		@Override
		public void onResponse(String response) {
			LogUtil.d("numListener", response);
			int num = GsonParse.getCommentNum(response);
			LogUtil.d("numListener2", num + "");
			comment.setText(num + "跟帖");
		}

	};
	ErrorListener error = new ErrorListener() {

		@Override
		public void onErrorResponse(VolleyError error) {
			ToastUtil.ToastShow(ShowActivity.this, "网络连接错误");

		}

	};

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.weixin:
			showShare(1);
			break;
		case R.id.qq:
			showShare(2);
			break;
		case R.id.friend:
			showShare(3);
			break;
		case R.id.weibo:
			showShare(4);
			break;

		}

	}

	@SuppressLint("SdCardPath")
	private void showShare(int platforms) {
		ShareSDK.initSDK(this);
		OnekeyShare oks = new OnekeyShare();
		// 关闭sso授权
		oks.disableSSOWhenAuthorize();
		// title标题，印象笔记、邮箱、信息、微信、人人网和QQ空间使用
		oks.setTitle(getString(R.string.share));
		// titleUrl是标题的网络链接，仅在人人网和QQ空间使用
		oks.setTitleUrl(news.link);
		// text是分享文本，所有平台都需要这个字段
		oks.setText(news.title);
		// imagePath是图片的本地路径，Linked-In以外的平台都支持此参数
		// oks.setImagePath("/sdcard/test.jpg");
		// url仅在微信（包括好友和朋友圈）中使用
		oks.setUrl("http://sharesdk.cn");
		// comment是我对这条分享的评论，仅在人人网和QQ空间使用
		oks.setComment(news.summary);
		switch (platforms) {
		case 1:
			oks.setPlatform(Wechat.NAME);
			break;
		case 2:
			oks.setPlatform(QQ.NAME);
			break;
		case 3:
			oks.setPlatform(WechatMoments.NAME);
			break;
		case 4:
			oks.setPlatform(SinaWeibo.NAME);
			break;
		}
		// 启动分享GUI
		oks.show(this);
	}

}
