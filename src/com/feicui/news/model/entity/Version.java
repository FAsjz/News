package com.feicui.news.model.entity;

/**
 * ���ڿ��ư汾����
 * 
 * @author
 */
public class Version {
	@Override
	public String toString() {
		return "Version [link=" + link + ", packageName=" + packageName
				+ ", md5=" + md5 + ", version=" + version + "]";
	}
	public String getLink() {
		return link;
	}
	public void setLink(String link) {
		this.link = link;
	}
	public String getPackageName() {
		return packageName;
	}
	public void setPackageName(String packageName) {
		this.packageName = packageName;
	}
	public String getMd5() {
		return md5;
	}
	public void setMd5(String md5) {
		this.md5 = md5;
	}
	public String getVersion() {
		return version;
	}
	public void setVersion(String version) {
		this.version = version;
	}
	/**
	 * ����������ص�ַ
	 */
	private String link;
	/**
	 * Ӧ�ð��������ڶ���ȷ��
	 */
	private String packageName;
	/**
	 * md5 У�飬�жϰ�װ���Ƿ���
	 */
	private String md5;
	/**
	 * ��ǰ���°汾�İ汾����
	 */
	private String version;

}
