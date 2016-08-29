package com.feicui.news.model.biz.parser;

import java.lang.reflect.Type;
import java.util.ArrayList;
















import com.feicui.news.common.ConfigUtil;
import com.feicui.news.common.HttpUtil;
import com.feicui.news.common.LogUtil;
import com.feicui.news.model.entity.BaseEntity;
import com.feicui.news.model.entity.Comment;
import com.feicui.news.model.entity.CommentNumEntity;
import com.feicui.news.model.entity.ForgetPass;
import com.feicui.news.model.entity.LoginLog;
import com.feicui.news.model.entity.News;
import com.feicui.news.model.entity.Register;
import com.feicui.news.model.entity.SubBaseEntity;
import com.feicui.news.model.entity.SubGroup;
import com.feicui.news.model.entity.SubType;
import com.feicui.news.model.entity.UploadInfo;
import com.feicui.news.model.entity.UserInfo;
import com.feicui.news.model.entity.Version;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class GsonParse {
	/**
	 * 
	 * gson解析新闻列表
	 * @param path
	 * @return
	 * @throws Exception
	 */
	public static ArrayList<News> getNews() throws Exception{
		String s = HttpUtil.httpGetString(ConfigUtil.PATH + ConfigUtil.PATH1);
		Gson gson = new Gson();
		java.lang.reflect.Type type = new TypeToken<BaseEntity<ArrayList<News>>>(){}.getType();
		BaseEntity<ArrayList<News>> baseEntity = gson.fromJson(s, type);
		
		return baseEntity.getData();
		
	}
	public static ArrayList<News> getNews(String response) {
		Gson gson = new Gson();
		java.lang.reflect.Type type = new TypeToken<BaseEntity<ArrayList<News>>>(){}.getType();
		BaseEntity<ArrayList<News>> baseEntity = gson.fromJson(response, type);
		return baseEntity.getData();
		
	}
	
	public static ArrayList<SubType> getNewsType(String response){
		ArrayList<SubType> subType = new ArrayList<SubType>();
		Gson gson = new Gson();
		java.lang.reflect.Type type = new TypeToken<SubBaseEntity<ArrayList<SubGroup<ArrayList<SubType>>>>>(){}.getType();
		SubBaseEntity<ArrayList<SubGroup<ArrayList<SubType>>>> list = gson.fromJson(response, type);
		for (SubGroup<ArrayList<SubType>> group : list.getData()) {
			for (SubType types : group.getSubgrp()) {
				subType.add(types);
			}
		}
		LogUtil.d("subType", subType.toString());
		return subType;
	}
		public static UserInfo<LoginLog> getLoginLog(String response){
//			ArrayList<LoginLog> loginLog = new ArrayList<LoginLog>();
			Gson gson = new Gson();
			java.lang.reflect.Type type = new TypeToken<BaseEntity<UserInfo<LoginLog>>>(){}.getType();
			BaseEntity<UserInfo<LoginLog>> baseEntity = gson.fromJson(response, type);
			/*for (LoginLog group: list.getData().getLoginlog()) {
					loginLog.add(group);
			}
			LogUtil.d("loginLog", loginLog.toString());*/
			return baseEntity.getData();
		
	}
	public static ArrayList<Comment> getComment(String response) {
		Gson gson = new Gson();
		java.lang.reflect.Type type = new TypeToken<BaseEntity<ArrayList<Comment>>>(){}.getType();
		BaseEntity<ArrayList<Comment>> baseEntity = gson.fromJson(response, type);
		
		
		return baseEntity.getData();
		
	}
	public static int getCommentNum(String response) {
		Gson gson = new Gson();
		java.lang.reflect.Type type = new TypeToken<CommentNumEntity>(){}.getType();
		CommentNumEntity num = gson.fromJson(response, type);
		
		return num.getdata();
		
	}
	public static Object getRegist(String response) {
		Gson gson = new Gson();
		java.lang.reflect.Type type = new TypeToken<BaseEntity<Register>>(){}.getType();
		BaseEntity<Register> baseEntity = gson.fromJson(response, type);
		
		
		return baseEntity;
		
	}
	public static Object getForgetPass(String response) {
		Gson gson = new Gson();
		java.lang.reflect.Type type = new TypeToken<BaseEntity<ForgetPass>>(){}.getType();
		BaseEntity<ForgetPass> baseEntity = gson.fromJson(response, type);
		
		
		return baseEntity.getData();
		
	}
	public static UploadInfo getUploadInfo(String response) {
		Gson gson = new Gson();
		java.lang.reflect.Type type = new TypeToken<BaseEntity<UploadInfo>>(){}.getType();
		BaseEntity<UploadInfo> baseEntity = gson.fromJson(response, type);
		
		LogUtil.d("getUploadInfo", baseEntity.toString());
		
		
		return baseEntity.getData();
		
	}
	public static Version getUpdataInfo(String response) {
		Gson gson = new Gson();
		java.lang.reflect.Type type = new TypeToken<Version>(){}.getType();
		Version baseEntity = gson.fromJson(response, type);
		
		LogUtil.d("getUpdataInfo", baseEntity.toString());
		
		
		return baseEntity;
		
	}
	
	
}
