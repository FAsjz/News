package com.feicui.news.ui.adapter;


import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.feicui.news.R;
import com.feicui.news.model.entity.LoginLog;
import com.feicui.news.ui.base.MyBaseAdapter;

public class LoginLogAdapter extends MyBaseAdapter<LoginLog> {

	public LoginLogAdapter(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder vh = null;
		if (convertView == null) {
			vh = new ViewHolder();
			convertView = inflater.inflate(R.layout.activity_item_login_log, null);
			vh.tv1 = (TextView) convertView.findViewById(R.id.login_time);
			vh.tv2 = (TextView) convertView.findViewById(R.id.login_adress);
			vh.tv3 = (TextView) convertView.findViewById(R.id.login_type);
			convertView.setTag(vh);
		}else {
			vh = (ViewHolder) convertView.getTag();
		}
		vh.tv1.setText(mylist.get(position).getTime());
		vh.tv2.setText(mylist.get(position).getAddress());
		vh.tv3.setText(mylist.get(position).getDevice()  == 0 ? "ÒÆ¶¯¶ËµÇÂ½" : "ÍøÒ³¶ËµÇÂ¼");
		
		
		return convertView;
	}
	class ViewHolder{
		private TextView tv1,tv2,tv3;
	}

	

}
