package com.feicui.news.model.entity;

public class ForgetPass {

	private int result;
	private String explain;
	@Override
	public String toString() {
		return "ForgetPass [result=" + result + ", explain=" + explain + "]";
	}
	public int getResult() {
		return result;
	}
	public void setResult(int result) {
		this.result = result;
	}
	public String getExplain() {
		return explain;
	}
	public void setExplain(String explain) {
		this.explain = explain;
	}
}
