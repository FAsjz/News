package com.feicui.news.model.entity;

public class SubGroup<T> {

	private T subgrp;
	private int gid;
	private String group;
	public T getSubgrp() {
		return subgrp;
	}
	public void setSubgrp(T subgrp) {
		this.subgrp = subgrp;
	}
	public int getGid() {
		return gid;
	}
	public void setGid(int gid) {
		this.gid = gid;
	}
	public String getGroup() {
		return group;
	}
	public void setGroup(String group) {
		this.group = group;
	}
	
}
