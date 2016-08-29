package com.feicui.news.ui.base;

import java.util.ArrayList;

import com.feicui.news.R;
import com.feicui.news.common.LogUtil;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

public class MyBasePagerAdapter extends PagerAdapter {
	protected LayoutInflater inflater;
	protected Context context;
	private ImageView iv;
	private int[] src = {R.drawable.lead1,R.drawable.lead2,R.drawable.lead3,R.drawable.lead4};
	protected ArrayList<View> mlist = new ArrayList<View>();
	
	public MyBasePagerAdapter(Context context){
		this.context = context;
		inflater = LayoutInflater.from(context);
		for (int s : src) {
			View v = inflater.inflate(R.layout.activity_lead_item, null);
			iv = (ImageView) v.findViewById(R.id.lead_item_iv);
			iv.setImageResource(s);
			mlist.add(v);
		}
		
	}
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return mlist.size();
	}

	@Override
	public boolean isViewFromObject(View arg0, Object arg1) {
		// TODO Auto-generated method stub
		return arg0 == arg1;
	}

	@Override
	public Object instantiateItem(ViewGroup container, int position) {
		// TODO Auto-generated method stub
		View v = mlist.get(position);
		LogUtil.d("mlist adapter", mlist.toString() + "11");
		container.addView(v);
		return v;
	}
	@Override
	public void destroyItem(ViewGroup container, int position, Object object) {
		container.removeView(mlist.get(position));
	}
}
