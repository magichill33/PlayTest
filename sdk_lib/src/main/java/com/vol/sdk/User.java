package com.vol.sdk;


import com.vol.sdk.base.BaseInfo;

public class User extends BaseInfo {
	private String uid;
	private String hid;
	private String oemid;
	private String sid;
	private String portal;
	private String version;
	private String buildtime;

	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}

	public String getHid() {
		return hid;
	}

	public void setHid(String hid) {
		this.hid = hid;
	}

	public String getOemid() {
		return oemid;
	}

	public void setOemid(String oemid) {
		this.oemid = oemid;
	}

	public String getSid() {
		return sid;
	}

	public void setSid(String sid) {
		this.sid = sid;
	}

	public String getPortal() {
		return portal;
	}

	public void setPortal(String portal) {
		this.portal = portal;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public String getBuildtime() {
		return buildtime;
	}

	public void setBuildtime(String buildtime) {
		this.buildtime = buildtime;
	}
	
	
}
