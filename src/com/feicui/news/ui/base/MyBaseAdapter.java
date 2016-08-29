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
		// ��ʼ��context��inflate����
		this.context = context;
		inflater = LayoutInflater.from(context);
	}

	// ��������
	public ArrayList<T> getAdapterDate() {
		return mylist;
	}

	// �������
	public void clear() {
		mylist.clear();
	}

	// ������ݵ���ǰ����������
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

	// ������ݵ���ǰ������ָ����λ��
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
