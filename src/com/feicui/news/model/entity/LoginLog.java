package com.feicui.news.model.entity;

public class LoginLog {
	
	
    private String time;//登录时间
	@Override
	public String toString() {
		return "LoginLog [time=" + time + ", address=" + address + ", device="
				+ device + "]";
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public int getDevice() {
		return device;
	}
	public void setDevice(int device) {
		this.device = device;
	}
	private String address;//北京市朝阳区
	private int device;//登陆的设备,(0为手机客户端,1为PC网页端)
}
