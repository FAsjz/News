package com.feicui.news.model.entity;

import java.io.Serializable;

public class News implements Serializable {

	public String summary; 
	public String icon;
	public String stamp; 
	public String title;
	public int nid;
	public String link;
	public int type;
	@Override
	public String toString() {
		return "News [summary=" + summary + ", icon=" + icon + ", stamp="
				+ stamp + ", title=" + title + ", nid=" + nid + ", link="
				+ link + ", type=" + type + "]";
	}
	
	
	
}
