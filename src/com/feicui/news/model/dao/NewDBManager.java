package com.feicui.news.model.dao;

import java.util.ArrayList;

import com.feicui.news.model.entity.News;
import com.feicui.news.model.entity.SubType;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class NewDBManager {
/**
 * 数据管理类
 */
	private static NewDBManager dbManager;
	private DBOpenHelper helper;
	
	private NewDBManager(Context context){
		helper = new DBOpenHelper(context);
	}
	public static NewDBManager getNewDBManager(Context context){
		if (dbManager == null) {
			synchronized (NewDBManager.class) {
				if (dbManager == null) {
					dbManager = new NewDBManager(context);
				}
			}
		}
		
		return dbManager;
		
	}
	//新闻收藏
	public boolean saveLoveNews(News news){
		SQLiteDatabase db = helper.getWritableDatabase();
		Cursor cursor = db.rawQuery("select * from lovenews where nid=" + news.nid, null);
		if (cursor.moveToFirst()) {
			cursor.close();
			return false;
		}
		cursor.close();
		ContentValues value = new ContentValues();
		value.put("nid", news.nid);
		value.put("title", news.title);
		value.put("content", news.summary);
		value.put("icon", news.icon);
		value.put("link", news.link);
		value.put("type", news.type);
		value.put("stamp", news.stamp);
		db.insert("lovenews", null, value);
		db.close();
		return true;
		
	}
	//获取收藏的新闻列表
	public ArrayList<News> queryLoveNews(){
		ArrayList<News> list = new ArrayList<News>();
		SQLiteDatabase db = helper.getWritableDatabase();
		//order by _id desc
		Cursor cursor = db.rawQuery("select * from lovenews", null);
		if (cursor.moveToFirst()) {
			do {
				News news = new News();
				news.nid = cursor.getInt(cursor.getColumnIndex("nid"));
				news.title = cursor.getString(cursor.getColumnIndex("title"));
				news.icon = cursor.getString(cursor.getColumnIndex("icon"));
				news.link = cursor.getString(cursor.getColumnIndex("link"));
				news.summary = cursor.getString(cursor.getColumnIndex("content"));
				news.stamp = cursor.getString(cursor.getColumnIndex("stamp"));
				news.type = cursor.getInt(cursor.getColumnIndex("type"));
				list.add(news);	
			} while (cursor.moveToNext());
			db.close();
			cursor.close();
		}
		
		
		
		return list;
		
	}
	
	
	
	//news添加数据
	public void addNews(ArrayList<News> list){
		SQLiteDatabase db = helper.getWritableDatabase();
		for (News news : list) {
			ContentValues values = new ContentValues();
			values.put("nid", news.nid);
			values.put("title", news.title);
			values.put("content", news.summary);
			values.put("icon", news.icon);
			values.put("link", news.link);
			values.put("type", news.type);
			values.put("stamp", news.stamp);
			db.insert("news", null, values);
		}
		db.close();
		
	}
	//newstype添加数据
	public void addNewsType(ArrayList<SubType> list){
		SQLiteDatabase db = helper.getWritableDatabase();
		for (SubType newstype : list) {
			ContentValues values = new ContentValues();
			values.put("subid", newstype.getSubid());
			values.put("subgroup", newstype.getSubgroup());
			db.insert("newstype", null, values);
		}
		db.close();
		
	}
/*	public void addNews(News news){
		SQLiteDatabase db = helper.getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put("nid", news.nid);
		values.put("title", news.title);
		values.put("content", news.content);
		values.put("icon", news.iconPath);
		values.put("link", news.link);
		values.put("type", news.type);
		values.put("stamp", news.stamp);
		db.insert("news", null, values);
		db.close();
		
	}
*/	//获取news数量
	public long getCount(){
		SQLiteDatabase db = helper.getReadableDatabase();
		Cursor cur = db.rawQuery("select count (*) from news", null);
		long l = 0;
		if(cur.moveToFirst()){
			l = cur.getLong(0);
		}
		cur.close();
		db.close();
		return l;
	}
	//获取newstype数量
	public long getNewsTypeCount(){
		SQLiteDatabase db = helper.getReadableDatabase();
		Cursor cur = db.rawQuery("select count (*) from newstype", null);
		long l = 0;
		if(cur.moveToFirst()){
			l = cur.getLong(0);
		}
		cur.close();
		db.close();
		return l;
	}
	//查询数据
	public ArrayList<News> queryNews(/*int count, int offset*/){
		ArrayList<News> list = new ArrayList<News>();
		SQLiteDatabase db = helper.getWritableDatabase();
//		String sql = "select * from news order by _id desc limit" + count +"offset" +offset;
		String sql = "select * from news ";
		Cursor cursor = db.rawQuery(sql, null);
		if (cursor.moveToFirst()) {
			do {
				News news = new News();
				news.nid = cursor.getInt(cursor.getColumnIndex("nid"));
				news.title = cursor.getString(cursor.getColumnIndex("title"));
				news.icon = cursor.getString(cursor.getColumnIndex("icon"));
				news.link = cursor.getString(cursor.getColumnIndex("link"));
				news.summary = cursor.getString(cursor.getColumnIndex("content"));
				news.stamp = cursor.getString(cursor.getColumnIndex("stamp"));
				news.type = cursor.getInt(cursor.getColumnIndex("type"));
				list.add(news);
			}while (cursor.moveToNext());
			cursor.close();
			db.close();
		}
		return list;
		
	}
	//newsType查询数据
	public ArrayList<SubType> queryNewsType(/*int count, int offset*/){
		ArrayList<SubType> list = new ArrayList<SubType>();
		SQLiteDatabase db = helper.getWritableDatabase();
//		String sql = "select * from news order by _id desc limit" + count +"offset" +offset;
		String sql = "select * from newstype ";
		Cursor cursor = db.rawQuery(sql, null);
		if (cursor.moveToFirst()) {
			do {
				SubType newstype = new SubType();
				newstype.setSubid(cursor.getInt(cursor.getColumnIndex("subid")));
				newstype.setSubgroup(cursor.getString(cursor.getColumnIndex("subgroup")));
				
				list.add(newstype);
			}while (cursor.moveToNext());
			cursor.close();
			db.close();
		}
		return list;
		
	}

	
}
