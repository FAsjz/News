package com.feicui.news.model.entity;

/**
 * 用于控制版本更新
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
	 * 更新软件下载地址
	 */
	private String link;
	/**
	 * 应用包名，用于二次确认
	 */
	private String packageName;
	/**
	 * md5 校验，判断安装包是否损坏
	 */
	private String md5;
	/**
	 * 当前最新版本的版本号码
	 */
	private String version;

}
