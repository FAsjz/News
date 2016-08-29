package com.feicui.news.ui.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.feicui.news.R;
import com.feicui.news.model.entity.SubType;
import com.feicui.news.ui.base.MyBaseAdapter;

public class NewsTypeAdapter extends MyBaseAdapter<SubType> {

	public NewsTypeAdapter(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		
		
		convertView = inflater.inflate(R.layout.activity_menu_news_item_hlist, null);
		TextView tv = (TextView) convertView.findViewById(R.id.news_hlist_tv);
		tv.setText(mylist.get(position).getSubgroup());
		
		
		
		
		return convertView;
	}
}
