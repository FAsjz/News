package com.feicui.news.ui;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.onekeyshare.OnekeyShare;
import cn.sharesdk.sina.weibo.SinaWeibo;
import cn.sharesdk.tencent.qq.QQ;
import cn.sharesdk.wechat.friends.Wechat;
import cn.sharesdk.wechat.moments.WechatMoments;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.feicui.news.MainActivity;
import com.feicui.news.R;
import com.feicui.news.UserActivity;
import com.feicui.news.common.LogUtil;
import com.feicui.news.common.SystemUtil;
import com.feicui.news.common.ToastUtil;
import com.feicui.news.model.biz.parser.GsonParse;
import com.feicui.news.model.dao.UpdataManager;
import com.feicui.news.model.entity.Version;

public class FragmentRight extends Fragment implements OnClickListener {
	private LinearLayout unLogined;
	private LinearLayout Logined;
	private TextView tv,updata;
	private ImageView iv, weixin, qq, friend, weibo;
	private Bundle bundle;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_menu_right, null);
		unLogined = (LinearLayout) view.findViewById(R.id.menu_right_unlogin);
		Logined = (LinearLayout) view.findViewById(R.id.menu_right_logined);
		tv = (TextView) view.findViewById(R.id.menu_right_logined_tv);
		updata = (TextView) view.findViewById(R.id.updata_tv);
		iv = (ImageView) view.findViewById(R.id.menu_right_logined_iv);
		weixin = (ImageView) view.findViewById(R.id.weixin);
		qq = (ImageView) view.findViewById(R.id.qq);
		friend = (ImageView) view.findViewById(R.id.friend);
		weibo = (ImageView) view.findViewById(R.id.weibo);
		weixin.setOnClickListener(this);
		qq.setOnClickListener(this);
		friend.setOnClickListener(this);
		weibo.setOnClickListener(this);
		SharedPreferences shared = getActivity().getSharedPreferences(
				"userinfo", Context.MODE_PRIVATE);
		String name = shared.getString("username", "");
		LogUtil.d("FragmentRight", name);
		if (!TextUtils.isEmpty(name)) {
			unLogined.setVisibility(View.GONE);
			Logined.setVisibility(View.VISIBLE);
			tv.setText(name);
			Bitmap bm = BitmapFactory.decodeFile(shared.getString("photopath",
					""));
			if (bm != null) {
				iv.setImageBitmap(bm);
			}

		}
		unLogined.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				
				// 替换loginFragment
				((MainActivity) getActivity()).showLoginContent();

			}
		});
		Logined.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent it = new Intent(getActivity(), UserActivity.class);
				getActivity().startActivity(it);
				((MainActivity) getActivity()).showNewsContent();
//				getActivity().finish();
			}
		});
		updata.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				LogUtil.d("updata", "updata");
				UpdataManager.loadUpdata(getActivity(), updataListence, error);
				
			}
		});
		return view;
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		// 分享到微信
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
	Listener<String> updataListence = new Listener<String>(){

		@Override
		public void onResponse(String response) {
			LogUtil.d("updataListence", response);
			Version version = GsonParse.getUpdataInfo(response);
			if (Integer.parseInt(version.getVersion()) > SystemUtil.getVersionCode(FragmentRight.this
					.getActivity())) {
				ToastUtil.ToastShow(getActivity(), "正在下载最新版本");
				UpdataManager.downLoad(version.getLink(), getActivity());
			}else{
				ToastUtil.ToastShow(getActivity(), "已是最新版本");
			}
			
		}

		
		
	};
	ErrorListener error = new ErrorListener(){

		@Override
		public void onErrorResponse(VolleyError error) {
			ToastUtil.ToastShow(getActivity(), "版本更新失败");
			
		}
		
	};

	@SuppressLint("SdCardPath")
	private void showShare(int platforms) { 
		ShareSDK.initSDK(getActivity()); 
		OnekeyShare oks = new OnekeyShare(); 
		//关闭sso授权 
		oks.disableSSOWhenAuthorize(); 
		// title标题，印象笔记、邮箱、信息、微信、人人网和QQ空间使用 
		oks.setTitle(getString(R.string.share)); 
		// titleUrl是标题的网络链接，仅在人人网和QQ空间使用 
		oks.setTitleUrl("http://sharesdk.cn"); 
		// text是分享文本，所有平台都需要这个字段 
		oks.setText("sjz新闻客户端"); 
		// imagePath是图片的本地路径，Linked-In以外的平台都支持此参数 
//		 oks.setImagePath("/sdcard/test.jpg"); 
		// url仅在微信（包括好友和朋友圈）中使用 
		oks.setUrl("http://sharesdk.cn"); 
		// comment是我对这条分享的评论，仅在人人网和QQ空间使用 
		oks.setComment("sjz新闻客户端是一款好的新闻软件"); 
		switch (platforms) { 
		case 1: oks.setPlatform(Wechat.NAME); 
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
		oks.show(getActivity()); 
		}
      
	
}
