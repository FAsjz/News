package com.feicui.news.ui.adapter;


import android.content.Context;
import android.graphics.Bitmap;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.feicui.news.R;
import com.feicui.news.common.HttpUtil;
import com.feicui.news.common.ImageLoaderUtil;
import com.feicui.news.common.LogUtil;
import com.feicui.news.common.ImageLoaderUtil.ImageLoaderListenter;
import com.feicui.news.model.entity.Comment;
import com.feicui.news.ui.base.MyBaseAdapter;

public class CommentAdapter extends MyBaseAdapter<Comment> {

	private ViewHolder vh;
	private ImageLoaderUtil imageLoad;
	public CommentAdapter(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		if (convertView == null) {
			vh = new ViewHolder();
			convertView = inflater.inflate(R.layout.activity_comment_item_list, null);
			vh.im = (ImageView) convertView.findViewById(R.id.imageView1);
			vh.tv1 = (TextView) convertView.findViewById(R.id.textView1);
			vh.tv2 = (TextView) convertView.findViewById(R.id.textView2);
			vh.tv3 = (TextView) convertView.findViewById(R.id.textView3);
			convertView.setTag(vh);
		}else{
			vh = (ViewHolder) convertView.getTag();
		}
		/*try {
			Bitmap bitMap = HttpUtil.httpGetBitmap(mylist.get(position).getPortrait());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
		vh.im.setImageResource(R.drawable.ic_launcher);
		
		ImageLoaderListenter on = new ImageLoaderListenter(){
			public void imageLoadOk(Bitmap bitmap, String path) {
				
					vh.im.setImageBitmap(bitmap);
				
			};
		};
		imageLoad = new ImageLoaderUtil(on,context);
		Bitmap bitmap = imageLoad.getBitmap(mylist.get(position).getPortrait());
		if (bitmap != null) {
			vh.im.setImageBitmap(bitmap);
		}
		vh.im.setTag(mylist.get(position).getPortrait());
		vh.tv1.setText(mylist.get(position).getContent());
		vh.tv2.setText(mylist.get(position).getUid());
		vh.tv3.setText(mylist.get(position).getStamp());
		
		
		return convertView;
	}
	
	class ViewHolder{
		private ImageView im;
		private TextView tv1,tv2,tv3;
		
		
	}
}
