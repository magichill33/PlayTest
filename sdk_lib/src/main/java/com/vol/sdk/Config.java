package com.vol.sdk;


import android.content.Context;

import com.vol.sdk.utils.LogUtil;
import com.vol.sdk.utils.PropertiesUtil;

public class Config {
	private static Config instance = new Config();

	private Config() {
	}

	public static Config GetInstance() {
		return instance;
	}

	public enum OemType {
		Standard;

		public static OemType getOemType(String str) {
			if ("".equals(str)) {
				str = "Standard";
			}
			OemType type = null;
			try {
				type = valueOf(str);
			} catch (Exception e) {
				str = "Standard";
				LogUtil.d("Config OemType getOemType Error:\n" + e.getMessage());
			}
			return type;
		}
	}

	public void init(Context context) {
		PropertiesUtil propertiesUtil = new PropertiesUtil(context, "config.properties");
		setOemType(propertiesUtil.getProperty("oemType", ""));
		setVooleVersion(propertiesUtil.getProperty("vooleVersion", ""));
		setInterfaceVersion(propertiesUtil.getProperty("interfaceVersion", ""));
		setAuthPort(propertiesUtil.getProperty("authport", ""));
		setAuthFileName(propertiesUtil.getProperty("authfilename", ""));
		setProxyPort(propertiesUtil.getProperty("proxyport", ""));
		setProxyExitPort(propertiesUtil.getProperty("proxyexitport", ""));
		setProxyFileName(propertiesUtil.getProperty("proxyfilename", ""));
		setQq(propertiesUtil.getProperty("qq", ""));
		setTerminalReportUrl(propertiesUtil.getProperty("reportUrl", ""));
		setAppid(propertiesUtil.getProperty("appid", ""));
		setApkPacks(propertiesUtil.getProperty("apkPack", ""));
		//setDetailAction(propertiesUtil.getProperty("detail_action", ""));
		setDownloadUrl(propertiesUtil.getProperty("downloadUrl", ""));
		setCloseLog(propertiesUtil.getProperty("closeLog", ""));
		setIsShowTimer(propertiesUtil.getProperty("isShowTimer", ""));
		setBootStartP2P(propertiesUtil.getProperty("bootStartP2P", ""));
		String isScan = propertiesUtil.getProperty("isScan", "false");
		setScan(Boolean.parseBoolean(isScan));
		setChannelType(propertiesUtil.getProperty("channelType", "xml"));
	}

	private String oemType = null;
	private String vooleVersion = null;
	private String interfaceVersion = null;
	private String authPort = null;
	private String authFileName = null;
	private String proxyPort = null;
	private String proxyExitPort = null;
	private String proxyFileName = null;
	private String qq = null;
	private String terminalReportUrl = null;
	private String appid = null;
	private String apkPacks = null;
	//private String detailAction = null;
	private String downloadUrl = null;
	private String closeLog = null;
	private String isShowTimer = null;
	private String bootStartP2P = null;
	private boolean isScan = false; //终端上报时是否进行扫描
	private String channelType = "xml"; //片单类型 xml或者json

	public String getCloseLog() {
		return closeLog;
	}

	public void setCloseLog(String closeLog) {
		this.closeLog = closeLog;
	}

	public String getOemType() {
		return oemType;
	}

	public void setOemType(String oemType) {
		this.oemType = oemType;
	}

	public String getVooleVersion() {
		return vooleVersion;
	}

	public void setVooleVersion(String vooleVersion) {
		this.vooleVersion = vooleVersion;
	}

	public String getInterfaceVersion() {
		return interfaceVersion;
	}

	public void setInterfaceVersion(String interfaceVersion) {
		this.interfaceVersion = interfaceVersion;
	}

	public String getAuthPort() {
		return authPort;
	}

	public void setAuthPort(String authPort) {
		this.authPort = authPort;
	}

	public String getAuthFileName() {
		return authFileName;
	}

	public void setAuthFileName(String authFileName) {
		this.authFileName = authFileName;
	}

	public String getProxyPort() {
		return proxyPort;
	}

	public void setProxyPort(String proxyPort) {
		this.proxyPort = proxyPort;
	}

	public String getProxyExitPort() {
		return proxyExitPort;
	}

	public void setProxyExitPort(String proxyExitPort) {
		this.proxyExitPort = proxyExitPort;
	}

	public String getProxyFileName() {
		return proxyFileName;
	}

	public void setProxyFileName(String proxyFileName) {
		this.proxyFileName = proxyFileName;
	}

	public String getQq() {
		return qq;
	}

	public void setQq(String qq) {
		this.qq = qq;
	}

	public String getTerminalReportUrl() {
		return terminalReportUrl;
	}

	public void setTerminalReportUrl(String terminalReportUrl) {
		this.terminalReportUrl = terminalReportUrl;
	}

	public String getAppid() {
		return appid;
	}

	public void setAppid(String appid) {
		this.appid = appid;
	}

	public String getApkPacks() {
		return apkPacks;
	}

	public void setApkPacks(String apkPacks) {
		this.apkPacks = apkPacks;
	}

/*	public String getDetailAction() {
		return detailAction;
	}

	public void setDetailAction(String detailAction) {
		this.detailAction = detailAction;
	}*/

	public String getDownloadUrl() {
		return downloadUrl;
	}

	public void setDownloadUrl(String downloadUrl) {
		this.downloadUrl = downloadUrl;
	}

	public String getIsShowTimer() {
		return isShowTimer;
	}

	public void setIsShowTimer(String isShowTimer) {
		this.isShowTimer = isShowTimer;
	}

	public String getBootStartP2P() {
		return bootStartP2P;
	}

	public void setBootStartP2P(String bootStartP2P) {
		this.bootStartP2P = bootStartP2P;
	}

	public boolean isScan() {
		return isScan;
	}

	public void setScan(boolean isScan) {
		this.isScan = isScan;
	}

	public String getChannelType() {
		return channelType;
	}

	public void setChannelType(String channelType) {
		this.channelType = channelType;
	}
	
}
