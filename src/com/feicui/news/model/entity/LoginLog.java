package com.feicui.news.model.entity;

public class LoginLog {
	
	
    private String time;//��¼ʱ��
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
	private String address;//�����г�����
	private int device;//��½���豸,(0Ϊ�ֻ��ͻ���,1ΪPC��ҳ��)
}
