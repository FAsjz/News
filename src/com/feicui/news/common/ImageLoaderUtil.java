package com.feicui.news.common;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Bitmap.CompressFormat;
import android.os.AsyncTask;
import android.support.v4.util.LruCache;

public class ImageLoaderUtil {
	private ImageLoaderListenter on;
	public static boolean flag = false;
	private Context context;
	private LruCache<String, Bitmap> cache;
	public interface ImageLoaderListenter{
		void imageLoadOk(Bitmap bitmap,String path);
	};
	public ImageLoaderUtil(ImageLoaderListenter on,Context context){
		this.on = on;
		this.context = context;
		cache = new LruCache<String, Bitmap>((int) (Runtime.getRuntime().maxMemory()/4)){
			@Override
			protected int sizeOf(String key, Bitmap value) {
				// TODO Auto-generated method stub
				return value.getRowBytes() * value.getHeight();
			}
		};
		
	}
	//�ӻ����л�ȡͼƬ
	public Bitmap getBitmap(String iconPath){
//		if (flag) {
//			return null;
//		}
		//һ������
		Bitmap bitmap = cache.get(iconPath);
		if (bitmap != null) {
			return bitmap;
		}
		
		//��������
		File file = context.getCacheDir();
		File[] files = file.listFiles();
		String name = iconPath.substring(iconPath.lastIndexOf("/")+1);
		for (File file2 : files) {
			if (name.equals(file2.getName())) {
				return BitmapFactory.decodeFile(file2.getPath());
			}
		}
		
		
		//����ͼƬ���첽���񷽷�
		MyTask task = new MyTask();
		task.execute(iconPath);		
		
		
		return null;
		
	}
	/*
	 * �첽����
	 * ����
	 * 1. Ҫִ�е�����Ĳ���,������ͼƬ��Ҫ·��
	 * 2. ִ����������еĽ���
	 * 3. ����ִ�н����󷵻صĽ��
	 * */
	class MyTask extends AsyncTask<String, Void, Bitmap>{
		private String url;
		@Override
		protected Bitmap doInBackground(String... params) {
			url = params[0];
			try {
				Bitmap bitmap =  HttpUtil.httpGetBitmap(url);
				if (bitmap != null) {
					//�ȱ��浽�������ٱ��浽�ļ�����
					cache.put(url, bitmap);
					String name = url.substring(url.lastIndexOf("/")+1);
					File file = new File(context.getCacheDir().getPath() + "/" + name);
					OutputStream os = null;
					os = new FileOutputStream(file);
					bitmap.compress(CompressFormat.PNG, 70, os);
					os.close();
				}
				
				return bitmap;
			} catch (Exception e) {
				e.printStackTrace();
				return null;
			}
			
			
			
		}
		//������ִ�н�������UI�߳���ִ��
		@Override
		protected void onPostExecute(Bitmap result) {
			super.onPostExecute(result);
			on.imageLoadOk(result, url);
		}
		//������ִ��ǰ��UI�߳���ִ��
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			
		}
		
	}
	
	
}
