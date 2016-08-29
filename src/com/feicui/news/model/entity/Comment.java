package com.feicui.news.model.entity;

public class Comment {

	private int cid ;//��cid��:���۱��
	public Comment() {
		super();
	}
	public Comment(int cid, String uid, String portrait, String stamp,
			String content) {
		super();
		this.cid = cid;
		this.uid = uid;
		this.portrait = portrait;
		this.stamp = stamp;
		this.content = content;
	}
	@Override
	public String toString() {
		return "Comment [cid=" + cid + ", uid=" + uid + ", portrait="
				+ portrait + ", stamp=" + stamp + ", content=" + content + "]";
	}
	public int getCid() {
		return cid;
	}
	public void setCid(int cid) {
		this.cid = cid;
	}
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
	public String getStamp() {
		return stamp;
	}
	public void setStamp(String stamp) {
		this.stamp = stamp;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	private String uid;//��uid��:����������
	private String portrait;//��portrait��:�û�ͷ������
	private String stamp;//��stamp��:����ʱ��
	private String content;//��content":��������
}
