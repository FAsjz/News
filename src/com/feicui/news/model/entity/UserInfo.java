package com.feicui.news.model.entity;

import java.util.ArrayList;

public class UserInfo<T> {

	
	private String uid;//用户名
	private String portrait;//用户图标
	private int integration;//用户积分票总数
	private int comnum;//评论总数
	private ArrayList<T> loginlog;//登录日志
	public String getUid() {
		return uid;
	}
	public void setUid(String uid) {
		this.uid = uid;
	}
	public String getPortrait() {
		return portrait;
	}
	public void setPortrait(String portrait) {
		this.portrait = portrait;
	}
	public int getIntegration() {
		return integration;
	}
	public void setIntegration(int integration) {
		this.integration = integration;
	}
	public int getComnum() {
		return comnum;
	}
	public void setComnum(int comnum) {
		this.comnum = comnum;
	}
	public ArrayList<T> getLoginlog() {
		return loginlog;
	}
	public void setLoginlog(ArrayList<T> loginlog) {
		this.loginlog = loginlog;
	}
	@Override
	public String toString() {
		return "UserInfo [uid=" + uid + ", portrait=" + portrait
				+ ", integration=" + integration + ", comnum=" + comnum
				+ ", loginlog=" + loginlog + "]";
	}

}
