package com.feicui.news;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;

import org.apache.http.Header;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Environment;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.feicui.news.common.HttpUtil;
import com.feicui.news.common.LogUtil;
import com.feicui.news.common.SharedPreferencesUtil;
import com.feicui.news.common.SystemUtil;
import com.feicui.news.common.ToastUtil;
import com.feicui.news.model.biz.parser.GsonParse;
import com.feicui.news.model.dao.LoginLogManager;
import com.feicui.news.model.dao.RegistManager;
import com.feicui.news.model.entity.LoginLog;
import com.feicui.news.model.entity.UploadInfo;
import com.feicui.news.model.entity.UserInfo;
import com.feicui.news.model.httpclient.TextHttpResponseHandler;
import com.feicui.news.ui.adapter.LoginLogAdapter;
import com.feicui.news.ui.base.MyBaseActivity;

public class UserActivity extends MyBaseActivity {

	private ImageView back,icon;
	private Button bt;
	private TextView tvname,integral,comment_count;
	private String name;
	private ProgressDialog dialog;
	private LayoutInflater inflater;
	private LinearLayout camera,photo,linearlayout;
	private PopupWindow pw;
	private Bitmap bitmap;
	private String token;
	private File photopath;
	private SharedPreferences shared;
	private LoginLogAdapter adapter;
	private ListView lv;
	private File file;
//	private 
	@SuppressLint("NewApi")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_user);
		//解决主线程中访问网络
		StrictMode.ThreadPolicy policy=new StrictMode.ThreadPolicy.Builder().permitAll().build();
		StrictMode.setThreadPolicy(policy);
		initView();
		setListener();
		
		
	}

	@Override
	protected void initView() {
		dialog = ProgressDialog.show(this, null, "正在加载...");
		icon = (ImageView) findViewById(R.id.icon);
		back = (ImageView) findViewById(R.id.imageView_back);
		bt = (Button) findViewById(R.id.btn_exit);
		tvname = (TextView) findViewById(R.id.name);
		comment_count = (TextView) findViewById(R.id.comment_count);
		integral = (TextView) findViewById(R.id.integral);
		inflater = LayoutInflater.from(this);
		View v = inflater.inflate(R.layout.activity_item_pop_photo, null);
		camera = (LinearLayout) v.findViewById(R.id.pop_camera);
		photo = (LinearLayout) v.findViewById(R.id.pop_photo);
		linearlayout = (LinearLayout) findViewById(R.id.layout);
		pw = new PopupWindow(v,ViewGroup.LayoutParams.MATCH_PARENT,
				ViewGroup.LayoutParams.WRAP_CONTENT,true);
		pw.setBackgroundDrawable(new BitmapDrawable());
		camera.setOnClickListener(photoListener);
		photo.setOnClickListener(photoListener);
		shared = getSharedPreferences("userinfo", Context.MODE_PRIVATE);
		lv = (ListView) findViewById(R.id.list);
		adapter = new LoginLogAdapter(this);
		lv.setAdapter(adapter);
		String name = shared.getString("username", "");
		if (name == "") {
			dialog.dismiss();
			bt.setText("点击登录");
		}else {
			Bitmap bm = BitmapFactory.decodeFile(shared.getString("photopath", ""));
			if (bm != null) {
				icon.setImageBitmap(bm);
			}
			tvname.setText(name);
			token = shared.getString("token", "");
			if (token != null) {
				if (SystemUtil.isNetConn(this) == true) {
					LogUtil.d("isNetConn", SystemUtil.isNetConn(this)+"");
					LoginLogManager.LoadLoginLog(this, loginlogListener, error, token);
				}else {
					dialog.dismiss();
					startActivity(new Intent(this,NoNetConnectionActivity.class));
				}
				
			}
		}

	}

	OnClickListener photoListener = new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			pw.dismiss();
			switch (v.getId()) {
			case R.id.pop_camera:
				Intent camera = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
				startActivityForResult(camera, 1);
				break;
			case R.id.pop_photo:
				Intent sel = new Intent(Intent.ACTION_PICK);
				sel.setType("image/*");
				sel.putExtra("crop", "true");// 设置裁剪功能
				sel.putExtra("aspectX", 1); // 宽高比例
				sel.putExtra("aspectY", 1);
				sel.putExtra("outputX", 80); // 宽高值
				sel.putExtra("outputY", 80);
				sel.putExtra("return-data", true); // 返回裁剪结果
				startActivityForResult(sel, 1);
				break;

			
			}
			
		}
	};
	
	protected void onActivityResult(int arg0, int arg1, Intent arg2) {
		if (arg2 != null) {
			bitmap = arg2.getParcelableExtra("data");
			if (bitmap == null) {
				return;
			}
//			icon.setImageBitmap(bitmap);
			//将图片保存至SD卡
			boolean sdCardExist = 
					Environment.getExternalStorageState().
					equals(android.os.Environment.MEDIA_MOUNTED); //判断sd卡是否存在
			if (sdCardExist){
				file = new File(Environment.getExternalStorageDirectory().getPath(),"newsphoto");
			  }
			if (!file.exists()) {
				file.mkdir();
			}
			photopath = new File(file.getAbsolutePath(),"photo.jpg");
			FileOutputStream os = null;
			try {
				os = new FileOutputStream(photopath);
				bitmap.compress(CompressFormat.JPEG, 90, os);
				 //上传文件到服务器
				LoginLogManager.uploadIcon(UserActivity.this, token, photopath, new MyResponseHandler());
			} catch (FileNotFoundException e) {
				LogUtil.d("OutputStream", "文件读写异常");
				e.printStackTrace();
			} finally{
				if (os != null) {
					try {
						os.close();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		
				
				
		}
	};
	
	
	
	@Override
	protected void setListener() {
		icon.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				pw.showAtLocation(linearlayout, Gravity.BOTTOM, 0, 0);
				
			}
		});
		back.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if (getIntent().getBooleanExtra("login", false)) {
					Intent it = new Intent(UserActivity.this,MainActivity.class);
					startActivity(it);
				}
				finish();
			}
		});
		bt.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
