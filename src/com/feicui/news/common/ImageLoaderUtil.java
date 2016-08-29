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
	//从缓存中获取图片
	public Bitmap getBitmap(String iconPath){
//		if (flag) {
//			return null;
//		}
		//一级缓存
		Bitmap bitmap = cache.get(iconPath);
		if (bitmap != null) {
			return bitmap;
		}
		
		//二级缓存
		File file = context.getCacheDir();
		File[] files = file.listFiles();
		String name = iconPath.substring(iconPath.lastIndexOf("/")+1);
		for (File file2 : files) {
			if (name.equals(file2.getName())) {
				return BitmapFactory.decodeFile(file2.getPath());
			}
		}
		
		
		//下载图片用异步任务方法
		MyTask task = new MyTask();
		task.execute(iconPath);		
		
		
		return null;
		
	}
	/*
	 * 异步任务
	 * 泛型
	 * 1. 要执行的任务的参数,如下载图片需要路径
	 * 2. 执行任务过程中的进度
	 * 3. 任务执行结束后返回的结果
	 * */
	class MyTask extends AsyncTask<String, Void, Bitmap>{
		private String url;
		@Override
		protected Bitmap doInBackground(String... params) {
			url = params[0];
			try {
				Bitmap bitmap =  HttpUtil.httpGetBitmap(url);
				if (bitmap != null) {
					//先保存到缓存区再保存到文件夹中
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
		//当任务执行结束后在UI线程中执行
		@Override
		protected void onPostExecute(Bitmap result) {
			super.onPostExecute(result);
			on.imageLoadOk(result, url);
		}
		//当任务执行前在UI线程中执行
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			
		}
		
	}
	
	
}
