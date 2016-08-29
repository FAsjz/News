package com.feicui.news.ui.adapter;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.feicui.news.R;
import com.feicui.news.common.HttpUtil;
import com.feicui.news.common.ImageLoaderUtil;
import com.feicui.news.common.ImageLoaderUtil.ImageLoaderListenter;
import com.feicui.news.model.entity.News;
import com.feicui.news.ui.base.MyBaseAdapter;

public class MainAdapter extends MyBaseAdapter<News> {
	private ViewHold vh = null;
	private ImageLoaderUtil imageLoader;
	private boolean flag = true;
	public void setFlag(boolean flag) {
		this.flag = flag;
	}
	public MainAdapter(Context context) {
		super(context);
		
	}
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		
		if (convertView == null) {
			vh = new ViewHold();
			convertView = inflater.inflate(R.layout.activity_main_item_list, null);
			vh.iv = (ImageView) convertView.findViewById(R.id.main_list_item_iv);
			vh.tv1 = (TextView) convertView.findViewById(R.id.main_list_item_tv1);
			vh.tv2 = (TextView) convertView.findViewById(R.id.main_list_item_tv2);
			convertView.setTag(vh);
		}else{
			vh = (ViewHold) convertView.getTag();
		}
		
		vh.iv.setTag(mylist.get(position).icon);
		vh.iv.setImageResource(R.drawable.ic_launcher);
		//»Øµ÷ÉèÖÃ¼àÌý
		if(flag){
			ImageLoaderListenter on = new ImageLoaderListenter() {
				
				@Override
				public void imageLoadOk(Bitmap bitmap, String path) {
					
					vh.iv.setImageBitmap(bitmap);
					
				}
			};
			imageLoader = new ImageLoaderUtil(on, context);
			Bitmap bitmap = imageLoader.getBitmap(mylist.get(position).icon);
			if (bitmap != null) {
				vh.iv.setImageBitmap(bitmap);
			}
			
		}
		vh.tv1.setText(mylist.get(position).title);
		vh.tv2.setText(mylist.get(position).summary);
		
		return convertView;
	}
	class ViewHold{
		private ImageView iv;
		private TextView tv1,tv2;
	}
}
