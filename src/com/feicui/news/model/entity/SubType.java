package com.feicui.news.model.entity;

public class SubType {

	private String subgroup;
	private int subid;
	@Override
	public String toString() {
		return "SubType [subgroup=" + subgroup + ", subid=" + subid + "]";
	}
	public String getSubgroup() {
		return subgroup;
	}
	public void setSubgroup(String subgroup) {
		this.subgroup = subgroup;
	}
	public int getSubid() {
		return subid;
	}
	public void setSubid(int subid) {
		this.subid = subid;
	}
	
}
