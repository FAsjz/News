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
//		update?imei=Ψһʶ���&pkg=����&ver=�汾
		String url = "update?imei="+TelePhonyManagerUtil.getImei(context)+"&pkg=com.feicui.news&ver=1";
		MyVolleyUtil.setVolleyParameter(context, url, listence, error);
		
	}
	@SuppressLint("NewApi")
	public static void downLoad(String link,Context context){
	//  ��ʼ�����ع�����
		DownloadManager manager = (DownloadManager) context
		.getSystemService(Context.DOWNLOAD_SERVICE); 
		//  ��������
		DownloadManager.Request request = new DownloadManager.Request(
		Uri.parse(link));
		//  ��������ʹ�õ��������ͣ�wifi
		request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI);
		//  ��֪ͨ����ʾ���������� API 11  �б� setNotificationVisibility()
//		ȡ��
		request.setShowRunningNotification(true);
		//  ��ʾ���ؽ���
		request.setVisibleInDownloadsUi(true);
		SimpleDateFormat dateformat = new SimpleDateFormat("yyyy-MM-ddhh-mm-ss");
		
		String date = dateformat.format(new Date());
		// �������غ��ļ���ŵ�λ��-- ���Ŀ��λ���Ѿ���������ļ�������ִ����
//		�� �أ������� date  �������ȡ����
		request.setDestinationInExternalFilesDir(context, null, date +
		".apk");
		manager.enqueue(request);//  ����������������
	}
	
}
