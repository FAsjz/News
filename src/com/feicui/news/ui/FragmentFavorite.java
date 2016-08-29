package com.feicui.news.ui;

import java.util.ArrayList;

import com.feicui.news.R;
import com.feicui.news.ShowActivity;
import com.feicui.news.model.dao.NewDBManager;
import com.feicui.news.model.entity.News;
import com.feicui.news.ui.adapter.NewsAdapter;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

public class FragmentFavorite extends Fragment {
	private NewsAdapter adapter;
	private ListView lv;
	private View v;
	private NewDBManager dbManager;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		v = inflater.inflate(R.layout.fragment_favorite, null);
		lv = (ListView) v.findViewById(R.id.fragment_favorite_lv);
		adapter = new NewsAdapter(getActivity());
		dbManager = NewDBManager.getNewDBManager(getActivity());
		lv.setAdapter(adapter);
		loadLoveNews();
		lv.setOnItemClickListener(itemListener);
		return v;
	}
	
	private OnItemClickListener itemListener = new OnItemClickListener(){

		@Override
		public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
				long arg3) {
			News news = (News) arg0.getItemAtPosition(arg2);
			Intent it = new Intent(getActivity(), ShowActivity.class);
			it.putExtra("news", news);
			getActivity().startActivity(it);
		}
		
	};
	//从数据库加载保存的新闻
	public void loadLoveNews(){
		ArrayList<News> loveNews = dbManager.queryLoveNews();
		adapter.clear();
		adapter.addDataToAdapter(loveNews);
		adapter.notifyDataSetChanged();
	}
	
}
