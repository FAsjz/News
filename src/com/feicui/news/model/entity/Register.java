package com.feicui.news.model.entity;

public class Register {
	
	@Override
	public String toString() {
		return "Register [result=" + result + ", token=" + token + ", explain="
				+ explain + "]";
	}
	private int result;//状态码
	private String token;//用户令牌
	private String explain;//注册是否成功的文字
	public Register(int result, String token, String explain) {
		super();
		this.result = result;
		this.token = token;
		this.explain = explain;
	}
	public int getResult() {
		return result;
	}
	public void setResult(int result) {
		this.result = result;
	}
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	public String getExplain() {
		return explain;
	}
	public void setExplain(String explain) {
		this.explain = explain;
	}
	public Register() {
		super();
	}
	
}