//				MainActivity main = new MainActivity();
//				main.showLoginContent();
				SharedPreferences shared = getSharedPreferences("userinfo", Context.MODE_PRIVATE);
				Editor ed = shared.edit();
				ed.clear();
				ed.commit();
				SharedPreferencesUtil.clearRegister(UserActivity.this);
				startActivity(MainActivity.class);
				finish();
			}
		});
		
		
	}
	
	Listener<String> loginlogListener = new Listener<String>(){

		@Override
		public void onResponse(String response) {
			UserInfo<LoginLog> userInfo = GsonParse.getLoginLog(response);
			dialog.dismiss();
			if (userInfo == null) {
				return;
			}
			String portrait = userInfo.getPortrait();
			int comment = userInfo.getComnum();
			int integration = userInfo.getIntegration();
			ArrayList<LoginLog> loginLog = userInfo.getLoginlog();
			LogUtil.d("loginLog", loginLog.toString());
//			adapter.clear();
			adapter.addDataToAdapter(loginLog);
			adapter.notifyDataSetChanged();
			
			integral.setText("积分："+integration);
			comment_count.setText(comment+"");
			
			Bitmap bitmap;
			if (TextUtils.isEmpty(shared.getString("photopath", ""))) {
				
				try {
					bitmap = HttpUtil.httpGetBitmap(portrait);
					icon.setImageBitmap(bitmap);
					//将图片保存至SD卡
					boolean sdCardExist = 
							Environment.getExternalStorageState().
							equals(android.os.Environment.MEDIA_MOUNTED); //判断sd卡是否存在
					if (sdCardExist){
						file = new File(Environment.getExternalStorageDirectory().getPath(),"newsphoto");
					  }else {
						return;
					}
					if (!file.exists()) {
						file.mkdir();
					}
					photopath = new File(file.getAbsolutePath(),"photo.jpg");
					OutputStream os = null;
					try {
						os = new FileOutputStream(photopath);
						bitmap.compress(CompressFormat.JPEG, 100, os);
						Editor ed = shared.edit();
						ed.putString("photopath", photopath.getAbsolutePath());
						ed.commit();
					} catch (FileNotFoundException e) {
						LogUtil.d("OutputStream", "文件读写异常");
						e.printStackTrace();
					} finally{
						if (os != null) {
							try {
								os.close();
							} catch (IOException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}
					}
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}else {
				bitmap = BitmapFactory.decodeFile(shared.getString("photopath", ""));
				icon.setImageBitmap(bitmap);
			}
			
		}
		
	};
	
	ErrorListener error = new ErrorListener(){

		@Override
		public void onErrorResponse(VolleyError error) {
			ToastUtil.ToastShow(UserActivity.this, "网络连接异常");
			dialog.dismiss();
		}
		
	};	
class MyResponseHandler extends TextHttpResponseHandler{

	@Override
	public void onFailure(int statusCode, Header[] headers,
			String responseString, Throwable throwable) {
		ToastUtil.ToastShow(UserActivity.this, "网络连接异常");
		
	}

	@Override
	public void onSuccess(int statusCode, Header[] headers,
			String responseString) {
		LogUtil.d("getUploadInfo11", responseString+"");
		UploadInfo info =  (UploadInfo) GsonParse.getUploadInfo(responseString);
		if (info == null) {
			return;
		}
		LogUtil.d("getUploadInfo22", info.toString());
		if (info.getResult() == 0) {
			ToastUtil.ToastShow(UserActivity.this, info.getExplain());
			icon.setImageBitmap(bitmap);
			
			Editor ed = shared.edit();
			ed.putString("photopath", photopath.getAbsolutePath());
			ed.commit();
		}else {
			ToastUtil.ToastShow(UserActivity.this, info.getExplain());
		}
		
		
	}

}
}
