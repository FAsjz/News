package com.feicui.news.model.dao;

import java.text.SimpleDateFormat;
import java.util.Date;
import android.annotation.SuppressLint;
import android.app.DownloadManager;
import android.content.Context;
import android.net.Uri;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.feicui.news.common.MyVolleyUtil;
import com.feicui.news.common.TelePhonyManagerUtil;

public class UpdataManager {
	public static void loadUpdata(Context context,Listener<String> listence,ErrorListener error){
//		update?imei=唯一识别号&pkg=包名&ver=版本
		String url = "update?imei="+TelePhonyManagerUtil.getImei(context)+"&pkg=com.feicui.news&ver=1";
		MyVolleyUtil.setVolleyParameter(context, url, listence, error);
		
	}
	@SuppressLint("NewApi")
	public static void downLoad(String link,Context context){
	//  初始化下载管理器
		DownloadManager manager = (DownloadManager) context
		.getSystemService(Context.DOWNLOAD_SERVICE); 
		//  创建请求
		DownloadManager.Request request = new DownloadManager.Request(
		Uri.parse(link));
		//  设置允许使用的网络类型，wifi
		request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI);
		//  在通知栏显示下载详情在 API 11  中被 setNotificationVisibility()
//		取代
		request.setShowRunningNotification(true);
		//  显示下载界面
		request.setVisibleInDownloadsUi(true);
		SimpleDateFormat dateformat = new SimpleDateFormat("yyyy-MM-ddhh-mm-ss");
		
		String date = dateformat.format(new Date());
		// 设置下载后文件存放的位置-- 如果目标位置已经存在这个文件名，则不执行下
//		用 载，所以用 date  类型随机取名。
		request.setDestinationInExternalFilesDir(context, null, date +
		".apk");
		manager.enqueue(request);//  将下载请求放入队列
	}
	
}
