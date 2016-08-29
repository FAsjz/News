package com.feicui.news.model.dao;

import com.feicui.news.common.LogUtil;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class DBOpenHelper extends SQLiteOpenHelper {

	public DBOpenHelper(Context context) {
		super(context, "newsDb.db", null, 1);
		LogUtil.d("DBOpenHelper","DBOpenHelper");
		// TODO Auto-generated constructor stub
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL("create table news (_id integer primary key autoincrement,"
				+ "nid integer,title text,content text,icon text,link text,type integer,stamp text)");
		db.execSQL("create table lovenews (_id integer primary key autoincrement,"
				+ "nid integer,title text,content text,icon text,link text,type integer,stamp text)");
		db.execSQL("create table newstype (_id integer primary key autoincrement,subgroup text,subid Integer)");

	}

	@Override
	public void onUpgrade(SQLiteDatabase arg0, int arg1, int arg2) {
		// TODO Auto-generated method stub

	}

}
