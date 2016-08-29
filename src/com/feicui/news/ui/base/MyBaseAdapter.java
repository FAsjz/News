package com.feicui.news.ui.base;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

public class MyBaseAdapter<T> extends BaseAdapter {
	protected Context context;
	protected LayoutInflater inflater;
	protected ArrayList<T> mylist = new ArrayList<T>();

	public MyBaseAdapter(Context context) {
		// 初始化context，inflate对象
		this.context = context;
		inflater = LayoutInflater.from(context);
	}

	// 查找数据
	public ArrayList<T> getAdapterDate() {
		return mylist;
	}

	// 清除数据
	public void clear() {
		mylist.clear();
	}

	// 添加数据到当前适配器集合
	public void addDataToAdapter(T e) {
		if (e != null) {
			mylist.add(e);
		}
	}
	public void addDataToAdapter(ArrayList<T> list) {
		if (list != null) {
			mylist.addAll(list);
		}
	}

	// 添加数据到当前适配器指定的位置
	public void addDataToAdapter(T e, int index) {
		if (e != null) {
			mylist.add(index, e);
		}
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return mylist.size();
	}

	@Override
	public T getItem(int position) {
		// TODO Auto-generated method stub
		return mylist.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		return null;
	}

}
