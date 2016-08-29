package com.feicui.news.model.entity;

public class CommentNumEntity {

	private String message;
	private int data;
	private int status;
	@Override
	public String toString() {
		return "CommentNumEntity [message=" + message + ", data=" + data
				+ ", status=" + status + "]";
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public int getdata() {
		return data;
	}
	public void setdata(int data) {
		this.data = data;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
}
