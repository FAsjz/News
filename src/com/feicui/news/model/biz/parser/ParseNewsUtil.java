package com.feicui.news.model.biz.parser;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;

import com.feicui.news.common.ConfigUtil;
import com.feicui.news.common.HttpUtil;
import com.feicui.news.model.entity.News;

public class ParseNewsUtil {
	private static ArrayList<News> list;

	public static ArrayList<News> getNews() {
		try {
			list = new ArrayList<News>();
			String data = HttpUtil.httpGetString(ConfigUtil.PATH
					+ ConfigUtil.PATH1);
			// LogUtil.d("httpGetString", data);
			// ��������
			JSONObject json = new JSONObject(data);
			// String message = json.getString("message");
			// int status = json.getInt("status");
			JSONArray arr = json.getJSONArray("data");
			// ��ȡJSONArray��������
			for (int i = 0; i < arr.length(); i++) {
				JSONObject obj = arr.getJSONObject(i);
				String content = obj.getString("summary");
				String iconPath = obj.getString("icon");
				String stamp = obj.getString("stamp");
				String title = obj.getString("title");
				String link = obj.getString("link");
				int nid = obj.getInt("nid");
				int type = obj.getInt("type");
				// �����ݷŵ�News����
				News news = new News();
				news.summary = content;
				news.icon = iconPath;
				news.link = link;
				news.nid = nid;
				news.stamp = stamp;
				news.title = title;
				news.type = type;
				// LogUtil.d("News", news.toString());
				// ������ݵ�������
				for (int j = 0; j < 20; j++) {
					list.add(news);
				}
//				LogUtil.d("list111", list.toString());
			}
//			LogUtil.d("list333", list.toString());

		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}
}
